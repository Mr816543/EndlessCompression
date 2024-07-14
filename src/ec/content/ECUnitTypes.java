package ec.content;

import arc.graphics.Color;
import mindustry.Vars;
import mindustry.ai.UnitCommand;
import mindustry.ai.types.MinerAI;
import mindustry.ai.types.SuicideAI;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.entities.abilities.RepairFieldAbility;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LaserBoltBulletType;
import mindustry.entities.bullet.MissileBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.ammo.ItemAmmoType;
import mindustry.type.ammo.PowerAmmoType;
import mindustry.type.weapons.RepairBeamWeapon;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.meta.BlockFlag;

import static mindustry.type.ItemStack.with;

public class ECUnitTypes {
    public static void load(){
        for (int i = 1 ; i < 10 ; i++){

            int num = i;
            float sizeBase = (float) Math.pow(1.4,num);
            float damageBase = (float) Math.pow(5,num);

            new UnitType("dagger"+num){{

                constructor = Vars.content.unit("dagger").constructor;

                speed = 0.5f;
                hitSize = 8f;
                health = 150*damageBase;
                weapons.add(new Weapon("large-weapon"){{
                    reload = 13f;
                    x = 4f;
                    y = 2f;
                    top = false;
                    ejectEffect = Fx.casing1;
                    bullet = new BasicBulletType(2.5f, 9*damageBase){{
                        width = 7f*sizeBase;
                        height = 9f*sizeBase;
                        lifetime = 60f;
                    }};
                }});
            }};
            new UnitType("nova"+num){{

                constructor = Vars.content.unit("nova").constructor;

                canBoost = true;
                boostMultiplier = 1.5f;
                speed = 0.55f;
                hitSize = 8f;
                health = 120f*damageBase;
                buildSpeed = 0.35f*damageBase;
                armor = 1f*damageBase;

                abilities.add(new RepairFieldAbility(10f*damageBase, 60f * 4, 60f*sizeBase));
                ammoType = new PowerAmmoType(1000*damageBase);

                weapons.add(new Weapon("heal-weapon"){{
                    top = false;
                    shootY = 2f;
                    reload = 24f;
                    x = 4.5f;
                    alternate = false;
                    ejectEffect = Fx.none;
                    recoil = 2f*sizeBase;
                    shootSound = Sounds.lasershoot;

                    bullet = new LaserBoltBulletType(5.2f, 13*damageBase){{
                        lifetime = 30f;
                        healPercent = 5f*sizeBase;
                        collidesTeam = true;
                        backColor = Pal.heal;
                        frontColor = Color.white;
                    }};
                }});
            }};
            new UnitType("crawler"+num){{
                aiController = SuicideAI::new;

                constructor = Vars.content.unit("crawler").constructor;

                speed = 1f;
                hitSize = 8f;
                health = 200*damageBase;
                mechSideSway = 0.25f;
                range = 40f;
                ammoType = new ItemAmmoType(Vars.content.item("ec-coal"+num));

                weapons.add(new Weapon(){{
                    shootOnDeath = true;
                    reload = 24f;
                    shootCone = 180f;
                    ejectEffect = Fx.none;
                    shootSound = Sounds.explosion;
                    x = shootY = 0f;
                    mirror = false;
                    bullet = new BulletType(){{
                        collidesTiles = false;
                        collides = false;
                        hitSound = Sounds.explosion;

                        rangeOverride = 30f*sizeBase;
                        hitEffect = Fx.pulverize;
                        speed = 0f;
                        splashDamageRadius = 55f*sizeBase;
                        instantDisappear = true;
                        splashDamage = 90f*damageBase;
                        killShooter = true;
                        hittable = false;
                        collidesAir = true;
                    }};
                }});
            }};
            new UnitType("flare"+num){{

                constructor = Vars.content.unit("flare").constructor;
                speed = 2.7f;
                accel = 0.08f;
                drag = 0.04f;
                flying = true;
                health = 70*damageBase;
                engineOffset = 5.75f;
                //TODO balance
                //targetAir = false;
                targetFlags = new BlockFlag[]{BlockFlag.generator, null};
                hitSize = 9;
                itemCapacity = (int) (10*damageBase);

                weapons.add(new Weapon(){{
                    y = 0f;
                    x = 2f;
                    reload = 20f;
                    ejectEffect = Fx.casing1;
                    bullet = new BasicBulletType(2.5f, 9*damageBase){{
                        width = 7f*sizeBase;
                        height = 9f*sizeBase;
                        lifetime = 45f;
                        shootEffect = Fx.shootSmall;
                        smokeEffect = Fx.shootSmallSmoke;
                        ammoMultiplier = 2;
                    }};
                    shootSound = Sounds.pew;
                }});
            }};
            new UnitType("mono"+num){{

                constructor = Vars.content.unit("mono").constructor;
                controller = u -> new MinerAI();
                itemCapacity = (int) (40*damageBase);

                defaultCommand = UnitCommand.mineCommand;

                flying = true;
                drag = 0.06f;
                accel = 0.12f;
                speed = 1.5f;
                health = 100*damageBase;
                engineSize = 1.8f;
                engineOffset = 5.7f;
                range = 50f*sizeBase;
                isEnemy = false;

                ammoType = new PowerAmmoType(500*damageBase);

                mineTier = 1+num;
                mineSpeed = 2.5f*damageBase;
            }};
            new UnitType("risso"+num){{

                constructor = Vars.content.unit("risso").constructor;
                speed = 1.1f;
                drag = 0.13f;
                hitSize = 10f;
                health = 280*damageBase;
                accel = 0.4f;
                rotateSpeed = 3.3f;
                faceTarget = false;

                armor = 2f*damageBase;

                weapons.add(new Weapon("mount-weapon"){{
                    reload = 13f;
                    x = 4f;
                    shootY = 4f;
                    y = 1.5f;
                    rotate = true;
                    ejectEffect = Fx.casing1;
                    bullet = new BasicBulletType(2.5f, 9*damageBase){{
                        width = 7f*sizeBase;
                        height = 9f*sizeBase;
                        lifetime = 60f;
                        ammoMultiplier = 2;
                    }};
                }});

                weapons.add(new Weapon("missiles-mount"){{
                    mirror = false;
                    reload = 25f;
                    x = 0f;
                    y = -5f;
                    rotate = true;
                    ejectEffect = Fx.casing1;
                    shootSound = Sounds.missile;
                    bullet = new MissileBulletType(2.7f, 12*damageBase, "missile"){{
                        keepVelocity = true;
                        width = 8f*sizeBase;
                        height = 8f*sizeBase;
                        shrinkY = 0f;
                        drag = -0.003f;
                        homingRange = 60f;
                        splashDamageRadius = 25f*sizeBase;
                        splashDamage = 10f*damageBase;
                        lifetime = 65f;
                        trailColor = Color.gray;
                        backColor = Pal.bulletYellowBack;
                        frontColor = Pal.bulletYellow;
                        hitEffect = Fx.blastExplosion;
                        despawnEffect = Fx.blastExplosion;
                        weaveScale = 8f;
                        weaveMag = 2f;
                    }};
                }});
            }};
            new UnitType("retusa"+num){{
                constructor = Vars.content.unit("retusa").constructor;
                speed = 0.9f;
                targetAir = false;
                drag = 0.14f;
                hitSize = 11f;
                health = 270*damageBase;
                accel = 0.4f;
                rotateSpeed = 5f;
                trailLength = 20;
                waveTrailX = 5f;
                trailScl = 1.3f;
                faceTarget = false;
                range = 100f;
                ammoType = new PowerAmmoType(900*damageBase);
                armor = 3f*damageBase;

                buildSpeed = 1.5f*damageBase;

                weapons.add(new RepairBeamWeapon("repair-beam-weapon-center"){{
                    x = 0f;
                    y = -5.5f;
                    shootY = 6f;
                    beamWidth = 0.8f;
                    mirror = false;
                    repairSpeed = 0.75f*damageBase;

                    bullet = new BulletType(1f,1f*damageBase){{
                        maxRange = 120f*sizeBase;
                    }};
                }});

                weapons.add(new Weapon(){{
                    mirror = false;
                    rotate = true;
                    reload = 90f;
                    x = y = shootX = shootY = 0f;
                    shootSound = Sounds.mineDeploy;
                    rotateSpeed = 180f;
                    targetAir = false;

                    shoot.shots = 3;
                    shoot.shotDelay = 7f;

                    bullet = new BasicBulletType(1f,1f*damageBase){{
                        sprite = "mine-bullet";
                        width = height = 8f*sizeBase;
                        layer = Layer.scorch;
                        shootEffect = smokeEffect = Fx.none;

                        maxRange = 50f;
                        ignoreRotation = true;
                        healPercent = 4f*sizeBase;

                        backColor = Pal.heal;
                        frontColor = Color.white;
                        mixColorTo = Color.white;

                        hitSound = Sounds.plasmaboom;

                        ejectEffect = Fx.none;
                        hitSize = 22f;

                        collidesAir = false;

                        lifetime = 87f;

                        hitEffect = new MultiEffect(Fx.blastExplosion, Fx.greenCloud);
                        keepVelocity = false;

                        shrinkX = shrinkY = 0f;

                        inaccuracy = 2f;
                        weaveMag = 5f;
                        weaveScale = 4f;
                        speed = 0.7f;
                        drag = -0.017f;
                        homingPower = 0.05f;
                        collideFloor = true;
                        trailColor = Pal.heal;
                        trailWidth = 3f;
                        trailLength = 8;

                        splashDamage = 33f*damageBase;
                        splashDamageRadius = 32f*sizeBase;
                    }};
                }});
            }};

            ((UnitFactory)(Blocks.groundFactory)).plans.addAll(
                    new UnitFactory.UnitPlan(Vars.content.unit("ec-dagger"+num), 60f * 15, with(
                            Vars.content.item("ec-silicon"+num), 10,
                            Vars.content.item("ec-lead"+num), 10
                    )),
                    new UnitFactory.UnitPlan(Vars.content.unit("ec-crawler"+num), 60f * 10, with(
                            Vars.content.item("ec-silicon"+num), 8,
                            Vars.content.item("ec-coal"+num), 10
                    )),
                    new UnitFactory.UnitPlan(Vars.content.unit("ec-nova"+num), 60f * 40, with(
                            Vars.content.item("ec-silicon"+num), 30,
                            Vars.content.item("ec-lead"+num), 20,
                            Vars.content.item("ec-titanium"+num), 20
                    ))
            );
            ((UnitFactory)(Blocks.airFactory)).plans.addAll(
                    new UnitFactory.UnitPlan(Vars.content.unit("ec-flare"+num), 60f * 15, with(
                            Vars.content.item("ec-silicon"+num), 15
                    )),
                    new UnitFactory.UnitPlan(Vars.content.unit("ec-mono"+num), 60f * 35, with(
                            Vars.content.item("ec-silicon"+num), 30,
                            Vars.content.item("ec-lead"+num), 15
                    ))
            );
            ((UnitFactory)(Blocks.navalFactory)).plans.addAll(
                    new UnitFactory.UnitPlan(Vars.content.unit("ec-risso"+num), 60f * 45f, with(
                            Vars.content.item("ec-silicon"+num), 20,
                            Vars.content.item("ec-metaglass"+num), 35
                    )),
                    new UnitFactory.UnitPlan(Vars.content.unit("ec-retusa"+num), 60f * 50f, with(
                            Vars.content.item("ec-silicon"+num), 15,
                            Vars.content.item("ec-metaglass"+num), 25,
                            Vars.content.item("ec-titanium"+num), 20
                    ))

            );



        };

    };
}
