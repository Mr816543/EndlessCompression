package ec.cType;

import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.blocks.sandbox.LiquidSource;

public class ECLiquidSource extends LiquidSource {
    public ECLiquidSource(String name) {
        super(name);
    }
    public class ECLiquidsSourceBuild extends LiquidSourceBuild{
        @Override
        public void dumpLiquid(Liquid liquid, float scaling, int outputDir) {
            int dump = this.cdump;
            if (!(this.liquids.get(liquid) <= 1.0E-4F)) {
                if (!Vars.net.client() && Vars.state.isCampaign() && this.team == Vars.state.rules.defaultTeam) {
                    liquid.unlock();
                }

                for(int i = 0; i < this.proximity.size; ++i) {
                    this.incrementDump(this.proximity.size);
                    Building other = (Building)this.proximity.get((i + dump) % this.proximity.size);
                    if (outputDir == -1 || (outputDir + this.rotation) % 4 == this.relativeTo(other)) {
                        other = other.getLiquidDestination(this, liquid);
                        if (other != null && other.block.hasLiquids && this.canDumpLiquid(other, liquid) && other.liquids != null) {

                            float amount = Math.max(this.block.liquidCapacity,other.block.liquidCapacity);
                            amount = Math.max(amount,other.block.liquidCapacity - other.liquids.get(liquid));
                            other.liquids.add(liquid,amount);

                        }
                    }
                }

            }
        }
    }
}
