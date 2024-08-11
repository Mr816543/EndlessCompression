package ec.cType;

import arc.math.Mathf;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.ItemBuffer;
import mindustry.world.Tile;
import mindustry.world.blocks.distribution.BufferedItemBridge;

import static mindustry.Vars.content;
import static mindustry.Vars.world;

public class ECBufferedItemBridge extends BufferedItemBridge {
    public float oldSpeed ;
    public float speedBase = 1;

    public ECBufferedItemBridge(String name) {
        super(name);
        oldSpeed = 11f/60;
        speedBase = 1;
    }

    public class ECBufferedItemBridgeBuild extends BufferedItemBridgeBuild {
        public int thisFrameItems = 0;
        public int noOutFrame = 0;
        public float amount;
        @Override
        public void updateTile(){
            if(timer(timerCheckMoved, 30f)){
                wasMoved = moved;
                moved = false;
            }

            //smooth out animation, so it doesn't stop/start immediately
            timeSpeed = Mathf.approachDelta(timeSpeed, wasMoved ? 1f : 0f, 1f / 60f);

            time += timeSpeed * delta();

            checkIncoming();

            Tile other = world.tile(link);
            if(!linkValid(tile, other)){
                for (int i = 0 ; i < items.total();i++){
                    doDump();
                }
                warmup = 0f;
            }else{
                var inc = ((ItemBridgeBuild)other.build).incoming;
                int pos = tile.pos();
                if(!inc.contains(pos)){
                    inc.add(pos);
                }

                warmup = Mathf.approachDelta(warmup, efficiency, 1f / 30f);

                amount += oldSpeed*speedBase;
                if (amount>=1) {
                    for (Item item : content.items()) {
                        if (this.items.get(item) > 0 && other.build.acceptItem(this, item)) {
                            int amount = (int) Math.min(this.amount, other.build.getMaximumAccepted(item) - other.build.items.get(item));
                            amount = Math.min(amount, this.items.get(item));
                            this.items.remove(item, amount);
                            other.build.items.add(item, amount);
                            this.amount -= amount;
                            thisFrameItems += 1;
                            noOutFrame = 0;
                            break;
                        }
                    }
                }
                if (amount>5) amount = 5;


                //updateTransport(other.build);

            }
        }

    }
}
