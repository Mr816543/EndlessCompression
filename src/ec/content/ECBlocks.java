package ec.content;


import arc.struct.Seq;
import ec.Blocks.*;
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

import static ec.content.ECItems.*;
import static mindustry.type.ItemStack.with;
import static mindustry.world.meta.StatValues.ammo;
import static mindustry.world.meta.StatValues.content;


public class ECBlocks {
    public static void load(){
        double damageBase = 10;
        double sizeBase = 1.3;
        ((ItemTurret) Blocks.duo).ammoTypes.put(copper1, new BasicBulletType(2.5f, (float) (9*Math.pow(damageBase,1))){{
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


        ((ItemTurret) Blocks.scatter).ammoTypes.put(lead1, new BasicBulletType(4.2f, (float) (3*Math.pow(damageBase,1))) {{
            ammoMultiplier = 4f;
            shootEffect = Fx.shootSmall;
            hitEffect = Fx.flakExplosion;
            splashDamage = (float) (27f* 1.5f*Math.pow(damageBase,1));
            splashDamageRadius = (float) (15f*Math.pow(damageBase,1));
            width = (float) (6f*Math.pow(sizeBase,1));
            height = (float) (8f*Math.pow(sizeBase,1));
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(lead2, new BasicBulletType(4.2f, (float) (3*Math.pow(damageBase,2))) {{
            ammoMultiplier = 4f;
            shootEffect = Fx.shootSmall;
            hitEffect = Fx.flakExplosion;
            splashDamage = (float) (27f* 1.5f*Math.pow(damageBase,2));
            splashDamageRadius = (float) (15f*Math.pow(damageBase,2));
            width = (float) (6f*Math.pow(sizeBase,2));
            height = (float) (8f*Math.pow(sizeBase,2));
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(lead3, new BasicBulletType(4.2f, (float) (3*Math.pow(damageBase,3))) {{
            ammoMultiplier = 4f;
            shootEffect = Fx.shootSmall;
            hitEffect = Fx.flakExplosion;
            splashDamage = (float) (27f* 1.5f*Math.pow(damageBase,3));
            splashDamageRadius = (float) (15f*Math.pow(damageBase,3));
            width = (float) (6f*Math.pow(sizeBase,3));
            height = (float) (8f*Math.pow(sizeBase,3));
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(lead4, new BasicBulletType(4.2f, (float) (3*Math.pow(damageBase,4))) {{
            ammoMultiplier = 4f;
            shootEffect = Fx.shootSmall;
            hitEffect = Fx.flakExplosion;
            splashDamage = (float) (27f* 1.5f*Math.pow(damageBase,4));
            splashDamageRadius = (float) (15f*Math.pow(damageBase,4));
            width = (float) (6f*Math.pow(sizeBase,4));
            height = (float) (8f*Math.pow(sizeBase,4));
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(lead5, new BasicBulletType(4.2f, (float) (3*Math.pow(damageBase,5))) {{
            ammoMultiplier = 4f;
            shootEffect = Fx.shootSmall;
            hitEffect = Fx.flakExplosion;
            splashDamage = (float) (27f* 1.5f*Math.pow(damageBase,5));
            splashDamageRadius = (float) (15f*Math.pow(damageBase,5));
            width = (float) (6f*Math.pow(sizeBase,5));
            height = (float) (8f*Math.pow(sizeBase,5));
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(lead6, new BasicBulletType(4.2f, (float) (3*Math.pow(damageBase,6))) {{
            ammoMultiplier = 4f;
            shootEffect = Fx.shootSmall;
            hitEffect = Fx.flakExplosion;
            splashDamage = (float) (27f* 1.5f*Math.pow(damageBase,6));
            splashDamageRadius = (float) (15f*Math.pow(damageBase,6));
            width = (float) (6f*Math.pow(sizeBase,6));
            height = (float) (8f*Math.pow(sizeBase,6));
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(lead7, new BasicBulletType(4.2f, (float) (3*Math.pow(damageBase,7))) {{
            ammoMultiplier = 4f;
            shootEffect = Fx.shootSmall;
            hitEffect = Fx.flakExplosion;
            splashDamage = (float) (27f* 1.5f*Math.pow(damageBase,7));
            splashDamageRadius = (float) (15f*Math.pow(damageBase,7));
            width = (float) (6f*Math.pow(sizeBase,7));
            height = (float) (8f*Math.pow(sizeBase,7));
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(lead8, new BasicBulletType(4.2f, (float) (3*Math.pow(damageBase,8))) {{
            ammoMultiplier = 4f;
            shootEffect = Fx.shootSmall;
            hitEffect = Fx.flakExplosion;
            splashDamage = (float) (27f* 1.5f*Math.pow(damageBase,8));
            splashDamageRadius = (float) (15f*Math.pow(damageBase,8));
            width = (float) (6f*Math.pow(sizeBase,8));
            height = (float) (8f*Math.pow(sizeBase,8));
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(lead9, new BasicBulletType(4.2f, (float) (3*Math.pow(damageBase,9))) {{
            ammoMultiplier = 4f;
            shootEffect = Fx.shootSmall;
            hitEffect = Fx.flakExplosion;
            splashDamage = (float) (27f* 1.5f*Math.pow(damageBase,9));
            splashDamageRadius = (float) (15f*Math.pow(damageBase,9));
            width = (float) (6f*Math.pow(sizeBase,9));
            height = (float) (8f*Math.pow(sizeBase,9));
            lifetime = 60f;
        }});

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
                                copper1, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                copper1, 9));}};
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
    public static Block sandCompressor = new MultiCrafter("sandCompressor"){{
        requirements(Category.crafting, with(Items.sand, 30));
        size = 2;
        hasPower = false;
        hasLiquids = false;
        itemCapacity = 18;
        craftEffect = Fx.pulverizeMedium;

        resolvedRecipes = Seq.with(
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                Items.sand, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sand1, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sand1, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sand2, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sand2, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sand3, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sand3, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sand4, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sand4, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sand5, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sand5, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sand6, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sand6, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sand7, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sand7, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sand8, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sand8, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sand9, 1));}};
                    craftTime = ECBlocks.makeTime;
                }}
        );

    }};
    public static Block titaniumCompressor = new MultiCrafter("titaniumCompressor"){{
        requirements(Category.crafting, with(Items.titanium, 30));
        size = 2;
        hasPower = false;
        hasLiquids = false;
        itemCapacity = 18;
        craftEffect = Fx.pulverizeMedium;

        resolvedRecipes = Seq.with(
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                Items.titanium, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.titanium1, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.titanium1, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.titanium2, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.titanium2, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.titanium3, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.titanium3, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.titanium4, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.titanium4, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.titanium5, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.titanium5, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.titanium6, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.titanium6, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.titanium7, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.titanium7, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.titanium8, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.titanium8, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.titanium9, 1));}};
                    craftTime = ECBlocks.makeTime;
                }}
        );

    }};



    public static ECWalls copperWall1 = new ECWalls("copperWall1",1,80,copper1){};
    public static ECWalls copperWall2 = new ECWalls("copperWall2",2,80,copper2){};
    public static ECWalls copperWall3 = new ECWalls("copperWall3",3,80,copper3){};
    public static ECWalls copperWall4 = new ECWalls("copperWall4",4,80,copper4){};
    public static ECWalls copperWall5 = new ECWalls("copperWall5",5,80,copper5){};
    public static ECWalls copperWall6 = new ECWalls("copperWall6",6,80,copper6){};
    public static ECWalls copperWall7 = new ECWalls("copperWall7",7,80,copper7){};
    public static ECWalls copperWall8 = new ECWalls("copperWall8",8,80,copper8){};
    public static ECWalls copperWall9 = new ECWalls("copperWall9",9,80,copper9){};

    /*
    public ECWallsLarge copperWallLarge1 = new ECWallsLarge("copperWallLarge1",1,80,copper1){};
    public ECWallsLarge copperWallLarge2 = new ECWallsLarge("copperWallLarge2",2,80,copper2){};
    public ECWallsLarge copperWallLarge3 = new ECWallsLarge("copperWallLarge3",3,80,copper3){};
    public ECWallsLarge copperWallLarge4 = new ECWallsLarge("copperWallLarge4",4,80,copper4){};
    public ECWallsLarge copperWallLarge5 = new ECWallsLarge("copperWallLarge5",5,80,copper5){};
    public ECWallsLarge copperWallLarge6 = new ECWallsLarge("copperWallLarge6",6,80,copper6){};
    public ECWallsLarge copperWallLarge7 = new ECWallsLarge("copperWallLarge7",7,80,copper7){};
    public ECWallsLarge copperWallLarge8 = new ECWallsLarge("copperWallLarge8",8,80,copper8){};
    public ECWallsLarge copperWallLarge9 = new ECWallsLarge("copperWallLarge9",9,80,copper9){};

     */

    public static ECWalls titaniumWall1 = new ECWalls("titaniumWall1",1,110,titanium1){};
    public static ECWalls titaniumWall2 = new ECWalls("titaniumWall2",2,110,titanium2){};
    public static ECWalls titaniumWall3 = new ECWalls("titaniumWall3",3,110,titanium3){};
    public static ECWalls titaniumWall4 = new ECWalls("titaniumWall4",4,110,titanium4){};
    public static ECWalls titaniumWall5 = new ECWalls("titaniumWall5",5,110,titanium5){};
    public static ECWalls titaniumWall6 = new ECWalls("titaniumWall6",6,110,titanium6){};
    public static ECWalls titaniumWall7 = new ECWalls("titaniumWall7",7,110,titanium7){};
    public static ECWalls titaniumWall8 = new ECWalls("titaniumWall8",8,110,titanium8){};
    public static ECWalls titaniumWall9 = new ECWalls("titaniumWall9",9,110,titanium9){};

}
