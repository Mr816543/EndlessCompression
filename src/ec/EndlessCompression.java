package ec;

import arc.Events;
import arc.util.Log;
import ec.Tools.GradualDisplayName;
import ec.content.*;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import mindustry.world.Block;

public class EndlessCompression extends Mod {

    public EndlessCompression() {
        //Vars.ui.settings.game.find("Compress-other-Mods").remove();
        Events.on(EventType.ClientLoadEvent.class, e -> Vars.ui.settings.game.checkPref("Compress-other-Mods", false));
    }

    public void init() {
        super.init();
    }


    @Override
    public void loadContent() {

        Log.info(Block.class.getClassLoader());

        GradualDisplayName.load();
        try {
            ECItems.load();
            ECLiquids.load();
            ECUnitTypes.load();
            ECBlocks.load();
            ECOnly.load();
            ECTest.load();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }
}
