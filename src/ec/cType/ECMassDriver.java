package ec.cType;

import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.gen.Building;
import mindustry.world.blocks.distribution.MassDriver;

import static mindustry.Vars.world;

public class ECMassDriver extends MassDriver {
    public ECMassDriver(String name) {
        super(name);
    }

    public class ECMassDriverBuild extends MassDriverBuild {
        @Override
        public void updateTile() {
            Building link = world.build(this.link);
            boolean hasLink = linkValid();

            if (hasLink) {
                this.link = link.pos();
            }

            //reload regardless of state
            if (reloadCounter > 0f) {
                reloadCounter = Mathf.clamp(reloadCounter - edelta() / reload);
            }

            var current = currentShooter();

            //cleanup waiting shooters that are not valid
            if (current != null && !shooterValid(current)) {
                waitingShooters.remove(current);
            }

            //switch states
            if (state == DriverState.idle) {
                //start accepting when idle and there's space
                if (!waitingShooters.isEmpty() && (itemCapacity - items.total() >= minDistribute)) {
                    state = DriverState.accepting;
                } else if (hasLink) { //switch to shooting if there's a valid link.
                    state = DriverState.shooting;
                }
            }

            //dump when idle or accepting
            if (state == DriverState.idle || state == DriverState.accepting) {
                for (int i = 0; i < 60; i++) {
                    dumpAccumulate();
                }
            }

            //skip when there's no power
            if (efficiency <= 0f) {
                return;
            }

            if (state == DriverState.accepting) {
                //if there's nothing shooting at this, bail - OR, items full
                if (currentShooter() == null || (itemCapacity - items.total() < minDistribute)) {
                    state = DriverState.idle;
                    return;
                }

                //align to shooter rotation
                rotation = Angles.moveToward(rotation, angleTo(currentShooter()), rotateSpeed * efficiency);
            } else if (state == DriverState.shooting) {
                //if there's nothing to shoot at OR someone wants to shoot at this thing, bail
                if (!hasLink || (!waitingShooters.isEmpty() && (itemCapacity - items.total() >= minDistribute))) {
                    state = DriverState.idle;
                    return;
                }

                float targetRotation = angleTo(link);

                if (
                        items.total() >= minDistribute && //must shoot minimum amount of items
                                link.block.itemCapacity - link.items.total() >= minDistribute //must have minimum amount of space
                ) {
                    MassDriverBuild other = (MassDriverBuild) link;
                    other.waitingShooters.add(this);

                    if (reloadCounter <= 0.0001f) {

                        //align to target location
                        rotation = Angles.moveToward(rotation, targetRotation, rotateSpeed * efficiency);

                        //fire when it's the first in the queue and angles are ready.
                        if (other.currentShooter() == this &&
                                other.state == DriverState.accepting &&
                                Angles.near(rotation, targetRotation, 2f) && Angles.near(other.rotation, targetRotation + 180f, 2f)) {
                            //actually fire
                            fire(other);
                            float timeToArrive = Math.min(bulletLifetime, dst(other) / bulletSpeed);
                            Time.run(timeToArrive, () -> {
                                //remove waiting shooters, it's done firing
                                other.waitingShooters.remove(this);
                                other.state = DriverState.idle;
                            });
                            //driver is immediately idle
                            state = DriverState.idle;
                        }
                    }
                }
            }
        }
    }
}
