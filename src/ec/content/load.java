package ec.content;

import arc.graphics.Color;
import arc.struct.Seq;
import ec.Blocks.ECWalls;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.Env;
import multicraft.IOEntry;
import multicraft.MultiCrafter;
import multicraft.Recipe;

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

    public static void itemCompressor(String name0,String material){

        float makeTime = 120f;
        new MultiCrafter(name0+"Compressor"){{
            requirements(Category.crafting, with(Vars.content.item(material), 30));
            size = 2;
            hasPower = false;
            hasLiquids = false;
            itemCapacity = 18;
            craftEffect = Fx.pulverizeMedium;
            alwaysUnlocked = true;

            resolvedRecipes = Seq.with();
        }};
        for (int i = 1; i < 10;i++){
            int num = i;
            int num0 = num-1;
            if (num==1) {
                ((MultiCrafter) Vars.content.block("ec-" + name0 + "Compressor")).resolvedRecipes.add(new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                Vars.content.item(material), 9));
                    }};
                    output = new IOEntry() {{

                        items = Seq.with(ItemStack.with(
                                Vars.content.item("ec-" + name0 + num), 1));
                    }};
                    craftTime = makeTime;
                }});
            }else {
                ((MultiCrafter) Vars.content.block("ec-" + name0 + "Compressor")).resolvedRecipes.add(new Recipe() {{
                            input = new IOEntry() {{
                                items = Seq.with(ItemStack.with(
                                        Vars.content.item("ec-"+name0+num0), 9));}};
                            output = new IOEntry() {{
                                items = Seq.with(ItemStack.with(
                                        Vars.content.item("ec-"+name0+num), 1));}};
                            craftTime = makeTime;
                        }});

            }


        };

    };

    public static void liquidCompressor(String name0,String material){

        float makeTime = 120f;
        new MultiCrafter(name0+"Compressor"){{
            requirements(Category.crafting, with(Vars.content.item(material), 30));
            size = 2;
            hasPower = false;
            hasLiquids = true;
            liquidCapacity = 18*60;
            craftEffect = Fx.pulverizeMedium;
            alwaysUnlocked = true;

            resolvedRecipes = Seq.with();

        }};

        for (int i = 1; i < 10;i++){
            int num = i;
            int num0 = num-1;
            if (num==1) {
                ((MultiCrafter) Vars.content.block("ec-" + name0 + "Compressor")).resolvedRecipes.add(new Recipe() {{
                    input = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                Vars.content.liquid(name0), 9));
                    }};
                    output = new IOEntry() {{

                        fluids = Seq.with(LiquidStack.with(
                                Vars.content.liquid("ec-" + name0 + num), 1));
                    }};
                    craftTime = makeTime;
                }});
            }else {
                ((MultiCrafter) Vars.content.block("ec-" + name0 + "Compressor")).resolvedRecipes.add(new Recipe() {{
                    input = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                Vars.content.liquid("ec-"+name0+num0), 9));}};
                    output = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                Vars.content.liquid("ec-"+name0+num), 1));}};
                    craftTime = makeTime;
                }});

            }


        };

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

    public static void drill(String name,String material,int drilltime,int num){

        int drillBase = 3;
        new Drill(name+num){{
            hardnessDrillMultiplier = (float) (50f/Math.pow(drillBase,num));
            size = 2;
            researchCost = with(Vars.content.item("ec-"+material+num), 10);
            requirements(Category.production, with(Vars.content.item("ec-"+material+num), 12));
            itemCapacity = (int) (10*Math.pow(drillBase,num));
            drillTime = (float) (drilltime/Math.pow(drillBase,num));
            tier = 2;
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


}
