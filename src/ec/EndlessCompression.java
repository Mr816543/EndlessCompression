package ec;

import arc.Events;
import arc.util.Log;
import ec.content.*;
import mindustry.Vars;
import mindustry.content.Blocks;
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
            ECOnly.load();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }
}
