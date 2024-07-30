package ec.content;

import arc.Core;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import ec.Tools.AnyMtiCrafter;
import mindustry.Vars;
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
import static mindustry.type.ItemStack.with;


@SuppressWarnings("SpellCheckingInspection")
public class ECItems {
    public static ObjectMap<Item, Seq<Item>> ECItems = new ObjectMap<>();

    public static void load() throws IllegalAccessException {

        Item power = new Item("power", load.itemColor(surgeAlloy, 0, true)){{charge = 1f;}};

        Seq<Item> items = new Seq<>();

        //判断设置"Compress-other-Mods"(是否对其他Mod生效)的状态
        if (Core.settings.getBool("Compress-other-Mods")) {
            //把所有物品添加进items里
            items = Vars.content.items().copy();
        } else {
            //遍历全部物品
            for (Item item : Vars.content.items()) {
                //把非Mod物品添加进items里
                if (!item.isModded()) {
                    items.add(item);
                }
            }
        }

        //遍历items
        for (Item item : items) {
            //运行加载压缩物品的方法
            load.item(item);
        }

        load.item(power,"power",10);

        new AnyMtiCrafter("powerCompressor") {{
            requirements(Category.power, with(ECItems.get(copper).get(1), 30));
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
                            ECItems.get(power).get(num), 1);
                    craftTime = 60f;
                    craftEffect = Fx.generatespark;
                }});
            }
        }};
        new ConsumeGenerator("powerProducer") {{
            requirements(Category.power, with(
                    ECItems.get(titanium).get(1), 20,
                    ECItems.get(lead).get(1), 50,
                    ECItems.get(silicon).get(1), 30));
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
