package ec.content;

import arc.graphics.Color;
import mindustry.Vars;
import mindustry.ai.types.SuicideAI;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.entities.abilities.RepairFieldAbility;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LaserBoltBulletType;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.ammo.ItemAmmoType;
import mindustry.type.ammo.PowerAmmoType;
import mindustry.world.blocks.units.UnitFactory;

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
                weapons.add(new Weapon("large-weapon"+num){{
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
                armor = 1f*sizeBase;

                abilities.add(new RepairFieldAbility(10f*damageBase, 60f * 4, 60f*sizeBase));
                ammoType = new PowerAmmoType(1000*damageBase);

                weapons.add(new Weapon("heal-weapon"+num){{
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
                    reload = 24f*sizeBase;
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

            ((UnitFactory)(Blocks.groundFactory)).plans.add(
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


        };

    };
}
