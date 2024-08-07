package ec.cType;

import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.LaunchPayload;
import mindustry.type.ItemStack;
import mindustry.world.blocks.campaign.LaunchPad;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.state;

public class ECLaunchPad extends LaunchPad {
    public int minLaunchCapacity = 0;

    public ECLaunchPad(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(new Stat("minLaunchCapacity", StatCat.items),minLaunchCapacity, StatUnit.items);
    }

    public class ECLaunchPadBuild extends LaunchPadBuild {
        @Override
        public void updateTile() {

            if (minLaunchCapacity >= itemCapacity) minLaunchCapacity = itemCapacity;

            if (!state.isCampaign()) return;

            //increment launchCounter then launch when full and base conditions are met
            if ((launchCounter += edelta()) >= launchTime && items.total() >= minLaunchCapacity) {
                //if there are item requirements, use those.
                consume();
                launchSound.at(x, y);
                LaunchPayload entity = LaunchPayload.create();
                items.each((item, amount) -> entity.stacks.add(new ItemStack(item, amount)));
                entity.set(this);
                entity.lifetime(120f);
                entity.team(team);
                entity.add();
                Fx.launchPod.at(this);
                items.clear();
                Effect.shake(3f, 3f, this);
                launchCounter = 0f;
            }
        }
    }
}
