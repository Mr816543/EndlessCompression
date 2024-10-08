package ec.content;

import arc.Core;
import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.Pixmap;
import arc.graphics.Texture;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Eachable;
import ec.Tools.AnyMtiCrafter;
import ec.Tools.Tool;
import ec.cType.*;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.TechTree;
import mindustry.content.UnitTypes;
import mindustry.entities.Effect;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.pattern.ShootPattern;
import mindustry.entities.units.BuildPlan;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.type.weapons.RepairBeamWeapon;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.campaign.LaunchPad;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.payloads.PayloadBlock;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.sandbox.ItemSource;
import mindustry.world.blocks.sandbox.LiquidSource;
import mindustry.world.blocks.sandbox.PowerSource;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.blocks.storage.Unloader;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.RepairTurret;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.consumers.*;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;
import mindustry.world.meta.BuildVisibility;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static arc.graphics.g2d.Draw.color;
import static arc.math.Angles.randLenVectors;
import static ec.Tools.AnyMtiCrafter.name;
import static ec.content.ECBlocks.ECBlocks;
import static ec.content.ECItems.ECItems;
import static ec.content.ECLiquids.ECLiquids;
import static ec.content.ECUnitTypes.ECUnits;
import static mindustry.Vars.content;
import static mindustry.Vars.state;
import static mindustry.content.Items.silicon;
import static mindustry.content.TechTree.*;
import static mindustry.type.ItemStack.with;

@SuppressWarnings("ALL")
public class load {
    //颜色
    public static Color Color(Color color, int num, boolean deepen) {

        Color color0 = Color.rgb(255, 255, 255);

        if (deepen) {
            color0 = Color.rgb(0, 0, 0);
        }

        return color.cpy().lerp(color0, 0.035f * num);

    }

    //物品颜色
    public static Color itemColor(Item item, int num, boolean deepen) {
        return Color(item.color, num, deepen);
    }

    public static Color itemColor(Item item, int num) {
        return itemColor(item, num, true);
    }

    //液体颜色
    public static Color liquidColor(Liquid liquid, int num, boolean deepen) {
        return Color(liquid.color, num, deepen);
    }

    public static Color liquidColor(Liquid liquid, int num) {
        return liquidColor(liquid, num, true);
    }

