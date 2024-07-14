package ec.content;

import arc.graphics.Color;
import arc.struct.Seq;
import ec.AnyMtiCrafter;
import ec.Blocks.ECWalls;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.StackConveyor;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.consumers.ConsumePower;
import mindustry.world.draw.DrawLiquidTile;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;
import mindustry.world.meta.Env;
import multicraft.IOEntry;
import multicraft.MultiCrafter;
import multicraft.Recipe;

import static ec.Get.base;
import static mindustry.content.Items.copper;
import static mindustry.type.ItemStack.with;

public class load {

    public static Color itemcolor(String name0, int num , boolean deepen) {

        Color color2;

        if(deepen){
            color2 = Color.rgb(0,0,0);
        }else {
            color2 = Color.rgb(255,255,255);
        };

        Color color0 = Vars.content.item(name0).color.cpy();

        Color color1 = color0.cpy().lerp(color2,0.035f*num);

        return color1;

    };

    public static Color liquidcolor(String name0, int num , boolean deepen) {

        Color color2;

        if(deepen){
            color2 = Color.rgb(0,0,0);
        }else {
            color2 = Color.rgb(255,255,255);
        };

        Color color0 = Vars.content.liquid(name0).color.cpy();

        Color color1 = color0.cpy().lerp(color2,0.035f*num);

        return color1;

    };

    public static void itemCompressor(String name0,String name1){
        new AnyMtiCrafter(name0+"Compressor"){{
            requirements(Category.crafting, with(Vars.content.item("copper"), 30));
            size = 2;
            hasLiquids = false;
            itemCapacity = 18;
            craftEffect = Fx.pulverizeMedium;
            alwaysUnlocked = true;
            products.add(
                    new Formula(){{
                        consumeItem(Vars.content.item(name1),9);
                        outputItems = ItemStack.with(Vars.content.item("ec-"+name0+1),1);
                        craftTime = 60f;
                        craftEffect = Fx.pulverizeMedium;
                    }}
            );
            for (int i = 2 ;i < 10;i++){
                int num = i;
                int num0 = i - 1;
                products.add(
                        new Formula(){{
                            consumeItem(Vars.content.item("ec-"+name0+num0),9);
                            outputItems = ItemStack.with(Vars.content.item("ec-"+name0+num),1);
                            craftTime = 60f;
                            craftEffect = Fx.pulverizeMedium;
                        }}
                );
            };

        }};


    };

    public static void liquidCompressor(String name0){
        new AnyMtiCrafter(name0+"Compressor"){{
            requirements(Category.crafting, with(Vars.content.item("ec-"+"copper"+1), 60));
            size = 2;
            liquidCapacity = 18*60;
            alwaysUnlocked = true;
            products.add(
                    new Formula(){{
                        consumeLiquid(Vars.content.liquid(name0),9f);
                        outputLiquids = LiquidStack.with(Vars.content.liquid("ec-"+name0+1), 1f);
                        craftTime = 60f;
                        craftEffect = Fx.pulverizeMedium;
                    }}
            );
            for (int i = 2 ;i < 10;i++){
                int num = i;
                int num0 = i - 1;
                products.add(
                        new Formula(){{
                            consumeLiquid(Vars.content.liquid("ec-"+name0+num0),9f);
                            outputLiquids = LiquidStack.with(Vars.content.liquid("ec-"+name0+num),1f);
                            craftTime = 60f;
                            craftEffect = Fx.pulverizeMedium;
                        }}
                );
            };
            products.add(
                    new Formula(){{
                        consumeLiquid(Vars.content.liquid("ec-"+name0+1), 1f);
                        outputLiquids = LiquidStack.with(Vars.content.liquid(name0),9f);
                        craftTime = 60f;
                        craftEffect = Fx.pulverizeMedium;
                    }}
            );
            for (int i = 2 ;i < 10;i++){
                int num = i;
                int num0 = i - 1;
                products.add(
                        new Formula(){{
                            consumeLiquid(Vars.content.liquid("ec-"+name0+num),1f);
                            outputLiquids = LiquidStack.with(Vars.content.liquid("ec-"+name0+num0),9f);
                            craftTime = 60f;
                            craftEffect = Fx.pulverizeMedium;
                        }}
                );
            };

        }};

    };

    public static void conveyor(String name,String material,float v,int num){
        int health0 = 45;
        int conveyorBase = 4;
        new Conveyor(name+"conveyor"+num){{
            requirements(Category.distribution, with(Vars.content.item("ec-"+material+num), 1));
            researchCost = with(Vars.content.item("ec-"+material+num), 5);
            health = (int) (health0*Math.pow(conveyorBase,num));
            speed = (float) (v*Math.pow(conveyorBase,num)/140);
            displayedSpeed = (float) (v*Math.pow(conveyorBase,num));
            itemCapacity = (int) (10*Math.pow(conveyorBase,num));
            buildCostMultiplier = 2f;
        }};

    };

