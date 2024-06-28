package ec;


import arc.struct.Seq;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import multicraft.IOEntry;
import multicraft.MultiCrafter;
import multicraft.Recipe;
import multicraft.*;
import mindustry.*;
import arc.*;

import static mindustry.type.ItemStack.with;
import static mindustry.world.meta.StatValues.ammo;
import static mindustry.world.meta.StatValues.content;


public class ECBlocks {
    public static void load(){

        double damageBase = 10;
        double sizeBase = 1.3;
        ((ItemTurret) Blocks.duo).ammoTypes.put(ECItems.copper1, new BasicBulletType(2.5f, (float) (9*Math.pow(damageBase,1))){{
            width = (float) (7f*Math.pow(sizeBase,1));height = (float) (9f*Math.pow(sizeBase,1));lifetime = 60f;ammoMultiplier = 2;}});
        ((ItemTurret) Blocks.duo).ammoTypes.put(ECItems.copper2, new BasicBulletType(2.5f, (float) (9*Math.pow(damageBase,2))){{
            width = (float) (7f*Math.pow(sizeBase,2));height = (float) (9f*Math.pow(sizeBase,2));lifetime = 60f;ammoMultiplier = 2;}});
        ((ItemTurret) Blocks.duo).ammoTypes.put(ECItems.copper3, new BasicBulletType(2.5f, (float) (9*Math.pow(damageBase,3))){{
            width = (float) (7f*Math.pow(sizeBase,3));height = (float) (9f*Math.pow(sizeBase,3));lifetime = 60f;ammoMultiplier = 2;}});
        ((ItemTurret) Blocks.duo).ammoTypes.put(ECItems.copper4, new BasicBulletType(2.5f, (float) (9*Math.pow(damageBase,4))){{
            width = (float) (7f*Math.pow(sizeBase,4));height = (float) (9f*Math.pow(sizeBase,4));lifetime = 60f;ammoMultiplier = 2;}});
        ((ItemTurret) Blocks.duo).ammoTypes.put(ECItems.copper5, new BasicBulletType(2.5f, (float) (9*Math.pow(damageBase,5))){{
            width = (float) (7f*Math.pow(sizeBase,5));height = (float) (9f*Math.pow(sizeBase,5));lifetime = 60f;ammoMultiplier = 2;}});
        ((ItemTurret) Blocks.duo).ammoTypes.put(ECItems.copper6, new BasicBulletType(2.5f, (float) (9*Math.pow(damageBase,6))){{
            width = (float) (7f*Math.pow(sizeBase,6));height = (float) (9f*Math.pow(sizeBase,6));lifetime = 60f;ammoMultiplier = 2;}});
        ((ItemTurret) Blocks.duo).ammoTypes.put(ECItems.copper7, new BasicBulletType(2.5f, (float) (9*Math.pow(damageBase,7))){{
            width = (float) (7f*Math.pow(sizeBase,7));height = (float) (9f*Math.pow(sizeBase,7));lifetime = 60f;ammoMultiplier = 2;}});
        ((ItemTurret) Blocks.duo).ammoTypes.put(ECItems.copper8, new BasicBulletType(2.5f, (float) (9*Math.pow(damageBase,8))){{
            width = (float) (7f*Math.pow(sizeBase,8));height = (float) (9f*Math.pow(sizeBase,8));lifetime = 60f;ammoMultiplier = 2;}});
        ((ItemTurret) Blocks.duo).ammoTypes.put(ECItems.copper9, new BasicBulletType(2.5f, (float) (9*Math.pow(damageBase,9))){{
            width = (float) (7f*Math.pow(sizeBase,9));height = (float) (9f*Math.pow(sizeBase,9));lifetime = 60f;ammoMultiplier = 2;}});

    };



    private static final float makeTime = 120f;

    public static Block copperCompressor = new MultiCrafter("copperCompressor"){{
        requirements(Category.crafting, with(Items.copper, 30));
        size = 2;
        hasPower = false;
        hasLiquids = false;
        itemCapacity = 18;
        craftEffect = Fx.pulverizeMedium;
        alwaysUnlocked = true;


        resolvedRecipes = Seq.with(
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                Items.copper, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.copper1, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.copper1, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.copper2, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.copper2, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.copper3, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.copper3, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.copper4, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.copper4, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.copper5, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.copper5, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.copper6, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.copper6, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.copper7, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.copper7, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.copper8, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.copper8, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.copper9, 1));}};
                    craftTime = ECBlocks.makeTime;
                }}
        );

    }};
    /*
    public static Block leadCompressor = new MultiCrafter("leadCompressor"){{
        requirements(Category.crafting, with(Items.lead, 30));
        size = 2;
        hasPower = false;
        hasLiquids = false;
        itemCapacity = 18;
        craftEffect = Fx.pulverizeMedium;

        resolvedRecipes = Seq.with(
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                Items.lead, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.lead1, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.lead1, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.lead2, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.lead2, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.lead3, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.lead3, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.lead4, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.lead4, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.lead5, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.lead5, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.lead6, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.lead6, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.lead7, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.lead7, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.lead8, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.lead8, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.lead9, 1));}};
                    craftTime = ECBlocks.makeTime;
                }}
        );

    }};
    */
}
