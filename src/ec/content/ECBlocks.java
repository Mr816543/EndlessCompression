package ec.content;


import arc.graphics.Color;
import arc.struct.Seq;
import ec.Blocks.*;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.Damage;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.logic.LExecutor;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Door;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.LiquidTurret;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.Env;
import multicraft.*;

import static ec.content.ECItems.*;
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
        };

        String[] liquid0 = {"water","slag","oil","cryofluid"};
        for (int i = 0 ; i < liquid0.length ; i++){
            load.liquidCompressor(liquid0[i],"copper");
        };


        double damageBase = 4;
        double sizeBase = 1.4;
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
                pointEffectSpace = (float) (20f*Math.pow(damageBase,num));
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

            load.drill("mechanicalDrill","copper",600,num);

            load.wall("copper","copper",80,num);
            load.wall("titanium","titanium",110,num);
            float healthBase = 4;
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







/*
        ((ItemTurret) Blocks.duo).ammoTypes.put(copper1, new BasicBulletType(2.5f, (float) (9*Math.pow(damageBase,1))){{
            width = (float) (7f*Math.pow(sizeBase,1));height = (float) (9f*Math.pow(sizeBase,1));lifetime = 60f;ammoMultiplier = 2;}});
        ((ItemTurret) Blocks.duo).ammoTypes.put(copper2, new BasicBulletType(2.5f, (float) (9*Math.pow(damageBase,2))){{
            width = (float) (7f*Math.pow(sizeBase,2));height = (float) (9f*Math.pow(sizeBase,2));lifetime = 60f;ammoMultiplier = 2;}});
        ((ItemTurret) Blocks.duo).ammoTypes.put(copper3, new BasicBulletType(2.5f, (float) (9*Math.pow(damageBase,3))){{
            width = (float) (7f*Math.pow(sizeBase,3));height = (float) (9f*Math.pow(sizeBase,3));lifetime = 60f;ammoMultiplier = 2;}});
        ((ItemTurret) Blocks.duo).ammoTypes.put(copper4, new BasicBulletType(2.5f, (float) (9*Math.pow(damageBase,4))){{
            width = (float) (7f*Math.pow(sizeBase,4));height = (float) (9f*Math.pow(sizeBase,4));lifetime = 60f;ammoMultiplier = 2;}});
        ((ItemTurret) Blocks.duo).ammoTypes.put(copper5, new BasicBulletType(2.5f, (float) (9*Math.pow(damageBase,5))){{
            width = (float) (7f*Math.pow(sizeBase,5));height = (float) (9f*Math.pow(sizeBase,5));lifetime = 60f;ammoMultiplier = 2;}});
        ((ItemTurret) Blocks.duo).ammoTypes.put(copper6, new BasicBulletType(2.5f, (float) (9*Math.pow(damageBase,6))){{
            width = (float) (7f*Math.pow(sizeBase,6));height = (float) (9f*Math.pow(sizeBase,6));lifetime = 60f;ammoMultiplier = 2;}});
        ((ItemTurret) Blocks.duo).ammoTypes.put(copper7, new BasicBulletType(2.5f, (float) (9*Math.pow(damageBase,7))){{
            width = (float) (7f*Math.pow(sizeBase,7));height = (float) (9f*Math.pow(sizeBase,7));lifetime = 60f;ammoMultiplier = 2;}});
        ((ItemTurret) Blocks.duo).ammoTypes.put(copper8, new BasicBulletType(2.5f, (float) (9*Math.pow(damageBase,8))){{
            width = (float) (7f*Math.pow(sizeBase,8));height = (float) (9f*Math.pow(sizeBase,8));lifetime = 60f;ammoMultiplier = 2;}});
        ((ItemTurret) Blocks.duo).ammoTypes.put(copper9, new BasicBulletType(2.5f, (float) (9*Math.pow(damageBase,9))){{
            width = (float) (7f*Math.pow(sizeBase,9));height = (float) (9f*Math.pow(sizeBase,9));lifetime = 60f;ammoMultiplier = 2;}});

        ((ItemTurret) Blocks.duo).ammoTypes.put(graphite1, new BasicBulletType(3.5f, (float) (18*Math.pow(damageBase,1))){{
            width = (float) (9f*Math.pow(sizeBase,1));
            height = (float) (12f*Math.pow(sizeBase,1));
            reloadMultiplier = 0.6f;
            ammoMultiplier = 4;
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.duo).ammoTypes.put(graphite2, new BasicBulletType(3.5f, (float) (18*Math.pow(damageBase,2))){{
            width = (float) (9f*Math.pow(sizeBase,2));
            height = (float) (12f*Math.pow(sizeBase,2));
            reloadMultiplier = 0.6f;
            ammoMultiplier = 4;
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.duo).ammoTypes.put(graphite3, new BasicBulletType(3.5f, (float) (18*Math.pow(damageBase,3))){{
            width = (float) (9f*Math.pow(sizeBase,3));
            height = (float) (12f*Math.pow(sizeBase,3));
            reloadMultiplier = 0.6f;
            ammoMultiplier = 4;
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.duo).ammoTypes.put(graphite4, new BasicBulletType(3.5f, (float) (18*Math.pow(damageBase,4))){{
            width = (float) (9f*Math.pow(sizeBase,4));
            height = (float) (12f*Math.pow(sizeBase,4));
            reloadMultiplier = 0.6f;
            ammoMultiplier = 4;
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.duo).ammoTypes.put(graphite5, new BasicBulletType(3.5f, (float) (18*Math.pow(damageBase,5))){{
            width = (float) (9f*Math.pow(sizeBase,5));
            height = (float) (12f*Math.pow(sizeBase,5));
            reloadMultiplier = 0.6f;
            ammoMultiplier = 4;
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.duo).ammoTypes.put(graphite6, new BasicBulletType(3.5f, (float) (18*Math.pow(damageBase,6))){{
            width = (float) (9f*Math.pow(sizeBase,6));
            height = (float) (12f*Math.pow(sizeBase,6));
            reloadMultiplier = 0.6f;
            ammoMultiplier = 4;
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.duo).ammoTypes.put(graphite7, new BasicBulletType(3.5f, (float) (18*Math.pow(damageBase,7))){{
            width = (float) (9f*Math.pow(sizeBase,7));
            height = (float) (12f*Math.pow(sizeBase,7));
            reloadMultiplier = 0.6f;
            ammoMultiplier = 4;
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.duo).ammoTypes.put(graphite8, new BasicBulletType(3.5f, (float) (18*Math.pow(damageBase,8))){{
            width = (float) (9f*Math.pow(sizeBase,8));
            height = (float) (12f*Math.pow(sizeBase,8));
            reloadMultiplier = 0.6f;
            ammoMultiplier = 4;
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.duo).ammoTypes.put(graphite9, new BasicBulletType(3.5f, (float) (18*Math.pow(damageBase,9))){{
            width = (float) (9f*Math.pow(sizeBase,9));
            height = (float) (12f*Math.pow(sizeBase,9));
            reloadMultiplier = 0.6f;
            ammoMultiplier = 4;
            lifetime = 60f;
        }});

        ((ItemTurret) Blocks.duo).ammoTypes.put(silicon1, new BasicBulletType(3f, (float) (12*Math.pow(damageBase,1))){{
            width = (float) (7f*Math.pow(sizeBase,1));
            height = (float) (9f*Math.pow(sizeBase,1));
            homingPower = (float) (0.1f*Math.pow(sizeBase,1));
            reloadMultiplier = 1.5f;
            ammoMultiplier = 5;
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.duo).ammoTypes.put(silicon2, new BasicBulletType(3f, (float) (12*Math.pow(damageBase,2))){{
            width = (float) (7f*Math.pow(sizeBase,2));
            height = (float) (9f*Math.pow(sizeBase,2));
            homingPower = (float) (0.1f*Math.pow(sizeBase,2));
            reloadMultiplier = 1.5f;
            ammoMultiplier = 5;
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.duo).ammoTypes.put(silicon3, new BasicBulletType(3f, (float) (12*Math.pow(damageBase,3))){{
            width = (float) (7f*Math.pow(sizeBase,3));
            height = (float) (9f*Math.pow(sizeBase,3));
            homingPower = (float) (0.1f*Math.pow(sizeBase,3));
            reloadMultiplier = 1.5f;
            ammoMultiplier = 5;
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.duo).ammoTypes.put(silicon4, new BasicBulletType(3f, (float) (12*Math.pow(damageBase,4))){{
            width = (float) (7f*Math.pow(sizeBase,4));
            height = (float) (9f*Math.pow(sizeBase,4));
            homingPower = (float) (0.1f*Math.pow(sizeBase,4));
            reloadMultiplier = 1.5f;
            ammoMultiplier = 5;
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.duo).ammoTypes.put(silicon5, new BasicBulletType(3f, (float) (12*Math.pow(damageBase,5))){{
            width = (float) (7f*Math.pow(sizeBase,5));
            height = (float) (9f*Math.pow(sizeBase,5));
            homingPower = (float) (0.1f*Math.pow(sizeBase,5));
            reloadMultiplier = 1.5f;
            ammoMultiplier = 5;
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.duo).ammoTypes.put(silicon6, new BasicBulletType(3f, (float) (12*Math.pow(damageBase,6))){{
            width = (float) (7f*Math.pow(sizeBase,6));
            height = (float) (9f*Math.pow(sizeBase,6));
            homingPower = (float) (0.1f*Math.pow(sizeBase,6));
            reloadMultiplier = 1.5f;
            ammoMultiplier = 5;
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.duo).ammoTypes.put(silicon7, new BasicBulletType(3f, (float) (12*Math.pow(damageBase,7))){{
            width = (float) (7f*Math.pow(sizeBase,7));
            height = (float) (9f*Math.pow(sizeBase,7));
            homingPower = (float) (0.1f*Math.pow(sizeBase,7));
            reloadMultiplier = 1.5f;
            ammoMultiplier = 5;
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.duo).ammoTypes.put(silicon8, new BasicBulletType(3f, (float) (12*Math.pow(damageBase,8))){{
            width = (float) (7f*Math.pow(sizeBase,8));
            height = (float) (9f*Math.pow(sizeBase,8));
            homingPower = (float) (0.1f*Math.pow(sizeBase,8));
            reloadMultiplier = 1.5f;
            ammoMultiplier = 5;
            lifetime = 60f;
        }});
        ((ItemTurret) Blocks.duo).ammoTypes.put(silicon9, new BasicBulletType(3f, (float) (12*Math.pow(damageBase,9))){{
            width = (float) (7f*Math.pow(sizeBase,9));
            height = (float) (9f*Math.pow(sizeBase,9));
            homingPower = (float) (0.1f*Math.pow(sizeBase,9));
            reloadMultiplier = 1.5f;
            ammoMultiplier = 5;
            lifetime = 60f;
        }});


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

        ((ItemTurret) Blocks.scatter).ammoTypes.put(metaglass1, new FlakBulletType(4f, (float) (3*Math.pow(damageBase,1))){{
            lifetime = 60f;
            ammoMultiplier = 5f;
            shootEffect = Fx.shootSmall;
            reloadMultiplier = 0.8f;
            hitEffect = Fx.flakExplosion;
            width = (float) (6f*Math.pow(sizeBase,1));
            height = (float) (8f*Math.pow(sizeBase,1));
            splashDamage = (float) (30f * 1.5f*Math.pow(damageBase,1));
            splashDamageRadius = (float) (20f*Math.pow(damageBase,1));
            fragBullets = (int) (6*Math.pow(sizeBase,1));
            fragBullet = new BasicBulletType(3f, (float) (5*Math.pow(damageBase,1))){{
                width = (float) (5f*Math.pow(sizeBase,1));
                height = (float) (12f*Math.pow(sizeBase,1));
                shrinkY = 1f;
                lifetime = 20f;
                backColor = Pal.gray;
                frontColor = Color.white;
                despawnEffect = Fx.none;
                collidesGround = false;
            }};
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(metaglass2, new FlakBulletType(4f, (float) (3*Math.pow(damageBase,2))){{
            lifetime = 60f;
            ammoMultiplier = 5f;
            shootEffect = Fx.shootSmall;
            reloadMultiplier = 0.8f;
            hitEffect = Fx.flakExplosion;
            width = (float) (6f*Math.pow(sizeBase,2));
            height = (float) (8f*Math.pow(sizeBase,2));
            splashDamage = (float) (30f * 1.5f*Math.pow(damageBase,2));
            splashDamageRadius = (float) (20f*Math.pow(damageBase,2));
            fragBullets = (int) (6*Math.pow(sizeBase,2));
            fragBullet = new BasicBulletType(3f, (float) (5*Math.pow(damageBase,2))){{
                width = (float) (5f*Math.pow(sizeBase,2));
                height = (float) (12f*Math.pow(sizeBase,2));
                shrinkY = 1f;
                lifetime = 20f;
                backColor = Pal.gray;
                frontColor = Color.white;
                despawnEffect = Fx.none;
                collidesGround = false;
            }};
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(metaglass3, new FlakBulletType(4f, (float) (3*Math.pow(damageBase,3))){{
            lifetime = 60f;
            ammoMultiplier = 5f;
            shootEffect = Fx.shootSmall;
            reloadMultiplier = 0.8f;
            hitEffect = Fx.flakExplosion;
            width = (float) (6f*Math.pow(sizeBase,3));
            height = (float) (8f*Math.pow(sizeBase,3));
            splashDamage = (float) (30f * 1.5f*Math.pow(damageBase,3));
            splashDamageRadius = (float) (20f*Math.pow(damageBase,3));
            fragBullets = (int) (6*Math.pow(sizeBase,3));
            fragBullet = new BasicBulletType(3f, (float) (5*Math.pow(damageBase,3))){{
                width = (float) (5f*Math.pow(sizeBase,3));
                height = (float) (12f*Math.pow(sizeBase,3));
                shrinkY = 1f;
                lifetime = 20f;
                backColor = Pal.gray;
                frontColor = Color.white;
                despawnEffect = Fx.none;
                collidesGround = false;
            }};
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(metaglass4, new FlakBulletType(4f, (float) (3*Math.pow(damageBase,4))){{
            lifetime = 60f;
            ammoMultiplier = 5f;
            shootEffect = Fx.shootSmall;
            reloadMultiplier = 0.8f;
            hitEffect = Fx.flakExplosion;
            width = (float) (6f*Math.pow(sizeBase,4));
            height = (float) (8f*Math.pow(sizeBase,4));
            splashDamage = (float) (30f * 1.5f*Math.pow(damageBase,4));
            splashDamageRadius = (float) (20f*Math.pow(damageBase,4));
            fragBullets = (int) (6*Math.pow(sizeBase,4));
            fragBullet = new BasicBulletType(3f, (float) (5*Math.pow(damageBase,4))){{
                width = (float) (5f*Math.pow(sizeBase,4));
                height = (float) (12f*Math.pow(sizeBase,4));
                shrinkY = 1f;
                lifetime = 20f;
                backColor = Pal.gray;
                frontColor = Color.white;
                despawnEffect = Fx.none;
                collidesGround = false;
            }};
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(metaglass5, new FlakBulletType(4f, (float) (3*Math.pow(damageBase,5))){{
            lifetime = 60f;
            ammoMultiplier = 5f;
            shootEffect = Fx.shootSmall;
            reloadMultiplier = 0.8f;
            hitEffect = Fx.flakExplosion;
            width = (float) (6f*Math.pow(sizeBase,5));
            height = (float) (8f*Math.pow(sizeBase,5));
            splashDamage = (float) (30f * 1.5f*Math.pow(damageBase,5));
            splashDamageRadius = (float) (20f*Math.pow(damageBase,5));
            fragBullets = (int) (6*Math.pow(sizeBase,5));
            fragBullet = new BasicBulletType(3f, (float) (5*Math.pow(damageBase,5))){{
                width = (float) (5f*Math.pow(sizeBase,5));
                height = (float) (12f*Math.pow(sizeBase,5));
                shrinkY = 1f;
                lifetime = 20f;
                backColor = Pal.gray;
                frontColor = Color.white;
                despawnEffect = Fx.none;
                collidesGround = false;
            }};
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(metaglass6, new FlakBulletType(4f, (float) (3*Math.pow(damageBase,6))){{
            lifetime = 60f;
            ammoMultiplier = 5f;
            shootEffect = Fx.shootSmall;
            reloadMultiplier = 0.8f;
            hitEffect = Fx.flakExplosion;
            width = (float) (6f*Math.pow(sizeBase,6));
            height = (float) (8f*Math.pow(sizeBase,6));
            splashDamage = (float) (30f * 1.5f*Math.pow(damageBase,6));
            splashDamageRadius = (float) (20f*Math.pow(damageBase,6));
            fragBullets = (int) (6*Math.pow(sizeBase,6));
            fragBullet = new BasicBulletType(3f, (float) (5*Math.pow(damageBase,6))){{
                width = (float) (5f*Math.pow(sizeBase,6));
                height = (float) (12f*Math.pow(sizeBase,6));
                shrinkY = 1f;
                lifetime = 20f;
                backColor = Pal.gray;
                frontColor = Color.white;
                despawnEffect = Fx.none;
                collidesGround = false;
            }};
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(metaglass7, new FlakBulletType(4f, (float) (3*Math.pow(damageBase,7))){{
            lifetime = 60f;
            ammoMultiplier = 5f;
            shootEffect = Fx.shootSmall;
            reloadMultiplier = 0.8f;
            hitEffect = Fx.flakExplosion;
            width = (float) (6f*Math.pow(sizeBase,7));
            height = (float) (8f*Math.pow(sizeBase,7));
            splashDamage = (float) (30f * 1.5f*Math.pow(damageBase,7));
            splashDamageRadius = (float) (20f*Math.pow(damageBase,7));
            fragBullets = (int) (6*Math.pow(sizeBase,7));
            fragBullet = new BasicBulletType(3f, (float) (5*Math.pow(damageBase,7))){{
                width = (float) (5f*Math.pow(sizeBase,7));
                height = (float) (12f*Math.pow(sizeBase,7));
                shrinkY = 1f;
                lifetime = 20f;
                backColor = Pal.gray;
                frontColor = Color.white;
                despawnEffect = Fx.none;
                collidesGround = false;
            }};
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(metaglass8, new FlakBulletType(4f, (float) (3*Math.pow(damageBase,8))){{
            lifetime = 60f;
            ammoMultiplier = 5f;
            shootEffect = Fx.shootSmall;
            reloadMultiplier = 0.8f;
            hitEffect = Fx.flakExplosion;
            width = (float) (6f*Math.pow(sizeBase,8));
            height = (float) (8f*Math.pow(sizeBase,8));
            splashDamage = (float) (30f * 1.5f*Math.pow(damageBase,8));
            splashDamageRadius = (float) (20f*Math.pow(damageBase,8));
            fragBullets = (int) (6*Math.pow(sizeBase,8));
            fragBullet = new BasicBulletType(3f, (float) (5*Math.pow(damageBase,8))){{
                width = (float) (5f*Math.pow(sizeBase,8));
                height = (float) (12f*Math.pow(sizeBase,8));
                shrinkY = 1f;
                lifetime = 20f;
                backColor = Pal.gray;
                frontColor = Color.white;
                despawnEffect = Fx.none;
                collidesGround = false;
            }};
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(metaglass9, new FlakBulletType(4f, (float) (3*Math.pow(damageBase,9))){{
            lifetime = 60f;
            ammoMultiplier = 5f;
            shootEffect = Fx.shootSmall;
            reloadMultiplier = 0.8f;
            hitEffect = Fx.flakExplosion;
            width = (float) (6f*Math.pow(sizeBase,9));
            height = (float) (8f*Math.pow(sizeBase,9));
            splashDamage = (float) (30f * 1.5f*Math.pow(damageBase,9));
            splashDamageRadius = (float) (20f*Math.pow(damageBase,9));
            fragBullets = (int) (6*Math.pow(sizeBase,9));
            fragBullet = new BasicBulletType(3f, (float) (5*Math.pow(damageBase,9))){{
                width = (float) (5f*Math.pow(sizeBase,9));
                height = (float) (12f*Math.pow(sizeBase,9));
                shrinkY = 1f;
                lifetime = 20f;
                backColor = Pal.gray;
                frontColor = Color.white;
                despawnEffect = Fx.none;
                collidesGround = false;
            }};
        }});

        ((ItemTurret) Blocks.scatter).ammoTypes.put(scrap1, new FlakBulletType(4f, (float) (3*Math.pow(damageBase,1))){{
            lifetime = 60f;
            ammoMultiplier = 5f;
            shootEffect = Fx.shootSmall;
            reloadMultiplier = 0.5f;
            width = (float) (6f*Math.pow(sizeBase,1));
            height = (float) (8f*Math.pow(sizeBase,1));
            hitEffect = Fx.flakExplosion;
            splashDamage = (float) (22f * 1.5f*Math.pow(damageBase,1));
            splashDamageRadius = (float) (24f*Math.pow(damageBase,1));
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(scrap2, new FlakBulletType(4f, (float) (3*Math.pow(damageBase,2))){{
            lifetime = 60f;
            ammoMultiplier = 5f;
            shootEffect = Fx.shootSmall;
            reloadMultiplier = 0.5f;
            width = (float) (6f*Math.pow(sizeBase,2));
            height = (float) (8f*Math.pow(sizeBase,2));
            hitEffect = Fx.flakExplosion;
            splashDamage = (float) (22f * 1.5f*Math.pow(damageBase,2));
            splashDamageRadius = (float) (24f*Math.pow(damageBase,2));
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(scrap3, new FlakBulletType(4f, (float) (3*Math.pow(damageBase,3))){{
            lifetime = 60f;
            ammoMultiplier = 5f;
            shootEffect = Fx.shootSmall;
            reloadMultiplier = 0.5f;
            width = (float) (6f*Math.pow(sizeBase,3));
            height = (float) (8f*Math.pow(sizeBase,3));
            hitEffect = Fx.flakExplosion;
            splashDamage = (float) (22f * 1.5f*Math.pow(damageBase,3));
            splashDamageRadius = (float) (24f*Math.pow(damageBase,3));
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(scrap4, new FlakBulletType(4f, (float) (3*Math.pow(damageBase,4))){{
            lifetime = 60f;
            ammoMultiplier = 5f;
            shootEffect = Fx.shootSmall;
            reloadMultiplier = 0.5f;
            width = (float) (6f*Math.pow(sizeBase,4));
            height = (float) (8f*Math.pow(sizeBase,4));
            hitEffect = Fx.flakExplosion;
            splashDamage = (float) (22f * 1.5f*Math.pow(damageBase,4));
            splashDamageRadius = (float) (24f*Math.pow(damageBase,4));
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(scrap5, new FlakBulletType(4f, (float) (3*Math.pow(damageBase,5))){{
            lifetime = 60f;
            ammoMultiplier = 5f;
            shootEffect = Fx.shootSmall;
            reloadMultiplier = 0.5f;
            width = (float) (6f*Math.pow(sizeBase,5));
            height = (float) (8f*Math.pow(sizeBase,5));
            hitEffect = Fx.flakExplosion;
            splashDamage = (float) (22f * 1.5f*Math.pow(damageBase,5));
            splashDamageRadius = (float) (24f*Math.pow(damageBase,5));
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(scrap6, new FlakBulletType(4f, (float) (3*Math.pow(damageBase,6))){{
            lifetime = 60f;
            ammoMultiplier = 5f;
            shootEffect = Fx.shootSmall;
            reloadMultiplier = 0.5f;
            width = (float) (6f*Math.pow(sizeBase,6));
            height = (float) (8f*Math.pow(sizeBase,6));
            hitEffect = Fx.flakExplosion;
            splashDamage = (float) (22f * 1.5f*Math.pow(damageBase,6));
            splashDamageRadius = (float) (24f*Math.pow(damageBase,6));
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(scrap7, new FlakBulletType(4f, (float) (3*Math.pow(damageBase,7))){{
            lifetime = 60f;
            ammoMultiplier = 5f;
            shootEffect = Fx.shootSmall;
            reloadMultiplier = 0.5f;
            width = (float) (6f*Math.pow(sizeBase,7));
            height = (float) (8f*Math.pow(sizeBase,7));
            hitEffect = Fx.flakExplosion;
            splashDamage = (float) (22f * 1.5f*Math.pow(damageBase,7));
            splashDamageRadius = (float) (24f*Math.pow(damageBase,7));
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(scrap8, new FlakBulletType(4f, (float) (3*Math.pow(damageBase,8))){{
            lifetime = 60f;
            ammoMultiplier = 5f;
            shootEffect = Fx.shootSmall;
            reloadMultiplier = 0.5f;
            width = (float) (6f*Math.pow(sizeBase,8));
            height = (float) (8f*Math.pow(sizeBase,8));
            hitEffect = Fx.flakExplosion;
            splashDamage = (float) (22f * 1.5f*Math.pow(damageBase,8));
            splashDamageRadius = (float) (24f*Math.pow(damageBase,8));
        }});
        ((ItemTurret) Blocks.scatter).ammoTypes.put(scrap9, new FlakBulletType(4f, (float) (3*Math.pow(damageBase,9))){{
            lifetime = 60f;
            ammoMultiplier = 5f;
            shootEffect = Fx.shootSmall;
            reloadMultiplier = 0.5f;
            width = (float) (6f*Math.pow(sizeBase,9));
            height = (float) (8f*Math.pow(sizeBase,9));
            hitEffect = Fx.flakExplosion;
            splashDamage = (float) (22f * 1.5f*Math.pow(damageBase,9));
            splashDamageRadius = (float) (24f*Math.pow(damageBase,9));
        }});


        ((ItemTurret) Blocks.scorch).ammoTypes.put(coal1, new BulletType(3.35f, (float) (17f*Math.pow(damageBase,1))){{

            hitSize = (float) (7f*Math.pow(sizeBase,1));
            statusDuration = (float) (60f * 4*Math.pow(sizeBase,1));

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
        ((ItemTurret) Blocks.scorch).ammoTypes.put(coal2, new BulletType(3.35f, (float) (17f*Math.pow(damageBase,2))){{

            hitSize = (float) (7f*Math.pow(sizeBase,2));
            statusDuration = (float) (60f * 4*Math.pow(sizeBase,2));

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
        ((ItemTurret) Blocks.scorch).ammoTypes.put(coal3, new BulletType(3.35f, (float) (17f*Math.pow(damageBase,3))){{

            hitSize = (float) (7f*Math.pow(sizeBase,3));
            statusDuration = (float) (60f * 4*Math.pow(sizeBase,3));

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
        ((ItemTurret) Blocks.scorch).ammoTypes.put(coal4, new BulletType(3.35f, (float) (17f*Math.pow(damageBase,4))){{

            hitSize = (float) (7f*Math.pow(sizeBase,4));
            statusDuration = (float) (60f * 4*Math.pow(sizeBase,4));

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
        ((ItemTurret) Blocks.scorch).ammoTypes.put(coal5, new BulletType(3.35f, (float) (17f*Math.pow(damageBase,5))){{

            hitSize = (float) (7f*Math.pow(sizeBase,5));
            statusDuration = (float) (60f * 4*Math.pow(sizeBase,5));

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
        ((ItemTurret) Blocks.scorch).ammoTypes.put(coal6, new BulletType(3.35f, (float) (17f*Math.pow(damageBase,6))){{

            hitSize = (float) (7f*Math.pow(sizeBase,6));
            statusDuration = (float) (60f * 4*Math.pow(sizeBase,6));

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
        ((ItemTurret) Blocks.scorch).ammoTypes.put(coal7, new BulletType(3.35f, (float) (17f*Math.pow(damageBase,7))){{

            hitSize = (float) (7f*Math.pow(sizeBase,7));
            statusDuration = (float) (60f * 4*Math.pow(sizeBase,7));

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
        ((ItemTurret) Blocks.scorch).ammoTypes.put(coal8, new BulletType(3.35f, (float) (17f*Math.pow(damageBase,8))){{

            hitSize = (float) (7f*Math.pow(sizeBase,8));
            statusDuration = (float) (60f * 4*Math.pow(sizeBase,8));

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
        ((ItemTurret) Blocks.scorch).ammoTypes.put(coal9, new BulletType(3.35f, (float) (17f*Math.pow(damageBase,9))){{

            hitSize = (float) (7f*Math.pow(sizeBase,9));
            statusDuration = (float) (60f * 4*Math.pow(sizeBase,9));

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

        ((ItemTurret) Blocks.scorch).ammoTypes.put(pyratite1, new BulletType(4f, (float) (60f*Math.pow(damageBase,1))){{
            hitSize = (float) (7f*Math.pow(sizeBase,1));
            statusDuration = (float) (60f * 10*Math.pow(sizeBase,1));
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
        ((ItemTurret) Blocks.scorch).ammoTypes.put(pyratite2, new BulletType(4f, (float) (60f*Math.pow(damageBase,2))){{
            hitSize = (float) (7f*Math.pow(sizeBase,2));
            statusDuration = (float) (60f * 10*Math.pow(sizeBase,2));
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
        ((ItemTurret) Blocks.scorch).ammoTypes.put(pyratite3, new BulletType(4f, (float) (60f*Math.pow(damageBase,3))){{
            hitSize = (float) (7f*Math.pow(sizeBase,3));
            statusDuration = (float) (60f * 10*Math.pow(sizeBase,3));
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
        ((ItemTurret) Blocks.scorch).ammoTypes.put(pyratite4, new BulletType(4f, (float) (60f*Math.pow(damageBase,4))){{
            hitSize = (float) (7f*Math.pow(sizeBase,4));
            statusDuration = (float) (60f * 10*Math.pow(sizeBase,4));
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
        ((ItemTurret) Blocks.scorch).ammoTypes.put(pyratite5, new BulletType(4f, (float) (60f*Math.pow(damageBase,5))){{
            hitSize = (float) (7f*Math.pow(sizeBase,5));
            statusDuration = (float) (60f * 10*Math.pow(sizeBase,5));
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
        ((ItemTurret) Blocks.scorch).ammoTypes.put(pyratite6, new BulletType(4f, (float) (60f*Math.pow(damageBase,6))){{
            hitSize = (float) (7f*Math.pow(sizeBase,6));
            statusDuration = (float) (60f * 10*Math.pow(sizeBase,6));
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
        ((ItemTurret) Blocks.scorch).ammoTypes.put(pyratite7, new BulletType(4f, (float) (60f*Math.pow(damageBase,7))){{
            hitSize = (float) (7f*Math.pow(sizeBase,7));
            statusDuration = (float) (60f * 10*Math.pow(sizeBase,7));
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
        ((ItemTurret) Blocks.scorch).ammoTypes.put(pyratite8, new BulletType(4f, (float) (60f*Math.pow(damageBase,8))){{
            hitSize = (float) (7f*Math.pow(sizeBase,8));
            statusDuration = (float) (60f * 10*Math.pow(sizeBase,8));
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
        ((ItemTurret) Blocks.scorch).ammoTypes.put(pyratite9, new BulletType(4f, (float) (60f*Math.pow(damageBase,9))){{
            hitSize = (float) (7f*Math.pow(sizeBase,9));
            statusDuration = (float) (60f * 10*Math.pow(sizeBase,9));
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


 */






    };
