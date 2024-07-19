package ec.content;


import arc.Core;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Door;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.blocks.distribution.ArmoredConveyor;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.StackConveyor;
import mindustry.world.blocks.liquid.ArmoredConduit;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.SolidPump;
import mindustry.world.blocks.units.UnitFactory;

import static mindustry.type.ItemStack.with;


@SuppressWarnings("ALL")
public class ECBlocks {
    public static ObjectMap<Block, Seq<Block>> ECBlocks = new ObjectMap<>();

    public static void load() throws IllegalAccessException, NoSuchFieldException {
        Seq<Block> blocks = new Seq<>();

        //判断设置"Compress-other-Mods"(是否对其他Mod生效)的状态
        if (Core.settings.getBool("Compress-other-Mods")) {
            //把全部方块添加进blocks中
            blocks = Vars.content.blocks().copy();
        } else {
            //遍历全部方块
            for (Block block : Vars.content.blocks()) {
                //把非Mod方块添加进blocks中
                if (!block.isModded()) {
                    blocks.add(block);
                }
            }
        }

        //遍历blocks
        for (Block block : blocks) {
            if (block instanceof Wall) {
                if (!(block instanceof Door)) {
                    load.wall(block);
                }
            } else if (block instanceof GenericCrafter) {
                load.genericCrafter(block);
            } else if (block instanceof Turret) {
                load.turret(block);
            } else if (block instanceof UnitFactory) {
                load.unitFactorys(block);
            } else if (block instanceof Drill) {
                load.drill(block);
            } else if (block instanceof Conveyor) {
                if (block instanceof ArmoredConveyor) {
                    load.armoredConveyor(block);
                } else {
                    load.conveyor(block);
                }
            } else if (block instanceof StackConveyor) {
                load.stackConveyor(block);
            } else if (block instanceof SolidPump) {
                load.solidPump(block);
            } else if (block instanceof PowerGenerator) {
                if (block instanceof ConsumeGenerator) {
                    load.consumeGenerator(block);
                } else {

                }
            } else if (block instanceof PowerNode) {
                load.powerNode(block);
            } else if (block instanceof Conduit) {
                if (block instanceof ArmoredConduit) {
                    load.armoredConduit(block);
                } else {
                    load.conduit(block);
                }
            } else if (block instanceof LightBlock) {
                load.lightBlock(block);
            }
        }


        //原版电力
        int powerBase = 5;
        double sizeBase = 1.4;

        for (int i = 1; i < 10; i++) {
            int num = i;
            new Battery("battery" + num) {{
                double healthBase = 5;
                int batteryBase = 5;
                health = (int) (40 * Math.pow(healthBase, num));
                requirements(Category.power, with(Vars.content.item("ec-" + "copper" + num), 5, Vars.content.item("ec-" + "lead" + num), 20));
                consumePowerBuffered((float) (4000f * Math.pow(batteryBase, num)));
                baseExplosiveness = (float) (1f * Math.pow(batteryBase, num));
            }};
        }
        ;
        /*
        new ConsumeGenerator("combustion-generator") {{
            requirements(Category.power, with(Vars.content.item("ec-" + "copper" + 1), 25, Vars.content.item("ec-" + "lead" + 1), 15));
            powerProduction = 2.5f;
            itemDuration = 120f;

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.03f;
            generateEffect = Fx.generatespark;
            explosionDamage = 0;


            consume(new ConsumeItemFlammable());


            ConsumeItemExplode explode = new ConsumeItemExplode();
            explode.baseChance = -1;
            consume(explode);

            drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
        }};
        new ConsumeGenerator("steam-generator") {{
            requirements(Category.power, with(
                    Vars.content.item("ec-" + "copper" + 1), 35,
                    Vars.content.item("ec-" + "graphite" + 1), 25,
                    Vars.content.item("ec-" + "lead" + 1), 40,
                    Vars.content.item("ec-" + "silicon" + 1), 30));
            powerProduction = 5.5f * 2.5f;
            itemDuration = 90f;
            consumeLiquid(Vars.content.liquid("ec-" + "water" + 1), 0.1f);
            hasLiquids = true;
            size = 2;
            generateEffect = Fx.generatespark;

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.06f;

            consume(new ConsumeItemFlammable());
            ConsumeItemExplode explode = new ConsumeItemExplode();
            explode.baseChance = -1;
            consume(explode);

            drawer = new DrawMulti(
                    new DrawDefault(),
                    new DrawWarmupRegion(),
                    new DrawRegion("-turbine") {{
                        rotateSpeed = 2f;
                    }},
                    new DrawRegion("-turbine") {{
                        rotateSpeed = -2f;
                        rotation = 45f;
                    }},
                    new DrawRegion("-cap"),
                    new DrawLiquidRegion()
            );
        }};


        new AnyMtiCrafter("differential-generator") {{
            requirements(Category.power, with(
                    Vars.content.item("ec-" + "copper" + 1), 70,
                    Vars.content.item("ec-" + "titanium" + 1), 50,
                    Vars.content.item("ec-" + "lead" + 1), 100,
                    Vars.content.item("ec-" + "silicon" + 1), 65,
                    Vars.content.item("ec-" + "metaglass" + 1), 50));
            hasLiquids = true;
            hasItems = true;
            size = 3;
            ambientSound = Sounds.steam;
            ambientSoundVolume = 0.03f;

            drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion(), new DrawLiquidRegion());

            products.add(new Formula() {{
                consumeLiquid(Vars.content.liquid("cryofluid"), 0.1f);
                consumeItem(Vars.content.item("pyratite"), 1);

                outputItems = ItemStack.with(
                        Vars.content.item("ec-" + "power" + 1), 6,
                        Vars.content.item("ec-" + "power" + 0), 6);
                craftTime = 220f;
                craftEffect = Fx.generatespark;
            }});
            for (int i = 1; i < 9; i++) {
                int num = i;
                int num1 = i + 1;
                products.add(new Formula() {{
                    consumeLiquid(Vars.content.liquid("ec-" + "cryofluid" + num), 0.1f);
                    consumeItem(Vars.content.item("ec-" + "pyratite" + num), 1);

                    outputItems = ItemStack.with(
                            Vars.content.item("ec-" + "power" + num1), 6,
                            Vars.content.item("ec-" + "power" + num), 6);
                    craftTime = 220f;
                    craftEffect = Fx.generatespark;
                }});
            }
            ;
            products.add(new Formula() {{
                consumeLiquid(Vars.content.liquid("ec-" + "cryofluid" + 9), 0.1f);
                consumeItem(Vars.content.item("ec-" + "pyratite" + 9), 1);

                outputItems = ItemStack.with(
                        Vars.content.item("ec-" + "power" + 9), 66);
                craftTime = 220f;
                craftEffect = Fx.generatespark;
            }});
        }};
        */
    }
}
