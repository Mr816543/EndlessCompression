package ec.content;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.struct.Seq;
import arc.util.Eachable;
import arc.util.Log;
import ec.cType.ECBeaker;
import mindustry.content.TechTree;
import mindustry.ctype.MappableContent;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.consumers.Consume;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static ec.content.ECItems.All;
import static ec.content.ECItems.ECItems;
import static ec.content.ECLiquids.ECLiquids;
import static mindustry.content.Items.silicon;
import static mindustry.content.TechTree.node;
import static mindustry.type.ItemStack.with;

public class ECTest {
    public static void load() {
        ECBeaker Compressor = new ECBeaker("Compressor") {{
            requirements(Category.crafting, with(ECItems.get(silicon).get(0), 5));

            localizedName = Core.bundle.get("string.supperCompressor.name");
            description = Core.bundle.get("string.Compressor.description");
            details = Core.bundle.get("string.Compressor.details");
            size = 2;
            itemCapacity = 18;
            liquidCapacity = 1080;
            hasLiquids=true;
            useBlockDrawer = false;
            region = Core.atlas.find("ec-Compressor");
            multiProduct = false;

            for (int j = 0; j < All.length; j++) {
                int finalJ = j;
                for (int i = 1; i < 10; i++) {
                    int num = i;
                    products.add(new Formula() {{
                        Item item = All[finalJ];
                        consItems = new ItemStack[]{new ItemStack(ECItems.get(item).get(num - 1), 9)};
                        outputItems = new ItemStack[]{new ItemStack(ECItems.get(item).get(num), 1)};
                        craftTime = 60f;
                        drawer = new DrawMulti(new DrawRegion() {
                            @Override
                            public void load(Block block) {
                                region = Core.atlas.find("ec-Compressor");
                            }

                            @Override
                            public TextureRegion[] icons(Block block) {
                                TextureRegion[] textureRegions = new TextureRegion[2];
                                textureRegions[0] = Core.atlas.find("ec-Compressor");
                                textureRegions[1] = Core.atlas.find("ec-Compressor-icon");
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
                    }});
                }

            }

            for (int j = 0; j < ec.content.ECLiquids.All.length; j++) {
                int finalJ = j;
                for (int i = 1; i < 10; i++) {
                    int num = i;
                    products.add(new Formula() {{
                        Liquid liquid = ec.content.ECLiquids.All[finalJ];
                        consLiquids = new LiquidStack[]{new LiquidStack(ECLiquids.get(liquid).get(num - 1), 9)};
                        outputLiquids =  new LiquidStack[]{new LiquidStack(ECLiquids.get(liquid).get(num), 1)};
                        craftTime = 60f;
                        drawer = new DrawMulti(new DrawRegion() {
                            @Override
                            public void load(Block block) {
                                region = Core.atlas.find("ec-Compressor");
                            }

                            @Override
                            public TextureRegion[] icons(Block block) {
                                TextureRegion[] textureRegions = new TextureRegion[2];
                                textureRegions[0] = Core.atlas.find("ec-Compressor");
                                textureRegions[1] = Core.atlas.find("ec-Compressor-icon");
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
                    }});
                }

            }

        }};
        for (TechTree.TechNode techNode : ECItems.get(silicon).get(0).techNodes) {
            TechTree.TechNode node = node(Compressor, () -> {
            });
            node.parent = techNode;
            techNode.children.add(node);
        }
        /*
        ECBeaker MultiPress = new ECBeaker("MultiPress") {{
            requirements(Category.crafting, with(ECItems.get(silicon).get(0), 5));

            localizedName = Core.bundle.get("string.MultiPress.name0") + Core.bundle.get("string.MultiPress.name1");
            description = Core.bundle.get("string.MultiPress.description");
            details = Core.bundle.get("string.MultiPress.details");
            size = 3;
            AiItemCapacity = true;
            itemCapacity = Integer.MAX_VALUE;
            useBlockDrawer = false;
            multiProduct = false;


            for (int j = 0; j < All.length; j++) {
                int finalJ = j;
                for (int i = 1; i < 10; i++) {
                    int num = i;
                    float Base = (float) Math.pow(5, num);
                    products.add(new Formula() {{
                        Item item = All[finalJ];
                        consItems = new ItemStack[]{new ItemStack(ECItems.get(item).get(0), (int) Math.pow(9,num))};
                        outputItems = new ItemStack[]{new ItemStack(ECItems.get(item).get(num), 1)};
                        craftTime = 60f;
                        consPower = 108f * Base / 5 / 60;
                        drawer = new DrawMulti(new DrawRegion() {
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
                        }, new DrawRegion() {
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
                        }, new DrawRegion() {
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
                        }, new DrawRegion() {
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
                    }});
                }

            }

            for (int j = 0; j < All.length; j++) {
                int finalJ = j;
                for (int i = 1; i < 10; i++) {
                    int num = i;
                    products.add(new Formula() {{
                        Item item = All[finalJ];
                        consItems = new ItemStack[]{new ItemStack(ECItems.get(item).get(num), 1)};
                        outputItems = new ItemStack[]{new ItemStack(ECItems.get(item).get(num-1), 9)};
                        craftTime = 60f;
                        consPower = 108f/60;
                        drawer = new DrawMulti(new DrawRegion() {
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
                        }, new DrawRegion() {
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
                        }, new DrawRegion() {
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
                        }, new DrawRegion() {
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
                    }});
                }

            }

        }};
        for (TechTree.TechNode techNode : Compressor.techNodes) {
            TechTree.TechNode node = node(MultiPress, () -> {
            });
            node.parent = techNode;
            techNode.children.add(node);
        }

         */
    }

    public static void Fileds(MappableContent object, Class<?>[] clazzs) throws IllegalAccessException {
        Log.info(object.name);
        for (Class<?> clazz : clazzs) {
            Log.info(clazz.getName());
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                Object value = field.get(object);
                Log.info(name + ":" + value);
            }
        }
    }

    public static void Fileds(MappableContent object, Class<?> clazz) throws IllegalAccessException {
        Fileds(object, new Class[]{clazz});
    }

    public static void getConsume(Block block) throws IllegalAccessException {
        Field[] fields = Block.class.getDeclaredFields();
        for (Field field : fields) {
            //允许通过反射访问私有变量
            field.setAccessible(true);
            //获取属性名
            String name0 = field.getName();
            //判断是否为final修饰的属性
            if (!Modifier.isFinal(field.getModifiers())) {
                //获取原物品属性的属性值
                Object value = field.get(block);
                //将新物品的属性设置为和原物品相同
                if (name0.equals("consumeBuilder")) {
                    Seq<Consume> consumeBuilder = (Seq<Consume>) value;
                    Log.info(block.localizedName + ":" + consumeBuilder.size + " Consume");
                }
            }
        }
    }
}
