package ec.content;

import ec.Tools.AnyMtiCrafter;
import mindustry.content.Fx;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.consumers.ConsumeItemCharged;
import mindustry.world.consumers.ConsumeItemExplode;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawLiquidRegion;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawWarmupRegion;

import static mindustry.content.Items.*;
import static mindustry.content.Items.silicon;
import static mindustry.type.ItemStack.with;

public class ECOnly {
    public static void load() throws IllegalAccessException {

        Item power = new Item("power", load.itemColor(surgeAlloy, 0, true)){{charge = 1f;}};
        //mod特有
        load.item(power,"power",10);
        new AnyMtiCrafter("powerCompressor") {{
            requirements(Category.power, with(ECItems.ECItems.get(copper).get(1), 30));
            hasLiquids = true;
            hasItems = true;
            size = 2;
            ambientSound = Sounds.steam;
            ambientSoundVolume = 0.03f;

            drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion(), new DrawLiquidRegion());

            for (int i = 0; i < 10; i++) {
                int num = i;
                products.add(new Formula() {{
                    consumePower((float) (1f * Math.pow(10, num)));
                    outputItems = ItemStack.with(
                            ECItems.ECItems.get(power).get(num), 1);
                    craftTime = 60f;
                    craftEffect = Fx.generatespark;
                }});
            }
        }};
        new ConsumeGenerator("powerProducer") {{
            requirements(Category.power, with(
                    ECItems.ECItems.get(titanium).get(1), 20,
                    ECItems.ECItems.get(lead).get(1), 50,
                    ECItems.ECItems.get(silicon).get(1), 30));
            size = 3;
            baseExplosiveness = 5f;
            powerProduction = 1f;
            itemDuration = 60f;

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.03f;
            generateEffect = Fx.generatespark;
            explosionDamage = 0;

            consume(new ConsumeItemCharged(1f));
            ConsumeItemExplode explode = new ConsumeItemExplode();
            explode.baseChance = -1;
            consume(explode);

            drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
        }};
    }
}
