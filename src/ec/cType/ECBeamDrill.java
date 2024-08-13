package ec.cType;

import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.Tile;
import mindustry.world.blocks.production.BeamDrill;

public class ECBeamDrill extends BeamDrill {
    public ECBeamDrill(String name) {
        super(name);
    }
    public class ECBeamDrillBuild extends BeamDrillBuild{

        @Override
        public void updateTile(){

            if(lasers[0] == null) updateLasers();

            warmup = Mathf.approachDelta(warmup, Mathf.num(efficiency > 0), 1f / 60f);

            updateFacing();

            float multiplier = Mathf.lerp(1f, optionalBoostIntensity, optionalEfficiency);
            float drillTime = getDrillTime(lastItem);
            boostWarmup = Mathf.lerpDelta(boostWarmup, optionalEfficiency, 0.1f);
            lastDrillSpeed = (facingAmount * multiplier * timeScale) / drillTime;

            time += edelta() * multiplier;

            if(time >= drillTime){
                int amount = (int) (time / drillTime);
                for(Tile tile : facing){
                    Item drop = tile == null ? null : tile.wallDrop();
                    if(items.total() < itemCapacity && drop != null){
                        amount = Math.min(amount,itemCapacity-items.total());
                        items.add(drop, amount);
                        produced(drop);
                        dump(drop,amount);
                    }
                }
                time %= drillTime;
            }

            if(timer(timerDump, dumpTime)){
                dump();
            }
        }

        public boolean dump(Item todump,int amount) {
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
                                    amount = Math.min(amount,other.getMaximumAccepted(item) - other.items.get(item));
                                    amount = Math.min(amount,items.get(item));
                                    other.handleStack(item,amount,this);
                                    this.items.remove(item, amount);
                                    this.incrementDump(this.proximity.size);
                                    return true;
                                }
                            }
                        }
                    } else if (other.acceptItem(this, todump) && this.canDump(other, todump)) {
                        amount = Math.min(amount,other.getMaximumAccepted(todump) - other.items.get(todump));
                        amount = Math.min(amount,items.get(todump));
                        other.handleStack(todump,amount,this);
                        this.items.remove(todump, amount);
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
    }
}
