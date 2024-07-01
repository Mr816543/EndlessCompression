package ec.Blocks;

import mindustry.type.Category;
import mindustry.type.Item;
import mindustry.world.blocks.defense.Wall;

import static mindustry.type.ItemStack.with;

@SuppressWarnings("SpellCheckingInspection")

public class ECWallsLarge extends Wall {
    public ECWallsLarge(String name, int ECindex, double EChealth, Item material) {
        super(name);
        health = (int) (EChealth*Math.pow(10,ECindex)*4*4);

        requirements(Category.defense, with(material, 6));
    }
}
