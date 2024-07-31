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
    }
}