/*
    //Compressor
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
        alwaysUnlocked = true;

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
        alwaysUnlocked = true;

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
        alwaysUnlocked = true;

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
    public static Block metaglassCompressor = new MultiCrafter("metaglassCompressor"){{
        requirements(Category.crafting, with(Items.metaglass, 30));
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
                                Items.metaglass, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.metaglass1, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.metaglass1, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.metaglass2, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.metaglass2, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.metaglass3, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.metaglass3, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.metaglass4, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.metaglass4, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.metaglass5, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.metaglass5, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.metaglass6, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.metaglass6, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.metaglass7, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.metaglass7, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.metaglass8, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.metaglass8, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.metaglass9, 1));}};
                    craftTime = ECBlocks.makeTime;
                }}
        );

    }};
    public static Block scrapCompressor = new MultiCrafter("scrapCompressor"){{
        requirements(Category.crafting, with(Items.scrap, 30));
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
                                Items.scrap, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.scrap1, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.scrap1, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.scrap2, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.scrap2, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.scrap3, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.scrap3, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.scrap4, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.scrap4, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.scrap5, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.scrap5, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.scrap6, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.scrap6, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.scrap7, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.scrap7, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.scrap8, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.scrap8, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.scrap9, 1));}};
                    craftTime = ECBlocks.makeTime;
                }}
        );

    }};
    public static Block coalCompressor = new MultiCrafter("coalCompressor"){{
        requirements(Category.crafting, with(Items.coal, 30));
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
                                Items.coal, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.coal1, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.coal1, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.coal2, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.coal2, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.coal3, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.coal3, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.coal4, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.coal4, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.coal5, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.coal5, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.coal6, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.coal6, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.coal7, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.coal7, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.coal8, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.coal8, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.coal9, 1));}};
                    craftTime = ECBlocks.makeTime;
                }}
        );

    }};
    public static Block thoriumCompressor = new MultiCrafter("thoriumCompressor"){{
        requirements(Category.crafting, with(Items.thorium, 30));
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
                                Items.thorium, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.thorium1, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.thorium1, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.thorium2, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.thorium2, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.thorium3, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.thorium3, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.thorium4, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.thorium4, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.thorium5, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.thorium5, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.thorium6, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.thorium6, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.thorium7, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.thorium7, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.thorium8, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.thorium8, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.thorium9, 1));}};
                    craftTime = ECBlocks.makeTime;
                }}
        );

    }};
    public static Block surgeAlloyCompressor = new MultiCrafter("surgeAlloyCompressor"){{
        requirements(Category.crafting, with(Items.surgeAlloy, 30));
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
                                Items.surgeAlloy, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.surgeAlloy1, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.surgeAlloy1, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.surgeAlloy2, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.surgeAlloy2, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.surgeAlloy3, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.surgeAlloy3, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.surgeAlloy4, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.surgeAlloy4, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.surgeAlloy5, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.surgeAlloy5, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.surgeAlloy6, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.surgeAlloy6, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.surgeAlloy7, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.surgeAlloy7, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.surgeAlloy8, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.surgeAlloy8, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.surgeAlloy9, 1));}};
                    craftTime = ECBlocks.makeTime;
                }}
        );

    }};
    public static Block phaseFabricCompressor = new MultiCrafter("phaseFabricCompressor"){{
        requirements(Category.crafting, with(Items.phaseFabric, 30));
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
                                Items.phaseFabric, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.phaseFabric1, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.phaseFabric1, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.phaseFabric2, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.phaseFabric2, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.phaseFabric3, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.phaseFabric3, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.phaseFabric4, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.phaseFabric4, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.phaseFabric5, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.phaseFabric5, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.phaseFabric6, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.phaseFabric6, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.phaseFabric7, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.phaseFabric7, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.phaseFabric8, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.phaseFabric8, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.phaseFabric9, 1));}};
                    craftTime = ECBlocks.makeTime;
                }}
        );

    }};
    public static Block graphiteCompressor = new MultiCrafter("graphiteCompressor"){{
        requirements(Category.crafting, with(Items.graphite, 30));
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
                                Items.graphite, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.graphite1, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.graphite1, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.graphite2, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.graphite2, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.graphite3, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.graphite3, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.graphite4, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.graphite4, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.graphite5, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.graphite5, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.graphite6, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.graphite6, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.graphite7, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.graphite7, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.graphite8, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.graphite8, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.graphite9, 1));}};
                    craftTime = ECBlocks.makeTime;
                }}
        );

    }};
    public static Block siliconCompressor = new MultiCrafter("siliconCompressor"){{
        requirements(Category.crafting, with(Items.silicon, 30));
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
                                Items.silicon, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.silicon1, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.silicon1, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.silicon2, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.silicon2, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.silicon3, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.silicon3, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.silicon4, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.silicon4, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.silicon5, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.silicon5, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.silicon6, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.silicon6, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.silicon7, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.silicon7, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.silicon8, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.silicon8, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.silicon9, 1));}};
                    craftTime = ECBlocks.makeTime;
                }}
        );

    }};
    public static Block pyratiteCompressor = new MultiCrafter("pyratiteCompressor"){{
        requirements(Category.crafting, with(Items.pyratite, 30));
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
                                Items.pyratite, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.pyratite1, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.pyratite1, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.pyratite2, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.pyratite2, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.pyratite3, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.pyratite3, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.pyratite4, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.pyratite4, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.pyratite5, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.pyratite5, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.pyratite6, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.pyratite6, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.pyratite7, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.pyratite7, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.pyratite8, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.pyratite8, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.pyratite9, 1));}};
                    craftTime = ECBlocks.makeTime;
                }}
        );

    }};
    public static Block blastCompoundCompressor = new MultiCrafter("blastCompoundCompressor"){{
        requirements(Category.crafting, with(Items.blastCompound, 30));
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
                                Items.blastCompound, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.blastCompound1, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.blastCompound1, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.blastCompound2, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.blastCompound2, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.blastCompound3, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.blastCompound3, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.blastCompound4, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.blastCompound4, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.blastCompound5, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.blastCompound5, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.blastCompound6, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.blastCompound6, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.blastCompound7, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.blastCompound7, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.blastCompound8, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.blastCompound8, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.blastCompound9, 1));}};
                    craftTime = ECBlocks.makeTime;
                }}
        );

    }};
    public static Block sporePodCompressor = new MultiCrafter("sporePodCompressor"){{
        requirements(Category.crafting, with(Items.sporePod, 30));
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
                                Items.sporePod, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sporePod1, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sporePod1, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sporePod2, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sporePod2, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sporePod3, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sporePod3, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sporePod4, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sporePod4, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sporePod5, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sporePod5, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sporePod6, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sporePod6, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sporePod7, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sporePod7, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sporePod8, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sporePod8, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.sporePod9, 1));}};
                    craftTime = ECBlocks.makeTime;
                }}
        );

    }};
    public static Block plastaniumCompressor = new MultiCrafter("plastaniumCompressor"){{
        requirements(Category.crafting, with(Items.plastanium, 30));
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
                                Items.plastanium, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.plastanium1, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.plastanium1, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.plastanium2, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.plastanium2, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.plastanium3, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.plastanium3, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.plastanium4, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.plastanium4, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.plastanium5, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.plastanium5, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.plastanium6, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.plastanium6, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.plastanium7, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.plastanium7, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.plastanium8, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.plastanium8, 9));}};
                    output = new IOEntry() {{
                        items = Seq.with(ItemStack.with(
                                ECItems.plastanium9, 1));}};
                    craftTime = ECBlocks.makeTime;
                }}
        );

    }};
    */
    /*
    public static Block waterCompressor = new MultiCrafter("waterCompressor"){{
        requirements(Category.liquid, with(Items.copper, 15, Items.metaglass, 10));
        size = 3;
        hasPower = false;
        hasLiquids = true;
        liquidCapacity = 18;
        craftEffect = Fx.pulverizeMedium;
        alwaysUnlocked = true;

        resolvedRecipes = Seq.with(
                new Recipe() {{
                    input = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                Liquids.water, 9));}};
                    output = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                ECLiquids.water2, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                ECLiquids.water1, 9));}};
                    output = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                ECLiquids.water2, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                ECLiquids.water2, 9));}};
                    output = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                ECLiquids.water3, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                ECLiquids.water3, 9));}};
                    output = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                ECLiquids.water4, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                ECLiquids.water4, 9));}};
                    output = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                ECLiquids.water5, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                ECLiquids.water5, 9));}};
                    output = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                ECLiquids.water6, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                ECLiquids.water6, 9));}};
                    output = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                ECLiquids.water7, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                ECLiquids.water7, 9));}};
                    output = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                ECLiquids.water8, 1));}};
                    craftTime = ECBlocks.makeTime;
                }},
                new Recipe() {{
                    input = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                ECLiquids.water8, 9));}};
                    output = new IOEntry() {{
                        fluids = Seq.with(LiquidStack.with(
                                ECLiquids.water9, 1));}};
                    craftTime = ECBlocks.makeTime;
                }}
        );

    }};


    //converyor
    static int conveyorBase = 3;
    static double stackConveyorBase = 3;

    public static StackConveyor conveyor1 = new StackConveyor("conveyor1"){{
        requirements(Category.distribution, with(ECItems.copper1, 1));
        health = (int) (45*Math.pow(conveyorBase,1));
        speed = (float) ((4.2f*Math.pow(conveyorBase,1))/(int) (4.2f*Math.pow(conveyorBase,1)/stackConveyorBase))/60f;
        itemCapacity = (int) (4.2f*Math.pow(conveyorBase,1)/stackConveyorBase);
    }};
    public static StackConveyor conveyor2 = new StackConveyor("conveyor2"){{
        requirements(Category.distribution, with(ECItems.copper2, 1));
        health = (int) (45*Math.pow(conveyorBase,2));
        speed = (float) ((4.2f*Math.pow(conveyorBase,2))/(int) (4.2f*Math.pow(conveyorBase,2)/stackConveyorBase))/60f;
        itemCapacity = (int) (4.2f*Math.pow(conveyorBase,2)/stackConveyorBase);
    }};
    public static StackConveyor conveyor3 = new StackConveyor("conveyor3"){{
        requirements(Category.distribution, with(ECItems.copper3, 1));
        health = (int) (45*Math.pow(conveyorBase,3));
        speed = (float) ((4.2f*Math.pow(conveyorBase,3))/(int) (4.2f*Math.pow(conveyorBase,3)/stackConveyorBase))/60f;
        itemCapacity = (int) (4.2f*Math.pow(conveyorBase,3)/stackConveyorBase);
    }};
    public static StackConveyor conveyor4 = new StackConveyor("conveyor4"){{
        requirements(Category.distribution, with(ECItems.copper4, 1));
        health = (int) (45*Math.pow(conveyorBase,4));
        speed = (float) ((4.2f*Math.pow(conveyorBase,4))/(int) (4.2f*Math.pow(conveyorBase,4)/stackConveyorBase))/60f;
        itemCapacity = (int) (4.2f*Math.pow(conveyorBase,4)/stackConveyorBase);
    }};
    public static StackConveyor conveyor5 = new StackConveyor("conveyor5"){{
        requirements(Category.distribution, with(ECItems.copper5, 1));
        health = (int) (45*Math.pow(conveyorBase,5));
        speed = (float) ((4.2f*Math.pow(conveyorBase,5))/(int) (4.2f*Math.pow(conveyorBase,5)/stackConveyorBase))/60f;
        itemCapacity = (int) (4.2f*Math.pow(conveyorBase,5)/stackConveyorBase);
    }};
    public static StackConveyor conveyor6 = new StackConveyor("conveyor6"){{
        requirements(Category.distribution, with(ECItems.copper6, 1));
        health = (int) (45*Math.pow(conveyorBase,6));
        speed = (float) ((4.2f*Math.pow(conveyorBase,6))/(int) (4.2f*Math.pow(conveyorBase,6)/stackConveyorBase))/60f;
        itemCapacity = (int) (4.2f*Math.pow(conveyorBase,6)/stackConveyorBase);
    }};
    public static StackConveyor conveyor7 = new StackConveyor("conveyor7"){{
        requirements(Category.distribution, with(ECItems.copper7, 1));
        health = (int) (45*Math.pow(conveyorBase,7));
        speed = (float) ((4.2f*Math.pow(conveyorBase,7))/(int) (4.2f*Math.pow(conveyorBase,7)/stackConveyorBase))/60f;
        itemCapacity = (int) (4.2f*Math.pow(conveyorBase,7)/stackConveyorBase);
    }};
    public static StackConveyor conveyor8 = new StackConveyor("conveyor8"){{
        requirements(Category.distribution, with(ECItems.copper8, 1));
        health = (int) (45*Math.pow(conveyorBase,8));
        speed = (float) ((4.2f*Math.pow(conveyorBase,8))/(int) (4.2f*Math.pow(conveyorBase,8)/stackConveyorBase))/60f;
        itemCapacity = (int) (4.2f*Math.pow(conveyorBase,8)/stackConveyorBase);
    }};
    public static StackConveyor conveyor9 = new StackConveyor("conveyor9"){{
        requirements(Category.distribution, with(ECItems.copper9, 1));
        health = (int) (45*Math.pow(conveyorBase,9));
        speed = (float) ((4.2f*Math.pow(conveyorBase,9))/(int) (4.2f*Math.pow(conveyorBase,9)/stackConveyorBase))/60f;
        itemCapacity = (int) (4.2f*Math.pow(conveyorBase,9)/stackConveyorBase);
    }};

    public static Conveyor conveyor1 = new Conveyor("conveyor1"){{
        requirements(Category.distribution, with(ECItems.copper1, 1));
        researchCost = with(ECItems.copper1, 5);
        health = (int) (45*Math.pow(conveyorBase,1));
        speed = (float) (0.03f*Math.pow(conveyorBase,1));
        displayedSpeed = (float) (4.2f*Math.pow(conveyorBase,1));
        itemCapacity = (int) (10*Math.pow(conveyorBase,1));
        buildCostMultiplier = 2f;
    }};
    public static Conveyor conveyor2 = new Conveyor("conveyor2"){{
        requirements(Category.distribution, with(ECItems.copper2, 1));
        researchCost = with(ECItems.copper2, 5);
        health = (int) (45*Math.pow(conveyorBase,2));
        speed = (float) (0.03f*Math.pow(conveyorBase,2));
        displayedSpeed = (float) (4.2f*Math.pow(conveyorBase,2));
        itemCapacity = (int) (10*Math.pow(conveyorBase,2));
        buildCostMultiplier = 2f;
    }};
    public static Conveyor conveyor3 = new Conveyor("conveyor3"){{
        requirements(Category.distribution, with(ECItems.copper3, 1));
        researchCost = with(ECItems.copper3, 5);
        health = (int) (45*Math.pow(conveyorBase,3));
        speed = (float) (0.03f*Math.pow(conveyorBase,3));
        displayedSpeed = (float) (4.2f*Math.pow(conveyorBase,3));
        itemCapacity = (int) (10*Math.pow(conveyorBase,3));
        buildCostMultiplier = 2f;
    }};
    public static Conveyor conveyor4 = new Conveyor("conveyor4"){{
        requirements(Category.distribution, with(ECItems.copper4, 1));
        researchCost = with(ECItems.copper4, 5);
        health = (int) (45*Math.pow(conveyorBase,4));
        speed = (float) (0.03f*Math.pow(conveyorBase,4));
        displayedSpeed = (float) (4.2f*Math.pow(conveyorBase,4));
        itemCapacity = (int) (10*Math.pow(conveyorBase,4));
        buildCostMultiplier = 2f;
    }};
    public static Conveyor conveyor5 = new Conveyor("conveyor5"){{
        requirements(Category.distribution, with(ECItems.copper5, 1));
        researchCost = with(ECItems.copper5, 5);
        health = (int) (45*Math.pow(conveyorBase,5));
        speed = (float) (0.03f*Math.pow(conveyorBase,5));
        displayedSpeed = (float) (4.2f*Math.pow(conveyorBase,5));
        itemCapacity = (int) (10*Math.pow(conveyorBase,5));
        buildCostMultiplier = 2f;
    }};
    public static Conveyor conveyor6 = new Conveyor("conveyor6"){{
        requirements(Category.distribution, with(ECItems.copper6, 1));
        researchCost = with(ECItems.copper6, 5);
        health = (int) (45*Math.pow(conveyorBase,6));
        speed = (float) (0.03f*Math.pow(conveyorBase,6));
        displayedSpeed = (float) (4.2f*Math.pow(conveyorBase,6));
        itemCapacity = (int) (10*Math.pow(conveyorBase,6));
        buildCostMultiplier = 2f;
    }};
    public static Conveyor conveyor7 = new Conveyor("conveyor7"){{
        requirements(Category.distribution, with(ECItems.copper7, 1));
        researchCost = with(ECItems.copper7, 5);
        health = (int) (45*Math.pow(conveyorBase,7));
        speed = (float) (0.03f*Math.pow(conveyorBase,7));
        displayedSpeed = (float) (4.2f*Math.pow(conveyorBase,7));
        itemCapacity = (int) (10*Math.pow(conveyorBase,7));
        buildCostMultiplier = 2f;
    }};
    public static Conveyor conveyor8 = new Conveyor("conveyor8"){{
        requirements(Category.distribution, with(ECItems.copper8, 1));
        researchCost = with(ECItems.copper8, 5);
        health = (int) (45*Math.pow(conveyorBase,8));
        speed = (float) (0.03f*Math.pow(conveyorBase,8));
        displayedSpeed = (float) (4.2f*Math.pow(conveyorBase,8));
        itemCapacity = (int) (10*Math.pow(conveyorBase,8));
        buildCostMultiplier = 2f;
    }};
    public static Conveyor conveyor9 = new Conveyor("conveyor9"){{
        requirements(Category.distribution, with(ECItems.copper9, 1));
        researchCost = with(ECItems.copper9, 5);
        health = (int) (45*Math.pow(conveyorBase,9));
        speed = (float) (0.03f*Math.pow(conveyorBase,9));
        displayedSpeed = (float) (4.2f*Math.pow(conveyorBase,9));
        itemCapacity = (int) (10*Math.pow(conveyorBase,9));
        buildCostMultiplier = 2f;
    }};


    //drill
    static double drillBase = 3;
    public static Drill mechanicalDrill1 = new Drill("mechanicalDrill1"){{
        hardnessDrillMultiplier = (float) (50f/Math.pow(drillBase,1));
        size = 2;
        researchCost = with(ECItems.copper1, 10);
        requirements(Category.production, with(ECItems.copper1, 12));
        itemCapacity = (int) (10*Math.pow(drillBase,1));
        drillTime = (float) (600/Math.pow(drillBase,1));
        tier = 2;
        envEnabled ^= Env.space;

        consumeLiquid(Liquids.water, 0.05f).boost();
    }};
    public static Drill mechanicalDrill2 = new Drill("mechanicalDrill2"){{
        hardnessDrillMultiplier = (float) (50f/Math.pow(drillBase,2));
        size = 2;
        researchCost = with(ECItems.copper2, 10);
        requirements(Category.production, with(ECItems.copper2, 12));
        itemCapacity = (int) (10*Math.pow(drillBase,2));
        drillTime = (float) (600/Math.pow(drillBase,2));
        tier = 2;
        envEnabled ^= Env.space;

        consumeLiquid(Liquids.water, 0.05f).boost();
    }};
    public static Drill mechanicalDrill3 = new Drill("mechanicalDrill3"){{
        hardnessDrillMultiplier = (float) (50f/Math.pow(drillBase,3));
        size = 2;
        researchCost = with(ECItems.copper3, 10);
        requirements(Category.production, with(ECItems.copper3, 12));
        itemCapacity = (int) (10*Math.pow(drillBase,3));
        drillTime = (float) (600/Math.pow(drillBase,3));
        tier = 2;
        envEnabled ^= Env.space;

        consumeLiquid(Liquids.water, 0.05f).boost();
    }};
    public static Drill mechanicalDrill4 = new Drill("mechanicalDrill4"){{
        hardnessDrillMultiplier = (float) (50f/Math.pow(drillBase,4));
        size = 2;
        researchCost = with(ECItems.copper4, 10);
        requirements(Category.production, with(ECItems.copper4, 12));
        itemCapacity = (int) (10*Math.pow(drillBase,4));
        drillTime = (float) (600/Math.pow(drillBase,4));
        tier = 2;
        envEnabled ^= Env.space;

        consumeLiquid(Liquids.water, 0.05f).boost();
    }};
    public static Drill mechanicalDrill5 = new Drill("mechanicalDrill5"){{
        hardnessDrillMultiplier = (float) (50f/Math.pow(drillBase,5));
        size = 2;
        researchCost = with(ECItems.copper5, 10);
        requirements(Category.production, with(ECItems.copper5, 12));
        itemCapacity = (int) (10*Math.pow(drillBase,5));
        drillTime = (float) (600/Math.pow(drillBase,5));
        tier = 2;
        envEnabled ^= Env.space;

        consumeLiquid(Liquids.water, 0.05f).boost();
    }};
    public static Drill mechanicalDrill6 = new Drill("mechanicalDrill6"){{
        hardnessDrillMultiplier = (float) (50f/Math.pow(drillBase,6));
        size = 2;
        researchCost = with(ECItems.copper6, 10);
        requirements(Category.production, with(ECItems.copper6, 12));
        itemCapacity = (int) (10*Math.pow(drillBase,6));
        drillTime = (float) (600/Math.pow(drillBase,6));
        tier = 2;
        envEnabled ^= Env.space;

        consumeLiquid(Liquids.water, 0.05f).boost();
    }};
    public static Drill mechanicalDrill7 = new Drill("mechanicalDrill7"){{
        hardnessDrillMultiplier = (float) (50f/Math.pow(drillBase,7));
        size = 2;
        researchCost = with(ECItems.copper7, 10);
        requirements(Category.production, with(ECItems.copper7, 12));
        itemCapacity = (int) (10*Math.pow(drillBase,7));
        drillTime = (float) (600/Math.pow(drillBase,7));
        tier = 2;
        envEnabled ^= Env.space;

        consumeLiquid(Liquids.water, 0.05f).boost();
    }};
    public static Drill mechanicalDrill8 = new Drill("mechanicalDrill8"){{
        hardnessDrillMultiplier = (float) (50f/Math.pow(drillBase,8));
        size = 2;
        researchCost = with(ECItems.copper8, 10);
        requirements(Category.production, with(ECItems.copper8, 12));
        itemCapacity = (int) (10*Math.pow(drillBase,8));
        drillTime = (float) (600/Math.pow(drillBase,8));
        tier = 2;
        envEnabled ^= Env.space;

        consumeLiquid(Liquids.water, 0.05f).boost();
    }};
    public static Drill mechanicalDrill9 = new Drill("mechanicalDrill9"){{
        hardnessDrillMultiplier = (float) (50f/Math.pow(drillBase,9));
        size = 2;
        researchCost = with(ECItems.copper9, 10);
        requirements(Category.production, with(ECItems.copper9, 12));
        itemCapacity = (int) (10*Math.pow(drillBase,9));
        drillTime = (float) (600/Math.pow(drillBase,9));
        tier = 2;
        envEnabled ^= Env.space;

        consumeLiquid(Liquids.water, 0.05f).boost();
    }};


*/