    //物品
    public static void item(Item item, String name, int attributeIndex) throws IllegalAccessException {
        //创建物品检索表
        Seq<Item> Items = new Seq<>();

        Items.add(item);
        ECItems.put(item, Items);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(attributeIndex, num);
            //创建新物品
            Item newitem = new Item(name + num) {
                {
                    localizedName = Core.bundle.get("string.Compress" + num) + item.localizedName;
                    description = item.description;
                    details = item.details;
                }
            };

            int finalI = i;
            Tool.forceRun(() -> {
                //如果uiIcon是null就稍后重新执行
                if (item.uiIcon == null) return false;
                //获取原uiIcon对应的Pixmap
                TextureAtlas.AtlasRegion Icon = new TextureAtlas.AtlasRegion(item.uiIcon);
                PixmapRegion base = Core.atlas.getPixmap(Icon);
                //新建画布??
                Pixmap mix = base.crop();
                //获取数字的AtlasRegion
                TextureAtlas.AtlasRegion number = Core.atlas.find(name("num-" + finalI));
                //不为null或ohno
                if (number.found()) {
                    //获取数字对应的Pixmap
                    PixmapRegion region = TextureAtlas.blankAtlas().getPixmap(number);
                    //mix叠加上number
                    int size = Math.max(base.width, base.height) + 6;
                    mix.draw(region.pixmap, region.x, region.y, region.width, region.height, 0, base.height - size, size, size, false, true);
                }
                //把mix设置为新内容的uiIcon和fullIcon
                newitem.uiIcon = newitem.fullIcon = new TextureRegion(new Texture(mix));
                return true;
            });

            //将此物品加入物品检索表
            ECItems.get(item).add(newitem);

            //遍历上级物品的全部科技节点,将本物品作为子节点添加
            for (TechNode techNode : ECItems.get(item).get(num - 1).techNodes) {
                TechNode node = nodeProduce(ECItems.get(item).get(num), () -> {
                });
                node.parent = techNode;
                techNode.children.add(node);
            }

            //获取物品的全部属性
            Field[] field0 = Item.class.getDeclaredFields();
            //遍历物品的全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(item);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        //矿物等级属性自定义
                        case "hardness" -> field.set(newitem, (int) value0 + num);
                        //颜色属性自定义
                        case "color" -> field.set(newitem, itemColor(item, num));
                        //爆炸性,燃烧性,放射性,放电性,血量缩放额外系数自定义
                        case "explosiveness", "flammability", "radioactivity", "charge", "healthScaling" ->
                                field.set(newitem, (float) value0 * attributeBase);
                        case "uiIcon", "fullIcon" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newitem, value0);
                    }
                }
            }
        }
    }

    public static void item(Item item) throws IllegalAccessException {
        load.item(item, item.name, 5);
    }

    //物品压缩器
    public static void itemCompressor(Item item) {
        //创建新多合成工厂
        AnyMtiCrafter anyMtiCrafter = new AnyMtiCrafter(item.name + "Compressor") {{
            //本地化显示修改
            localizedName = item.localizedName + Core.bundle.get("string.Compressor.name");
            description = Core.bundle.get("string.Compressor.description");
            details = Core.bundle.get("string.Compressor.details");

            //贴图方面
            useBlockDrawer = true;
            drawer = new DrawMulti(new DrawRegion() {
                @Override
                public void load(Block block) {
                    region = Core.atlas.find("ec-Compressor");
                }

                @Override
                public TextureRegion[] icons(Block block) {
                    TextureRegion[] textureRegions = new TextureRegion[3];
                    textureRegions[0] = Core.atlas.find("ec-Compressor");
                    textureRegions[1] = Core.atlas.find("ec-Compressor-icon");
                    if (Core.atlas.find("item-" + item.name) != null) {
                        textureRegions[2] = Core.atlas.find("item-" + item.name);
                    } else if (Core.atlas.find(item.name) != null) {
                        textureRegions[2] = Core.atlas.find(item.name);
                    } else textureRegions[2] = textureRegions[1];
                    return textureRegions;
                }
            }, new DrawRegion() {
                @Override
                public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                    if (!drawPlan) return;
                    Draw.color(ECItems.get(item).get(1).color);
                    Draw.rect(Core.atlas.find("ec-Compressor-top0"), plan.drawx(), plan.drawy(), (buildingRotate ? plan.rotation * 90f : 0));
                }

                @Override
                public void draw(Building build) {
                    Draw.color(ECItems.get(item).get(1).color);
                    super.draw(build);
                }

                @Override
                public void load(Block block) {
                    region = Core.atlas.find("ec-Compressor" + "-top0");
                }

                @Override
                public TextureRegion[] icons(Block block) {
                    return new TextureRegion[]{};
                }
            }, new DrawRegion() {
                @Override
                public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                    if (!drawPlan) return;
                    Draw.color(ECItems.get(item).get(5).color);
                    Draw.rect(Core.atlas.find("ec-Compressor-top1"), plan.drawx(), plan.drawy(), (buildingRotate ? plan.rotation * 90f : 0));
                }

                @Override
                public void draw(Building build) {
                    Draw.color(ECItems.get(item).get(5).color);
                    super.draw(build);
                }

                @Override
                public void load(Block block) {
                    region = Core.atlas.find("ec-Compressor" + "-top1");
                }

                @Override
                public TextureRegion[] icons(Block block) {
                    return new TextureRegion[]{};
                }
            }, new DrawRegion() {
                @Override
                public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                    if (!drawPlan) return;
                    Draw.color(ECItems.get(item).get(9).color);
                    Draw.rect(Core.atlas.find("ec-Compressor-top2"), plan.drawx(), plan.drawy(), (buildingRotate ? plan.rotation * 90f : 0));
                }

                @Override
                public void draw(Building build) {
                    Draw.color(ECItems.get(item).get(9).color);
                    super.draw(build);
                }

                @Override
                public void load(Block block) {
                    region = Core.atlas.find("ec-Compressor" + "-top2");
                }

                @Override
                public TextureRegion[] icons(Block block) {
                    return new TextureRegion[]{};
                }
            });
            //其他属性
            size = 2;
            hasLiquids = false;
            itemCapacity = 18;
            //根据物品检索表批量添加压缩配方
            for (int i = 1; i < 10; i++) {
                int num = i;
                products.add(new Formula() {{
                    consumeItem(ECItems.get(item).get(num - 1), 9);
                    outputItems = ItemStack.with(ECItems.get(item).get(num), 1);
                    craftTime = 60f;
                    craftEffect = Fx.pulverizeMedium;
                }});
            }
            requirements(Category.crafting, with(silicon, 5));
        }};
        //加入原版物品的科技节点
        for (TechNode techNode : item.techNodes) {
            TechNode node = node(anyMtiCrafter, () -> {
            });
            node.parent = techNode;
            techNode.children.add(node);
        }
    }

    //物品多重压缩器
    public static void itemMultiPress(Item item) {
        //创建新多合成工厂
        AnyMtiCrafter anyMtiCrafter = new AnyMtiCrafter(item.name + "MultiPress") {{
            //本地化修改
            localizedName = Core.bundle.get("string.MultiPress.name0") + item.localizedName + Core.bundle.get("string.MultiPress.name1");
            description = Core.bundle.get("string.MultiPress.description");
            details = Core.bundle.get("string.MultiPress.details");
            AiItemCapacity = true;
            //贴图
            useBlockDrawer = true;
            drawer = new DrawMulti(
                    new DrawRegion() {
                        @Override
                        public void load(Block block) {
                            region = Core.atlas.find("ec-MultiPress");
                        }

                        @Override
                        public TextureRegion[] icons(Block block) {
                            TextureRegion[] textureRegions = new TextureRegion[3];
                            textureRegions[0] = Core.atlas.find("ec-MultiPress");
                            textureRegions[1] = Core.atlas.find("ec-MultiPress-icon");
                            if (Core.atlas.find("item-" + item.name) != null) {
                                textureRegions[2] = Core.atlas.find("item-" + item.name);
                            } else if (Core.atlas.find(item.name) != null) {
                                textureRegions[2] = Core.atlas.find(item.name);
                            } else textureRegions[2] = textureRegions[1];
                            return textureRegions;
                        }
                    },
                    new DrawRegion() {
                        @Override
                        public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                            if (!drawPlan) return;
                            Draw.color(ECItems.get(item).get(1).color);
                            Draw.rect(Core.atlas.find("ec-MultiPress-top0"), plan.drawx(), plan.drawy(), (buildingRotate ? plan.rotation * 90f : 0));
                        }

                        @Override
                        public void draw(Building build) {
                            Draw.color(ECItems.get(item).get(1).color);
                            super.draw(build);
                        }

                        @Override
                        public void load(Block block) {
                            region = Core.atlas.find("ec-MultiPress" + "-top0");
                        }

                        @Override
                        public TextureRegion[] icons(Block block) {
                            return new TextureRegion[]{};
                        }
                    },
                    new DrawRegion() {
                        @Override
                        public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                            if (!drawPlan) return;
                            Draw.color(ECItems.get(item).get(5).color);
                            Draw.rect(Core.atlas.find("ec-MultiPress-top1"), plan.drawx(), plan.drawy(), (buildingRotate ? plan.rotation * 90f : 0));
                        }

                        @Override
                        public void draw(Building build) {
                            Draw.color(ECItems.get(item).get(5).color);
                            super.draw(build);
                        }

                        @Override
                        public void load(Block block) {
                            region = Core.atlas.find("ec-MultiPress" + "-top1");
                        }

                        @Override
                        public TextureRegion[] icons(Block block) {
                            return new TextureRegion[]{};
                        }
                    },
                    new DrawRegion() {
                        @Override
                        public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                            if (!drawPlan) return;
                            Draw.color(ECItems.get(item).get(9).color);
                            Draw.rect(Core.atlas.find("ec-MultiPress-top2"), plan.drawx(), plan.drawy(), (buildingRotate ? plan.rotation * 90f : 0));
                        }

                        @Override
                        public void draw(Building build) {
                            Draw.color(ECItems.get(item).get(9).color);
                            super.draw(build);
                        }

                        @Override
                        public void load(Block block) {
                            region = Core.atlas.find("ec-MultiPress" + "-top2");
                        }

                        @Override
                        public TextureRegion[] icons(Block block) {
                            return new TextureRegion[]{};
                        }
                    });
            //其他属性
            requirements(Category.crafting, with(ECItems.get(silicon).get(1), 20));
            size = 3;
            maxList = 5;
            itemCapacity = Integer.MAX_VALUE;
            hasItems = true;
            hasLiquids = true;
            hasPower = true;
            //根据物品检索表批量创建压缩配方
            for (int i = 1; i < 10; i++) {
                int num = i;
                float Base = (float) Math.pow(5, num);
                products.add(new Formula() {{
                    consumeItem(item, (int) Math.pow(9, num));
                    outputItems = ItemStack.with(ECItems.get(item).get(num), 1);
                    craftTime = 60f;
                    craftEffect = Fx.pulverizeMedium;
                    consumePower(108f * Base / 5 / 60);
                }});
            }
            //根据物品检索表批量创建解压配方
            for (int i = 1; i < 10; i++) {
                int num = i;
                float Base = (float) Math.pow(5, num);
                products.add(new Formula() {{
                    consumeItem(ECItems.get(item).get(num), 1);
                    outputItems = ItemStack.with(item, (int) Math.pow(9, num));
                    craftTime = 60f;
                    craftEffect = Fx.pulverizeMedium;
                    consumePower(108f * Base / 5 / 60);
                }});
            }
        }};
        //加入压缩器的子科技节点
        if (Vars.content.block("ec-" + item.name + "Compressor") != null) {
            for (TechNode techNode : Vars.content.block("ec-" + item.name + "Compressor").techNodes) {
                TechNode node = node(anyMtiCrafter, () -> {
                });
                node.parent = techNode;
                techNode.children.add(node);
            }
        }
    }

    //液体
    public static void liquid(Liquid liquid) throws IllegalAccessException {
        //创建液体检索表
        Seq<Liquid> Liquids = new Seq<>();
        Liquids.add(liquid);
        ECLiquids.put(liquid, Liquids);
        //批量创建液体
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            //创建新液体
            Liquid newliquid = new Liquid(liquid.name + num) {{
                //本地化修改
                localizedName = Core.bundle.get("string.Compress" + num) + liquid.localizedName;
                description = liquid.description;
                details = liquid.details;
            }};

            int finalI = i;
            Tool.forceRun(() -> {
                //如果uiIcon是null就稍后重新执行
                if (liquid.uiIcon == null) return false;
                //获取原uiIcon对应的Pixmap
                PixmapRegion base = Core.atlas.getPixmap(liquid.uiIcon);
                //新建画布??
                Pixmap mix = base.crop();
                //获取数字的AtlasRegion
                TextureAtlas.AtlasRegion number = Core.atlas.find(name("num-" + finalI));
                //不为null或ohno
                if (number.found()) {
                    //获取数字对应的Pixmap
                    PixmapRegion region = TextureAtlas.blankAtlas().getPixmap(number);
                    //mix叠加上number
                    int size = Math.max(base.width, base.height) + 6;
                    mix.draw(region.pixmap, region.x, region.y, region.width, region.height, 0 - 6, base.height - size, size, size, false, true);
                }
                //把mix设置为新内容的uiIcon和fullIcon
                newliquid.uiIcon = newliquid.fullIcon = new TextureRegion(new Texture(mix));
                return true;
            });

            //把新液体添加进检索表
            ECLiquids.get(liquid).add(newliquid);
            //加入上一级液体的子科技节点
            for (TechNode techNode : ECLiquids.get(liquid).get(num - 1).techNodes) {
                TechNode node = nodeProduce(ECLiquids.get(liquid).get(num), () -> {
                });
                node.parent = techNode;
                techNode.children.add(node);
            }

            //获取液体的全部属性
            Field[] field0 = Liquid.class.getDeclaredFields();
            //遍历液体的全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原液体属性的属性值
                    Object value0 = field.get(liquid);
                    //将新液体的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        //颜色自定义
                        case "color" -> field.set(newliquid, load.liquidColor(liquid, num));
                        //燃烧性,爆炸性,比热容,沸点自定义
                        case "flammability", "explosiveness", "heatCapacity", "boilPoint" ->
                                field.set(newliquid, (float) value0 * attributeBase);
                        //温度自定义
                        case "temperature" -> field.set(newliquid, 0.5f - (0.5f - (float) value0) * attributeBase);
                        case "uiIcon", "fullIcon" -> {
                        }

                        //其他没有自定义需求的属性
                        default -> field.set(newliquid, value0);
                    }
                }
            }
        }
    }

    //液体压缩器
    public static void liquidCompressor(Liquid liquid) {
        //创建新多合成工厂
        AnyMtiCrafter anyMtiCrafter = new AnyMtiCrafter(liquid.name + "Compressor") {{
            //本地化修改
            localizedName = liquid.localizedName + Core.bundle.get("string.Compressor.name");
            description = Core.bundle.get("string.Compressor.description");
            details = Core.bundle.get("string.Compressor.details");
            //贴图
            drawer = new DrawMulti(new DrawRegion() {
                @Override
                public void load(Block block) {
                    region = Core.atlas.find("ec-Compressor");
                }

                @Override
                public TextureRegion[] icons(Block block) {
                    TextureRegion[] textureRegions = new TextureRegion[3];
                    textureRegions[0] = Core.atlas.find("ec-Compressor");
                    textureRegions[1] = Core.atlas.find("ec-Compressor-icon");
                    if (Core.atlas.find("liquid-" + liquid.name) != null) {
                        textureRegions[2] = Core.atlas.find("liquid-" + liquid.name);
                    } else if (Core.atlas.find(liquid.name) != null) {
                        textureRegions[2] = Core.atlas.find(liquid.name);
                    } else textureRegions[2] = textureRegions[1];
                    return textureRegions;
                }
            }, new DrawRegion() {
                @Override
                public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                    if (!drawPlan) return;
                    Draw.color(ECLiquids.get(liquid).get(1).color);
                    Draw.rect(Core.atlas.find("ec-Compressor-top0"), plan.drawx(), plan.drawy(), (buildingRotate ? plan.rotation * 90f : 0));
                }

                @Override
                public void draw(Building build) {
                    Draw.color(ECLiquids.get(liquid).get(1).color);
                    super.draw(build);
                }

                @Override
                public void load(Block block) {
                    region = Core.atlas.find("ec-Compressor" + "-top0");
                }

                @Override
                public TextureRegion[] icons(Block block) {
                    return new TextureRegion[]{};
                }
            }, new DrawRegion() {
                @Override
                public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                    if (!drawPlan) return;
                    Draw.color(ECLiquids.get(liquid).get(5).color);
                    Draw.rect(Core.atlas.find("ec-Compressor-top1"), plan.drawx(), plan.drawy(), (buildingRotate ? plan.rotation * 90f : 0));
                }

                @Override
                public void draw(Building build) {
                    Draw.color(ECLiquids.get(liquid).get(5).color);
                    super.draw(build);
                }

                @Override
                public void load(Block block) {
                    region = Core.atlas.find("ec-Compressor" + "-top1");
                }

                @Override
                public TextureRegion[] icons(Block block) {
                    return new TextureRegion[]{};
                }
            }, new DrawRegion() {
                @Override
                public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                    if (!drawPlan) return;
                    Draw.color(ECLiquids.get(liquid).get(9).color);
                    Draw.rect(Core.atlas.find("ec-Compressor-top2"), plan.drawx(), plan.drawy(), (buildingRotate ? plan.rotation * 90f : 0));
                }

                @Override
                public void draw(Building build) {
                    Draw.color(ECLiquids.get(liquid).get(9).color);
                    super.draw(build);
                }

                @Override
                public void load(Block block) {
                    region = Core.atlas.find("ec-Compressor" + "-top2");
                }

                @Override
                public TextureRegion[] icons(Block block) {
                    return new TextureRegion[]{};
                }
            });
            //其他属性
            requirements(Category.crafting, with(ECItems.get(silicon).get(1), 15));
            size = 2;
            liquidCapacity = 18 * 60;
            alwaysUnlocked = false;
            //根据检索表批量创建压缩液体配方
            for (int i = 1; i < 10; i++) {
                int num = i;
                products.add(new Formula() {{
                    consumeLiquid(ECLiquids.get(liquid).get(num - 1), 9f);
                    outputLiquids = LiquidStack.with(ECLiquids.get(liquid).get(num), 1f);
                    craftTime = 60f;
                    craftEffect = Fx.pulverizeMedium;
                }});
            }
        }};
        //加入原液体的子科技节点
        for (TechNode techNode : liquid.techNodes) {
            TechNode node = node(anyMtiCrafter, () -> {
            });
            node.parent = techNode;
            techNode.children.add(node);
        }
    }

    //多重液体压缩器
    public static void liquidMultiPress(Liquid liquid) {
        //创建新多合成工厂
        AnyMtiCrafter anyMtiCrafter = new AnyMtiCrafter(liquid.name + "MultiPress") {{
            //本地化修改
            localizedName = Core.bundle.get("string.MultiPress.name0") + liquid.localizedName + Core.bundle.get("string.MultiPress.name1");
            description = Core.bundle.get("string.MultiPress.description");
            details = Core.bundle.get("string.MultiPress.details");
            AiLiquidCapacity = true;
            //贴图
            useBlockDrawer = true;
            drawer = new DrawMulti(
                    new DrawRegion() {
                        @Override
                        public void load(Block block) {
                            region = Core.atlas.find("ec-MultiPress");
                        }

                        @Override
                        public TextureRegion[] icons(Block block) {
                            TextureRegion[] textureRegions = new TextureRegion[3];
                            textureRegions[0] = Core.atlas.find("ec-MultiPress");
                            textureRegions[1] = Core.atlas.find("ec-MultiPress-icon");
                            if (Core.atlas.find("liquid-" + liquid.name) != null) {
                                textureRegions[2] = Core.atlas.find("liquid-" + liquid.name);
                            } else if (Core.atlas.find(liquid.name) != null) {
                                textureRegions[2] = Core.atlas.find(liquid.name);
                            } else textureRegions[2] = textureRegions[1];
                            return textureRegions;
                        }
                    },
                    new DrawRegion() {
                        @Override
                        public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                            if (!drawPlan) return;
                            Draw.color(ECLiquids.get(liquid).get(1).color);
                            Draw.rect(Core.atlas.find("ec-MultiPress-top0"), plan.drawx(), plan.drawy(), (buildingRotate ? plan.rotation * 90f : 0));
                        }

                        @Override
                        public void draw(Building build) {
                            Draw.color(ECLiquids.get(liquid).get(1).color);
                            super.draw(build);
                        }

                        @Override
                        public void load(Block block) {
                            region = Core.atlas.find("ec-MultiPress" + "-top0");
                        }

                        @Override
                        public TextureRegion[] icons(Block block) {
                            return new TextureRegion[]{};
                        }
                    },
                    new DrawRegion() {
                        @Override
                        public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                            if (!drawPlan) return;
                            Draw.color(ECLiquids.get(liquid).get(5).color);
                            Draw.rect(Core.atlas.find("ec-MultiPress-top1"), plan.drawx(), plan.drawy(), (buildingRotate ? plan.rotation * 90f : 0));
                        }

                        @Override
                        public void draw(Building build) {
                            Draw.color(ECLiquids.get(liquid).get(5).color);
                            super.draw(build);
                        }

                        @Override
                        public void load(Block block) {
                            region = Core.atlas.find("ec-MultiPress" + "-top1");
                        }

                        @Override
                        public TextureRegion[] icons(Block block) {
                            return new TextureRegion[]{};
                        }
                    },
                    new DrawRegion() {
                        @Override
                        public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                            if (!drawPlan) return;
                            Draw.color(ECLiquids.get(liquid).get(9).color);
                            Draw.rect(Core.atlas.find("ec-MultiPress-top2"), plan.drawx(), plan.drawy(), (buildingRotate ? plan.rotation * 90f : 0));
                        }

                        @Override
                        public void draw(Building build) {
                            Draw.color(ECLiquids.get(liquid).get(9).color);
                            super.draw(build);
                        }

                        @Override
                        public void load(Block block) {
                            region = Core.atlas.find("ec-MultiPress" + "-top2");
                        }

                        @Override
                        public TextureRegion[] icons(Block block) {
                            return new TextureRegion[]{};
                        }
                    });
            //其他属性
            requirements(Category.crafting, with(ECItems.get(silicon).get(1), 20));
            size = 3;
            maxList = 5;
            liquidCapacity = Float.MAX_VALUE;
            hasItems = true;
            hasLiquids = true;
            hasPower = true;
            //根据物品检索表批量创建压缩配方
            for (int i = 1; i < 10; i++) {
                int num = i;
                float Base = (float) Math.pow(5, num);
                products.add(new Formula() {{
                    consumeLiquid(liquid, (int) Math.pow(9, num));
                    outputLiquids = LiquidStack.with(ECLiquids.get(liquid).get(num), 1);
                    craftTime = 60f;
                    craftEffect = Fx.pulverizeMedium;
                    consumePower(108f * Base / 5 / 60);
                }});
            }
            //根据物品检索表批量创建解压配方
            for (int i = 1; i < 10; i++) {
                int num = i;
                float Base = (float) Math.pow(5, num);
                products.add(new Formula() {{
                    consumeLiquid(ECLiquids.get(liquid).get(num), 1);
                    outputLiquids = LiquidStack.with(liquid, (int) Math.pow(9, num));
                    craftTime = 60f;
                    craftEffect = Fx.pulverizeMedium;
                    consumePower(108f * Base / 5 / 60);
                }});
            }
        }};
        //加入压缩器的子科技节点
        if (Vars.content.block("ec-" + liquid.name + "Compressor") != null) {
            for (TechNode techNode : Vars.content.block("ec-" + liquid.name + "Compressor").techNodes) {
                TechNode node = node(anyMtiCrafter, () -> {
                });
                node.parent = techNode;
                techNode.children.add(node);
            }
        }
    }

    //运输
    //传送带
    public static void conveyor(Block conveyor) throws IllegalAccessException {

        //创建物品检索表
        Seq<Block> Conveyors = new Seq<>();
        Conveyors.add(conveyor);

        ECBlocks.put(conveyor, Conveyors);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(3, num);

            //创建新钻头
            Conveyor newconveyor = new Conveyor(conveyor.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + conveyor.localizedName;
                description = conveyor.description;
                details = conveyor.details;
            }};

            //将此钻头加入方块检索表
            ECBlocks.get(conveyor).add(newconveyor);
            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加Drill的属性
            field0.add(Conveyor.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(conveyor);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(newconveyor, (int) (160 * attributeBase));
                            else field.set(newconveyor, (int) ((int) value0 * attributeBase));
                        }
                        case "speed" -> field.set(newconveyor, (float) value0 * attributeBase);
                        case "displayedSpeed" -> field.set(newconveyor, newconveyor.speed * 140);
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[conveyor.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[conveyor.requirements.length];
                            for (int j = 0; j < conveyor.requirements.length; j++) {
                                Item item = ECItems.get(conveyor.requirements[j].item).get(i);
                                int amount = conveyor.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newconveyor, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(conveyor).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(conveyor).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "buildType" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newconveyor, value0);
                    }
                }
            }


            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            Seq<String> sprites = new Seq<>(new String[]{""});

            Seq<String> sprites1 = new Seq<>(new String[]{"-0", "-1", "-2", "-3", "-4"});

            Seq<String> sprites2 = new Seq<>(new String[]{"-0", "-1", "-2", "-3"});

            for (String sprite1 : sprites1) {
                for (String sprite2 : sprites2) {
                    sprites.add(sprite1 + sprite2);
                }
            }

            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + conveyor.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newconveyor.name + sprite, Core.atlas.find(prefix + conveyor.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    public static void armoredConveyor(Block conveyor) throws IllegalAccessException {

        //创建物品检索表
        Seq<Block> Conveyors = new Seq<>();
        Conveyors.add(conveyor);

        ECBlocks.put(conveyor, Conveyors);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(3, num);

            //创建新钻头
            ArmoredConveyor newconveyor = new ArmoredConveyor(conveyor.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + conveyor.localizedName;
                description = conveyor.description;
                details = conveyor.details;
            }};

            //将此钻头加入方块检索表
            ECBlocks.get(conveyor).add(newconveyor);
            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加Drill的属性
            field0.add(Conveyor.class.getDeclaredFields());
            field0.add(ArmoredConveyor.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(conveyor);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> field.set(newconveyor, (int) ((int) value0 * attributeBase));
                        case "speed" -> field.set(newconveyor, (float) value0 * attributeBase);
                        case "displayedSpeed" -> field.set(newconveyor, newconveyor.speed * 140);
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[conveyor.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[conveyor.requirements.length];
                            for (int j = 0; j < conveyor.requirements.length; j++) {
                                Item item = ECItems.get(conveyor.requirements[j].item).get(i);
                                int amount = conveyor.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newconveyor, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(conveyor).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(conveyor).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "buildType" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newconveyor, value0);
                    }
                }
            }


            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            Seq<String> sprites = new Seq<>(new String[]{""});

            Seq<String> sprites1 = new Seq<>(new String[]{"-0", "-1", "-2", "-3", "-4"});

            Seq<String> sprites2 = new Seq<>(new String[]{"-0", "-1", "-2", "-3"});

            for (String sprite1 : sprites1) {
                for (String sprite2 : sprites2) {
                    sprites.add(sprite1 + sprite2);
                }
            }

            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + conveyor.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newconveyor.name + sprite, Core.atlas.find(prefix + conveyor.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    public static void stackConveyor(Block conveyor) throws IllegalAccessException {

        //创建物品检索表
        Seq<Block> Conveyors = new Seq<>();
        Conveyors.add(conveyor);

        ECBlocks.put(conveyor, Conveyors);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            StackConveyor newconveyor = new StackConveyor(conveyor.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + conveyor.localizedName;
                description = conveyor.description;
                details = conveyor.details;
            }};

            //将此钻头加入方块检索表
            ECBlocks.get(conveyor).add(newconveyor);
            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加Drill的属性
            field0.add(StackConveyor.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(conveyor);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> field.set(newconveyor, (int) ((int) value0 * attributeBase));
                        case "speed" -> field.set(newconveyor, (float) value0 * sizeBase);
                        case "itemCapacity" -> field.set(newconveyor, (int) ((int) value0 * attributeBase / sizeBase));
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[conveyor.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[conveyor.requirements.length];
                            for (int j = 0; j < conveyor.requirements.length; j++) {
                                Item item = ECItems.get(conveyor.requirements[j].item).get(i);
                                int amount = conveyor.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newconveyor, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(conveyor).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(conveyor).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "buildType" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newconveyor, value0);
                    }
                }
            }


            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            Seq<String> sprites = new Seq<>(new String[]{"", "-0", "-1", "-2", "-edge", "-stack"});

            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + conveyor.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newconveyor.name + sprite, Core.atlas.find(prefix + conveyor.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //传送带桥
    public static void BufferedItemBridge(Block block,float speed) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            ECBufferedItemBridge newBlock = new ECBufferedItemBridge(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
                oldSpeed = speed/60;
                speedBase = attributeBase;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(ItemBridge.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {

                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = ((Seq<Consume>) field.get(block)).copy();
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (int j = 0; j < consumeBuilder.size; j++) {
                                if (consumeBuilder.get(j) instanceof ConsumeItems consume) {
                                    ItemStack[] items = new ItemStack[consume.items.length];
                                    for (int k = 0; k < consume.items.length; k++) {
                                        Item item = ECItems.get(consume.items[k].item).get(i);
                                        int amount = consume.items[k].amount;
                                        items[k] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(items));
                                } else if (consumeBuilder.get(j) instanceof ConsumeLiquid consume) {
                                    Liquid liquid = ECLiquids.get(consume.liquid).get(i);
                                    float amount = consume.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else if (consumeBuilder.get(j) instanceof ConsumePower consume) {
                                    float usage = consume.usage * attributeBase;
                                    float capacity = consume.capacity;
                                    boolean buffered = consume.buffered;
                                    newconsumeBuilder.add(new ConsumePower(usage, capacity, buffered));
                                } else newconsumeBuilder.add(consumeBuilder.get(j));
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "range"  -> field.set(newBlock, (int) ((int) value0 * sizeBase));
                        case "itemCapacity" -> field.set(newBlock,(int)((int)value0*attributeBase));
                        //case "speed" -> field.set(newBlock, (float) value0 / attributeBase);
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-arrow", "-bridge", "-end"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    public static void LiquidBridge(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            LiquidBridge newBlock = new LiquidBridge(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(ItemBridge.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {

                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = ((Seq<Consume>) field.get(block)).copy();
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (int j = 0; j < consumeBuilder.size; j++) {
                                if (consumeBuilder.get(j) instanceof ConsumeItems consume) {
                                    ItemStack[] items = new ItemStack[consume.items.length];
                                    for (int k = 0; k < consume.items.length; k++) {
                                        Item item = ECItems.get(consume.items[k].item).get(i);
                                        int amount = consume.items[k].amount;
                                        items[k] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(items));
                                } else if (consumeBuilder.get(j) instanceof ConsumeLiquid consume) {
                                    Liquid liquid = ECLiquids.get(consume.liquid).get(i);
                                    float amount = consume.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else if (consumeBuilder.get(j) instanceof ConsumePower consume) {
                                    float usage = consume.usage * attributeBase;
                                    float capacity = consume.capacity;
                                    boolean buffered = consume.buffered;
                                    newconsumeBuilder.add(new ConsumePower(usage, capacity, buffered));
                                } else newconsumeBuilder.add(consumeBuilder.get(j));
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "range"  -> field.set(newBlock, (int) ((int) value0 * sizeBase));
                        case "liquidCapacity" -> field.set(newBlock,((float)value0*attributeBase));
                        //case "speed" -> field.set(newBlock, (float) value0 / attributeBase);
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-arrow", "-bridge", "-end"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //液体导管
    public static void conduit(Block conduit) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Conduits = new Seq<>();
        Conduits.add(conduit);
        ECBlocks.put(conduit, Conduits);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            Conduit newconduit = new Conduit(conduit.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + conduit.localizedName;
                description = conduit.description;
                details = conduit.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(conduit).add(newconduit);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(Conduit.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(conduit);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(newconduit, (int) (160 * attributeBase));
                            else field.set(newconduit, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[conduit.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[conduit.requirements.length];
                            for (int j = 0; j < conduit.requirements.length; j++) {
                                Item item = ECItems.get(conduit.requirements[j].item).get(i);
                                int amount = conduit.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newconduit, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(conduit).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(conduit).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "buildType" -> {
                        }
                        case "liquidCapacity", "liquidPressure" -> field.set(newconduit, (float) value0 * sizeBase);
                        //其他没有自定义需求的属性
                        default -> field.set(newconduit, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            Seq<String> sprites = new Seq<>();
            String[] sprites1 = {"-bottom", "-top"};

            String[] sprites2 = {"", "-0", "-1", "-2", "-3", "-4"};
            for (String string1 : sprites1) {
                for (String string2 : sprites2) {
                    sprites.add(string1 + string2);
                }
            }

            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + conduit.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newconduit.name + sprite, Core.atlas.find(prefix + conduit.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    public static void armoredConduit(Block conduit) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Conduits = new Seq<>();
        Conduits.add(conduit);
        ECBlocks.put(conduit, Conduits);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            ArmoredConduit newconduit = new ArmoredConduit(conduit.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + conduit.localizedName;
                description = conduit.description;
                details = conduit.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(conduit).add(newconduit);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(Conduit.class.getDeclaredFields());
            field0.add(ArmoredConduit.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(conduit);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(newconduit, (int) (160 * attributeBase));
                            else field.set(newconduit, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[conduit.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[conduit.requirements.length];
                            for (int j = 0; j < conduit.requirements.length; j++) {
                                Item item = ECItems.get(conduit.requirements[j].item).get(i);
                                int amount = conduit.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newconduit, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(conduit).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(conduit).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "buildType" -> {
                        }
                        case "liquidCapacity", "liquidPressure" -> field.set(newconduit, (float) value0 * sizeBase);
                        //其他没有自定义需求的属性
                        default -> field.set(newconduit, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            Seq<String> sprites = new Seq<>();
            String[] sprites1 = {"-bottom", "-top", "-arrow", "-end", "bridge", "-cap"};

            String[] sprites2 = {"", "-0", "-1", "-2", "-3", "-4"};
            for (String string1 : sprites1) {
                for (String string2 : sprites2) {
                    sprites.add(string1 + string2);
                }
            }
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + conduit.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newconduit.name + sprite, Core.atlas.find(prefix + conduit.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //质量驱动器
    public static void MassDriver(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            ECMassDriver newBlock = new ECMassDriver(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(MassDriver.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {

                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = ((Seq<Consume>) field.get(block)).copy();
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (int j = 0; j < consumeBuilder.size; j++) {
                                if (consumeBuilder.get(j) instanceof ConsumeItems consume) {
                                    ItemStack[] items = new ItemStack[consume.items.length];
                                    for (int k = 0; k < consume.items.length; k++) {
                                        Item item = ECItems.get(consume.items[k].item).get(i);
                                        int amount = consume.items[k].amount;
                                        items[k] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(items));
                                } else if (consumeBuilder.get(j) instanceof ConsumeLiquid consume) {
                                    Liquid liquid = ECLiquids.get(consume.liquid).get(i);
                                    float amount = consume.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else if (consumeBuilder.get(j) instanceof ConsumePower consume) {
                                    float usage = consume.usage * attributeBase;
                                    float capacity = consume.capacity;
                                    boolean buffered = consume.buffered;
                                    newconsumeBuilder.add(new ConsumePower(usage, capacity, buffered));
                                } else newconsumeBuilder.add(consumeBuilder.get(j));
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "itemCapacity" -> field.set(newBlock, (int) ((int) value0 * attributeBase));
                        case "reload" -> field.set(newBlock, (float) value0 / attributeBase);
                        case "range", "bulletLifetime" -> field.set(newBlock, (float) value0 * sizeBase);
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-base"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //装卸器
    public static void Unloader(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            //创建新钻头
            ECUnloader newBlock = new ECUnloader(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
                centerRegion = ((Unloader)block).centerRegion;
                speed = ((Unloader)block).speed/attributeBase;
                size = block.size;
            }
            };
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "buildType" , "lastConfig"  , "configurations" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }



            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-center"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //发射台
    public static void LaunchPad(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            ECLaunchPad newBlock = new ECLaunchPad(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
                minLaunchCapacity = (int) (100 * sizeBase);
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(LaunchPad.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {

                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = ((Seq<Consume>) field.get(block)).copy();
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (int j = 0; j < consumeBuilder.size; j++) {
                                if (consumeBuilder.get(j) instanceof ConsumeItems consume) {
                                    ItemStack[] items = new ItemStack[consume.items.length];
                                    for (int k = 0; k < consume.items.length; k++) {
                                        Item item = ECItems.get(consume.items[k].item).get(i);
                                        int amount = consume.items[k].amount;
                                        items[k] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(items));
                                } else if (consumeBuilder.get(j) instanceof ConsumeLiquid consume) {
                                    Liquid liquid = ECLiquids.get(consume.liquid).get(i);
                                    float amount = consume.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else if (consumeBuilder.get(j) instanceof ConsumePower consume) {
                                    float usage = consume.usage * attributeBase;
                                    float capacity = consume.capacity;
                                    boolean buffered = consume.buffered;
                                    newconsumeBuilder.add(new ConsumePower(usage, capacity, buffered));
                                } else newconsumeBuilder.add(consumeBuilder.get(j));
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "itemCapacity" -> field.set(newBlock, (int) ((int) value0 * attributeBase));
                        case "launchTime" -> field.set(newBlock, (float) value0 / sizeBase);
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-light", "-pod"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //钻头
    public static void drill(Block drill) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Drills = new Seq<>();
        Drills.add(drill);
        ECBlocks.put(drill, Drills);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            ECDrill newdrill = new ECDrill(drill.name + num, num) {
                {
                    localizedName = Core.bundle.get("string.Compress" + num) + drill.localizedName;
                    description = drill.description;
                    details = drill.details;
                }

            };
            //将此钻头加入方块检索表
            ECBlocks.get(drill).add(newdrill);

            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加Drill的属性
            field0.add(Drill.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(drill);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(newdrill, (int) (160 * attributeBase));
                            else field.set(newdrill, (int) ((int) value0 * attributeBase));
                        }
                        case "hardnessDrillMultiplier", "drillTime" ->
                                field.set(newdrill, (float) value0 / attributeBase);
                        case "itemCapacity" -> field.set(newdrill, (int) ((int) value0 * attributeBase));
                        case "rotateSpeed" -> field.set(newdrill, (float) value0 * sizeBase);
                        case "tier" -> field.set(newdrill, (int) value0 + num);
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[drill.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[drill.requirements.length];
                            for (int j = 0; j < drill.requirements.length; j++) {
                                Item item = ECItems.get(drill.requirements[j].item).get(i);
                                int amount = drill.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newdrill, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(drill).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(drill).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "buildType", "barMap" -> {
                        }
                        case "consumeBuilder" -> {

                            Seq<Consume> consumeBuilder = (Seq<Consume>) value0;
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (Consume consume : consumeBuilder) {
                                if (consume instanceof ConsumeLiquid consume0) {
                                    Liquid liquid = ECLiquids.get(consume0.liquid).get(i);
                                    float amount = consume0.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume0.optional;
                                        booster = consume0.booster;
                                        update = consume0.update;
                                        multiplier = consume0.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else newconsumeBuilder.add(consume);
                            }
                            field.set(newdrill, newconsumeBuilder);
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newdrill, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-rotator", "-top", "-arrow", "-arrow-blur", "-glow", "-item", "-top-invert", "-rim"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + drill.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newdrill.name + sprite, Core.atlas.find(prefix + drill.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //环境工厂
    public static void AttributeCrafter(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            AttributeCrafter newBlock = new AttributeCrafter(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(GenericCrafter.class.getDeclaredFields());
            field0.add(AttributeCrafter.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {

                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = ((Seq<Consume>) field.get(block)).copy();
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (int j = 0; j < consumeBuilder.size; j++) {
                                if (consumeBuilder.get(j) instanceof ConsumeItems consume) {
                                    ItemStack[] items = new ItemStack[consume.items.length];
                                    for (int k = 0; k < consume.items.length; k++) {
                                        Item item = ECItems.get(consume.items[k].item).get(i);
                                        int amount = consume.items[k].amount;
                                        items[k] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(items));
                                } else if (consumeBuilder.get(j) instanceof ConsumeLiquid consume) {
                                    Liquid liquid = ECLiquids.get(consume.liquid).get(i);
                                    float amount = consume.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else if (consumeBuilder.get(j) instanceof ConsumePower consume) {
                                    float usage = consume.usage;
                                    float capacity = consume.capacity;
                                    boolean buffered = consume.buffered;
                                    newconsumeBuilder.add(new ConsumePower(usage, capacity, buffered));
                                } else newconsumeBuilder.add(consumeBuilder.get(j));
                            }
                            if (newconsumeBuilder.size==1&& newconsumeBuilder.get(0) instanceof ConsumePower consumePower){
                                consumePower.usage*=attributeBase;
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "outputItem" -> {
                            ItemStack outputItem = (ItemStack) value0;
                            Item item = ECItems.get(outputItem.item).get(i);
                            int amount = outputItem.amount;
                            field.set(newBlock, new ItemStack(item, amount));
                        }
                        case "outputLiquid" -> {
                            LiquidStack outputLiquid = (LiquidStack) value0;
                            Liquid liquid = ECLiquids.get(outputLiquid.liquid).get(i);
                            float amount = outputLiquid.amount;
                            field.set(newBlock,new LiquidStack(liquid,amount));
                        }
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-bottom", "-middle", "-top", "-rotator", "-rotator-blur", "-mid"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //墙壁钻头
    public static void BeamDrill(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            ECBeamDrill newBlock = new ECBeamDrill(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(BeamDrill.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "tier" -> field.set(newBlock, (int) value0 + i);
                        case "range", "fogRadius" -> field.set(newBlock, (int) ((int) value0 * sizeBase));
                        case "itemCapacity" -> field.set(newBlock, (int) ((int) value0 * attributeBase));
                        case "laserWidth" -> field.set(newBlock, (float) value0 * sizeBase);
                        case "drillTime" -> field.set(newBlock, (float) value0 / attributeBase);
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = (Seq<Consume>) value0;
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (Consume consume : consumeBuilder) {
                                if (consume instanceof ConsumeLiquid consume0) {
                                    Liquid liquid = ECLiquids.get(consume0.liquid).get(i);
                                    float amount = consume0.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume0.optional;
                                        booster = consume0.booster;
                                        update = consume0.update;
                                        multiplier = consume0.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else newconsumeBuilder.add(consume);
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-glow", "-top"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //泵
    public static void Pump(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            //创建新钻头
            Pump newBlock = new Pump(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(LiquidBlock.class.getDeclaredFields());
            field0.add(Pump.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);
                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "pumpAmount", "liquidCapacity" -> field.set(newBlock, (float) value0 * attributeBase);
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = (Seq<Consume>) value0;
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (Consume consume : consumeBuilder) {
                                if (consume instanceof ConsumeLiquid consume0) {
                                    Liquid liquid = ECLiquids.get(consume0.liquid).get(i);
                                    float amount = consume0.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume0.optional;
                                        booster = consume0.booster;
                                        update = consume0.update;
                                        multiplier = consume0.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else if (consume instanceof ConsumeItems) {
                                    ItemStack[] itemStacks = new ItemStack[((ConsumeItems) consume).items.length];
                                    for (int j = 0; j < ((ConsumeItems) consume).items.length; j++) {
                                        Item item = ECItems.get(((ConsumeItems) consume).items[j].item).get(i);
                                        int amount = ((ConsumeItems) consume).items[j].amount;
                                        itemStacks[j] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(itemStacks));
                                } else newconsumeBuilder.add(consume);
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-liquid"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //液体泵
    public static void solidPump(Block pump) throws IllegalAccessException {

        //创建物品检索表
        Seq<Block> Pumps = new Seq<>();
        Pumps.add(pump);

        ECBlocks.put(pump, Pumps);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);

            //创建新钻头
            SolidPump newpump = new SolidPump(pump.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + pump.localizedName;
                description = pump.description;
                details = pump.details;
            }};

            //将此钻头加入方块检索表
            ECBlocks.get(pump).add(newpump);
            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加Drill的属性
            field0.add(Pump.class.getDeclaredFields());
            field0.add(SolidPump.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(pump);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(newpump, (int) (160 * attributeBase));
                            else field.set(newpump, (int) ((int) value0 * attributeBase));
                        }
                        case "liquidCapacity", "rotateSpeed", "pumpAmount" ->
                                field.set(newpump, (float) value0 * attributeBase);
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[pump.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[pump.requirements.length];
                            for (int j = 0; j < pump.requirements.length; j++) {
                                Item item = ECItems.get(pump.requirements[j].item).get(i);
                                int amount = pump.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newpump, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(pump).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(pump).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "buildType" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newpump, value0);
                    }
                }
            }


            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            Seq<String> sprites = new Seq<>(new String[]{"", "-liquid", "-rotator", "-top"});

            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + pump.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newpump.name + sprite, Core.atlas.find(prefix + pump.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //钻机
    public static void Fracker(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            Fracker newBlock = new Fracker(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(LiquidBlock.class.getDeclaredFields());
            field0.add(Pump.class.getDeclaredFields());
            field0.add(SolidPump.class.getDeclaredFields());
            field0.add(Fracker.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {

                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = ((Seq<Consume>) field.get(block)).copy();
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (int j = 0; j < consumeBuilder.size; j++) {
                                if (consumeBuilder.get(j) instanceof ConsumeItems consume) {
                                    ItemStack[] items = new ItemStack[consume.items.length];
                                    for (int k = 0; k < consume.items.length; k++) {
                                        Item item = ECItems.get(consume.items[k].item).get(i);
                                        int amount = consume.items[k].amount;
                                        items[k] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(items));
                                } else if (consumeBuilder.get(j) instanceof ConsumeLiquid consume) {
                                    Liquid liquid = ECLiquids.get(consume.liquid).get(i);
                                    float amount = consume.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else if (consumeBuilder.get(j) instanceof ConsumePower consume) {
                                    float usage = consume.usage;
                                    float capacity = consume.capacity;
                                    boolean buffered = consume.buffered;
                                    newconsumeBuilder.add(new ConsumePower(usage, capacity, buffered));
                                } else newconsumeBuilder.add(consumeBuilder.get(j));
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "pumpAmount", "liquidCapacity" -> field.set(newBlock, (float) value0 * attributeBase);
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-bottom", "-middle", "-top", "-rotator", "-rotator-blur", " mid"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //城墙
    public static void wall(Block wall) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Walls = new Seq<>();
        Walls.add(wall);
        ECBlocks.put(wall, Walls);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float damageBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新物品
            Wall newwall = new Wall(wall.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + wall.localizedName;
                description = wall.description;
                details = wall.details;
                hideDetails = wall.hideDetails;
            }};
            //将此物品加入物品检索表
            ECBlocks.get(wall).add(newwall);


            //获取物品的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            field0.add(Wall.class.getDeclaredFields());

            //遍历物品的全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(wall);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> field.set(newwall, (int) ((int) value0 * damageBase));

                        case "armor", "lightningDamage" -> field.set(newwall, (float) value0 * damageBase);

                        case "chanceDeflect" -> {
                            if ((float) value0 > 0) {
                                field.set(newwall, 100 - ((100 - (float) value0) / sizeBase));
                            } else field.set(newwall, value0);
                        }
                        case "lightningChance" -> {
                            if ((float) value0 > 0) {
                                field.set(newwall, 1 - ((1 - (float) value0) / sizeBase));
                            } else field.set(newwall, value0);
                        }
                        case "lightningLength" -> field.set(newwall, (int) ((int) value0 * sizeBase));
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[wall.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[wall.requirements.length];
                            for (int j = 0; j < wall.requirements.length; j++) {
                                Item item = ECItems.get(wall.requirements[j].item).get(i);
                                int amount = wall.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newwall, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(wall).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(wall).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newwall, value0);
                    }
                }
            }

            //贴图前缀
            Seq<String> prefixs = new Seq<>(new String[]{""});
            //贴图后缀
            Seq<String> sprites = new Seq<>(new String[]{""});
            if (wall.variants > 0) {
                for (int j = 0; j < wall.variants; j++) {
                    sprites.add(Integer.toString(j + 1));
                }
            }
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + wall.name + sprite)) return false;
                        //以原版贴图覆盖新城墙贴图
                        Core.atlas.addRegion(prefix + newwall.name + sprite, Core.atlas.find(prefix + wall.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //门
    public static void Door(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            Door newBlock = new Door(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(Wall.class.getDeclaredFields());
            field0.add(Door.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {

                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = ((Seq<Consume>) field.get(block)).copy();
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (int j = 0; j < consumeBuilder.size; j++) {
                                if (consumeBuilder.get(j) instanceof ConsumeItems consume) {
                                    ItemStack[] items = new ItemStack[consume.items.length];
                                    for (int k = 0; k < consume.items.length; k++) {
                                        Item item = ECItems.get(consume.items[k].item).get(i);
                                        int amount = consume.items[k].amount;
                                        items[k] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(items));
                                } else if (consumeBuilder.get(j) instanceof ConsumeLiquid consume) {
                                    Liquid liquid = ECLiquids.get(consume.liquid).get(i);
                                    float amount = consume.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else if (consumeBuilder.get(j) instanceof ConsumePower consume) {
                                    float usage = consume.usage;
                                    float capacity = consume.capacity;
                                    boolean buffered = consume.buffered;
                                    newconsumeBuilder.add(new ConsumePower(usage, capacity, buffered));
                                } else newconsumeBuilder.add(consumeBuilder.get(j));
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-open"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //脉冲地雷
    public static void ShockMine(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            ShockMine newBlock = new ShockMine(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(ShockMine.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {

                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = ((Seq<Consume>) field.get(block)).copy();
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (int j = 0; j < consumeBuilder.size; j++) {
                                if (consumeBuilder.get(j) instanceof ConsumeItems consume) {
                                    ItemStack[] items = new ItemStack[consume.items.length];
                                    for (int k = 0; k < consume.items.length; k++) {
                                        Item item = ECItems.get(consume.items[k].item).get(i);
                                        int amount = consume.items[k].amount;
                                        items[k] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(items));
                                } else if (consumeBuilder.get(j) instanceof ConsumeLiquid consume) {
                                    Liquid liquid = ECLiquids.get(consume.liquid).get(i);
                                    float amount = consume.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else if (consumeBuilder.get(j) instanceof ConsumePower consume) {
                                    float usage = consume.usage;
                                    float capacity = consume.capacity;
                                    boolean buffered = consume.buffered;
                                    newconsumeBuilder.add(new ConsumePower(usage, capacity, buffered));
                                } else newconsumeBuilder.add(consumeBuilder.get(j));
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "tileDamage", "damage" -> field.set(newBlock, (float) value0 * attributeBase);
                        case "length", "tendrils", "shots" -> field.set(newBlock, (int) ((int) value0 * sizeBase));
                        case "bullet" -> {
                            BulletType bullet = ((BulletType) value0).copy();
                            bullet(bullet, i);
                            field.set(newBlock, bullet);
                        }
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-team-top"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //超速投影
    public static void OverdriveProjector(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            OverdriveProjector newBlock = new OverdriveProjector(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(OverdriveProjector.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取属性名
                    String name0 = field.getName();
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);
                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = (Seq<Consume>) value0;
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (Consume consume : consumeBuilder) {
                                if (consume instanceof ConsumeLiquid consume0) {
                                    Liquid liquid = ECLiquids.get(consume0.liquid).get(i);
                                    float amount = consume0.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else if (consume instanceof ConsumeItems) {
                                    ItemStack[] itemStacks = new ItemStack[((ConsumeItems) consume).items.length];
                                    for (int j = 0; j < ((ConsumeItems) consume).items.length; j++) {
                                        Item item = ECItems.get(((ConsumeItems) consume).items[j].item).get(i);
                                        int amount = ((ConsumeItems) consume).items[j].amount;
                                        itemStacks[j] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(itemStacks) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }});
                                } else newconsumeBuilder.add(consume);
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "range", "speedBoost", "speedBoostPhase", "phaseRangeBoost" ->
                                field.set(newBlock, (float) value0 * sizeBase);
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-top"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //力墙投影
    public static void ForceProjector(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            ForceProjector newBlock = new ForceProjector(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(ForceProjector.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取属性名
                    String name0 = field.getName();
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);
                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = (Seq<Consume>) value0;
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (Consume consume : consumeBuilder) {
                                if (consume instanceof ConsumeLiquid consume0) {
                                    Liquid liquid = ECLiquids.get(consume0.liquid).get(i);
                                    float amount = consume0.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else if (consume instanceof ConsumeItems) {
                                    ItemStack[] itemStacks = new ItemStack[((ConsumeItems) consume).items.length];
                                    for (int j = 0; j < ((ConsumeItems) consume).items.length; j++) {
                                        Item item = ECItems.get(((ConsumeItems) consume).items[j].item).get(i);
                                        int amount = ((ConsumeItems) consume).items[j].amount;
                                        itemStacks[j] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(itemStacks) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }});
                                } else if (consume instanceof ConsumePower consumePower) {
                                    float u = consumePower.usage * attributeBase;
                                    float c = consumePower.capacity;
                                    boolean b = consumePower.booster;
                                    newconsumeBuilder.add(new ConsumePower(u, c, b));
                                } else newconsumeBuilder.add(consume);
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "itemConsumer" -> {
                            if (value0 != null) {
                                Consume itemConsumer = ((Consume) value0);
                                if (itemConsumer instanceof ConsumeItems consume) {
                                    ItemStack[] newItemStacks = new ItemStack[consume.items.length];
                                    int j = 0;
                                    for (ItemStack itemStack : consume.items) {
                                        Item item = ECItems.get(itemStack.item).get(i);
                                        int amount = itemStack.amount;
                                        newItemStacks[j] = new ItemStack(item, amount);
                                        j++;
                                    }
                                    field.set(newBlock, newBlock.consumeItems(newItemStacks).boost());
                                }
                            }
                        }
                        case "radius", "phaseRadiusBoost" -> field.set(newBlock, (float) value0 * sizeBase);
                        case "shieldHealth", "phaseShieldBoost" -> field.set(newBlock, (float) value0 * attributeBase);
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-top"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //修理投影
    public static void MendProjector(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            MendProjector newBlock = new MendProjector(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(MendProjector.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取属性名
                    String name0 = field.getName();
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);
                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "range", "phaseRangeBoost" -> field.set(newBlock, (float) value0 * sizeBase);
                        case "reload" -> field.set(newBlock, (float) value0 / sizeBase);
                        case "scaledHealth" -> field.set(newBlock, (float) value0 * attributeBase);

                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = (Seq<Consume>) value0;
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (Consume consume : consumeBuilder) {
                                if (consume instanceof ConsumeLiquid consume0) {
                                    Liquid liquid = ECLiquids.get(consume0.liquid).get(i);
                                    float amount = consume0.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else if (consume instanceof ConsumeItems) {
                                    ItemStack[] itemStacks = new ItemStack[((ConsumeItems) consume).items.length];
                                    for (int j = 0; j < ((ConsumeItems) consume).items.length; j++) {
                                        Item item = ECItems.get(((ConsumeItems) consume).items[j].item).get(i);
                                        int amount = ((ConsumeItems) consume).items[j].amount;
                                        itemStacks[j] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(itemStacks) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }});
                                } else if (consume instanceof ConsumePower consumePower) {
                                    float u = consumePower.usage * attributeBase;
                                    float c = consumePower.capacity;
                                    boolean b = consumePower.booster;
                                    newconsumeBuilder.add(new ConsumePower(u, c, b));
                                } else newconsumeBuilder.add(consume);
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-liquid", "-top", "-bottom"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //修理炮塔
    public static void RepairTurret(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            RepairTurret newBlock = new RepairTurret(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(RepairTurret.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {

                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "length", "repairRadius", "beamWidth", "pulseRadius" ->
                                field.set(newBlock, (float) value0 * sizeBase);
                        case "powerUse", "repairSpeed" -> field.set(newBlock, (float) value0 * attributeBase);
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-bottom", "-middle", "-top", "-rotator", "-rotator-blur", " mid"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //炮台
    //物品炮台
    public static void ItemTurret(Block turret) throws IllegalAccessException {
        ObjectMap<Item, BulletType> newAmmoTypes = new ObjectMap<>();
        for (Item item : ((ItemTurret) turret).ammoTypes.keys()) {
            if (ECItems.get(item) != null) {
                for (int i = 1; i < 10; i++) {
                    Item ammoItem = ECItems.get(item).get(i);
                    BulletType bulletType = ((ItemTurret) turret).ammoTypes.get(item).copy();
                    load.bullet((ItemTurret) turret, bulletType, i);
                    newAmmoTypes.put(ammoItem, bulletType);
                }
            }

        }
        ((ItemTurret) turret).ammoTypes.putAll(newAmmoTypes);

        //获取Block的全部属性
        Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
        //遍历全部属性
        for (Field field : field0) {
            //允许通过反射访问私有变量
            field.setAccessible(true);
            //获取属性名
            String name0 = field.getName();
            //判断是否为final修饰的属性
            if (!Modifier.isFinal(field.getModifiers())) {
                //获取原物品属性的属性值
                Object value0 = field.get(turret);
                //将新物品的属性设置为和原物品相同
                if (name0.equals("consumeBuilder")) {
                    Seq<Consume> newconsumes = new Seq<>();
                    for (Consume consume : ((Seq<Consume>) value0)) {
                        if (consume instanceof ConsumeLiquid) {
                            Liquid liquid = ((ConsumeLiquid) consume).liquid;
                            float amount = ((ConsumeLiquid) consume).amount;
                            if (ECLiquids.get(liquid) != null) for (int i = 1; i < 10; i++) {

                                ConsumeLiquid consumeLiquid = new ConsumeLiquid(ECLiquids.get(liquid).get(i), amount) {{
                                    booster = true;
                                    multiplier = consume.multiplier;
                                    optional = true;
                                    update = consume.update;
                                }};
                                newconsumes.add(consumeLiquid);
                            }
                        }
                    }
                    ((Seq<Consume>) value0).add(newconsumes);
                }
            }
        }


    }

    //液体炮台
    public static void LiquidTurret(Block turret) throws IllegalAccessException {
        ObjectMap<Liquid, BulletType> newAmmoTypes = new ObjectMap<>();
        for (Liquid liquid : ((LiquidTurret) turret).ammoTypes.keys()) {
            if (!liquid.isModded()) {
                for (int i = 1; i < 10; i++) {
                    Liquid ammoLiquid = ECLiquids.get(liquid).get(i);
                    BulletType bulletType = ((LiquidTurret) turret).ammoTypes.get(liquid).copy();
                    load.bullet(bulletType, i);
                    newAmmoTypes.put(ammoLiquid, bulletType);
                }
            }

        }
        ((LiquidTurret) turret).ammoTypes.putAll(newAmmoTypes);
    }

    //能量炮台
    public static void PowerTurret(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            PowerTurret newBlock = new PowerTurret(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(BaseTurret.class.getDeclaredFields());
            field0.add(ReloadTurret.class.getDeclaredFields());
            field0.add(Turret.class.getDeclaredFields());
            field0.add(PowerTurret.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {

                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = ((Seq<Consume>) field.get(block)).copy();
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (int j = 0; j < consumeBuilder.size; j++) {
                                if (consumeBuilder.get(j) instanceof ConsumeItems consume) {
                                    ItemStack[] items = new ItemStack[consume.items.length];
                                    for (int k = 0; k < consume.items.length; k++) {
                                        Item item = ECItems.get(consume.items[k].item).get(i);
                                        int amount = consume.items[k].amount;
                                        items[k] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(items));
                                } else if (consumeBuilder.get(j) instanceof ConsumeLiquid consume) {
                                    Liquid liquid = ECLiquids.get(consume.liquid).get(i);
                                    float amount = consume.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else if (consumeBuilder.get(j) instanceof ConsumePower consume) {
                                    float usage = consume.usage * attributeBase;
                                    float capacity = consume.capacity;
                                    boolean buffered = consume.buffered;
                                    newconsumeBuilder.add(new ConsumePower(usage, capacity, buffered));
                                } else newconsumeBuilder.add(consumeBuilder.get(j));
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "shoot" -> {
                            ShootPattern shoot = ((ShootPattern) value0).copy();
                            shoot.firstShotDelay /= sizeBase;
                            field.set(newBlock, shoot);
                        }
                        case "shootType" -> {
                            BulletType shootType = ((BulletType) value0).copy();
                            load.bullet(shootType, i);
                            field.set(newBlock, shootType);

                        }
                        case "reload" -> field.set(newBlock, (float) value0 / attributeBase);
                        case "range" -> field.set(newBlock, (float) value0 * sizeBase);
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {
                    "", "-back-heat", "-back-l", "-back-r",
                    "-end", "-front-heat", "-front-l", "-front-r",
                    "-main", "-mid", "-mid-heat", "-mouth",
                    "-mouth-heat", "-preview", "-spine-heat", "-spine-l",
                    "-spine-r"


            };
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //牵引炮台
    public static void TractorBeamTurret(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            TractorBeamTurret newBlock = new TractorBeamTurret(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(BaseTurret.class.getDeclaredFields());
            field0.add(TractorBeamTurret.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {

                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = ((Seq<Consume>) field.get(block)).copy();
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (int j = 0; j < consumeBuilder.size; j++) {
                                if (consumeBuilder.get(j) instanceof ConsumeItems consume) {
                                    ItemStack[] items = new ItemStack[consume.items.length];
                                    for (int k = 0; k < consume.items.length; k++) {
                                        Item item = ECItems.get(consume.items[k].item).get(i);
                                        int amount = consume.items[k].amount;
                                        items[k] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(items));
                                } else if (consumeBuilder.get(j) instanceof ConsumeLiquid consume) {
                                    Liquid liquid = ECLiquids.get(consume.liquid).get(i);
                                    float amount = consume.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else if (consumeBuilder.get(j) instanceof ConsumePower consume) {
                                    float usage = consume.usage * attributeBase;
                                    float capacity = consume.capacity;
                                    boolean buffered = consume.buffered;
                                    newconsumeBuilder.add(new ConsumePower(usage, capacity, buffered));
                                } else newconsumeBuilder.add(consumeBuilder.get(j));
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "damage", "force", "scaledForce" -> field.set(newBlock, (float) value0 * attributeBase);
                        case "range", "rotateSpeed" -> field.set(newBlock, (float) value0 * sizeBase);
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-base", "-laser", "-laser-start", "-laser-end"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //裂解炮台
    public static void PointDefenseTurret(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            PointDefenseTurret newBlock = new PointDefenseTurret(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(BaseTurret.class.getDeclaredFields());
            field0.add(ReloadTurret.class.getDeclaredFields());
            field0.add(PointDefenseTurret.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {

                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = ((Seq<Consume>) field.get(block)).copy();
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (int j = 0; j < consumeBuilder.size; j++) {
                                if (consumeBuilder.get(j) instanceof ConsumeItems consume) {
                                    ItemStack[] items = new ItemStack[consume.items.length];
                                    for (int k = 0; k < consume.items.length; k++) {
                                        Item item = ECItems.get(consume.items[k].item).get(i);
                                        int amount = consume.items[k].amount;
                                        items[k] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(items));
                                } else if (consumeBuilder.get(j) instanceof ConsumeLiquid consume) {
                                    Liquid liquid = ECLiquids.get(consume.liquid).get(i);
                                    float amount = consume.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else if (consumeBuilder.get(j) instanceof ConsumePower consume) {
                                    float usage = consume.usage * attributeBase;
                                    float capacity = consume.capacity;
                                    boolean buffered = consume.buffered;
                                    newconsumeBuilder.add(new ConsumePower(usage, capacity, buffered));
                                } else newconsumeBuilder.add(consumeBuilder.get(j));
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "reload" -> field.set(newBlock, (float) value0 / attributeBase);
                        case "bulletDamage" -> field.set(newBlock, (float) value0 * attributeBase);
                        case "range", "shootLength", "rotateSpeed" -> field.set(newBlock, (float) value0 * sizeBase);
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-base"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //激光炮台
    public static void LaserTurret(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            LaserTurret newBlock = new LaserTurret(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(BaseTurret.class.getDeclaredFields());
            field0.add(ReloadTurret.class.getDeclaredFields());
            field0.add(Turret.class.getDeclaredFields());
            field0.add(PowerTurret.class.getDeclaredFields());
            field0.add(LaserTurret.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {

                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = ((Seq<Consume>) field.get(block)).copy();
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (int j = 0; j < consumeBuilder.size; j++) {
                                if (consumeBuilder.get(j) instanceof ConsumeItems consume) {
                                    ItemStack[] items = new ItemStack[consume.items.length];
                                    for (int k = 0; k < consume.items.length; k++) {
                                        Item item = ECItems.get(consume.items[k].item).get(i);
                                        int amount = consume.items[k].amount;
                                        items[k] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(items));
                                } else if (consumeBuilder.get(j) instanceof ConsumeLiquid consume) {
                                    Liquid liquid = ECLiquids.get(consume.liquid).get(i);
                                    float amount = consume.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else if (consumeBuilder.get(j) instanceof ConsumePower consume) {
                                    float usage = consume.usage * attributeBase;
                                    float capacity = consume.capacity;
                                    boolean buffered = consume.buffered;
                                    newconsumeBuilder.add(new ConsumePower(usage, capacity, buffered));
                                } else newconsumeBuilder.add(consumeBuilder.get(j));
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "shoot" -> {
                            ShootPattern shoot = ((ShootPattern) value0).copy();
                            shoot.firstShotDelay /= sizeBase;
                            field.set(newBlock, shoot);
                        }
                        case "shootType" -> {
                            BulletType shootType = ((BulletType) value0).copy();
                            load.bullet(shootType, i);
                            field.set(newBlock, shootType);

                        }
                        case "reload" -> field.set(newBlock, (float) value0 / attributeBase);
                        case "range" -> field.set(newBlock, (float) value0 * sizeBase);
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {
                    "", "-back-heat", "-back-l", "-back-r",
                    "-end", "-front-heat", "-front-l", "-front-r",
                    "-main", "-mid", "-mid-heat", "-mouth",
                    "-mouth-heat", "-preview", "-spine-heat", "-spine-l",
                    "-spine-r"


            };
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //单位
    public static void unit(UnitType unit) throws IllegalAccessException {
        //新建检索表
        Seq<UnitType> Units = new Seq<>();
        Units.add(unit);
        ECUnits.put(unit, Units);
        //批量创建压缩单位
        for (int i = 1; i < 10; i++) {
            int num = i;
            float healthBase = (float) Math.pow(5, num);
            float damageBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新单位
            UnitType newunit = new UnitType(unit.name + num) {{
                //本地化修改
                localizedName = Core.bundle.get("string.Compress" + num) + unit.localizedName;
                description = unit.description;
                constructor = unit.constructor;
                researchRequirements();
            }};
            //把新单位加入到检索表
            ECUnits.get(unit).add(newunit);
            //加入上一级单位的子科技节点
            for (TechNode techNode : ECUnits.get(unit).get(num - 1).techNodes) {
                TechNode node = node(ECUnits.get(unit).get(num), () -> {
                });
                node.parent = techNode;
                techNode.children.add(node);
            }
            //获取单位的全部属性
            Field[] field0 = UnitType.class.getDeclaredFields();
            //遍历单位的全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原版单位属性的属性值
                    Object value0 = field.get(unit);
                    //判断是否为血量
                    if (value0 != null) switch (name0) {
                        case "health" ->
                            //对血量进行增强
                                field.set(newunit, (float) value0 * healthBase);

                        //判断是否为物品容量
                        case "itemCapacity" -> {
                            //对物品容量进行增强
                            if ((int) value0 < 0) {
                                int itemCapacity = (int) (Math.max(Mathf.round((int) (unit.hitSize * 4f), 10), 10) * healthBase);
                                field.set(newunit, itemCapacity);
                            } else field.set(newunit, (int) ((int) value0 * healthBase));
                        }

                        //判断是否为护甲
                        case "armor" ->
                            //对护甲进行增强
                                field.set(newunit, (float) value0 * healthBase);

                        //判断是否为挖掘等级
                        case "mineTier" -> {
                            //对挖掘等级进行增强
                            if ((int) value0 > 0) {
                                field.set(newunit, (int) value0 + num);
                            }
                        }
                        //判断是否为挖掘速度
                        case "mineSpeed" -> {
                            //对挖掘等级进行增强
                            if ((float) value0 > 0) {
                                field.set(newunit, (float) value0 * damageBase);
                            }
                        }
                        //判断是否为武器
                        case "weapons" -> {
                            //遍历武器
                            for (int j = 0; j < unit.weapons.size; j++) {
                                //从原版复制武器
                                Weapon weapon = unit.weapons.get(j).copy();
                                //从原版武器复制武器子弹
                                weapon.bullet = unit.weapons.get(j).bullet.copy();
                                //增强子弹
                                bullet(weapon.bullet, num);

                                //判断武器类型
                                if (weapon instanceof RepairBeamWeapon) {
                                    //对修复速度进行增强
                                    ((RepairBeamWeapon) weapon).repairSpeed *= damageBase;
                                }

                                //把增强后的武器添加到新单位中
                                newunit.weapons.addAll(weapon);
                            }
                        }
                        //判断是否为能力
                        case "abilities" -> {
                            //遍历能力
                            for (int j = 0; j < unit.abilities.size; j++) {
                                Ability ability0 = unit.abilities.get(j);
                                Ability ability = ability0.copy();
                                if (ability0 instanceof RepairFieldAbility) {
                                    ((RepairFieldAbility) ability).amount *= damageBase;
                                    ((RepairFieldAbility) ability).range *= sizeBase;
                                } else if (ability0 instanceof SuppressionFieldAbility) {
                                    ((SuppressionFieldAbility) ability).orbRadius *= sizeBase;
                                } else if (ability0 instanceof ShieldArcAbility) {
                                    ((ShieldArcAbility) ability).regen *= damageBase;
                                    ((ShieldArcAbility) ability).max *= damageBase;
                                } else if (ability0 instanceof UnitSpawnAbility) {
                                    ((UnitSpawnAbility) ability).unit = Vars.content.unit("ec-" + ((UnitSpawnAbility) ability0).unit.name + num);
                                } else if (ability0 instanceof ForceFieldAbility) {
                                    ((ForceFieldAbility) ability).max *= damageBase;
                                    ((ForceFieldAbility) ability).regen *= damageBase;
                                } else if (ability0 instanceof ShieldRegenFieldAbility) {
                                    ((ShieldRegenFieldAbility) ability).max *= damageBase;
                                    ((ShieldRegenFieldAbility) ability).amount *= damageBase;
                                } else if (ability0 instanceof StatusFieldAbility) {
                                    ((StatusFieldAbility) ability).range *= sizeBase;
                                } else if (ability0 instanceof EnergyFieldAbility) {
                                    ((EnergyFieldAbility) ability).damage *= damageBase;
                                    ((EnergyFieldAbility) ability).range *= sizeBase;
                                    ((EnergyFieldAbility) ability).healPercent *= sizeBase;
                                    ((EnergyFieldAbility) ability).maxTargets *= (int) sizeBase;
                                    ((EnergyFieldAbility) ability).sameTypeHealMult = 1 - ((1 - ((EnergyFieldAbility) ability).sameTypeHealMult) / sizeBase);
                                }
                                newunit.abilities.add(ability);
                            }
                        }
                        //其他没有自定义需求的属性
                        default ->
                            //将新单位的属性设置为和原版单位相同
                                field.set(newunit, value0);
                    }
                }
            }
            //贴图后缀
            Seq<String> sprites = new Seq<>(new String[]{"", "-base", "-cell", "-leg", "-blade", "-blade-heat", "-heat", "-preview", "-side", "-missile", "-missile-fin", "-weapon", "-weapon-blade", "-weapon-preview"});

            //遍历贴图后缀
            for (String sprite : sprites) {
                //延时运行,来自I hope... 大佬
                Tool.forceRun(() -> {
                    //判断原版是否有该后缀贴图
                    if (!Core.atlas.has(unit.name + sprite)) return false;
                    //以原版贴图覆盖新单位贴图
                    Core.atlas.addRegion(newunit.name + sprite, Core.atlas.find(unit.name + sprite));
                    return true;
                });
            }

        }
    }

    //容器
    public static void StorageBlock(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            StorageBlock newBlock = new StorageBlock(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(StorageBlock.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取属性名
                    String name0 = field.getName();
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);
                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "itemCapacity" -> field.set(newBlock, (int) ((int) value0 * attributeBase));
                        case "scaledHealth" -> field.set(newBlock, (float) value0 * attributeBase);
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-team"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //核心
    public static void coreBlock(Block coreBlock) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> CoreBlocks = new Seq<>();
        CoreBlocks.add(coreBlock);
        ECBlocks.put(coreBlock, CoreBlocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            float attributeBase = (float) Math.pow(5, i);
            float sizeBase = (float) Math.pow(1.4, i);
            //创建新钻头
            CoreBlock newcoreBlock = getCoreBlock(coreBlock, i);
            //将此钻头加入方块检索表
            ECBlocks.get(coreBlock).add(newcoreBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加Drill的属性
            field0.add(CoreBlock.class.getDeclaredFields());
            field0.add(StorageBlock.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(coreBlock);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(newcoreBlock, (int) (160 * attributeBase));
                            else field.set(newcoreBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "alwaysUnlocked", "isFirstTier" -> field.set(newcoreBlock, false);
                        case "unitType" -> {
                            UnitType unitType = ECUnits.get((UnitType) value0).get(i);
                            field.set(newcoreBlock, unitType);
                        }
                        case "unitCapModifier" -> field.set(newcoreBlock, (int) ((int) value0 * sizeBase));
                        case "itemCapacity" -> field.set(newcoreBlock, (int) ((int) value0 * attributeBase));
                        case "buildVisibility" -> field.set(newcoreBlock, BuildVisibility.shown);
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[coreBlock.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[coreBlock.requirements.length];
                            for (int j = 0; j < coreBlock.requirements.length; j++) {
                                Item item = ECItems.get(coreBlock.requirements[j].item).get(i);
                                int amount = coreBlock.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newcoreBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(coreBlock).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(coreBlock).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "buildType" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newcoreBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-team", "-thruster1", "-thruster2"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + coreBlock.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newcoreBlock.name + sprite, Core.atlas.find(prefix + coreBlock.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    private static CoreBlock getCoreBlock(Block coreBlock, int i) {
        return new CoreBlock(coreBlock.name + i) {
            {
                localizedName = Core.bundle.get("string.Compress" + i) + coreBlock.localizedName;
                description = coreBlock.description;
                details = coreBlock.details;
            }

            @Override
            public boolean canPlaceOn(Tile tile, Team team, int rotation) {
                if (tile == null) return false;
                //in the editor, you can place them anywhere for convenience
                if (state.isEditor()) return true;

                CoreBuild core = team.core();

                //special floor upon which cores can be placed
                tile.getLinkedTilesAs(this, tempTiles);
                if (!tempTiles.contains(o -> !o.floor().allowCorePlacement || o.block() instanceof CoreBlock)) {
                    return true;
                }

                //must have all requirements
                if (core == null || (!state.rules.infiniteResources && !core.items.has(requirements, state.rules.buildCostMultiplier)))
                    return false;

                return tile.block() instanceof CoreBlock && (size > tile.block().size) || (ECBlocks.get(coreBlock).indexOf(tile.block()) != -1 && ECBlocks.get(coreBlock).indexOf(this) > ECBlocks.get(coreBlock).indexOf(tile.block())) && (!requiresCoreZone || tempTiles.allMatch(o -> o.floor().allowCorePlacement));
            }


        };
    }


    //子弹强化
    public static BulletType bullet(BulletType bullet, float damageBase, float sizeBase) {
        bullet.knockback *= sizeBase;
        bullet.lifetime *= sizeBase;
        bullet.drawSize *= sizeBase;
        //手机会卡//bullet.hitSize *= damageBase;
        bullet.damage *= damageBase;
        bullet.splashDamage *= damageBase;
        bullet.splashDamageRadius *= sizeBase;
        bullet.lightning = (int) (sizeBase * bullet.lightning);
        bullet.lightningDamage *= damageBase;
        bullet.lightningLength = (int) (sizeBase * bullet.lightningLength);
        bullet.healAmount *= damageBase;
        bullet.healPercent *= sizeBase;
        bullet.intervalBullets = (int) (sizeBase * bullet.intervalBullets);
        bullet.fragBullets = (int) (sizeBase * bullet.fragBullets);
        bullet.statusDuration *= damageBase;
        if (bullet.fragBullet != null) bullet.fragBullet = bullet(bullet.fragBullet.copy(), damageBase, sizeBase);
        if (bullet.intervalBullet != null)
            bullet.intervalBullet = bullet(bullet.intervalBullet.copy(), damageBase, sizeBase);
        if (bullet instanceof BasicBulletType) {
            ((BasicBulletType) bullet).width *= sizeBase;
            ((BasicBulletType) bullet).height *= sizeBase;
            if (bullet instanceof ArtilleryBulletType bulletType) {
                bulletType.trailSize *= sizeBase;
            }
        } else if (bullet instanceof LiquidBulletType) {
            bullet.puddles = (int) (bullet.puddles * sizeBase);
            ((LiquidBulletType) bullet).orbSize *= sizeBase;
        } else if (bullet instanceof ShrapnelBulletType) {
            ((ShrapnelBulletType) bullet).length *= sizeBase;
            ((ShrapnelBulletType) bullet).width *= sizeBase;
        } else if (bullet instanceof LaserBulletType bulletType) {
            bulletType.length *= sizeBase;
            bulletType.lifetime /= sizeBase;
            bulletType.pierceCap = (int) (bulletType.pierceCap * sizeBase);
        } else if (bullet instanceof LightningBulletType bulletType) {
            bulletType.lightningLength *= sizeBase;
        } else if (bullet instanceof ContinuousLaserBulletType bulletType) {
            bulletType.length *= sizeBase;
            bulletType.width *= sizeBase;
            bulletType.lifetime /= sizeBase;
        }
        return bullet;
    }

    public static BulletType bullet(ItemTurret turret, BulletType bullet, int num) throws IllegalAccessException {
        float damageBase = (float) Math.pow(5, num);
        float sizeBase = (float) Math.pow(1.4, num);
        BulletType bulletType = bullet(bullet, num);
        if (bulletType instanceof BulletType) {
            bulletType.rangeChange = turret.range * (sizeBase - 1);
        }
        return bulletType;
    }

    public static Effect shootEffect(Effect effect, Cons<Effect.EffectContainer> effectContainerCons) throws IllegalAccessException {
        Field[] fields = Effect.class.getDeclaredFields();
        Effect neweffect = new Effect();
        for (Field field : fields) {
            //允许通过反射访问私有变量
            field.setAccessible(true);
            //判断是否为final修饰的属性
            if (!Modifier.isFinal(field.getModifiers())) {
                //获取属性名
                String name0 = field.getName();
                //获取原物品属性的属性值
                Object value0 = field.get(effect);
                //将新物品的属性设置为和原物品相同
                if (value0 != null) switch (name0) {
                    case "renderer" -> {
                        field.set(neweffect, effectContainerCons);
                    }
                    //其他没有自定义需求的属性
                    default -> field.set(neweffect, value0);
                }
            }
        }
        return neweffect;
    }

    public static BulletType bullet(BulletType bullet, int num) throws IllegalAccessException {
        float damageBase = (float) Math.pow(5, num);
        float sizeBase = (float) Math.pow(1.4, num);
        if (bullet.shootEffect == Fx.shootSmallFlame) {
            bullet.shootEffect = load.shootEffect(Fx.shootSmallFlame, e -> {
                color(Pal.lightFlame, Pal.darkFlame, Color.gray, e.fin());

                randLenVectors(e.id, (int) Math.min(8 * sizeBase, 1024), e.finpow() * 60f * sizeBase, e.rotation, (float) Math.toDegrees(Math.atan(Math.toRadians(10f)) / sizeBase), (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.5f);
                });
            });
        }
        if (bullet.shootEffect == Fx.shootPyraFlame) {
            bullet.shootEffect = load.shootEffect(Fx.shootPyraFlame, e -> {
                color(Pal.lightPyraFlame, Pal.darkPyraFlame, Color.gray, e.fin());

                randLenVectors(e.id, (int) Math.min(10 * sizeBase, 2048), e.finpow() * 70f * sizeBase, e.rotation, (float) Math.toDegrees(Math.atan(Math.toRadians(10f)) / sizeBase), (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.6f);
                });
            });
        }
        if (bullet.shootEffect == UnitTypes.oxynoe.weapons.get(0).bullet.shootEffect) {

            bullet.shootEffect = new Effect(32f, 80f, e -> {
                color(Color.white, Pal.heal, Color.gray, e.fin());

                randLenVectors(e.id, (int) Math.min(8 * sizeBase, 1024), e.finpow() * 60f * sizeBase, e.rotation, (float) Math.toDegrees(Math.atan(Math.toRadians(10f)) / sizeBase), (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.5f);
                    Drawf.light(e.x + x, e.y + y, 16f * e.fout(), Pal.heal, 0.6f);
                });
            });
        }
        return bullet(bullet, damageBase, sizeBase);
    }

    //工厂
    public static void genericCrafter(Block genericCrafter) throws NoSuchFieldException, IllegalAccessException {
        //如果是,则通过反射获取block的consumeBuilder(消耗)
        Field field = Block.class.getDeclaredField("consumeBuilder");
        field.setAccessible(true);

        @SuppressWarnings("unchecked") Seq<Consume> consumeBuilder = (Seq<Consume>) field.get(genericCrafter);

        //新建多合成工厂anyMtiCrafter
        AnyMtiCrafter anyMtiCrafter = new AnyMtiCrafter(genericCrafter.name) {{
            //设置一些基本属性
            localizedName = Core.bundle.get("string.GenericCrafter.name") + genericCrafter.localizedName;
            category = genericCrafter.category;
            buildVisibility = genericCrafter.buildVisibility;
            description = genericCrafter.description;
            details = genericCrafter.details;
            size = genericCrafter.size;
            hasLiquids = genericCrafter.hasLiquids;
            liquidCapacity = genericCrafter.liquidCapacity;
            hasItems = genericCrafter.hasItems;
            itemCapacity = genericCrafter.itemCapacity;
            hasPower = genericCrafter.hasPower;
            //drawer = ((GenericCrafter)genericCrafter).drawer;
            region = genericCrafter.region;
        }};
        int num0 = 0;
        ItemStack[] newrequirements = new ItemStack[genericCrafter.requirements.length];
        for (ItemStack itemStack : genericCrafter.requirements) {
            Item item = ECItems.get(itemStack.item).get(1);
            int amount = itemStack.amount;
            newrequirements[num0] = new ItemStack(item, amount);
            num0++;
        }
        anyMtiCrafter.requirements = newrequirements;

        //遍历block的所有科技节点,把anyMtiCrafter作为子节点添加
        for (TechTree.TechNode techNode : genericCrafter.techNodes) {
            TechNode node = node(anyMtiCrafter, () -> {
            });
            node.parent = techNode;
            techNode.children.add(node);
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
                        Item item = ECItems.get(((ConsumeItems) consume).items[i].item).get(num);
                        int amount = ((ConsumeItems) consume).items[i].amount;
                        consumeItems[i] = new ItemStack(item, amount);
                    }
                } else if (consume instanceof ConsumeLiquids) {
                    consumeLiquids = new LiquidStack[((ConsumeLiquids) consume).liquids.length];
                    for (int i = 0; i < consumeLiquids.length; i++) {
                        Liquid liquid = ECLiquids.get(((ConsumeLiquids) consume).liquids[i].liquid).get(num);
                        float amount = ((ConsumeLiquids) consume).liquids[i].amount;
                        consumeLiquids[i] = new LiquidStack(liquid, amount);
                    }
                } else if (consume instanceof ConsumeLiquid) {
                    Liquid liquid = ECLiquids.get(((ConsumeLiquid) consume).liquid).get(num);
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
            if (((GenericCrafter) genericCrafter).outputItems != null) {
                outputItems = new ItemStack[((GenericCrafter) genericCrafter).outputItems.length];
                for (int i = 0; i < ((GenericCrafter) genericCrafter).outputItems.length; i++) {
                    Item item = ECItems.get(((GenericCrafter) genericCrafter).outputItems[i].item).get(num);
                    int amount = ((GenericCrafter) genericCrafter).outputItems[i].amount;
                    outputItems[i] = new ItemStack(item, amount);
                }
            } else if (((GenericCrafter) genericCrafter).outputItem != null) {
                Item item = ECItems.get(((GenericCrafter) genericCrafter).outputItem.item).get(num);
                int amount = ((GenericCrafter) genericCrafter).outputItem.amount;
                outputItems = new ItemStack[]{new ItemStack(item, amount)};
            }

            //初始化输出液体
            LiquidStack[] outputLiquids = null;
            //判断输出液体的个数并依次执行对应的压缩版输出
            if (((GenericCrafter) genericCrafter).outputLiquids != null) {
                outputLiquids = new LiquidStack[((GenericCrafter) genericCrafter).outputLiquids.length];
                for (int i = 0; i < ((GenericCrafter) genericCrafter).outputLiquids.length; i++) {
                    Liquid liquid = ECLiquids.get(((GenericCrafter) genericCrafter).outputLiquids[i].liquid).get(num);
                    float amount = ((GenericCrafter) genericCrafter).outputLiquids[i].amount;
                    outputLiquids[i] = new LiquidStack(liquid, amount);
                }
            } else if (((GenericCrafter) genericCrafter).outputLiquid != null) {
                Liquid liquid = ECLiquids.get(((GenericCrafter) genericCrafter).outputLiquid.liquid).get(num);
                float amount = ((GenericCrafter) genericCrafter).outputLiquid.amount;
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
                craftTime = ((GenericCrafter) genericCrafter).craftTime;
                craftEffect = ((GenericCrafter) genericCrafter).craftEffect;
                if (finalConsumeItems != null) consumeItems(finalConsumeItems);
                if (finalConsumeLiquids != null) consumeLiquids(finalConsumeLiquids);
                if (finalConsumePower > 0) consumePower(finalConsumePower);
                outputItems = finalOutputItems;
                outputLiquids = finalOutputLiquids;
            }});
        }

        //贴图前缀
        //贴图后缀
        Seq<String> sprites = new Seq<>(new String[]{"", "-bottom", "-middle", "-top"});
        if (anyMtiCrafter.variants > 0) {
            for (int j = 0; j < anyMtiCrafter.variants; j++) {
                sprites.add(Integer.toString(j + 1));
            }
        }
        //遍历贴图后缀
        for (String sprite : sprites) {
            //延时运行,来自I hope... 大佬
            Tool.forceRun(() -> {
                //判断是否有该后缀贴图
                if (!Core.atlas.has(genericCrafter.name + sprite)) return false;
                //以原贴图覆盖新内容贴图
                Core.atlas.addRegion(anyMtiCrafter.name + sprite, Core.atlas.find(genericCrafter.name + sprite));
                return true;
            });
        }
    }

    //分离机
    public static void Separator(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            //创建新钻头
            Separator newBlock = new Separator(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(Separator.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取属性名
                    String name0 = field.getName();
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);
                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = (Seq<Consume>) value0;
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (Consume consume : consumeBuilder) {
                                if (consume instanceof ConsumeLiquid consume0) {
                                    Liquid liquid = ECLiquids.get(consume0.liquid).get(i);
                                    float amount = consume0.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume0.optional;
                                        booster = consume0.booster;
                                        update = consume0.update;
                                        multiplier = consume0.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else if (consume instanceof ConsumeItems) {
                                    ItemStack[] itemStacks = new ItemStack[((ConsumeItems) consume).items.length];
                                    for (int j = 0; j < ((ConsumeItems) consume).items.length; j++) {
                                        Item item = ECItems.get(((ConsumeItems) consume).items[j].item).get(i);
                                        int amount = ((ConsumeItems) consume).items[j].amount;
                                        itemStacks[j] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(itemStacks));
                                } else newconsumeBuilder.add(consume);
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "results" -> {
                            if (value0 != null) {
                                ItemStack[] results = (ItemStack[]) value0;
                                ItemStack[] newresults = new ItemStack[results.length];
                                for (int j = 0; j < results.length; j++) {
                                    Item item = ECItems.get(results[j].item).get(i);
                                    int amount = results[j].amount;
                                    newresults[j] = new ItemStack(item, amount);
                                }
                                field.set(newBlock, newresults);
                            }
                        }
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-spinner", "-bottom"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //单位工厂
    public static void UnitFactory(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            //创建新钻头
            UnitFactory newBlock = new UnitFactory(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(PayloadBlock.class.getDeclaredFields());
            field0.add(UnitFactory.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "plans" -> {
                            Seq<UnitFactory.UnitPlan> plans = (Seq<UnitFactory.UnitPlan>) value0;
                            Seq<UnitFactory.UnitPlan> newplans = new Seq<>();
                            for (UnitFactory.UnitPlan unitPlan : plans) {
                                if (ECUnits.get(unitPlan.unit) != null) {
                                    UnitType unit = ECUnits.get(unitPlan.unit).get(i);
                                    float time = unitPlan.time;
                                    ItemStack[] requirements = unitPlan.requirements;
                                    ItemStack[] newrequirements = new ItemStack[unitPlan.requirements.length];
                                    int j = 0;
                                    for (ItemStack itemStack : requirements) {
                                        Item item = ECItems.get(itemStack.item).get(i);
                                        int amount = itemStack.amount;
                                        newrequirements[j] = new ItemStack(item, amount);
                                        j++;
                                    }
                                    newplans.add(new UnitFactory.UnitPlan(unit, time, newrequirements));
                                }
                            }
                            field.set(newBlock, newplans);
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = ((Seq<Consume>) field.get(block)).copy();
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (int j = 0; j < consumeBuilder.size; j++) {
                                if (consumeBuilder.get(j) instanceof ConsumeItems consume) {
                                    ItemStack[] items = new ItemStack[consume.items.length];
                                    for (int k = 0; k < consume.items.length; k++) {
                                        Item item = ECItems.get(consume.items[k].item).get(i);
                                        int amount = consume.items[k].amount;
                                        items[k] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(items));
                                } else if (consumeBuilder.get(j) instanceof ConsumeItemDynamic) {
                                    ConsumeItemDynamic consume = new ConsumeItemDynamic((UnitFactory.UnitFactoryBuild e) -> e.currentPlan != -1 ? newBlock.plans.get(Math.min(e.currentPlan, newBlock.plans.size - 1)).requirements : ItemStack.empty);
                                    newconsumeBuilder.add(consume);
                                } else newconsumeBuilder.add(consumeBuilder.get(j));
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "buildType", "barMap", "consumers" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-top"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //重构工厂
    public static void Reconstructor(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            //创建新钻头
            Reconstructor newBlock = new Reconstructor(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(PayloadBlock.class.getDeclaredFields());
            field0.add(Reconstructor.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "upgrades" -> {
                            Seq<UnitType[]> upgrades = (Seq<UnitType[]>) value0;
                            Seq<UnitType[]> newupgrades = new Seq<>();
                            for (UnitType[] unitTypes : upgrades) {
                                if (ECUnits.get(unitTypes[0]) != null && ECUnits.get(unitTypes[1]) != null) {
                                    UnitType unit0 = ECUnits.get(unitTypes[0]).get(i);
                                    UnitType unit1 = ECUnits.get(unitTypes[1]).get(i);
                                    newupgrades.add(new UnitType[]{unit0, unit1});
                                }
                            }
                            field.set(newBlock, newupgrades);
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = ((Seq<Consume>) field.get(block)).copy();
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (int j = 0; j < consumeBuilder.size; j++) {
                                if (consumeBuilder.get(j) instanceof ConsumeItems consume) {
                                    ItemStack[] items = new ItemStack[consume.items.length];
                                    for (int k = 0; k < consume.items.length; k++) {
                                        Item item = ECItems.get(consume.items[k].item).get(i);
                                        int amount = consume.items[k].amount;
                                        items[k] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(items));
                                } else if (consumeBuilder.get(j) instanceof ConsumeLiquid consume) {
                                    Liquid liquid = ECLiquids.get(consume.liquid).get(i);
                                    float amount = consume.amount;
                                    newconsumeBuilder.add(new ConsumeLiquid(liquid, amount));
                                } else newconsumeBuilder.add(consumeBuilder.get(j));
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-top"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //电力
    //发电厂
    public static void consumeGenerator(Block consumeGenerator) throws IllegalAccessException, NoSuchFieldException {

        Field consumes = Block.class.getDeclaredField("consumeBuilder");
        boolean isConsume = true;
        consumes.setAccessible(true);
        for (Consume consume : (Seq<Consume>) consumes.get(consumeGenerator)) {
            if (consume instanceof ConsumeItemFilter) {
                isConsume = false;
                break;
            }
        }
        Seq<Block> ConsumeGenerators = new Seq<>();
        ConsumeGenerators.add(consumeGenerator);
        ECBlocks.put(consumeGenerator, ConsumeGenerators);
        if (isConsume) {
            //创建物品检索表
            //根据原物品批量创建压缩物品
            for (int i = 1; i < 10; i++) {
                //创建新钻头
                float attributeBase = (float) Math.pow(5, i);
                int finalI = i;
                ConsumeGenerator newconsumeGenerator = new ConsumeGenerator(consumeGenerator.name + finalI) {{
                    localizedName = Core.bundle.get("string.Compress" + finalI) + consumeGenerator.localizedName;
                    description = consumeGenerator.description;
                    details = consumeGenerator.details;
                }};
                //将此钻头加入方块检索表
                ECBlocks.get(consumeGenerator).add(newconsumeGenerator);


                //获取Block的全部属性
                Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
                //添加的属性
                field0.add(PowerGenerator.class.getDeclaredFields());
                field0.add(ConsumeGenerator.class.getDeclaredFields());
                //遍历全部属性
                for (Field field : field0) {
                    //允许通过反射访问私有变量
                    field.setAccessible(true);
                    //获取属性名
                    String name0 = field.getName();
                    //判断是否为final修饰的属性
                    if (!Modifier.isFinal(field.getModifiers())) {
                        //获取原物品属性的属性值
                        Object value0 = field.get(consumeGenerator);
                        //将新物品的属性设置为和原物品相同
                        if (value0 != null) switch (name0) {
                            case "requirements" -> {
                                ItemStack[] requirements = new ItemStack[consumeGenerator.requirements.length];
                                ItemStack[] TechRequirements = new ItemStack[consumeGenerator.requirements.length];
                                for (int j = 0; j < consumeGenerator.requirements.length; j++) {
                                    Item item = ECItems.get(consumeGenerator.requirements[j].item).get(i);
                                    int amount = consumeGenerator.requirements[j].amount;
                                    requirements[j] = new ItemStack(item, amount);

                                    TechRequirements[j] = new ItemStack(item, amount * 30);
                                }
                                field.set(newconsumeGenerator, requirements);
                                //遍历block的所有科技节点,把anyMtiCrafter作为子节点添加
                                for (TechTree.TechNode techNode : ECBlocks.get(consumeGenerator).get(i - 1).techNodes) {
                                    TechNode node = node(newconsumeGenerator, TechRequirements, () -> {
                                    });
                                    node.parent = techNode;
                                    techNode.children.add(node);
                                }
                            }
                            case "outputLiquid" -> {
                                if (value0 != null) {
                                    LiquidStack outputLiquid = (LiquidStack) value0;
                                    Liquid liquid = ECLiquids.get(outputLiquid.liquid).get(i);
                                    float amount = outputLiquid.amount;
                                    field.set(newconsumeGenerator, new LiquidStack(liquid, amount));
                                }
                            }
                            case "buildType", "barMap" -> {
                            }
                            case "powerProduction" -> field.set(newconsumeGenerator, (float) value0 * attributeBase);
                            case "consumeBuilder" -> {
                                Seq<Consume> consumeBuilder = ((Seq<Consume>) field.get(consumeGenerator)).copy();
                                Seq<Consume> newconsumeBuilder = new Seq<>();
                                for (int j = 0; j < consumeBuilder.size; j++) {
                                    if (consumeBuilder.get(j) instanceof ConsumeItems) {
                                        ItemStack[] newItemStack = new ItemStack[((ConsumeItems) consumeBuilder.get(j)).items.length];
                                        int k = 0;
                                        for (ItemStack itemStack : ((ConsumeItems) consumeBuilder.get(j)).items) {
                                            Item item = ECItems.get(itemStack.item).get(i);
                                            int amount = itemStack.amount;
                                            newItemStack[k] = new ItemStack(item, amount);
                                            k++;
                                        }
                                        newconsumeBuilder.add(new ConsumeItems(newItemStack));
                                    } else if (consumeBuilder.get(j) instanceof ConsumeLiquid) {
                                        Liquid liquid = ECLiquids.get(((ConsumeLiquid) consumeBuilder.get(j)).liquid).get(i);
                                        float amount = ((ConsumeLiquid) consumeBuilder.get(j)).amount;
                                        newconsumeBuilder.add(new ConsumeLiquid(liquid, amount));
                                    } else if (consumeBuilder.get(j) instanceof ConsumeLiquids consume) {
                                        LiquidStack[] newliquids = new LiquidStack[consume.liquids.length];
                                        int k = 0;
                                        for (LiquidStack liquidStack : consume.liquids) {
                                            Liquid liquid = ECLiquids.get(liquidStack.liquid).get(i);
                                            float amount = liquidStack.amount;
                                            newliquids[k] = new LiquidStack(liquid, amount);
                                            k++;
                                        }
                                        newconsumeBuilder.add(new ConsumeLiquids(newliquids));
                                    } else newconsumeBuilder.add(consumeBuilder.get(j));
                                }
                                field.set(newconsumeGenerator, newconsumeBuilder);
                            }
                            //其他没有自定义需求的属性
                            default -> field.set(newconsumeGenerator, value0);
                        }
                    }
                }

                //贴图前缀
                String[] prefixs = {""};
                //贴图后缀
                String[] sprites = {"", "-rotator", "-top", "-cap", "-liquid", "-turbine", "-bottom", "-mid", "-glow", "-piston0", "-piston1", "-piston-icon"};
                //遍历贴图后缀
                for (String sprite : sprites) {
                    for (String prefix : prefixs) {
                        //延时运行,来自@(I hope...)
                        Tool.forceRun(() -> {
                            //判断原版是否有该后缀贴图
                            if (!Core.atlas.has(prefix + consumeGenerator.name + sprite)) return false;
                            //以原版贴图覆盖新物品贴图
                            Core.atlas.addRegion(prefix + newconsumeGenerator.name + sprite, Core.atlas.find(prefix + consumeGenerator.name + sprite));
                            return true;
                        });
                    }
                }
            }
        } else {
            //创建物品检索表
            //根据原物品批量创建压缩物品
            //创建新钻头
            ConsumeGenerator newconsumeGenerator = new ConsumeGenerator(consumeGenerator.name) {{
                localizedName = Core.bundle.get("string.GenericCrafter.name") + consumeGenerator.localizedName;
                description = consumeGenerator.description + Core.bundle.get("string.ConsumeGenerator.NoExplode");
                details = consumeGenerator.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(consumeGenerator).add(newconsumeGenerator);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加的属性
            field0.add(PowerGenerator.class.getDeclaredFields());
            field0.add(ConsumeGenerator.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(consumeGenerator);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[consumeGenerator.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[consumeGenerator.requirements.length];
                            for (int j = 0; j < consumeGenerator.requirements.length; j++) {
                                Item item = ECItems.get(consumeGenerator.requirements[j].item).get(1);
                                int amount = consumeGenerator.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newconsumeGenerator, requirements);
                            //遍历block的所有科技节点,把anyMtiCrafter作为子节点添加
                            for (TechTree.TechNode techNode : consumeGenerator.techNodes) {
                                TechNode node = node(newconsumeGenerator, TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "buildType" -> {
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = ((Seq<Consume>) field.get(consumeGenerator)).copy();
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (int i = 0; i < consumeBuilder.size; i++) {
                                if (consumeBuilder.get(i) instanceof ConsumeItemExplode) {
                                    consumeBuilder.set(i, new ConsumeItemExplode() {{
                                        baseChance = -1;
                                    }});
                                } else if (consumeBuilder.get(i) instanceof ConsumeLiquid) {
                                    for (int j = 1; j < 10; j++) {
                                        ConsumeLiquid consume = (ConsumeLiquid) consumeBuilder.get(i);
                                        newconsumeBuilder.add(new ConsumeLiquid(ECLiquids.get(consume.liquid).get(j), (float) (consume.amount / Math.pow(5, j))) {{
                                            booster = true;
                                            multiplier = consume.multiplier;
                                            optional = true;
                                            update = consume.update;
                                        }});
                                    }
                                }
                            }
                            consumeBuilder.add(newconsumeBuilder);
                            field.set(newconsumeGenerator, consumeBuilder);
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newconsumeGenerator, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-rotator", "-top", "-cap", "-liquid", "-turbine"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + consumeGenerator.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newconsumeGenerator.name + sprite, Core.atlas.find(prefix + consumeGenerator.name + sprite));
                        return true;
                    });
                }
            }
        }


    }

    //不同的实现方法,之前的方法有bug但我找不到
    //电力节点
    public static void powerNode(Block powerNode) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> PowerNodes = new Seq<>();
        PowerNodes.add(powerNode);
        ECBlocks.put(powerNode, PowerNodes);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            int finalI = i;
            PowerNode newpowerNode = new PowerNode(powerNode.name + num) {
                {
                    localizedName = Core.bundle.get("string.Compress" + num) + powerNode.localizedName;
                    description = powerNode.description;
                    details = powerNode.details;


                    size = powerNode.size;
                    maxNodes = (int) (((PowerNode) powerNode).maxNodes * attributeBase);
                    laserRange = ((PowerNode) powerNode).laserRange * sizeBase;
                    schematicPriority = powerNode.schematicPriority;

                    ItemStack[] newrequirements = new ItemStack[powerNode.requirements.length];
                    int j = 0;
                    for (ItemStack itemStack : powerNode.requirements) {
                        Item item = ECItems.get(itemStack.item).get(num);
                        int amount = itemStack.amount;
                        newrequirements[j] = new ItemStack(item, amount);
                        j++;
                    }
                    requirements(powerNode.category, powerNode.buildVisibility, newrequirements);


                }
            };
            //加入方块检索表
            ECBlocks.get(powerNode).add(newpowerNode);
            for (TechNode techNode : ECBlocks.get(powerNode).get(finalI - 1).techNodes) {
                TechNode node = node(ECBlocks.get(powerNode).get(finalI), () -> {
                });
                node.parent = techNode;
                techNode.children.add(node);
            }
            /*

            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(PowerBlock.class.getDeclaredFields());
            field0.add(PowerNode.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);


                //判断是否为final修饰的属性
                if ((!Modifier.isFinal(field.getModifiers())) && (!Modifier.isProtected(field.getModifiers()))) {
                    //获取属性名
                    String name0 = field.getName();
                    //获取原物品属性的属性值
                    Object value0 = field.get(powerNode);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(newpowerNode, (int) (160 * attributeBase));
                            else field.set(newpowerNode, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[powerNode.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[powerNode.requirements.length];
                            for (int j = 0; j < powerNode.requirements.length; j++) {
                                Item item = ECItems.get(powerNode.requirements[j].item).get(i);
                                int amount = powerNode.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newpowerNode, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(powerNode).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(powerNode).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "buildType", "barMap"  -> {
                        }
                        case "maxNodes" -> field.set(newpowerNode, (int) ((int) value0 * attributeBase));
                        case "laserRange" -> field.set(newpowerNode, (float) value0 * sizeBase);
                        //其他没有自定义需求的属性
                        default -> {
                            field.set(newpowerNode, value0);
                            if (value0 != null && (value0.getClass() == Integer.class || value0.getClass() == Float.class))
                                Log.info(newpowerNode.localizedName + "-" +field.getName() +":"+ value0);
                        }
                    }
                }
            }
            */
            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-rotator", "-top"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + powerNode.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newpowerNode.name + sprite, Core.atlas.find(prefix + powerNode.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //电力源
    public static void PowerSource(Block powerNode) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> PowerNodes = new Seq<>();
        PowerNodes.add(powerNode);
        ECBlocks.put(powerNode, PowerNodes);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            PowerSource newpowerNode = new PowerSource(powerNode.name + num) {
                {
                    localizedName = Core.bundle.get("string.Compress" + num) + powerNode.localizedName;
                    description = powerNode.description;
                    details = powerNode.details;


                    size = powerNode.size;
                    maxNodes = (int) (((PowerNode) powerNode).maxNodes * attributeBase);
                    powerProduction = ((PowerSource) powerNode).powerProduction * attributeBase;
                    laserRange = ((PowerNode) powerNode).laserRange * sizeBase;
                    schematicPriority = powerNode.schematicPriority;

                    ItemStack[] newrequirements = new ItemStack[powerNode.requirements.length];
                    int j = 0;
                    for (ItemStack itemStack : powerNode.requirements) {
                        Item item = ECItems.get(itemStack.item).get(num);
                        int amount = itemStack.amount;
                        newrequirements[j] = new ItemStack(item, amount);
                        j++;
                    }
                    requirements(powerNode.category, powerNode.buildVisibility, newrequirements);


                }
            };
            //加入方块检索表
            ECBlocks.get(powerNode).add(newpowerNode);

            /*
            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(PowerBlock.class.getDeclaredFields());
            field0.add(PowerNode.class.getDeclaredFields());
            field0.add(PowerSource.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取属性名
                    String name0 = field.getName();
                    //获取原物品属性的属性值
                    Object value0 = field.get(powerNode);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(newpowerNode, (int) (160 * attributeBase));
                            else field.set(newpowerNode, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[powerNode.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[powerNode.requirements.length];
                            for (int j = 0; j < powerNode.requirements.length; j++) {
                                Item item = ECItems.get(powerNode.requirements[j].item).get(i);
                                int amount = powerNode.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newpowerNode, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(powerNode).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(powerNode).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "buildType", "barMap" -> {
                        }
                        case "maxNodes" -> field.set(newpowerNode, (int) ((int) value0 * attributeBase));
                        case "laserRange" -> field.set(newpowerNode, (float) value0 * sizeBase);
                        case "powerProduction" -> field.set(newpowerNode, (float) value0 * attributeBase);
                        //其他没有自定义需求的属性
                        default -> field.set(newpowerNode, value0);
                    }
                }
            }

             */

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-rotator", "-top"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + powerNode.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newpowerNode.name + sprite, Core.atlas.find(prefix + powerNode.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //电池
    public static void battery(Block battery) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Batterys = new Seq<>();
        Batterys.add(battery);
        ECBlocks.put(battery, Batterys);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            //创建新钻头
            Battery newbattery = new Battery(battery.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + battery.localizedName;
                description = battery.description;
                details = battery.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(battery).add(newbattery);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(Battery.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取属性名
                    String name0 = field.getName();
                    //获取原物品属性的属性值
                    Object value0 = field.get(battery);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(newbattery, (int) (160 * attributeBase));
                            else field.set(newbattery, (int) ((int) value0 * attributeBase));
                        }
                        case "baseExplosiveness" -> field.set(newbattery, (float) value0 * attributeBase);
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[battery.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[battery.requirements.length];
                            for (int j = 0; j < battery.requirements.length; j++) {
                                Item item = ECItems.get(battery.requirements[j].item).get(i);
                                int amount = battery.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newbattery, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(battery).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(battery).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "buildType", "consPower", "barMap" -> {
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumes = new Seq<>();
                            for (Consume consume : (Seq<Consume>) value0) {
                                if (consume instanceof ConsumePower) {
                                    consumes.add(new ConsumePower(0f, ((ConsumePower) consume).capacity * attributeBase, true));
                                } else {
                                    consumes.add(consume);
                                }
                            }
                            field.set(newbattery, consumes);
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newbattery, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-rotator", "-top"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + battery.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newbattery.name + sprite, Core.atlas.find(prefix + battery.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //灯
    //和电力节点一样
    public static void lightBlock(Block lightBlock) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> LightBlocks = new Seq<>();
        LightBlocks.add(lightBlock);
        ECBlocks.put(lightBlock, LightBlocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            int finalI = i;
            LightBlock newlightBlock = new LightBlock(lightBlock.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + lightBlock.localizedName;
                description = lightBlock.description;
                details = lightBlock.details;


                size = lightBlock.size;
                radius = ((LightBlock) lightBlock).radius * sizeBase;
                schematicPriority = lightBlock.schematicPriority;

                consumePower(((LightBlock)lightBlock).consPower.usage * attributeBase);

                ItemStack[] newrequirements = new ItemStack[lightBlock.requirements.length];
                int j = 0;
                for (ItemStack itemStack : lightBlock.requirements) {
                    Item item = ECItems.get(itemStack.item).get(num);
                    int amount = itemStack.amount;
                    newrequirements[j] = new ItemStack(item, amount);
                    j++;
                }
                requirements(lightBlock.category, lightBlock.buildVisibility, newrequirements);

            }};
            //将此钻头加入方块检索表
            ECBlocks.get(lightBlock).add(newlightBlock);
            for (TechNode techNode : ECBlocks.get(lightBlock).get(finalI - 1).techNodes) {
                TechNode node = node(ECBlocks.get(lightBlock).get(finalI), () -> {
                });
                node.parent = techNode;
                techNode.children.add(node);
            }

            /*
            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(LightBlock.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //判断是否为final修饰的属性
                if (Modifier.isPublic(field.getModifiers())) {
                    //获取属性名
                    String name0 = field.getName();
                    //获取原物品属性的属性值
                    Object value0 = field.get(lightBlock);
                    //将新物品的属性设置为和原物品相同
                    switch (name0) {
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = (Seq<Consume>) value0;
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (Consume consume : consumeBuilder) {
                                if (consume instanceof ConsumeLiquid consume0) {
                                    Liquid liquid = ECLiquids.get(consume0.liquid).get(i);
                                    float amount = consume0.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else if (consume instanceof ConsumeItems) {
                                    ItemStack[] itemStacks = new ItemStack[((ConsumeItems) consume).items.length];
                                    for (int j = 0; j < ((ConsumeItems) consume).items.length; j++) {
                                        Item item = ECItems.get(((ConsumeItems) consume).items[j].item).get(i);
                                        int amount = ((ConsumeItems) consume).items[j].amount;
                                        itemStacks[j] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(itemStacks) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }});
                                } else if (consume instanceof ConsumePower consumePower) {
                                    float u = consumePower.usage * attributeBase;
                                    float c = consumePower.capacity;
                                    boolean b = consumePower.booster;
                                    newlightBlock.consumePower(u);
                                } else newconsumeBuilder.add(consume);
                            }
                            field.set(newlightBlock, newconsumeBuilder);
                        }
                        //其他没有自定义需求的属性
                        default -> {
                        }
                    }
                }
            }


             */


            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-top"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + lightBlock.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newlightBlock.name + sprite, Core.atlas.find(prefix + lightBlock.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //核反应堆
    public static void NuclearReactor(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            NuclearReactor newBlock = new NuclearReactor(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(NuclearReactor.class.getDeclaredFields());
            field0.add(PowerGenerator.class.getDeclaredFields());
            field0.add(PowerDistributor.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {

                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = ((Seq<Consume>) field.get(block)).copy();
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (int j = 0; j < consumeBuilder.size; j++) {
                                if (consumeBuilder.get(j) instanceof ConsumeItems consume) {
                                    ItemStack[] items = new ItemStack[consume.items.length];
                                    for (int k = 0; k < consume.items.length; k++) {
                                        Item item = ECItems.get(consume.items[k].item).get(i);
                                        int amount = consume.items[k].amount;
                                        items[k] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(items));
                                } else if (consumeBuilder.get(j) instanceof ConsumeLiquid consume) {
                                    Liquid liquid = ECLiquids.get(consume.liquid).get(i);
                                    float amount = consume.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else newconsumeBuilder.add(consumeBuilder.get(j));
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "fuelItem" -> {
                            Item item = ECItems.get((Item) value0).get(i);
                            field.set(newBlock, item);
                        }

                        case "powerProduction" -> field.set(newBlock, (float) value0 * attributeBase);
                        case "explosionPuddleRange", "explosionPuddleAmount" ->
                                field.set(newBlock, (float) value0 * sizeBase);
                        case "explosionDamage" -> field.set(newBlock, (int) ((int) value0 * attributeBase));
                        case "explosionRadius", "explosionPuddles" ->
                                field.set(newBlock, (int) ((int) value0 * sizeBase));

                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-top", "-light"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //冲击反应堆
    public static void ImpactReactor(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            ImpactReactor newBlock = new ImpactReactor(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(PowerGenerator.class.getDeclaredFields());
            field0.add(ImpactReactor.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {

                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = ((Seq<Consume>) field.get(block)).copy();
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (int j = 0; j < consumeBuilder.size; j++) {
                                if (consumeBuilder.get(j) instanceof ConsumeItems consume) {
                                    ItemStack[] items = new ItemStack[consume.items.length];
                                    for (int k = 0; k < consume.items.length; k++) {
                                        Item item = ECItems.get(consume.items[k].item).get(i);
                                        int amount = consume.items[k].amount;
                                        items[k] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(items));
                                } else if (consumeBuilder.get(j) instanceof ConsumeLiquid consume) {
                                    Liquid liquid = ECLiquids.get(consume.liquid).get(i);
                                    float amount = consume.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else if (consumeBuilder.get(j) instanceof ConsumePower consume) {
                                    float usage = consume.usage * attributeBase;
                                    float capacity = consume.capacity;
                                    boolean buffered = consume.buffered;
                                    newconsumeBuilder.add(new ConsumePower(usage, capacity, buffered));
                                } else newconsumeBuilder.add(consumeBuilder.get(j));
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "fuelItem" -> {
                            Item item = ECItems.get((Item) value0).get(i);
                            field.set(newBlock, item);
                        }

                        case "powerProduction" -> field.set(newBlock, (float) value0 * attributeBase);
                        case "explosionPuddleRange", "explosionPuddleAmount", "explosionShake",
                             "explosionShakeDuration" -> field.set(newBlock, (float) value0 * sizeBase);
                        case "explosionDamage" -> field.set(newBlock, (int) ((int) value0 * attributeBase));
                        case "explosionRadius", "explosionPuddles" ->
                                field.set(newBlock, (int) ((int) value0 * sizeBase));

                        case "explosionPuddleLiquid" -> {
                            if (value0 != null) field.set(newBlock, ECLiquids.get((Liquid) value0).get(i));
                        }
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-bottom", "-light", "-plasma-0", "-plasma-1", "-plasma-2", "-plasma-3", "-plasma-4"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //地热
    public static void ThermalGenerator(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            ThermalGenerator newBlock = new ThermalGenerator(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(PowerGenerator.class.getDeclaredFields());
            field0.add(ThermalGenerator.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取属性名
                    String name0 = field.getName();
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);
                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "powerProduction" -> field.set(newBlock, (float) value0 * attributeBase);
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-rotator"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //太阳能
    public static void SolarGenerator(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            //创建新钻头
            SolarGenerator newBlock = new SolarGenerator(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(PowerGenerator.class.getDeclaredFields());
            field0.add(SolarGenerator.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "powerProduction" -> field.set(newBlock, (float) value0 * attributeBase);
                        case "buildType" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {""};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //液体容器/路由器
    public static void LiquidRouter(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            LiquidRouter newBlock = new LiquidRouter(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(LiquidBlock.class.getDeclaredFields());
            field0.add(LiquidRouter.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取属性名
                    String name0 = field.getName();
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);
                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "liquidCapacity" -> field.set(newBlock, (float) value0 * attributeBase);
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-liquid", "-top", "-bottom"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //物品源
    public static void ItemSource(Block powerNode) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> PowerNodes = new Seq<>();
        PowerNodes.add(powerNode);
        ECBlocks.put(powerNode, PowerNodes);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            ECItemSource newpowerNode = new ECItemSource(powerNode.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + powerNode.localizedName;
                description = powerNode.description;
                details = powerNode.details;

                itemsPerSecond = (int) (((ItemSource)powerNode).itemsPerSecond*attributeBase);
            }};
            //加入方块检索表
            ECBlocks.get(powerNode).add(newpowerNode);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取属性名
                    String name0 = field.getName();
                    //获取原物品属性的属性值
                    Object value0 = field.get(powerNode);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {
                        case "health" -> {
                            if ((int) value0 == -1) field.set(newpowerNode, (int) (160 * attributeBase));
                            else field.set(newpowerNode, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[powerNode.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[powerNode.requirements.length];
                            for (int j = 0; j < powerNode.requirements.length; j++) {
                                Item item = ECItems.get(powerNode.requirements[j].item).get(i);
                                int amount = powerNode.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newpowerNode, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(powerNode).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(powerNode).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "buildType", "barMap" , "lastConfig"  , "configurations" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newpowerNode, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-rotator", "-top"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + powerNode.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newpowerNode.name + sprite, Core.atlas.find(prefix + powerNode.name + sprite));
                        return true;
                    });
                }
            }
        }
    }

    //液体源
    public static void LiquidSource(Block block) throws IllegalAccessException {
        //创建物品检索表
        Seq<Block> Blocks = new Seq<>();
        Blocks.add(block);
        ECBlocks.put(block, Blocks);
        //根据原物品批量创建压缩物品
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新钻头
            LiquidSource newBlock = new LiquidSource(block.name + num) {{
                localizedName = Core.bundle.get("string.Compress" + num) + block.localizedName;
                description = block.description;
                details = block.details;
            }};
            //将此钻头加入方块检索表
            ECBlocks.get(block).add(newBlock);


            //获取Block的全部属性
            Seq<Field> field0 = new Seq<>(Block.class.getDeclaredFields());
            //添加属性
            field0.add(LiquidSource.class.getDeclaredFields());
            //遍历全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(block);
                    //将新物品的属性设置为和原物品相同
                    if (value0 != null) switch (name0) {

                        case "health" -> {
                            if ((int) value0 == -1) field.set(block, (int) (160 * attributeBase));
                            else field.set(newBlock, (int) ((int) value0 * attributeBase));
                        }
                        case "requirements" -> {
                            ItemStack[] requirements = new ItemStack[block.requirements.length];
                            ItemStack[] TechRequirements = new ItemStack[block.requirements.length];
                            for (int j = 0; j < block.requirements.length; j++) {
                                Item item = ECItems.get(block.requirements[j].item).get(i);
                                int amount = block.requirements[j].amount;
                                requirements[j] = new ItemStack(item, amount);

                                TechRequirements[j] = new ItemStack(item, amount * 30);
                            }
                            field.set(newBlock, requirements);
                            //遍历上级的全部科技节点,将本方块作为子节点添加
                            for (TechNode techNode : ECBlocks.get(block).get(i - 1).techNodes) {
                                TechNode node = node(ECBlocks.get(block).get(i), TechRequirements, () -> {
                                });
                                node.parent = techNode;
                                techNode.children.add(node);
                            }
                        }
                        case "consumeBuilder" -> {
                            Seq<Consume> consumeBuilder = ((Seq<Consume>) field.get(block)).copy();
                            Seq<Consume> newconsumeBuilder = new Seq<>();
                            for (int j = 0; j < consumeBuilder.size; j++) {
                                if (consumeBuilder.get(j) instanceof ConsumeItems consume) {
                                    ItemStack[] items = new ItemStack[consume.items.length];
                                    for (int k = 0; k < consume.items.length; k++) {
                                        Item item = ECItems.get(consume.items[k].item).get(i);
                                        int amount = consume.items[k].amount;
                                        items[k] = new ItemStack(item, amount);
                                    }
                                    newconsumeBuilder.add(new ConsumeItems(items));
                                } else if (consumeBuilder.get(j) instanceof ConsumeLiquid consume) {
                                    Liquid liquid = ECLiquids.get(consume.liquid).get(i);
                                    float amount = consume.amount;
                                    ConsumeLiquid newconsume = new ConsumeLiquid(liquid, amount) {{
                                        optional = consume.optional;
                                        booster = consume.booster;
                                        update = consume.update;
                                        multiplier = consume.multiplier;
                                    }};
                                    newconsumeBuilder.add(newconsume);
                                } else if (consumeBuilder.get(j) instanceof ConsumePower consume) {
                                    float usage = consume.usage;
                                    float capacity = consume.capacity;
                                    boolean buffered = consume.buffered;
                                    newconsumeBuilder.add(new ConsumePower(usage, capacity, buffered));
                                } else newconsumeBuilder.add(consumeBuilder.get(j));
                            }
                            field.set(newBlock, newconsumeBuilder);
                        }
                        case "liquidCapacity" -> field.set(newBlock,(float)value0*attributeBase);
                        case "buildType", "barMap" -> {
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newBlock, value0);
                    }
                }
            }

            //贴图前缀
            String[] prefixs = {""};
            //贴图后缀
            String[] sprites = {"", "-rotator", "-top"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + block.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(prefix + newBlock.name + sprite, Core.atlas.find(prefix + block.name + sprite));
                        return true;
                    });
                }
            }
        }
    }


}
