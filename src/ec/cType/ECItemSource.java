package ec.cType;

import arc.*;
import arc.graphics.g2d.*;
import arc.scene.ui.layout.*;
import arc.struct.Seq;
import arc.util.*;
import arc.util.io.*;
import mindustry.Vars;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class ECItemSource extends Block{
    public int itemsPerSecond = 100;

    public ECItemSource(String name){
        super(name);
        hasItems = true;
        update = true;
        solid = true;
        group = BlockGroup.transportation;
        configurable = true;
        saveConfig = true;
        noUpdateDisabled = true;
        envEnabled = Env.any;
        clearOnDoubleTap = true;

        config(Item.class, (ItemSourceBuild tile, Item item) -> tile.outputItem = item);
        configClear((ItemSourceBuild tile) -> tile.outputItem = null);
    }

    @Override
    public void setBars(){
        super.setBars();
        removeBar("items");
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.output, itemsPerSecond, StatUnit.itemsSecond);
    }

    @Override
    protected TextureRegion[] icons(){
        return new TextureRegion[]{Core.atlas.find("source-bottom"), region};
    }

    @Override
    public void drawPlanConfig(BuildPlan plan, Eachable<BuildPlan> list){
        drawPlanConfigCenter(plan, plan.config, "center", true);
    }

    @Override
    public boolean outputsItems(){
        return true;
    }

    public class ItemSourceBuild extends Building{
        public float counter;
        public Item outputItem;

        @Override
        public void draw(){
            if(outputItem == null){
                Draw.rect("cross-full", x, y);
            }else{
                Draw.color(outputItem.color);
                Fill.square(x, y, tilesize/2f - 0.00001f);
                Draw.color();
            }

            super.draw();
        }
        float amount;

        @Override
        public void updateTile(){
            if(outputItem == null) return;

            counter += edelta();
            float limit = 60f / itemsPerSecond;

            if (counter >= limit){

                amount +=  itemsPerSecond / 60f;

                items.set(outputItem, (int) amount);
                dump(outputItem);
                items.set(outputItem, 0);
                amount -= (int) amount ;
                counter %= limit;

            }
        }
        //重写为一次多输出
        @Override
        public boolean dump(Item todump) {
            if (!(amount>=1)) amount = 1;


            if (this.block.hasItems && this.items.total() != 0 && this.proximity.size != 0 && (todump == null || this.items.has(todump))) {
                int dump = this.cdump;
                Seq<Item> allItems = Vars.content.items();
                int itemSize = allItems.size;
                Object[] itemArray = allItems.items;

                for(int i = 0; i < this.proximity.size; ++i) {
                    Building other = this.proximity.get((i + dump) % this.proximity.size);
                    if (todump == null) {
                        for(int ii = 0; ii < itemSize; ++ii) {
                            if (this.items.has(ii)) {
                                Item item = (Item)itemArray[ii];
                                if (other.acceptItem(this, item) && this.canDump(other, item)) {
                                    other.handleItem(this, item);
                                    this.items.remove(item, 1);
                                    this.incrementDump(this.proximity.size);
                                    return true;
                                }
                            }
                        }
                    } else if (other.acceptItem(this, todump) && this.canDump(other, todump)) {

                        amount = Math.min(amount,other.getMaximumAccepted(todump) - other.items.get(todump));
                        amount = Math.min(amount,this.items.get(todump));


                        other.handleStack(todump,(int) amount,this);
                        this.items.remove(todump, (int) amount);
                        this.incrementDump(this.proximity.size);
                        return true;
                    }

                    this.incrementDump(this.proximity.size);
                }

                return false;
            } else {
                return false;
            }
        }

        @Override
        public void buildConfiguration(Table table){
            ItemSelection.buildTable(ECItemSource.this, table, content.items(), () -> outputItem, this::configure, selectionRows, selectionColumns);
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return false;
        }

        @Override
        public Item config(){
            return outputItem;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.s(outputItem == null ? -1 : outputItem.id);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            outputItem = content.item(read.s());
        }
    }
}