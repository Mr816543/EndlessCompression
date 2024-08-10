package ec.cType;


import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Eachable;
import arc.util.io.Reads;
import arc.util.io.Writes;
import arc.util.pooling.Pools;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.blocks.ItemSelection;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.blocks.storage.Unloader;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import java.util.Comparator;

import static mindustry.Vars.content;

public class ECUnloader extends Unloader {
    public ECUnloader(String name) {
        super(name);
    }
    @Override
    public void setStats(){
        super.setStats();
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

    public static class ContainerStat extends Unloader.ContainerStat {
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

    public class UnloaderBuild extends Unloader.UnloaderBuild {
        public float unloadTimer = 0f;
        public int rotations = 0;
        private final int itemsLength = content.items().size;
        public Item sortItem = null;
        public ECUnloader.ContainerStat dumpingFrom, dumpingTo;
        public final Seq<ECUnloader.ContainerStat> possibleBlocks = new Seq<>();

        protected final Comparator<ECUnloader.ContainerStat> comparator = (x, y) -> {
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
                ECUnloader.ContainerStat pb =  Pools.obtain(ECUnloader.ContainerStat.class, ECUnloader.ContainerStat::new);

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

        @Override
        public void updateTile(){
            if(((unloadTimer += delta()) < speed) || (possibleBlocks.size < 2)) return;
            Item item = null;
            boolean any = false;

            if(sortItem != null){
                if(isPossibleItem(sortItem)) item = sortItem;
            }else{
                //selects the next item for nulloaders
                //inspired of nextIndex() but for all "proximity" (possibleBlocks) at once, and also way more powerful
                for(int i = 0; i < itemsLength; i++){
                    int total = (rotations + i + 1) % itemsLength;
                    Item possibleItem = content.item(total);

                    if(isPossibleItem(possibleItem)){
                        item = possibleItem;
                        break;
                    }
                }
            }

            if (unloadTimer > speed) {

                if(item != null){
                    rotations = item.id; //next rotation for nulloaders

                    for(int i = 0; i < possibleBlocks.size; i++){
                        var pb = possibleBlocks.get(i);
                        var other = pb.building;
                        pb.loadFactor = (other.getMaximumAccepted(item) == 0) || (other.items == null) ? 0 : other.items.get(item) / (float)other.getMaximumAccepted(item);
                        pb.lastUsed = (pb.lastUsed + 1) % Integer.MAX_VALUE; //increment the priority if not used
                    }

                    possibleBlocks.sort(comparator);

                    dumpingTo = null;
                    dumpingFrom = null;

                    //choose the building to accept the item
                    for(int i = 0; i < possibleBlocks.size; i++){
                        if(possibleBlocks.get(i).canLoad){
                            dumpingTo = possibleBlocks.get(i);
                            break;
                        }
                    }

                    //choose the building to take the item from
                    for(int i = possibleBlocks.size - 1; i >= 0; i--){
                        if(possibleBlocks.get(i).canUnload){
                            dumpingFrom = possibleBlocks.get(i);
                            break;
                        }
                    }

                    //trade the items
                    if(dumpingFrom != null && dumpingTo != null && (dumpingFrom.loadFactor != dumpingTo.loadFactor || !dumpingFrom.canLoad)){
                        int amount = (int)(unloadTimer / speed);
                        for (int i = 0 ; i<amount;i++){
                            if (dumpingTo.building.acceptItem(this,item)){
                                int removeStack = dumpingFrom.building.removeStack(item, 1);
                                if (removeStack != 0 ) dumpingTo.building.handleItem(this, item);
                            }
                        }
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

        }

        @Override
        public void draw(){
            super.draw();

            Draw.color(sortItem == null ? Color.clear : sortItem.color);
            Draw.rect(centerRegion, x, y);
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