/*
    //copperWall
    public static ECWalls copperWall1 = new ECWalls("copperWall1",1,80,copper1){};
    public static ECWalls copperWall2 = new ECWalls("copperWall2",2,80,copper2){};
    public static ECWalls copperWall3 = new ECWalls("copperWall3",3,80,copper3){};
    public static ECWalls copperWall4 = new ECWalls("copperWall4",4,80,copper4){};
    public static ECWalls copperWall5 = new ECWalls("copperWall5",5,80,copper5){};
    public static ECWalls copperWall6 = new ECWalls("copperWall6",6,80,copper6){};
    public static ECWalls copperWall7 = new ECWalls("copperWall7",7,80,copper7){};
    public static ECWalls copperWall8 = new ECWalls("copperWall8",8,80,copper8){};
    public static ECWalls copperWall9 = new ECWalls("copperWall9",9,80,copper9){};

 */
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
    /*
    //titaniumWall
    public static ECWalls titaniumWall1 = new ECWalls("titaniumWall1",1,110,titanium1){};
    public static ECWalls titaniumWall2 = new ECWalls("titaniumWall2",2,110,titanium2){};
    public static ECWalls titaniumWall3 = new ECWalls("titaniumWall3",3,110,titanium3){};
    public static ECWalls titaniumWall4 = new ECWalls("titaniumWall4",4,110,titanium4){};
    public static ECWalls titaniumWall5 = new ECWalls("titaniumWall5",5,110,titanium5){};
    public static ECWalls titaniumWall6 = new ECWalls("titaniumWall6",6,110,titanium6){};
    public static ECWalls titaniumWall7 = new ECWalls("titaniumWall7",7,110,titanium7){};
    public static ECWalls titaniumWall8 = new ECWalls("titaniumWall8",8,110,titanium8){};
    public static ECWalls titaniumWall9 = new ECWalls("titaniumWall9",9,110,titanium9){};


     */
}
