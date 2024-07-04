package ec.Blocks;

import mindustry.type.Category;
import mindustry.type.Item;
import mindustry.world.blocks.defense.Wall;


import static mindustry.type.ItemStack.with;

@SuppressWarnings("SpellCheckingInspection")

public class ECWalls extends Wall {
    public ECWalls(String name, int ECindex, double EChealth, Item material) {
        super(name);
        requirements(Category.defense, with(material, 6));
        health = (int) (EChealth*Math.pow(10,ECindex)*4);
        researchCostMultiplier = 0.1f;
    };
}
