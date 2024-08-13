package ec.cType;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import arc.util.pooling.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.blocks.storage.Unloader;
import mindustry.world.meta.*;

import java.util.*;

import static mindustry.Vars.*;
import static mindustry.content.Blocks.unloader;

public class ECUnloader extends Block{
    public TextureRegion centerRegion;

    public float speed = 1f;

    public ECUnloader(String name){
        super(name);
        update = true;
        solid = true;
        health = 70;
        hasItems = true;
        configurable = true;
        saveConfig = true;
        itemCapacity = 0;
        noUpdateDisabled = true;
        clearOnDoubleTap = true;
        unloadable = false;

        config(Item.class, (ECUnloaderBuild tile, Item item) -> tile.sortItem = item);
        configClear((ECUnloaderBuild tile) -> tile.sortItem = null);
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.speed, 60f / speed, StatUnit.itemsSecond);
    }

    @Override
    public void drawPlanConfig(BuildPlan plan, Eachable<BuildPlan> list){
        drawPlanConfigCenter(plan, plan.config, "unloader-center");
    }

    @Override
    public void setBars(){
        super.setBars();
        removeBar("items");
    }

    public static class ContainerStat{
        Building building;
        float loadFactor;
        boolean canLoad;
        boolean canUnload;
        int lastUsed;

        @Override
        public String toString(){
            return "ContainerStat{" +
                    "building=" + building.block + "#" + building.id +
                    ", loadFactor=" + loadFactor +
                    ", canLoad=" + canLoad +
                    ", canUnload=" + canUnload +
                    ", lastUsed=" + lastUsed +
                    '}';
        }
    }

    public class ECUnloaderBuild extends Building{
        public float unloadTimer = 0f;
        public int rotations = 0;
        private final int itemsLength = content.items().size;
        public Item sortItem = null;
        public ContainerStat dumpingFrom, dumpingTo;
        public final Seq<ContainerStat> possibleBlocks = new Seq<>();

        protected final Comparator<ContainerStat> comparator = (x, y) -> {
            //sort so it gives priority for blocks that can only either receive or give (not both), and then by load, and then by last use
            //highest = unload from, lowest = unload to
            int unloadPriority = Boolean.compare(x.canUnload && !x.canLoad, y.canUnload && !y.canLoad); //priority to receive if it cannot give
            if(unloadPriority != 0) return unloadPriority;
            int loadPriority = Boolean.compare(x.canUnload || !x.canLoad, y.canUnload || !y.canLoad); //priority to give if it cannot receive
            if(loadPriority != 0) return loadPriority;
            int loadFactor = Float.compare(x.loadFactor, y.loadFactor);
            if(loadFactor != 0) return loadFactor;
            return Integer.compare(y.lastUsed, x.lastUsed); //inverted
        };

        private boolean isPossibleItem(Item item){
            boolean hasProvider = false,
                    hasReceiver = false,
                    isDistinct = false;

            for(int i = 0; i < possibleBlocks.size; i++){
                var pb = possibleBlocks.get(i);
                var other = pb.building;

                //set the stats of buildings in possibleBlocks while we are at it
                pb.canLoad = !(other.block instanceof StorageBlock) && other.acceptItem(this, item);
                pb.canUnload = other.canUnload() && other.items != null && other.items.has(item);

                //thats also handling framerate issues and slow conveyor belts, to avoid skipping items if nulloader
                if((hasProvider && pb.canLoad) || (hasReceiver && pb.canUnload)) isDistinct = true;
                hasProvider |= pb.canUnload;
                hasReceiver |= pb.canLoad;
            }
            return isDistinct;
        }

        @Override
        public void onProximityUpdate(){
            //filter all blocks in the proximity that will never be able to trade items

            super.onProximityUpdate();
            Pools.freeAll(possibleBlocks, true);
            possibleBlocks.clear();

            for(int i = 0; i < proximity.size; i++){
                var other = proximity.get(i);
                if(!other.interactable(team)) continue; //avoid blocks of the wrong team
                ContainerStat pb = Pools.obtain(ContainerStat.class, ContainerStat::new);

                //partial check
                boolean canLoad = !(other.block instanceof StorageBlock);
                boolean canUnload = other.canUnload() && other.items != null;

                if(canLoad || canUnload){ //avoid blocks that can neither give nor receive items
                    pb.building = other;
                    //TODO store the partial canLoad/canUnload?
                    possibleBlocks.add(pb);
                }
            }
        }

        float amount;
        @Override
        public void updateTile(){
            if(((unloadTimer += delta()) < speed) || (possibleBlocks.size < 2)) return;
            Item item = null;
            boolean any = false;

            if(sortItem != null){
                if(isPossibleItem(sortItem)) item = sortItem;
            }else{
                //为空载程序选择下一个项目
                //受到 nextIndex() 的启发，但适用于所有 "接近度"。 (块），而且功能更强大
                for(int i = 0; i < itemsLength; i++){
                    int total = (rotations + i + 1) % itemsLength;
                    Item possibleItem = content.item(total);

                    if(isPossibleItem(possibleItem)){
                        item = possibleItem;
                        break;
                    }
                }
            }

            if(item != null){
                rotations = item.id; //无效载荷的下一次轮换 //TODO maybe if(sortItem == null)

                for(int i = 0; i < possibleBlocks.size; i++){
                    var pb = possibleBlocks.get(i);
                    var other = pb.building;
                    pb.loadFactor = (other.getMaximumAccepted(item) == 0) || (other.items == null) ? 0 : other.items.get(item) / (float)other.getMaximumAccepted(item);
                    pb.lastUsed = (pb.lastUsed + 1) % Integer.MAX_VALUE; //increment the priority if not used
                }

                possibleBlocks.sort(comparator);

                dumpingTo = null;
                dumpingFrom = null;

                //选择建筑接收项目
                for(int i = 0; i < possibleBlocks.size; i++){
                    if(possibleBlocks.get(i).canLoad){
                        dumpingTo = possibleBlocks.get(i);
                        break;
                    }
                }

                //选择要从哪个建筑物中提取物品
                for(int i = possibleBlocks.size - 1; i >= 0; i--){
                    if(possibleBlocks.get(i).canUnload){
                        dumpingFrom = possibleBlocks.get(i);
                        break;
                    }
                }

                //以物换物
                if(dumpingFrom != null && dumpingTo != null && (dumpingFrom.loadFactor != dumpingTo.loadFactor || !dumpingFrom.canLoad)){

                    amount = unloadTimer / speed ;
                    amount = Math.min(amount,dumpingTo.building.getMaximumAccepted(item)-dumpingTo.building.items.get(item));
                    amount = Math.min(amount,dumpingFrom.building.items.get(item));

                    dumpingTo.building.handleStack(item,(int) amount,this);
                    dumpingFrom.building.removeStack(item, (int) amount);
                    dumpingTo.lastUsed = 0;
                    dumpingFrom.lastUsed = 0;
                    any = true;
                }
            }

            if(any){
                unloadTimer %= speed;
            }else{
                unloadTimer = Math.min(unloadTimer, speed);
            }
        }

        @Override
        public void draw(){
            super.draw();

            Draw.color(sortItem == null ? Color.clear : sortItem.color);
            Draw.rect(((Unloader)unloader).centerRegion, x, y);
            Draw.color();
        }

        @Override
        public void buildConfiguration(Table table){
            ItemSelection.buildTable(ECUnloader.this, table, content.items(), () -> sortItem, this::configure, selectionRows, selectionColumns);
        }

        @Override
        public Item config(){
            return sortItem;
        }

        @Override
        public byte version(){
            return 1;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.s(sortItem == null ? -1 : sortItem.id);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            int id = revision == 1 ? read.s() : read.b();
            sortItem = id == -1 ? null : content.item(id);
        }
    }
}