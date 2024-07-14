package ec.content;


import arc.graphics.Color;
import ec.AnyMtiCrafter;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.blocks.defense.Door;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.LiquidTurret;
import mindustry.world.blocks.power.Battery;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.blocks.power.NuclearReactor;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.consumers.ConsumeItemCharged;
import mindustry.world.consumers.ConsumeItemExplode;
import mindustry.world.consumers.ConsumeItemFlammable;
import mindustry.world.consumers.ConsumeItemRadioactive;
import mindustry.world.draw.*;
import mindustry.world.meta.Env;
import mindustry.world.blocks.power.NuclearReactor.*;

import static mindustry.type.ItemStack.with;
import static mindustry.world.meta.StatValues.ammo;
import static mindustry.world.meta.StatValues.content;


@SuppressWarnings("ALL")
public class ECBlocks {
    public static void load(){

        String[] item0 = {
                "copper","lead","sand","titanium",
                "metaglass","scrap","coal","thorium",
                "surgeAlloy","phaseFabric","graphite", "silicon",
                "pyratite","blastCompound","sporePod","plastanium"
        };
        String[] item1 = {
                "copper","lead","sand","titanium",
                "metaglass","scrap","coal","thorium",
                "surge-alloy","phase-fabric","graphite", "silicon",
                "pyratite","blast-compound","spore-pod","plastanium"
        };
        for(int i = 0 ; i < item0.length ; i++ ){
            load.itemCompressor(item0[i],item1[i]);
            load.multipress(item0[i],item1[i]);
        };

        String[] liquid0 = {"water","slag","oil","cryofluid"};
        for (int i = 0 ; i < liquid0.length ; i++){
            load.liquidCompressor(liquid0[i]);
            //load.liquidmultipress(liquid0[i]);
        };

        //test
        /*
        new AnyMtiCrafter("test"){{
            requirements(Category.crafting, with());
            size = 3;

            liquidCapacity = 20;

            products.addAll(
                    new Formula(){{
                        consumeLiquid(Liquids.water, 12);
                        outputLiquids = LiquidStack.with(Liquids.oil, 12);
                        craftTime = 10f;
                    }},
                    new Formula(){{
                        consumeLiquid(Liquids.water, 6);
                        outputLiquids = LiquidStack.with(Liquids.cryofluid, 6);
                        craftTime = 10f;
                    }}
                    );
        }};

         */






        //原版工厂
        new AnyMtiCrafter("graphite-press"){{
            requirements(Category.crafting, with(Items.copper, 75, Items.lead, 30));
            craftEffect = Fx.pulverizeMedium;
            size = 2;
            hasItems = true;
        }};
        new AnyMtiCrafter("silicon-smelter"){{
            requirements(Category.crafting, with(Items.copper, 30, Items.lead, 25));
            craftEffect = Fx.smeltsmoke;
            size = 2;
            hasPower = true;
            hasLiquids = false;
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffef99")));
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.07f;

        }};
        new AnyMtiCrafter("kiln"){{
            requirements(Category.crafting, with(Items.copper, 60, Items.graphite, 30, Items.lead, 30));
            craftEffect = Fx.smeltsmoke;
            size = 2;
            hasPower = hasItems = true;
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffc099")));
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.07f;

        }};
        new AnyMtiCrafter("plastanium-compressor"){{
            requirements(Category.crafting, with(Items.silicon, 80, Items.lead, 115, Items.graphite, 60, Items.titanium, 80));
            hasItems = true;
            liquidCapacity = 60f;
            size = 2;
            health = 320;
            hasPower = hasLiquids = true;
            craftEffect = Fx.formsmoke;
            updateEffect = Fx.plasticburn;
            drawer = new DrawMulti(new DrawDefault(), new DrawFade());

        }};
        new AnyMtiCrafter("phase-weaver"){{
            requirements(Category.crafting, with(Items.silicon, 130, Items.lead, 120, Items.thorium, 75));
            craftEffect = Fx.smeltsmoke;
            size = 2;
            hasPower = true;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawWeave(), new DrawDefault());
            envEnabled |= Env.space;
            ambientSound = Sounds.techloop;
            ambientSoundVolume = 0.02f;

            itemCapacity = 20;
        }};
        new AnyMtiCrafter("surge-smelter"){{
            requirements(Category.crafting, with(Items.silicon, 80, Items.lead, 80, Items.thorium, 70));
            craftEffect = Fx.smeltsmoke;
            size = 3;
            hasPower = true;
            itemCapacity = 20;
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame());

        }};
        new AnyMtiCrafter("cryofluid-mixer"){{
            requirements(Category.crafting, with(Items.lead, 65, Items.silicon, 40, Items.titanium, 60));
            size = 2;
            hasPower = true;
            hasItems = true;
            hasLiquids = true;
            rotate = false;
            solid = true;
            outputsLiquid = true;
            envEnabled = Env.any;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.water), new DrawLiquidTile(Liquids.cryofluid){{drawLiquidLight = true;}}, new DrawDefault());
            liquidCapacity = 24f;
            lightLiquid = Liquids.cryofluid;

        }};
        new AnyMtiCrafter("pyratite-mixer"){{
            requirements(Category.crafting, with(Items.copper, 50, Items.lead, 25));
            hasItems = true;
            hasPower = true;
            envEnabled |= Env.space;

            size = 2;
        }};
        new AnyMtiCrafter("blast-mixer"){{
            requirements(Category.crafting, with(Items.lead, 30, Items.titanium, 20));
            hasItems = true;
            hasPower = true;
            size = 2;
            envEnabled |= Env.space;

        }};
        new AnyMtiCrafter("melter"){{
            requirements(Category.crafting, with(Items.copper, 30, Items.lead, 35, Items.graphite, 45));
            health = 200;
            hasLiquids = hasPower = true;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(), new DrawDefault());

        }};
        new AnyMtiCrafter("separator"){{
            requirements(Category.crafting, with(Items.copper, 30, Items.titanium, 25));
            hasPower = true;
            size = 2;

            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(), new DrawRegion("-spinner", 3, true), new DrawDefault());
        }};
        new AnyMtiCrafter("disassembler"){{
            requirements(Category.crafting, with(Items.plastanium, 40, Items.titanium, 100, Items.silicon, 150, Items.thorium, 80));
            hasPower = true;
            size = 3;
            itemCapacity = 20;

            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(), new DrawRegion("-spinner", 3, true), new DrawDefault());
        }};
        new AnyMtiCrafter("spore-press"){{
            requirements(Category.crafting, with(Items.lead, 35, Items.silicon, 30));
            liquidCapacity = 60f;
            size = 2;
            health = 320;
            hasLiquids = true;
            hasPower = true;
            craftEffect = Fx.none;
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawPistons(){{
                        sinMag = 1f;
                    }},
                    new DrawDefault(),
                    new DrawLiquidRegion(),
                    new DrawRegion("-top")
            );

        }};
        new AnyMtiCrafter("pulverizer"){{
            requirements(Category.crafting, with(Items.copper, 30, Items.lead, 25));
            craftEffect = Fx.pulverize;
            updateEffect = Fx.pulverizeSmall;
            hasItems = hasPower = true;
            drawer = new DrawMulti(new DrawDefault(), new DrawRegion("-rotator"){{
                spinSprite = true;
                rotateSpeed = 2f;
            }}, new DrawRegion("-top"));
            ambientSound = Sounds.grinding;
            ambientSoundVolume = 0.025f;

        }};
        new AnyMtiCrafter("coal-centrifuge"){{
            requirements(Category.crafting, with(Items.titanium, 20, Items.graphite, 40, Items.lead, 30));
            craftEffect = Fx.coalSmeltsmoke;
            size = 2;
            hasPower = hasItems = hasLiquids = true;
            rotateDraw = false;

        }};



        for (int i = 1;i<10;i++){
            int num = i;
            ((AnyMtiCrafter)Vars.content.block("ec-"+"graphite-press")).products.add(new AnyMtiCrafter.Formula(){{
                consumeItem(Vars.content.item("ec-"+"coal"+num),2);
                outputItems = ItemStack.with(Vars.content.item("ec-"+"graphite"+num),1);
                craftTime = 90f;
                craftEffect = Fx.pulverizeMedium;
            }});
            ((AnyMtiCrafter)Vars.content.block("ec-"+"silicon-smelter")).products.add(new AnyMtiCrafter.Formula(){{
                consumeItems(with(Vars.content.item("ec-"+"coal"+num),1,Vars.content.item("ec-"+"sand"+num),2));
                consumePower(0.5f);
                outputItems = ItemStack.with(Vars.content.item("ec-"+"silicon"+num),1);
                craftTime = 40f;
                craftEffect = Fx.smeltsmoke;
            }});
            ((AnyMtiCrafter)Vars.content.block("ec-"+"kiln")).products.add(new AnyMtiCrafter.Formula(){{
                consumeItems(with(Vars.content.item("ec-"+"lead"+num),1,Vars.content.item("ec-"+"sand"+num),1));
                consumePower(0.6f);
                outputItems = ItemStack.with(Vars.content.item("ec-"+"metaglass"+num),1);
                craftTime = 30f;
                drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffc099")));

                craftEffect = Fx.smeltsmoke;
            }});
            ((AnyMtiCrafter)Vars.content.block("ec-"+"plastanium-compressor")).products.add(new AnyMtiCrafter.Formula(){{
                consumeItem(Vars.content.item("ec-"+"titanium"+num),2);
                consumePower(3f);
                consumeLiquid(Vars.content.liquid("ec-"+"oil"+num),0.25f);
                outputItems = ItemStack.with(Vars.content.item("ec-"+"plastanium"+num),1);
                craftEffect = Fx.formsmoke;
                updateEffect = Fx.plasticburn;
                drawer = new DrawMulti(new DrawDefault(), new DrawFade());
                craftTime = 60f;
                craftEffect = Fx.pulverizeMedium;
            }});
            ((AnyMtiCrafter)Vars.content.block("ec-"+"phase-weaver")).products.add(new AnyMtiCrafter.Formula(){{
                consumeItems(with(Vars.content.item("ec-"+"thorium"+num),4,Vars.content.item("ec-"+"sand"+num),10));
                consumePower(5f);
                outputItems = ItemStack.with(Vars.content.item("ec-"+"phaseFabric"+num),1);
                craftTime = 120f;
                drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawWeave(), new DrawDefault());
                craftEffect = Fx.smeltsmoke;
            }});
            ((AnyMtiCrafter)Vars.content.block("ec-"+"surge-smelter")).products.add(new AnyMtiCrafter.Formula(){{
                consumeItems(with(
                        Vars.content.item("ec-"+"copper"+num),3,
                        Vars.content.item("ec-"+"lead"+num),4,
                        Vars.content.item("ec-"+"titanium"+num),2,
                        Vars.content.item("ec-"+"silicon"+num),3));
                consumePower(4f);
                outputItems = ItemStack.with(Vars.content.item("ec-"+"surgeAlloy"+num),1);
                drawer = new DrawMulti(new DrawDefault(), new DrawFlame());
                craftTime = 75f;
                craftEffect = Fx.smeltsmoke;
            }});
            ((AnyMtiCrafter)Vars.content.block("ec-"+"cryofluid-mixer")).products.add(new AnyMtiCrafter.Formula(){{
                consumeItem(Vars.content.item("ec-"+"titanium"+num),1);
                consumeLiquid(Vars.content.liquid("ec-"+"water"+num),12f/60);
                outputLiquids = LiquidStack.with(Vars.content.liquid("ec-"+"cryofluid"+num),12f/60);
                craftTime = 120f;

                consumePower(1f);
                craftEffect = Fx.pulverizeMedium;
            }});
            ((AnyMtiCrafter)Vars.content.block("ec-"+"pyratite-mixer")).products.add(new AnyMtiCrafter.Formula(){{
                consumeItems(with(
                        Vars.content.item("ec-"+"coal"+num),1,
                        Vars.content.item("ec-"+"lead"+num),2,
                        Vars.content.item("ec-"+"sand"+num),2));
                outputItems = ItemStack.with(Vars.content.item("ec-"+"pyratite"+num),1);
                craftTime = 80f;
                craftEffect = Fx.pulverizeMedium;

                consumePower(0.20f);
            }});
            ((AnyMtiCrafter)Vars.content.block("ec-"+"blast-mixer")).products.add(new AnyMtiCrafter.Formula(){{
                consumeItems(with(
                        Vars.content.item("ec-"+"pyratite"+num),1,
                        Vars.content.item("ec-"+"sporePod"+num),1));

                consumePower(0.40f);
                outputItems = ItemStack.with(Vars.content.item("ec-"+"blastCompound"+num),1);
                craftTime = 80f;
                craftEffect = Fx.pulverizeMedium;
            }});
            ((AnyMtiCrafter)Vars.content.block("ec-"+"melter")).products.add(new AnyMtiCrafter.Formula(){{
                consumeItem(Vars.content.item("ec-"+"scrap"+num),1);
                consumePower(1f);
                outputLiquids =LiquidStack.with(Vars.content.liquid("ec-"+"slag"+num),12f/60);
                craftTime = 10f;
                craftEffect = Fx.pulverizeMedium;

                drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(), new DrawDefault());
            }});
            ((AnyMtiCrafter)Vars.content.block("ec-"+"separator")).products.add(new AnyMtiCrafter.Formula(){{
                consumeLiquid(Vars.content.liquid("ec-"+"slag"+num),4f*12/60);
                consumePower(1.1f);
                outputItems = ItemStack.with(
                        Vars.content.item("ec-"+"copper"+num),5,
                        Vars.content.item("ec-"+"lead"+num),3,
                        Vars.content.item("ec-"+"graphite"+num),2,
                        Vars.content.item("ec-"+"titanium"+num),2);
                craftTime = 35f*12;
                drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(), new DrawRegion("-spinner", 3, true), new DrawDefault());
            }});
            ((AnyMtiCrafter)Vars.content.block("ec-"+"disassembler")).products.add(new AnyMtiCrafter.Formula(){{
                consumeItem(Vars.content.item("ec-"+"scrap"+num),1);
                consumeLiquid(Vars.content.liquid("ec-"+"slag"+num),0.12f*5);
                consumePower(4f);
                outputItems = ItemStack.with(
                        Vars.content.item("ec-"+"sand"+num),2,
                        Vars.content.item("ec-"+"graphite"+num),1,
                        Vars.content.item("ec-"+"titanium"+num),1,
                        Vars.content.item("ec-"+"thorium"+num),1);
                craftTime = 15f*5;
                drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(), new DrawRegion("-spinner", 3, true), new DrawDefault());
            }});
            ((AnyMtiCrafter)Vars.content.block("ec-"+"spore-press")).products.add(new AnyMtiCrafter.Formula(){{
                consumeItem(Vars.content.item("ec-"+"sporePod"+num),1);
                consumePower(0.7f);
                outputLiquids = LiquidStack.with(Vars.content.liquid("ec-"+"oil"+num),18f/60f);
                craftTime = 20f;
                craftEffect = Fx.none;
                drawer = new DrawMulti(
                        new DrawRegion("-bottom"),
                        new DrawPistons(){{
                            sinMag = 1f;
                        }},
                        new DrawDefault(),
                        new DrawLiquidRegion(),
                        new DrawRegion("-top")
                );
            }});
            ((AnyMtiCrafter)Vars.content.block("ec-"+"pulverizer")).products.add(new AnyMtiCrafter.Formula(){{
                consumeItem(Vars.content.item("ec-"+"scrap"+num),1);
                consumePower(0.50f);
                outputItems = ItemStack.with(Vars.content.item("ec-"+"sand"+num),1);
                craftTime = 40f;
                updateEffect = Fx.pulverizeSmall;
                craftEffect = Fx.pulverize;
                drawer = new DrawMulti(new DrawDefault(), new DrawRegion("-rotator"){{
                    spinSprite = true;
                    rotateSpeed = 2f;
                }}, new DrawRegion("-top"));
            }});
            ((AnyMtiCrafter)Vars.content.block("ec-"+"coal-centrifuge")).products.add(new AnyMtiCrafter.Formula(){{
                consumeLiquid(Vars.content.liquid("ec-"+"oil"+num),0.1f);
                consumePower(0.7f);
                outputItems = ItemStack.with(Vars.content.item("ec-"+"coal"+num),1);
                craftTime = 30f;
                craftEffect = Fx.coalSmeltsmoke;
            }});

        };

        //原版电力
        int powerBase = 5 ;
        double sizeBase = 1.4;

        new AnyMtiCrafter("powerCompressor"){{
            requirements(Category.power, with(Vars.content.item("ec-copper1"), 30));
            hasLiquids = true;
            hasItems = true;
            size = 2;
            ambientSound = Sounds.steam;
            ambientSoundVolume = 0.03f;

            drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion(), new DrawLiquidRegion());

            for (int i = 0 ; i < 8 ; i++){
                int num = i;
                products.add(new Formula(){{
                    consumePower((float) (1f*Math.pow(10,num)));
                    outputItems = ItemStack.with(
                            Vars.content.item("ec-"+"power"+num), 1);
                    craftTime = 60f;
                    craftEffect = Fx.generatespark;
                }});
            };
            products.addAll(new Formula(){{
                consumePower((float) (1f*Math.pow(10,7)));
                outputItems = ItemStack.with(
                        Vars.content.item("ec-"+"power"+8), 1);
                craftTime = 600f;
                craftEffect = Fx.generatespark;
            }},new Formula(){{
                consumePower((float) (1f*Math.pow(10,7)));
                outputItems = ItemStack.with(
                        Vars.content.item("ec-"+"power"+9), 1);
                craftTime = 6000f;
                craftEffect = Fx.generatespark;
            }});
        }};
        new ConsumeGenerator("power-producer"){{
            requirements(Category.power, with(
                    Vars.content.item("ec-"+"titanium"+1), 20,
                    Vars.content.item("ec-"+"lead"+1), 50,
                    Vars.content.item("ec-"+"silicon"+1), 30));
            size = 3;
            baseExplosiveness = 5f;
            powerProduction = 1f;
            itemDuration = 60f;

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.03f;
            generateEffect = Fx.generatespark;
            explosionDamage = 0 ;


            consume(new ConsumeItemCharged(1f));


            ConsumeItemExplode explode = new ConsumeItemExplode();
            explode.baseChance = -1;
            consume(explode);

            drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
        }};
        for (int i = 1 ; i < 10 ; i++){
            int num = i ;
            new PowerNode("power-node"+num){{
                double healthBase = 5;
                health = (int) (40*Math.pow(healthBase,num));
                requirements(Category.power, with(Vars.content.item("ec-"+"copper"+num), 1, Vars.content.item("ec-"+"lead"+num), 3));
                maxNodes = (int) (10*Math.pow(powerBase,num));
                laserRange = (float) (6*Math.pow(sizeBase,num));
            }};
            new Battery("battery"+num){{
                double healthBase = 5;
                int batteryBase = 5;
                health = (int) (40*Math.pow(healthBase,num));
                requirements(Category.power, with(Vars.content.item("ec-"+"copper"+num), 5, Vars.content.item("ec-"+"lead"+num), 20));
                consumePowerBuffered((float) (4000f*Math.pow(batteryBase,num)));
                baseExplosiveness = (float) (1f*Math.pow(batteryBase,num));
            }};
        };
        new ConsumeGenerator("combustion-generator"){{
            requirements(Category.power, with(Vars.content.item("ec-"+"copper"+1), 25, Vars.content.item("ec-"+"lead"+1), 15));
            powerProduction = 2.5f;
            itemDuration = 120f;

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.03f;
            generateEffect = Fx.generatespark;
            explosionDamage = 0 ;


            consume(new ConsumeItemFlammable());


            ConsumeItemExplode explode = new ConsumeItemExplode();
            explode.baseChance = -1;
            consume(explode);

            drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
        }};
        new ConsumeGenerator("steam-generator"){{
            requirements(Category.power, with(
                    Vars.content.item("ec-"+"copper"+1), 35,
                    Vars.content.item("ec-"+"graphite"+1), 25,
                    Vars.content.item("ec-"+"lead"+1), 40,
                    Vars.content.item("ec-"+"silicon"+1), 30));
            powerProduction = 5.5f*2.5f;
            itemDuration = 90f;
            consumeLiquid(Vars.content.liquid("ec-"+"water"+1), 0.1f);
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
                    new DrawRegion("-turbine"){{
                        rotateSpeed = 2f;
                    }},
                    new DrawRegion("-turbine"){{
                        rotateSpeed = -2f;
                        rotation = 45f;
                    }},
                    new DrawRegion("-cap"),
                    new DrawLiquidRegion()
            );
        }};
        new AnyMtiCrafter("differential-generator"){{
            requirements(Category.power, with(
                    Vars.content.item("ec-"+"copper"+1), 70,
                    Vars.content.item("ec-"+"titanium"+1), 50,
                    Vars.content.item("ec-"+"lead"+1), 100,
                    Vars.content.item("ec-"+"silicon"+1), 65,
                    Vars.content.item("ec-"+"metaglass"+1), 50));
            hasLiquids = true;
            hasItems = true;
            size = 3;
            ambientSound = Sounds.steam;
            ambientSoundVolume = 0.03f;

            drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion(), new DrawLiquidRegion());

            products.add(new Formula(){{
                consumeLiquid(Vars.content.liquid("cryofluid"),0.1f);
                consumeItem(Vars.content.item("pyratite"),1);

                outputItems = ItemStack.with(
                        Vars.content.item("ec-"+"power"+1), 6,
                        Vars.content.item("ec-"+"power"+0), 6);
                craftTime = 220f;
                craftEffect = Fx.generatespark;
            }});
            for (int i = 1 ; i < 9 ; i++){
                int num = i;
                int num1 = i + 1;
                products.add(new Formula(){{
                    consumeLiquid(Vars.content.liquid("ec-"+"cryofluid"+num),0.1f);
                    consumeItem(Vars.content.item("ec-"+"pyratite"+num),1);

                    outputItems = ItemStack.with(
                            Vars.content.item("ec-"+"power"+num1), 6,
                            Vars.content.item("ec-"+"power"+num), 6);
                    craftTime = 220f;
                    craftEffect = Fx.generatespark;
                }});
            };
            products.add(new Formula(){{
                consumeLiquid(Vars.content.liquid("ec-"+"cryofluid"+9),0.1f);
                consumeItem(Vars.content.item("ec-"+"pyratite"+9),1);

                outputItems = ItemStack.with(
                        Vars.content.item("ec-"+"power"+9), 66);
                craftTime = 220f;
                craftEffect = Fx.generatespark;
            }});
        }};






        double damageBase = 5;
        for (int i = 1 ; i < 10 ; i++){
            int num = i;
            ((ItemTurret) Blocks.duo).ammoTypes.put(Vars.content.item("ec-"+"copper"+num), new BasicBulletType(2.5f, (float) (9*Math.pow(damageBase,num))){{
                width = (float) (7f*Math.pow(sizeBase,num));
                height = (float) (9f*Math.pow(sizeBase,num));
                lifetime = 60f;
                ammoMultiplier = 2;
            }});
            ((ItemTurret) Blocks.duo).ammoTypes.put(Vars.content.item("ec-"+"graphite"+num), new BasicBulletType(3.5f, (float) (18*Math.pow(damageBase,num))){{
                width = (float) (9f*Math.pow(sizeBase,num));
                height = (float) (12f*Math.pow(sizeBase,num));
                reloadMultiplier = 0.6f;
                ammoMultiplier = 4;
                lifetime = 60f;
            }});
            ((ItemTurret) Blocks.duo).ammoTypes.put(Vars.content.item("ec-"+"silicon"+num), new BasicBulletType(3f, (float) (12*Math.pow(damageBase,num))){{
                width = (float) (7f*Math.pow(sizeBase,num));
                height = (float) (9f*Math.pow(sizeBase,num));
                homingPower = (float) (0.1f*Math.pow(sizeBase,num));
                reloadMultiplier = 1.5f;
                ammoMultiplier = 5;
                lifetime = 60f;
            }});

            ((ItemTurret) Blocks.scatter).ammoTypes.put(Vars.content.item("ec-"+"lead"+num), new BasicBulletType(4.2f, (float) (3*Math.pow(damageBase,num))) {{
                ammoMultiplier = 4f;
                shootEffect = Fx.shootSmall;
                hitEffect = Fx.flakExplosion;
                splashDamage = (float) (27f* 1.5f*Math.pow(damageBase,num));
                splashDamageRadius = (float) (15f*Math.pow(damageBase,num));
                width = (float) (6f*Math.pow(sizeBase,num));
                height = (float) (8f*Math.pow(sizeBase,num));
                lifetime = 60f;
            }});
            ((ItemTurret) Blocks.scatter).ammoTypes.put(Vars.content.item("ec-"+"metaglass"+num), new FlakBulletType(4f, (float) (3*Math.pow(damageBase,num))){{
                lifetime = 60f;
                ammoMultiplier = 5f;
                shootEffect = Fx.shootSmall;
                reloadMultiplier = 0.8f;
                hitEffect = Fx.flakExplosion;
                width = (float) (6f*Math.pow(sizeBase,num));
                height = (float) (8f*Math.pow(sizeBase,num));
                splashDamage = (float) (30f * 1.5f*Math.pow(damageBase,num));
                splashDamageRadius = (float) (20f*Math.pow(damageBase,num));
                fragBullets = (int) (6*Math.pow(sizeBase,num));
                fragBullet = new BasicBulletType(3f, (float) (5*Math.pow(damageBase,num))){{
                    width = (float) (5f*Math.pow(sizeBase,num));
                    height = (float) (12f*Math.pow(sizeBase,num));
                    shrinkY = 1f;
                    lifetime = 20f;
                    backColor = Pal.gray;
                    frontColor = Color.white;
                    despawnEffect = Fx.none;
                    collidesGround = false;
                }};
            }});
            ((ItemTurret) Blocks.scatter).ammoTypes.put(Vars.content.item("ec-"+"scrap"+num), new FlakBulletType(4f, (float) (3*Math.pow(damageBase,num))){{
                lifetime = 60f;
                ammoMultiplier = 5f;
                shootEffect = Fx.shootSmall;
                reloadMultiplier = 0.5f;
                width = (float) (6f*Math.pow(sizeBase,num));
                height = (float) (8f*Math.pow(sizeBase,num));
                hitEffect = Fx.flakExplosion;
                splashDamage = (float) (22f * 1.5f*Math.pow(damageBase,num));
                splashDamageRadius = (float) (24f*Math.pow(damageBase,num));
            }});

            ((ItemTurret) Blocks.scorch).ammoTypes.put(Vars.content.item("ec-"+"coal"+num), new BulletType(3.35f, (float) (17f*Math.pow(damageBase,num))){{

                hitSize = (float) (7f*Math.pow(sizeBase,num));
                statusDuration = (float) (60f * 4*Math.pow(sizeBase,num));

                ammoMultiplier = 3f;
                lifetime = 18f;
                pierce = true;
                collidesAir = false;
                shootEffect = Fx.shootSmallFlame;
                hitEffect = Fx.hitFlameSmall;
                despawnEffect = Fx.none;
                status = StatusEffects.burning;
                keepVelocity = false;
                hittable = false;
            }});
            ((ItemTurret) Blocks.scorch).ammoTypes.put(Vars.content.item("ec-"+"pyratite"+num), new BulletType(4f, (float) (60f*Math.pow(damageBase,num))){{
                hitSize = (float) (7f*Math.pow(sizeBase,num));
                statusDuration = (float) (60f * 10*Math.pow(sizeBase,num));
                ammoMultiplier = 6f;
                lifetime = 18f;
                pierce = true;
                collidesAir = false;
                shootEffect = Fx.shootPyraFlame;
                hitEffect = Fx.hitFlameSmall;
                despawnEffect = Fx.none;
                status = StatusEffects.burning;
                hittable = false;
            }});

            ((ItemTurret) Blocks.hail).ammoTypes.put(Vars.content.item("ec-"+"graphite"+num), new ArtilleryBulletType(3f, (float) (20*Math.pow(damageBase,num))){{
                knockback = (float) (0.8f*Math.pow(sizeBase,num));
                lifetime = 80f;
                width = height = (float) (11f * Math.pow(sizeBase,num));
                collidesTiles = false;
                splashDamageRadius = (float) (25f * 0.75f * Math.pow(sizeBase,num));
                splashDamage = (float) (33f*Math.pow(damageBase,num));
            }});
            ((ItemTurret) Blocks.hail).ammoTypes.put(Vars.content.item("ec-"+"silicon"+num), new ArtilleryBulletType(3f, (float) (20*Math.pow(damageBase,num))){{
                knockback = (float) (0.8f*Math.pow(sizeBase,num));
                lifetime = 80f;
                width = height = (float) (11f*Math.pow(sizeBase,num));
                collidesTiles = false;
                splashDamageRadius = (float) (25f * 0.75f*Math.pow(sizeBase,num));
                splashDamage = (float) (33f*Math.pow(damageBase,num));
                reloadMultiplier = 1.2f;
                ammoMultiplier = 3f;
                homingPower = (float) (0.08f*Math.pow(sizeBase,num));
                homingRange = (float) (50f/Math.pow(sizeBase,num));
            }});
            ((ItemTurret) Blocks.hail).ammoTypes.put(Vars.content.item("ec-"+"pyratite"+num), new ArtilleryBulletType(3f, (float) (25*Math.pow(damageBase,num))){{
                hitEffect = Fx.blastExplosion;
                knockback = (float) (0.8f*Math.pow(sizeBase,num));
                lifetime = 80f;
                width = height = (float) (13f*Math.pow(sizeBase,num));
                collidesTiles = false;
                splashDamageRadius = (float) (25f * 0.75f*Math.pow(sizeBase,num));
                splashDamage = (float) (45f*Math.pow(damageBase,num));
                status = StatusEffects.burning;
                statusDuration = (float) (60f * 12f*Math.pow(sizeBase,num));
                frontColor = Pal.lightishOrange;
                backColor = Pal.lightOrange;
                makeFire = true;
                trailEffect = Fx.incendTrail;
                ammoMultiplier = 4f;
            }});

            ((LiquidTurret) Blocks.wave).ammoTypes.put(Vars.content.liquid("ec-"+"water"+num),new LiquidBulletType(Vars.content.liquid("ec-"+"water"+num)){{
                knockback = (float) (0.7f*Math.pow(sizeBase,num));
                drag = 0.01f;
                layer = Layer.bullet - 2f;
            }});
            ((LiquidTurret) Blocks.wave).ammoTypes.put(Vars.content.liquid("ec-"+"slag"+num),new LiquidBulletType(Vars.content.liquid("ec-"+"slag"+num)){{
                knockback = (float) (0.7f*Math.pow(sizeBase,num));
                damage = (float) (4*Math.pow(damageBase,num));
                drag = 0.01f;
            }});
            ((LiquidTurret) Blocks.wave).ammoTypes.put(Vars.content.liquid("ec-"+"cryofluid"+num),new LiquidBulletType(Vars.content.liquid("ec-"+"cryofluid"+num)){{
                knockback = (float) (0.55f*Math.pow(sizeBase,num));
                drag = 0.01f;
            }});
            ((LiquidTurret) Blocks.wave).ammoTypes.put(Vars.content.liquid("ec-"+"oil"+num),new LiquidBulletType(Vars.content.liquid("ec-"+"oil"+num)){{
                knockback = (float) (0.55f*Math.pow(sizeBase,num));
                drag = 0.01f;
                layer = Layer.bullet - 2f;
            }});

            ((ItemTurret) Blocks.swarmer).ammoTypes.put(Vars.content.item("ec-"+"blastCompound"+num), new MissileBulletType(3.7f, (float) (10*Math.pow(damageBase,num))){{
                width = (float) (8f*Math.pow(sizeBase,num));
                height = (float) (8f*Math.pow(sizeBase,num));
                shrinkY = 0f;
                splashDamageRadius = (float) (30f*Math.pow(sizeBase,num));
                splashDamage = (float) (30f * 1.5f*Math.pow(damageBase,num));
                ammoMultiplier = 5f;
                hitEffect = Fx.blastExplosion;
                despawnEffect = Fx.blastExplosion;

                status = StatusEffects.blasted;
                statusDuration = (float) (60f*Math.pow(sizeBase,num));
            }});
            ((ItemTurret) Blocks.swarmer).ammoTypes.put(Vars.content.item("ec-"+"pyratite"+num), new MissileBulletType(3.7f, (float) (12*Math.pow(damageBase,num))){{
                frontColor = Pal.lightishOrange;
                backColor = Pal.lightOrange;
                width = (float) (7f*Math.pow(sizeBase,num));
                height = (float) (8f*Math.pow(sizeBase,num));
                shrinkY = 0f;
                homingPower = (float) (0.08f*Math.pow(sizeBase,num));
                splashDamageRadius = (float) (20f*Math.pow(sizeBase,num));
                splashDamage = (float) (30f * 1.5f*Math.pow(damageBase,num));
                makeFire = true;
                ammoMultiplier = 5f;
                hitEffect = Fx.blastExplosion;
                status = StatusEffects.burning;
            }});
            ((ItemTurret) Blocks.swarmer).ammoTypes.put(Vars.content.item("ec-"+"surgeAlloy"+num),new MissileBulletType(3.7f, (float) (18*Math.pow(damageBase,num))){{
                width = (float) (8f*Math.pow(sizeBase,num));
                height = (float) (8f*Math.pow(sizeBase,num));
                shrinkY = 0f;
                splashDamageRadius = (float) (25f*Math.pow(sizeBase,num));
                splashDamage = (float) (25f * 1.4f*Math.pow(damageBase,num));
                hitEffect = Fx.blastExplosion;
                despawnEffect = Fx.blastExplosion;
                ammoMultiplier = 4f;
                lightningDamage = (float) (10*Math.pow(damageBase,num));
                lightning = (int) (2*Math.pow(sizeBase,num));
                lightningLength = (int) (10*Math.pow(sizeBase,num));
            }});

            ((ItemTurret) Blocks.salvo).ammoTypes.put(Vars.content.item("ec-"+"copper"+num),new BasicBulletType(2.5f, (float) (11*Math.pow(damageBase,num))){{
                width = (float) (7f*Math.pow(sizeBase,num));
                height = (float) (9f*Math.pow(sizeBase,num));
                lifetime = 60f;
                ammoMultiplier = 2;
            }});
            ((ItemTurret) Blocks.salvo).ammoTypes.put(Vars.content.item("ec-"+"graphite"+num),new BasicBulletType(3.5f, (float) (20*Math.pow(damageBase,num))){{
                width = (float) (9f*Math.pow(sizeBase,num));
                height = (float) (12f*Math.pow(sizeBase,num));
                reloadMultiplier = 0.6f;
                ammoMultiplier = 4;
                lifetime = 60f;
            }});
            ((ItemTurret) Blocks.salvo).ammoTypes.put(Vars.content.item("ec-"+"pyratite"+num),new BasicBulletType(3.2f, (float) (18*Math.pow(damageBase,num))){{
                width = (float) (10f*Math.pow(sizeBase,num));
                height = (float) (12f*Math.pow(sizeBase,num));
                frontColor = Pal.lightishOrange;
                backColor = Pal.lightOrange;
                status = StatusEffects.burning;
                hitEffect = new MultiEffect(Fx.hitBulletSmall, Fx.fireHit);

                ammoMultiplier = 5;

                splashDamage = (float) (12f*Math.pow(damageBase,num));
                splashDamageRadius = (float) (22f*Math.pow(sizeBase,num));

                makeFire = true;
                lifetime = 60f;
            }});
            ((ItemTurret) Blocks.salvo).ammoTypes.put(Vars.content.item("ec-"+"silicon"+num),new BasicBulletType(3f, (float) (15*Math.pow(damageBase,num)), "bullet"){{
                width = (float) (7f*Math.pow(sizeBase,num));
                height = (float) (9f*Math.pow(sizeBase,num));
                homingPower = (float) (0.1f*Math.pow(sizeBase,num));
                reloadMultiplier = 1.5f;
                ammoMultiplier = 5;
                lifetime = 60f;
            }});
            ((ItemTurret) Blocks.salvo).ammoTypes.put(Vars.content.item("ec-"+"thorium"+num),new BasicBulletType(4f, (float) (29*Math.pow(damageBase,num)), "bullet"){{
                width = (float) (10f*Math.pow(sizeBase,num));
                height = (float) (13f*Math.pow(sizeBase,num));
                shootEffect = Fx.shootBig;
                smokeEffect = Fx.shootBigSmoke;
                ammoMultiplier = 4;
                lifetime = 60f;
            }});

            ((LiquidTurret) Blocks.tsunami).ammoTypes.put(Vars.content.liquid("ec-"+"water"+num),new LiquidBulletType(Vars.content.liquid("ec-"+"water"+num)){{
                lifetime = (float) (49f*Math.pow(sizeBase,num));
                speed = (float) (4f*Math.pow(sizeBase,num));
                knockback = (float) (1.7f*Math.pow(sizeBase,num));
                puddleSize = (float) (8f*Math.pow(sizeBase,num));
                orbSize = (float) (4f*Math.pow(sizeBase,num));
                drag = 0.001f;
                ammoMultiplier = 0.4f;
                statusDuration = (float) (60f * 4f*Math.pow(sizeBase,num));
                damage = (float) (0.2f*Math.pow(damageBase,num));
                layer = Layer.bullet - 2f;
            }});
            ((LiquidTurret) Blocks.tsunami).ammoTypes.put(Vars.content.liquid("ec-"+"slag"+num),new LiquidBulletType(Vars.content.liquid("ec-"+"slag"+num)){{
                lifetime = (float) (49f*Math.pow(sizeBase,num));
                speed = (float) (4f*Math.pow(sizeBase,num));
                knockback = (float) (1.3f*Math.pow(sizeBase,num));
                puddleSize = (float) (8f*Math.pow(sizeBase,num));
                orbSize = (float) (4f*Math.pow(sizeBase,num));
                damage = (float) (4.75f*Math.pow(damageBase,num));
                drag = 0.001f;
                ammoMultiplier = 0.4f;
                statusDuration = (float) (60f * 4f*Math.pow(sizeBase,num));
            }});
            ((LiquidTurret) Blocks.tsunami).ammoTypes.put(Vars.content.liquid("ec-"+"cryofluid"+num),new LiquidBulletType(Vars.content.liquid("ec-"+"cryofluid"+num)){{
                lifetime = (float) (49f*Math.pow(sizeBase,num));
                speed = (float) (4f*Math.pow(sizeBase,num));
                knockback = (float) (1.3f*Math.pow(sizeBase,num));
                puddleSize = (float) (8f*Math.pow(sizeBase,num));
                orbSize = (float) (4f*Math.pow(sizeBase,num));
                drag = 0.001f;
                ammoMultiplier = 0.4f;
                statusDuration = (float) (60f * 4f*Math.pow(sizeBase,num));
                damage = (float) (0.2f*Math.pow(damageBase,num));
            }});
            ((LiquidTurret) Blocks.tsunami).ammoTypes.put(Vars.content.liquid("ec-"+"oil"+num),new LiquidBulletType(Vars.content.liquid("ec-"+"oil"+num)){{
                lifetime = (float) (49f*Math.pow(sizeBase,num));
                speed = (float) (4f*Math.pow(sizeBase,num));
                knockback = (float) (1.3f*Math.pow(sizeBase,num));
                puddleSize = (float) (8f*Math.pow(sizeBase,num));
                orbSize = (float) (4f*Math.pow(sizeBase,num));
                drag = 0.001f;
                ammoMultiplier = 0.4f;
                statusDuration = (float) (60f * 4f*Math.pow(sizeBase,num));
                damage = (float) (0.2f*Math.pow(damageBase,num));
                layer = Layer.bullet - 2f;
            }});

            ((ItemTurret) Blocks.fuse).ammoTypes.put(Vars.content.item("ec-"+"titanium"+num),new ShrapnelBulletType(){{
                length = (float) (100f*Math.pow(sizeBase,num));
                damage = (float) (66f*Math.pow(damageBase,num));
                ammoMultiplier = 4f;
                width = (float) (17f*Math.pow(sizeBase,num));
                reloadMultiplier = 1.3f;
            }});
            ((ItemTurret) Blocks.fuse).ammoTypes.put(Vars.content.item("ec-"+"thorium"+num),new ShrapnelBulletType(){{
                length = (float) (100f*Math.pow(sizeBase,num));
                damage = (float) (105f*Math.pow(damageBase,num));
                ammoMultiplier = 5f;
                toColor = Pal.thoriumPink;

                width = (float) (width*Math.pow(sizeBase,num));
                shootEffect = smokeEffect = Fx.thoriumShoot;
            }});

            ((ItemTurret) Blocks.ripple).ammoTypes.put(Vars.content.item("ec-"+"graphite"+num),new ArtilleryBulletType(3f, (float) (20*Math.pow(damageBase,num))){{
                knockback = (float) (0.8f*Math.pow(sizeBase,num));
                lifetime = 80f;
                width = height = (float) (11f*Math.pow(sizeBase,num));
                collidesTiles = false;
                splashDamageRadius = (float) (25f * 0.75f*Math.pow(sizeBase,num));
                splashDamage = (float) (33f*Math.pow(damageBase,num));
            }});
            ((ItemTurret) Blocks.ripple).ammoTypes.put(Vars.content.item("ec-"+"silicon"+num),new ArtilleryBulletType(3f, (float) (20*Math.pow(damageBase,num))){{
                knockback = (float) (0.8f*Math.pow(sizeBase,num));
                lifetime = 80f;
                width = height = (float) (11f*Math.pow(sizeBase,num));
                collidesTiles = false;
                splashDamageRadius = (float) (25f * 0.75f*Math.pow(sizeBase,num));
                splashDamage = (float) (33f*Math.pow(sizeBase,num));
                reloadMultiplier = 1.2f;
                ammoMultiplier = 3f;
                homingPower = (float) (0.08f*Math.pow(sizeBase,num));
                homingRange = (float) (50f/Math.pow(sizeBase,num));
            }});
            ((ItemTurret) Blocks.ripple).ammoTypes.put(Vars.content.item("ec-"+"pyratite"+num),new ArtilleryBulletType(3f, (float) (24*Math.pow(damageBase,num))){{
                hitEffect = Fx.blastExplosion;
                knockback = (float) (0.8f*Math.pow(sizeBase,num));
                lifetime = 80f;
                width = height = (float) (13f*Math.pow(sizeBase,num));
                collidesTiles = false;
                splashDamageRadius = (float) (25f * 0.75f*Math.pow(sizeBase,num));
                splashDamage = (float) (45f*Math.pow(damageBase,num));
                status = StatusEffects.burning;
                statusDuration = (float) (60f * 12f*Math.pow(sizeBase,num));
                frontColor = Pal.lightishOrange;
                backColor = Pal.lightOrange;
                makeFire = true;
                trailEffect = Fx.incendTrail;
                ammoMultiplier = 4f;
            }});
            ((ItemTurret) Blocks.ripple).ammoTypes.put(Vars.content.item("ec-"+"blastCompound"+num),new ArtilleryBulletType(2f, (float) (20*Math.pow(damageBase,num)), "shell"){{
                hitEffect = Fx.blastExplosion;
                knockback = (float) (0.8f*Math.pow(sizeBase,num));
                lifetime = 80f;
                width = height = (float) (14f*Math.pow(sizeBase,num));
                collidesTiles = false;
                ammoMultiplier = 4f;
                splashDamageRadius = (float) (45f * 0.75f*Math.pow(sizeBase,num));
                splashDamage = (float) (55f/Math.pow(sizeBase,num));
                backColor = Pal.missileYellowBack;
                frontColor = Pal.missileYellow;

                status = StatusEffects.blasted;
            }});
            ((ItemTurret) Blocks.ripple).ammoTypes.put(Vars.content.item("ec-"+"plastanium"+num),new ArtilleryBulletType(3.4f, (float) (20*Math.pow(damageBase,num)), "shell"){{
                hitEffect = Fx.plasticExplosion;
                knockback = (float) (1f*Math.pow(sizeBase,num));
                lifetime = 80f;
                width = height = (float) (13f*Math.pow(sizeBase,num));
                collidesTiles = false;
                splashDamageRadius = (float) (35f * 0.75f*Math.pow(sizeBase,num));
                splashDamage = (float) (45f*Math.pow(damageBase,num));
                fragBullet = new BasicBulletType(2.5f, (float) (10*Math.pow(damageBase,num)), "bullet"){{
                    width = (float) (10f*Math.pow(sizeBase,num));
                    height = (float) (12f*Math.pow(sizeBase,num));
                    shrinkY = (float) (1f*Math.pow(sizeBase,num));
                    lifetime = 15f;
                    backColor = Pal.plastaniumBack;
                    frontColor = Pal.plastaniumFront;
                    despawnEffect = Fx.none;
                    collidesAir = false;
                }};
                fragBullets = (int) (10*Math.pow(sizeBase,num));
                backColor = Pal.plastaniumBack;
                frontColor = Pal.plastaniumFront;
            }});

            ((ItemTurret) Blocks.cyclone).ammoTypes.put(Vars.content.item("ec-"+"metaglass"+num),new FlakBulletType(4f, (float) (6*Math.pow(damageBase,num))){{
                ammoMultiplier = 2f;
                shootEffect = Fx.shootSmall;
                reloadMultiplier = 0.8f;
                width = (float) (6f*Math.pow(sizeBase,num));
                height = (float) (8f*Math.pow(sizeBase,num));
                hitEffect = Fx.flakExplosion;
                splashDamage = (float) (45f*Math.pow(damageBase,num));
                splashDamageRadius = (float) (25f*Math.pow(sizeBase,num));
                fragBullet = new BasicBulletType(3f, (float) (12*Math.pow(damageBase,num)), "bullet"){{
                    width = (float) (5f*Math.pow(sizeBase,num));
                    height = (float) (12f*Math.pow(sizeBase,num));
                    shrinkY = (float) (1f*Math.pow(sizeBase,num));
                    lifetime = 20f;
                    backColor = Pal.gray;
                    frontColor = Color.white;
                    despawnEffect = Fx.none;
                }};
                fragBullets = (int) (4*Math.pow(sizeBase,num));
                explodeRange = (float) (20f*Math.pow(sizeBase,num));
                collidesGround = true;
            }});
            ((ItemTurret) Blocks.cyclone).ammoTypes.put(Vars.content.item("ec-"+"blastCompound"+num),new FlakBulletType(4f, (float) (8*Math.pow(damageBase,num))){{
                shootEffect = Fx.shootBig;
                ammoMultiplier = 5f;
                splashDamage = (float) (45f*Math.pow(damageBase,num));
                splashDamageRadius = (float) (60f*Math.pow(sizeBase,num));
                collidesGround = true;

                status = StatusEffects.blasted;
                statusDuration = (float) (60f*Math.pow(sizeBase,num));
            }});
            ((ItemTurret) Blocks.cyclone).ammoTypes.put(Vars.content.item("ec-"+"plastanium"+num),new FlakBulletType(4f, (float) (8*Math.pow(damageBase,num))){{
                ammoMultiplier = (float) (4f*Math.pow(sizeBase,num));
                splashDamageRadius = (float) (40f*Math.pow(sizeBase,num));
                splashDamage = (float) (37.5f*Math.pow(damageBase,num));
                fragBullet = new BasicBulletType(2.5f, (float) (12*Math.pow(damageBase,num)), "bullet"){{
                    width = (float) (10f*Math.pow(sizeBase,num));
                    height = (float) (12f*Math.pow(sizeBase,num));
                    shrinkY = (float) (1f*Math.pow(sizeBase,num));
                    lifetime = 15f;
                    backColor = Pal.plastaniumBack;
                    frontColor = Pal.plastaniumFront;
                    despawnEffect = Fx.none;
                }};
                fragBullets = (int) (6*Math.pow(sizeBase,num));
                hitEffect = Fx.plasticExplosion;
                frontColor = Pal.plastaniumFront;
                backColor = Pal.plastaniumBack;
                shootEffect = Fx.shootBig;
                collidesGround = true;
                explodeRange = (float) (20f*Math.pow(sizeBase,num));
            }});
            ((ItemTurret) Blocks.cyclone).ammoTypes.put(Vars.content.item("ec-"+"surgeAlloy"+num),new FlakBulletType(4.5f, (float) (13*Math.pow(damageBase,num))){{
                ammoMultiplier = 5f;
                splashDamage = (float) (50f * 1.5f*Math.pow(damageBase,num));
                splashDamageRadius = (float) (38f*Math.pow(sizeBase,num));
                lightning = (int) (2*Math.pow(sizeBase,num));
                lightningLength = (int) (7*Math.pow(sizeBase,num));
                shootEffect = Fx.shootBig;
                collidesGround = true;
                explodeRange = (float) (20f*Math.pow(sizeBase,num));
            }});

            ((ItemTurret) Blocks.foreshadow).ammoTypes.put(Vars.content.item("ec-"+"surgeAlloy"+num),new RailBulletType(){{
                shootEffect = Fx.instShoot;
                hitEffect = Fx.instHit;
                pierceEffect = Fx.railHit;
                smokeEffect = Fx.smokeCloud;
                pointEffect = Fx.instTrail;
                despawnEffect = Fx.instBomb;
                pointEffectSpace = (float) (20f*Math.pow(sizeBase,num));
                damage = (float) (1350*Math.pow(damageBase,num));
                buildingDamageMultiplier = (float) (1-0.8f/Math.pow(damageBase,num));
                //maxDamageFraction = 0.6f;
                pierceDamageFactor = (float) (1f*Math.pow(damageBase,num));
                length = (float) (500f*Math.pow(sizeBase,num));
                hitShake = (float) (6f*Math.pow(sizeBase,num));
                ammoMultiplier = 1f;
            }});

            ((ItemTurret) Blocks.spectre).ammoTypes.put(Vars.content.item("ec-"+"graphite"+num),new BasicBulletType(7.5f, (float) (50*Math.pow(damageBase,num))){{
                hitSize = (float) (4.8f*Math.pow(sizeBase,num));
                width = (float) (15f*Math.pow(sizeBase,num));
                height = (float) (21f*Math.pow(sizeBase,num));
                shootEffect = Fx.shootBig;
                ammoMultiplier = 4;
                reloadMultiplier = 1.7f;
                knockback = (float) (0.3f*Math.pow(sizeBase,num));
            }});
            ((ItemTurret) Blocks.spectre).ammoTypes.put(Vars.content.item("ec-"+"thorium"+num),new BasicBulletType(8f, (float) (80*Math.pow(damageBase,num))){{
                hitSize = (float) (5*Math.pow(sizeBase,num));
                width = (float) (16f*Math.pow(sizeBase,num));
                height = (float) (23f*Math.pow(sizeBase,num));
                shootEffect = Fx.shootBig;
                pierceCap = (int) (2*Math.pow(sizeBase,num));
                pierceBuilding = true;
                knockback = (float) (0.7f*Math.pow(sizeBase,num));
            }});
            ((ItemTurret) Blocks.spectre).ammoTypes.put(Vars.content.item("ec-"+"pyratite"+num),new BasicBulletType(7f, (float) (70*Math.pow(damageBase,num))){{
                hitSize = (float) (5*Math.pow(sizeBase,num));
                width = (float) (16f*Math.pow(sizeBase,num));
                height = (float) (21f*Math.pow(sizeBase,num));
                frontColor = Pal.lightishOrange;
                backColor = Pal.lightOrange;
                status = StatusEffects.burning;
                hitEffect = new MultiEffect(Fx.hitBulletSmall, Fx.fireHit);
                shootEffect = Fx.shootBig;
                makeFire = true;
                pierceCap = (int) (2*Math.pow(sizeBase,num));
                pierceBuilding = true;
                knockback = (float) (0.6f*Math.pow(sizeBase,num));
                ammoMultiplier = 3;
                splashDamage = (float) (20f*Math.pow(damageBase,num));
                splashDamageRadius = (float) (25f*Math.pow(sizeBase,num));
            }});




            load.conveyor("","copper",4.2f,num);
            load.titaniumConveyor(num);

            load.drill("mechanicalDrill","copper",600,num);

            load.wall("copper","copper",80,num);
            load.wall("titanium","titanium",110,num);
            float healthBase = 5;
            new Wall("plastaniumWall"+num){{
                requirements(Category.defense, with(Vars.content.item("ec-"+"plastanium"+num), 5, Vars.content.item("ec-"+"metaglass"+num), 2));
                health = (int) (125 * 4 * Math.pow(healthBase,num));
                insulated = true;
                absorbLasers = true;
                schematicPriority = 10;
                envDisabled |= Env.scorching;
                researchCostMultiplier = 0.1f;
            }};
            new Wall("thoriumWall"+num){{
                requirements(Category.defense, with(Vars.content.item("ec-"+"thorium"+num), 6));
                health = (int) (200 * 4* Math.pow(healthBase,num));
                envDisabled |= Env.scorching;
                researchCostMultiplier = 0.1f;
            }};
            new Wall("phaseWall"+num){{
                requirements(Category.defense, with(Vars.content.item("ec-"+"phaseFabric"+num), 6));
                health = (int) (150 * 4* Math.pow(healthBase,num));
                chanceDeflect = (float) (100f-90f/Math.pow(sizeBase,num));
                flashHit = true;
                envDisabled |= Env.scorching;
                researchCostMultiplier = 0.1f;
            }};
            new Wall("surgeWall"+num){{
                requirements(Category.defense, with(Vars.content.item("ec-"+"surgeAlloy"+num), 6));
                health = (int) (230 * 4* Math.pow(healthBase,num));
                lightningChance = (float) (1f-0.95f/Math.pow(sizeBase,num));
                lightningDamage = (float) (5*Math.pow(damageBase,num));
                envDisabled |= Env.scorching;
                researchCostMultiplier = 0.1f;
            }};
            new Door("door"+num){{
                requirements(Category.defense, with(Vars.content.item("ec-"+"titanium"+num), 6,Vars.content.item("ec-"+"silicon"+num), 4));
                health = (int) (100 * 4* Math.pow(healthBase,num));
                envDisabled |= Env.scorching;
                researchCostMultiplier = 0.1f;
            }};
        };






    };
}
