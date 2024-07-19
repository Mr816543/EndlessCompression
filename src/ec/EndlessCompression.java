package ec;

import arc.Events;
import arc.util.Log;
import ec.Blocks.ECGenericCrafters;
import ec.Blocks.ECTurrets;
import ec.Blocks.ECUnitFactorys;
import ec.content.*;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.mod.Mod;

public class EndlessCompression extends Mod {

    public EndlessCompression() {
        Log.info("Loaded ExampleJavaMod constructor.");

        //listen for game load event
        //Core.settings.remove("Compress-other-Mods");
        Events.on(EventType.ClientLoadEvent.class, e -> Vars.ui.settings.game.checkPref("Compress-other-Mods", false));
    }
    public void init(){
    };

    @Override
    public void loadContent() {
        Log.info("Loading some example content.");


        try {
            ECItems.load();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        try {
            ECLiquids.load();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            ECUnitTypes.load();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ECUnitFactorys.load();
        ECBlocks.load();
        //ECPlanets.load();
        //ECTechTree.load();
        try {
            ECGenericCrafters.load();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        ECTurrets.load();


    }

}
