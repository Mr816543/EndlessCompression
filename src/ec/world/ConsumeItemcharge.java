package ec.world;

import mindustry.gen.*;
import mindustry.world.consumers.ConsumeItemFilter;

public class ConsumeItemcharge extends ConsumeItemFilter {
    public float mincharge;

    public ConsumeItemcharge(float minFlammability){
        this.mincharge = minFlammability;
        filter = item -> item.charge >= this.mincharge;
    }

    public ConsumeItemcharge(){
        this(1f);
    }

    @Override
    public float efficiencyMultiplier(Building build){
        var item = getConsumed(build);
        return item == null ? 0f : item.charge;
    }
}
