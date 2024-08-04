package ec.cType;

import arc.math.Mathf;
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
            if (timer(timerDump, Math.min(dumpTime,1))) {
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

            int Effects = 0 ;
            while (dominantItems > 0 && progress >= delay && items.total() < itemCapacity) {
                offload(dominantItem);
                progress -= delay;
                if (wasVisible && Mathf.chanceDelta(updateEffectChance * warmup)&&Effects < 2) {
                    Effects++;
                    drillEffect.at(x + Mathf.range(drillEffectRnd), y + Mathf.range(drillEffectRnd), dominantItem.color);
                }
            }

        }
    }
}
