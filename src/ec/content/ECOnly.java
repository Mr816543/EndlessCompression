package ec.content;

import arc.Core;
import arc.graphics.Pixmap;
import arc.graphics.Texture;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.PixmapRegion;
import arc.graphics.g2d.TextureAtlas;
import arc.graphics.g2d.TextureRegion;
import arc.util.Eachable;
import ec.Tools.AnyMtiCrafter;
import ec.Tools.Tool;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.TechTree;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.consumers.ConsumeItemCharged;
import mindustry.world.consumers.ConsumeItemExplode;
import mindustry.world.draw.*;

import static ec.Tools.AnyMtiCrafter.name;
import static ec.content.ECBlocks.ECBlocks;
import static ec.content.ECItems.All;
import static ec.content.ECItems.ECItems;
import static mindustry.content.Items.*;
import static mindustry.content.Items.silicon;
import static mindustry.content.TechTree.node;
import static mindustry.content.TechTree.nodeProduce;
import static mindustry.type.ItemStack.with;

public class ECOnly {
    public static void load() throws IllegalAccessException {

        Item power = new Item("power", load.itemColor(surgeAlloy, 0, true)){{charge = 1f;minfo.mod = null;}};
        Item[] newAll = new Item[All.length+1];
        System.arraycopy(All, 0, newAll, 0, All.length);
        newAll[All.length] = power;
        All = newAll;
        for (TechTree.TechNode techNode : copper.techNodes){
            TechTree.TechNode node = nodeProduce(power,() -> {});
            node.parent = techNode;
            techNode.children.add(node);
        }
        load.item(power,"power",10);

        AnyMtiCrafter powerCompressor = new AnyMtiCrafter("power" + "Compressor") {{
            //本地化显示修改
            localizedName = power.localizedName + Core.bundle.get("string.Compressor.name");
            description = Core.bundle.get("string.Compressor.description");
            details = Core.bundle.get("string.Compressor.details");

            //贴图方面
            useBlockDrawer = false;
            drawer = new DrawMulti(
                    new DrawRegion() {
                        @Override
                        public void load(Block block) {
                            region = Core.atlas.find("ec-Compressor");
                        }

                        @Override
                        public TextureRegion[] icons(Block block) {
                            TextureRegion[] textureRegions = new TextureRegion[3];
                            textureRegions[0] = Core.atlas.find("ec-Compressor");
                            textureRegions[1] = Core.atlas.find("ec-Compressor-icon");
                            if (Core.atlas.find("item-" + "power") != null) {
                                textureRegions[2] = Core.atlas.find("item-" + power.name);
                            } else if (Core.atlas.find("power") != null) {
                                textureRegions[2] = Core.atlas.find(power.name);
                            } else textureRegions[2] = textureRegions[1];
                            return textureRegions;
                        }
                    },
                    new DrawRegion() {
                        @Override
                        public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                            if (!drawPlan) return;
                            Draw.color(ECItems.get(power).get(1).color);
                            Draw.rect(Core.atlas.find("ec-Compressor-top0"), plan.drawx(), plan.drawy(), (buildingRotate ? plan.rotation * 90f : 0));
                        }

                        @Override
                        public void draw(Building build) {
                            Draw.color(ECItems.get(power).get(1).color);
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
                    },
                    new DrawRegion() {
                        @Override
                        public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                            if (!drawPlan) return;
                            Draw.color(ECItems.get(power).get(5).color);
                            Draw.rect(Core.atlas.find("ec-Compressor-top1"), plan.drawx(), plan.drawy(), (buildingRotate ? plan.rotation * 90f : 0));
                        }

                        @Override
                        public void draw(Building build) {
                            Draw.color(ECItems.get(power).get(5).color);
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
                    },
                    new DrawRegion() {
                        @Override
                        public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                            if (!drawPlan) return;
                            Draw.color(ECItems.get(power).get(9).color);
                            Draw.rect(Core.atlas.find("ec-Compressor-top2"), plan.drawx(), plan.drawy(), (buildingRotate ? plan.rotation * 90f : 0));
                        }

                        @Override
                        public void draw(Building build) {
                            Draw.color(ECItems.get(power).get(9).color);
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
                    }
            );
            //其他属性
            requirements(Category.crafting, with(silicon, 5));
            size = 2;
            hasLiquids = false;
            itemCapacity = 18;
            //根据物品检索表批量添加压缩配方
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
        ConsumeGenerator powerProducer = new ConsumeGenerator("powerProducer") {{
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
        //加入原版物品的科技节点
        for (TechTree.TechNode techNode : power.techNodes) {
            TechTree.TechNode node = node(powerCompressor, () -> {});
            TechTree.TechNode node1 = node(powerProducer, () -> {});
            node1.parent = node;
            node.parent = techNode;
            node.children.add(node1);
            techNode.children.add(node);
        }
    }
}
