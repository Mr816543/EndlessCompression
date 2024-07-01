package ec;

import arc.util.*;
import ec.content.ECBlocks;
import ec.content.ECItems;
import ec.content.ECPlanets;
import ec.content.ECTechTree;
import mindustry.mod.*;

public class EndlessCompression extends Mod{

    public EndlessCompression(){
        Log.info("Loaded ExampleJavaMod constructor.");

        //listen for game load event
        /*Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("icon.png");
                dialog.cont.add("无尽压缩能够让您可以压缩原版的物品并利用").row();
                //mod sprites are prefixed with the mod name (this mod is called 'example-java-mod' in its config)
                //dialog.cont.image(Core.atlas.find("example-java-mod-frog")).pad(20f).row();
                dialog.cont.button("我明白了", dialog::hide).size(100f, 50f);
                dialog.show();
            });


        });

         */
    }

    @Override
    public void loadContent(){
        Log.info("Loading some example content.");
        ECItems ECItems = new ECItems();
        ECItems.load();
        //ECBulletType ECBulletType = new ECBulletType();
        //ECBulletType.load();
        ECBlocks ECBlocks = new ECBlocks();
        ECBlocks.load();
        ECPlanets ECPlanets = new ECPlanets();
        ECPlanets.load();
        ECTechTree ECTechTree = new ECTechTree();
        ECTechTree.load();


    }

}
