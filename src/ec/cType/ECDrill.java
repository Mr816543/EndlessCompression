package ec.cType;

import arc.math.Mathf;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.blocks.production.Drill;

public class ECDrill extends Drill {
    public int grade;

    public ECDrill(String name, int grade) {
        super(name);
        this.grade = grade;
    }

    public class DrillBuild extends Drill.DrillBuild {
        @Override
        public void updateTile() {
            if (!(warmup >= 0)) warmup = 0;
            if (!(progress >= 0)) progress = 0;

            if (timer(timerDump, Math.min(dumpTime, 1))) {
                dump(dominantItem != null && items.has(dominantItem) ? dominantItem : null);
            }

            if (dominantItem == null) {
                return;
            }

            timeDrilled += warmup * delta();

            float delay = getDrillTime(dominantItem);

            if (items.total() < itemCapacity && dominantItems > 0 && efficiency > 0) {
                float speed = Mathf.lerp(1f, liquidBoostIntensity, optionalEfficiency) * efficiency;

                lastDrillSpeed = (speed * dominantItems * warmup) / delay;
                warmup = Mathf.approachDelta(warmup, speed, warmupSpeed);
                progress += delta() * dominantItems * speed * warmup;

                if (Mathf.chanceDelta(updateEffectChance * warmup))
                    updateEffect.at(x + Mathf.range(size * 2f), y + Mathf.range(size * 2f));
            } else {
                lastDrillSpeed = 0f;
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
                return;
            }

            if (dominantItems > 0 && progress >= delay && items.total() < itemCapacity) {
                int amount = (int) (progress / delay);
                offload(dominantItem, amount);
                progress -= amount * delay;
                if (wasVisible && Mathf.chanceDelta(updateEffectChance * warmup * amount)) {
                    drillEffect.at(x + Mathf.range(drillEffectRnd), y + Mathf.range(drillEffectRnd), dominantItem.color);
                }
            }

        }

        @Override
        public void offload(Item item) {
            this.produced(item, 1);
            int dump = this.cdump;

            for (int i = 0; i < this.proximity.size; ++i) {
                this.incrementDump(this.proximity.size);
                Building other = (Building) this.proximity.get((i + dump) % this.proximity.size);
                if (other.acceptItem(this, item) && this.canDump(other, item)) {
                    other.handleItem(this, item);
                    return;
                }
            }

            this.handleItem(this, item);
        }


        public void offload(Item item, int amount) {
            this.produced(item, amount);
            int dump = this.cdump;

            for (int i = 0; i < this.proximity.size; ++i) {
                this.incrementDump(this.proximity.size);
                Building other = this.proximity.get((i + dump) % this.proximity.size);


                if (other.items!=null&&other.acceptItem(this, item) && this.canDump(other, item)) {

                    amount = Math.min(amount, other.getMaximumAccepted(item) - other.items.get(item));

                    other.handleStack(item, amount, this);
                    return;


                }
            }
            amount = Math.min(amount, this.getMaximumAccepted(item) - this.items.get(item));
            this.handleStack(item, amount, this);
        }


    }
}
