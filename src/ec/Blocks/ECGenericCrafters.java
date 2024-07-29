package ec.Blocks;

import arc.Core;
import arc.struct.Seq;
import ec.AnyMtiCrafter;
import ec.content.ECItems;
import ec.content.ECLiquids;
import mindustry.Vars;
import mindustry.content.TechTree;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.consumers.*;

import java.lang.reflect.Field;

import static mindustry.content.TechTree.nodeProduce;

public class ECGenericCrafters {
    public static void load() throws NoSuchFieldException, IllegalAccessException {
        //初始化blocks
        Seq<Block> blocks = new Seq<>();
        //获取设置Compress-other-Mods(是否压缩其他mod)
        if (Core.settings.getBool("Compress-other-Mods")) {
            //是,则将blocks设置为全体方块
            blocks = Vars.content.blocks();
        } else {
            //否,则对全体方块进行遍历,将非mod方块添加到blocks中
            for (Block block : Vars.content.blocks()) if (!block.isModded()) blocks.add(block);
        }
        //遍历blocks
        for (Block block : blocks) {
            //判断block是不是GenericCrafter工厂的子类
            if (block instanceof GenericCrafter) {
                //如果是,则通过反射获取block的consumeBuilder(消耗)
                Field field = Block.class.getDeclaredField("consumeBuilder");
                field.setAccessible(true);
                Seq<Consume> consumeBuilder = (Seq<Consume>) field.get(block);

                //新建多合成工厂anyMtiCrafter
                AnyMtiCrafter anyMtiCrafter = new AnyMtiCrafter(block.name) {{
                    //设置一些基本属性
                    localizedName = Core.bundle.get("string.GenericCrafter.name") + block.localizedName;
                    category = block.category;
                    requirements = block.requirements;
                    buildVisibility = block.buildVisibility;
                    description = block.description;
                    details = block.details;
                    size = block.size;
                    hasLiquids = block.hasLiquids;
                    liquidCapacity = block.liquidCapacity;
                    hasItems = block.hasItems;
                    itemCapacity = block.itemCapacity;
                    hasPower = block.hasPower;
                    //drawer = ((GenericCrafter)block).drawer;
                    region = block.region;
                }};

                //遍历block的所有科技节点,把anyMtiCrafter作为子节点添加
                for (TechTree.TechNode techNode : block.techNodes) {
                    techNode.children.add(
                            nodeProduce(anyMtiCrafter, () -> {
                            })
                    );
                }

                //遍历每一重压缩
                for (int num = 1; num < 10; num++) {
                    //初始化消耗
                    ItemStack[] consumeItems = null;
                    LiquidStack[] consumeLiquids = null;
                    float consumePower = 0;
                    //遍历consumeBuilder,判断consume的类型并执行对应的压缩版consume构造
                    for (Consume consume : consumeBuilder) {
                        if (consume instanceof ConsumeItems) {
                            consumeItems = new ItemStack[((ConsumeItems) consume).items.length];
                            for (int i = 0; i < consumeItems.length; i++) {
                                Item item = ECItems.ECItems.get(((ConsumeItems) consume).items[i].item).get(num);
                                int amount = ((ConsumeItems) consume).items[i].amount;
                                consumeItems[i] = new ItemStack(item, amount);
                            }
                        } else if (consume instanceof ConsumeLiquids) {
                            consumeLiquids = new LiquidStack[((ConsumeLiquids) consume).liquids.length];
                            for (int i = 0; i < consumeLiquids.length; i++) {
                                Liquid liquid = ECLiquids.ECLiquids.get(((ConsumeLiquids) consume).liquids[i].liquid).get(num);
                                float amount = ((ConsumeLiquids) consume).liquids[i].amount;
                                consumeLiquids[i] = new LiquidStack(liquid, amount);
                            }
                        } else if (consume instanceof ConsumeLiquid) {
                            Liquid liquid = ECLiquids.ECLiquids.get(((ConsumeLiquid) consume).liquid).get(num);
                            float amount = ((ConsumeLiquid) consume).amount;
                            consumeLiquids = new LiquidStack[]{new LiquidStack(liquid, amount)};
                        } else if (consume instanceof ConsumePower) consumePower = ((ConsumePower) consume).usage;
                    }
                    //如果只消耗电力,则对电力消耗进行增强
                    if (consumeItems == null && consumeLiquids == null && consumePower > 0)
                        consumePower *= (float) Math.pow(9, num);

                    //初始化输出物品
                    ItemStack[] outputItems = null;
                    //判断输出物品的个数并依次执行对应的压缩版输出
                    if (((GenericCrafter) block).outputItems != null) {
                        outputItems = new ItemStack[((GenericCrafter) block).outputItems.length];
                        for (int i = 0; i < ((GenericCrafter) block).outputItems.length; i++) {
                            Item item = ECItems.ECItems.get(((GenericCrafter) block).outputItems[i].item).get(num);
                            int amount = ((GenericCrafter) block).outputItems[i].amount;
                            outputItems[i] = new ItemStack(item, amount);
                        }
                    } else if (((GenericCrafter) block).outputItem != null) {
                        Item item = ECItems.ECItems.get(((GenericCrafter) block).outputItem.item).get(num);
                        int amount = ((GenericCrafter) block).outputItem.amount;
                        outputItems = new ItemStack[]{new ItemStack(item, amount)};
                    }

                    //初始化输出液体
                    LiquidStack[] outputLiquids = null;
                    //判断输出液体的个数并依次执行对应的压缩版输出
                    if (((GenericCrafter) block).outputLiquids != null) {
                        outputLiquids = new LiquidStack[((GenericCrafter) block).outputLiquids.length];
                        for (int i = 0; i < ((GenericCrafter) block).outputLiquids.length; i++) {
                            Liquid liquid = ECLiquids.ECLiquids.get(((GenericCrafter) block).outputLiquids[i].liquid).get(num);
                            float amount = ((GenericCrafter) block).outputLiquids[i].amount;
                            outputLiquids[i] = new LiquidStack(liquid, amount);
                        }
                    } else if (((GenericCrafter) block).outputLiquid != null) {
                        Liquid liquid = ECLiquids.ECLiquids.get(((GenericCrafter) block).outputLiquid.liquid).get(num);
                        float amount = ((GenericCrafter) block).outputLiquid.amount;
                        outputLiquids = new LiquidStack[]{new LiquidStack(liquid, amount)};
                    }

                    //临时变量
                    ItemStack[] finalConsumeItems = consumeItems;
                    LiquidStack[] finalConsumeLiquids = consumeLiquids;
                    float finalConsumePower = consumePower;
                    ItemStack[] finalOutputItems = outputItems;
                    LiquidStack[] finalOutputLiquids = outputLiquids;
                    //为anyMtiCrafter添加压缩版的合成配方
                    anyMtiCrafter.products.add(new AnyMtiCrafter.Formula() {{
                        craftTime = ((GenericCrafter) block).craftTime;
                        craftEffect = ((GenericCrafter) block).craftEffect;
                        if (finalConsumeItems != null) consumeItems(finalConsumeItems);
                        if (finalConsumeLiquids != null) consumeLiquids(finalConsumeLiquids);
                        if (finalConsumePower > 0) consumePower(finalConsumePower);
                        outputItems = finalOutputItems;
                        outputLiquids = finalOutputLiquids;
                    }});
                }
            }
        }
    }
}