    public static void titaniumConveyor(int num){
        int health0 = 65;
        int conveyorBase = 4;
        new StackConveyor("titanium-conveyor"+num){{
            requirements(Category.distribution, with(Vars.content.item("ec-"+"copper"+num), 1, Vars.content.item("ec-"+"lead"+num), 1, Vars.content.item("ec-"+"titanium"+num), 1));
            health = (int) (health0*Math.pow(conveyorBase,num));
            speed = 4.8f/ 60f;
            itemCapacity = (int) ((11*Math.pow(conveyorBase,num))/4.8f);
        }};




    };

    public static void drill(String name,String material,int drilltime,int num){

        int drillBase = 3;
        new Drill(name+num){{
            hardnessDrillMultiplier = (float) (50f/Math.pow(drillBase,num));
            size = 2;
            researchCost = with(Vars.content.item("ec-"+material+num), 10);
            requirements(Category.production, with(Vars.content.item("ec-"+material+num), 12));
            itemCapacity = (int) (10*Math.pow(drillBase,num));
            drillTime = (float) (drilltime/Math.pow(drillBase,num));
            tier = 2+num;
            envEnabled ^= Env.space;

            consumeLiquid(Liquids.water, 0.05f).boost();
        }};

    };

    public static void wall(String name,String material,int health0,int num){
        int healthBase = 5;
        new Wall(name+"Wall"+num){{
            requirements(Category.defense, with(Vars.content.item("ec-"+material+num), 6));
            health = (int) (health0 * 4 * Math.pow(healthBase,num));
            researchCostMultiplier = 0.1f;
            envDisabled |= Env.scorching;
        }};

    };

    public static void multipress(String Item0, String Item1){

        new AnyMtiCrafter(Item0+"MultiPress"){{
            requirements(Category.crafting, with(Vars.content.item("ec-"+"copper"+1),100));
            size = 3;
            useBlockDrawer = false;
            maxList = 5;
            itemCapacity = ((int)Math.pow(9,9));
            hasItems = true;
            hasLiquids = true;
            hasPower = true;

            for (int i = 1 ;i < 10;i++){
                int num = i;
                products.add(
                    new Formula(){{
                        consumeItem(Vars.content.item(Item1), (int) Math.pow(9,num));
                        outputItems = ItemStack.with(Vars.content.item("ec-"+Item0+num),1);
                        int timeBase = num*num ;
                        craftTime = 60f*timeBase;
                        craftEffect = Fx.pulverizeMedium;
                        consumePower(108f/60);
                    }}
                );
            };

            for (int i = 1 ;i < 10;i++){
                int num = i;
                products.add(
                        new Formula(){{
                            consumeItem(Vars.content.item("ec-"+Item0+num),1);
                            outputItems = ItemStack.with(Vars.content.item(Item1), (int) Math.pow(9,num));
                            int timeBase = num*num ;
                            craftTime = 60f*timeBase;
                            craftEffect = Fx.pulverizeMedium;
                            consumePower(108f/60);
                        }}
                );
            };

        }};


    };

    public static void liquidmultipress(String liquid0){

        new AnyMtiCrafter(liquid0+"MultiPress"){{
            requirements(Category.crafting, with(Vars.content.item("ec-"+"copper"+1),100));
            size = 3;
            useBlockDrawer = false;
            maxList = 5;
            liquidCapacity = ((int)Math.pow(9,9));
            hasLiquids = true;
            hasPower = true;

            for (int i = 1 ;i < 10;i++){
                int num = i;
                products.add(
                        new Formula(){{
                            consumeLiquid(Vars.content.liquid(liquid0), (float) (12*Math.pow(9,num)/60));
                            outputLiquids = LiquidStack.with(Vars.content.liquid("ec-"+liquid0+num),12f/60);
                            int timeBase = num*num ;
                            craftTime = 10f*timeBase;
                            craftEffect = Fx.pulverizeMedium;
                            consumePower(108f/60);
                        }}
                );
            };
            for (int i = 1 ;i < 10;i++){
                int num = i;
                products.add(
                        new Formula(){{
                            consumeLiquid(Vars.content.liquid("ec-"+liquid0+num),12f/60);
                            outputLiquids = LiquidStack.with(Vars.content.liquid(liquid0), (float) (12*Math.pow(9,num)/60));
                            int timeBase = num*num ;
                            craftTime = 10f*timeBase;
                            craftEffect = Fx.pulverizeMedium;
                            consumePower(108f/60);
                        }}
                );
            };

        }};


    };


}
