package ec;

import arc.Events;
import ec.content.ECBlocks;
import ec.content.ECItems;
import ec.content.ECLiquids;
import ec.content.ECUnitTypes;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.mod.Mod;

public class EndlessCompression extends Mod {

    public EndlessCompression() {
        Events.on(EventType.ClientLoadEvent.class, e -> Vars.ui.settings.game.checkPref("Compress-other-Mods", false));
    }

    public void init() {
        super.init();
    }


    @Override
    public void loadContent() {

        try {
            ECItems.load();
            ECLiquids.load();
            ECUnitTypes.load();
            ECBlocks.load();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
