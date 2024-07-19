package ec.content;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.Texture;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Eachable;
import ec.AnyMtiCrafter;
import ec.Tools.Tool;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.Liquids;
import mindustry.content.Planets;
import mindustry.content.TechTree;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.type.*;
import mindustry.type.weapons.RepairBeamWeapon;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.StackConveyor;
import mindustry.world.blocks.production.Drill;
import mindustry.world.draw.*;
import mindustry.world.meta.Env;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

import static ec.content.ECItems.ECItems;
import static ec.content.ECLiquids.ECLiquids;
import static ec.content.ECUnitTypes.ECUnits;
import static mindustry.content.Items.*;
import static mindustry.content.TechTree.*;
import static mindustry.type.ItemStack.with;

public class load {
    //物品颜色
    public static Color itemColor(String name0, int num, boolean deepen) {

        Color color0;

        if (deepen) {
            color0 = Color.rgb(0, 0, 0);
        } else {
            color0 = Color.rgb(255, 255, 255);
        }

        Color color = Vars.content.item(name0).color.cpy();

        return color.cpy().lerp(color0, 0.035f * num);

    }

    public static Color itemColor(String name0, int num) {
        return itemColor(name0, num, true);
    }

    //液体颜色
    public static Color liquidColor(String name0, int num, boolean deepen) {

        Color color0;

        if (deepen) {
            color0 = Color.rgb(0, 0, 0);
        } else {
            color0 = Color.rgb(255, 255, 255);
        }

        Color color = Vars.content.liquid(name0).color.cpy();

        return color.cpy().lerp(color0, 0.035f * num);

    }

    public static Color liquidColor(String name0, int num){
        return liquidColor(name0,num,true);
    };

    //物品
    public static void item(String Name) throws IllegalAccessException {
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);

            //获取原版物品
            Item item0 = Vars.content.item(Name);
            //创建新物品
            Item item = new Item(Name + num) {{
                localizedName = Core.bundle.get("string.Compress"+num)+item0.localizedName;
                description = item0.description;
            }};
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
                    Object value0 = field.get(item0);
                    //将新物品的属性设置为和原物品相同
                    switch (name0) {
                        //矿物等级属性自定义
                        case "hardness" -> field.set(item, (int)value0+num);
                        //颜色属性自定义
                        case "color" -> field.set(item, itemColor(Name, num));
                        //爆炸性,燃烧性,放射性,放电性,血量缩放额外系数自定义
                        case "explosiveness", "flammability", "radioactivity", "charge", "healthScaling" ->
                                field.set(item, (float) value0 * attributeBase);
                        //其他没有自定义需求的属性
                        default -> field.set(item, value0);
                    }
                }


            }


            //贴图前缀
            String[] prefixs = {"", "item-"};
            //贴图后缀
            String[] sprites = {""};
            if (item0.frames>0){
                sprites = new String[item.frames];
                for (int num1 = 0;num1<item0.frames;num1++){
                    sprites[num1] = Integer.toString(num1+1);
                }
            }
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + Name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion("ec-" + Name + num + sprite, Core.atlas.find(prefix + Name + sprite));
                        return true;
                    });
                }

            }


        }


    }

    public static void item(Item item) throws IllegalAccessException {
        Seq<Item> Items = new Seq<>();
        Items.add(item);
        ECItems.put(item,Items);
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            //创建新物品
            Item newitem = new Item(item.name + num) {{
                localizedName = Core.bundle.get("string.Compress"+num)+item.localizedName;
                description = item.description;
                details = item.details;
            }};
            ECItems.get(item).add(newitem);

            for (TechNode techNode :ECItems.get(item).get(num - 1).techNodes){
                techNode.children.add(
                        nodeProduce(ECItems.get(item).get(num), () -> {})
                );
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
                    switch (name0) {
                        //矿物等级属性自定义
                        case "hardness" -> field.set(newitem, (int)value0+num);
                        //颜色属性自定义
                        case "color" -> field.set(newitem, itemColor(item.name, num));
                        //爆炸性,燃烧性,放射性,放电性,血量缩放额外系数自定义
                        case "explosiveness", "flammability", "radioactivity", "charge", "healthScaling" ->
                                field.set(newitem, (float) value0 * attributeBase);
                        //其他没有自定义需求的属性
                        default -> field.set(newitem, value0);
                    }
                }


            }


            //贴图前缀
            String[] prefixs = {"", "item-"};
            //贴图后缀
            String[] sprites = {""};
            if (item.frames>0){
                sprites = new String[item.frames];
                for (int num1 = 0;num1<item.frames;num1++){
                    sprites[num1] = Integer.toString(num1+1);
                }
            }
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + item.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion("ec-" + item.name + num + sprite, Core.atlas.find(prefix + item.name + sprite));
                        return true;
                    });
                }

            }


        }


    }

    //液体
    public static void liquid(String Name) throws IllegalAccessException {
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            //获取原版液体
            Liquid liquid0 = Vars.content.liquid(Name);
            //创建新物品
            Liquid liquid = new Liquid(Name + num) {{
                localizedName = Core.bundle.get("string.Compress"+num)+liquid0.localizedName;
                description = liquid0.description;
            }};
            //获取物品的全部属性
            Field[] field0 = Liquid.class.getDeclaredFields();
            //遍历物品的全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(liquid0);
                    //将新物品的属性设置为和原物品相同
                    switch (name0) {
                        case "color" -> field.set(liquid, load.liquidColor(Name,num));
                        case "flammability","explosiveness","heatCapacity","boilPoint" -> field.set(liquid, (float)value0*attributeBase);
                        case "temperature" -> {
                            if ((float)value0>=0.5f){
                                field.set(liquid, (float)value0*attributeBase);
                            }
                            else{
                                field.set(liquid, 0.5f-(0.5f-(float)value0)*attributeBase);
                            }
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(liquid, value0);
                    }
                }


            }


            //贴图前缀
            String[] prefixs = {"", "liquid-"};
            //贴图后缀
            String[] sprites = {""};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + Name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion("ec-" + Name + num + sprite, Core.atlas.find(prefix + Name + sprite));
                        return true;
                    });
                }

            }


        }


    }

    public static void liquid(Liquid liquid) throws IllegalAccessException {

        Seq<Liquid> Liquids = new Seq<>();
        Liquids.add(liquid);
        ECLiquids.put(liquid,Liquids);
        for (int i = 1; i < 10; i++) {
            int num = i;
            float attributeBase = (float) Math.pow(5, num);
            //创建新物品
            Liquid newliquid = new Liquid(liquid.name + num) {{
                localizedName = Core.bundle.get("string.Compress"+num)+liquid.localizedName;
                description = liquid.description;
                details = liquid.details;
            }};
            ECLiquids.get(liquid).add(newliquid);
            for (TechNode techNode :ECLiquids.get(liquid).get(num - 1).techNodes){
                techNode.children.add(
                        nodeProduce(ECLiquids.get(liquid).get(num), () -> {})
                );
            }

            //获取物品的全部属性
            Field[] field0 = Liquid.class.getDeclaredFields();
            //遍历物品的全部属性
            for (Field field : field0) {
                //允许通过反射访问私有变量
                field.setAccessible(true);
                //获取属性名
                String name0 = field.getName();
                //判断是否为final修饰的属性
                if (!Modifier.isFinal(field.getModifiers())) {
                    //获取原物品属性的属性值
                    Object value0 = field.get(liquid);
                    //将新物品的属性设置为和原物品相同
                    switch (name0) {
                        case "color" -> field.set(newliquid, load.liquidColor(liquid.name,num));
                        case "flammability","explosiveness","heatCapacity","boilPoint" -> field.set(newliquid, (float)value0*attributeBase);
                        case "temperature" -> {
                            if ((float)value0>=0.5f){
                                field.set(newliquid, (float)value0*attributeBase);
                            }
                            else{
                                field.set(newliquid, 0.5f-(0.5f-(float)value0)*attributeBase);
                            }
                        }
                        //其他没有自定义需求的属性
                        default -> field.set(newliquid, value0);
                    }
                }


            }


            //贴图前缀
            String[] prefixs = {"", "liquid-"};
            //贴图后缀
            String[] sprites = {""};
            //遍历贴图后缀
            for (String sprite : sprites) {
                for (String prefix : prefixs) {
                    //延时运行,来自@(I hope...)
                    Tool.forceRun(() -> {
                        //判断原版是否有该后缀贴图
                        if (!Core.atlas.has(prefix + liquid.name + sprite)) return false;
                        //以原版贴图覆盖新物品贴图
                        Core.atlas.addRegion(newliquid.name + sprite, Core.atlas.find(prefix + liquid.name + sprite));
                        return true;
                    });
                }

            }


        }


    }


    //物品压缩器
    public static void itemCompressor(String name0) {
        new AnyMtiCrafter(name0 + "Compressor") {{
            useBlockDrawer = true;
            requirements(Category.crafting, with(Vars.content.item("copper"), 30));
            size = 2;
            hasLiquids = false;
            itemCapacity = 18;
            craftEffect = Fx.pulverizeMedium;
            alwaysUnlocked = true;

            //fullIcon.set(new Texture("Compressor.png"));

            products.add(new Formula() {{
                consumeItem(Vars.content.item(name0), 9);
                outputItems = ItemStack.with(Vars.content.item("ec-" + name0 + 1), 1);
                craftTime = 60f;
                craftEffect = Fx.pulverizeMedium;
            }});
            for (int i = 2; i < 10; i++) {
                int num = i;
                int num0 = i - 1;
                products.add(new Formula() {{
                    consumeItem(Vars.content.item("ec-" + name0 + num0), 9);
                    outputItems = ItemStack.with(Vars.content.item("ec-" + name0 + num), 1);
                    craftTime = 60f;
                    craftEffect = Fx.pulverizeMedium;
                }});
            }

        }};
        //延时运行,来自@(I hope...)
        Tool.forceRun(() -> {
            //判断原版是否有该后缀贴图
            if (!Core.atlas.has("Compressor")) return false;
            //以原版贴图覆盖新物品贴图
            Core.atlas.addRegion("ec-" + name0 +"Compressor" , Core.atlas.find("Compressor"));
            return true;
        });
    }

    public static void itemCompressor(Item item) {
        AnyMtiCrafter anyMtiCrafter = new AnyMtiCrafter(item.name + "Compressor") {{
            localizedName = item.localizedName+Core.bundle.get("string.Compressor.name");
            description = Core.bundle.get("string.Compressor.description");
            details = Core.bundle.get("string.Compressor.details");
            useBlockDrawer = true;
            drawer = new DrawMulti(
                    new DrawRegion(){
                        @Override
                        public void load(Block block) {
                            region = Core.atlas.find("ec-Compressor");
                        }
                        @Override
                        public TextureRegion[] icons(Block block) {
                            return new TextureRegion[]{Core.atlas.find("ec-Compressor"),Core.atlas.find("ec-Compressor-icon")};
                        }
                    },
                    new DrawRegion(){
                        @Override
                        public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                            if(!drawPlan) return;
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
                            region = Core.atlas.find("ec-Compressor"+"-top0");
                        }
                        @Override
                        public TextureRegion[] icons(Block block) {
                            return new TextureRegion[]{};
                        }
                    },
                    new DrawRegion(){
                        @Override
                        public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                            if(!drawPlan) return;
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
                            region = Core.atlas.find("ec-Compressor"+"-top1");
                        }
                        @Override
                        public TextureRegion[] icons(Block block) {
                            return new TextureRegion[]{};
                        }
                    },
                    new DrawRegion(){
                        @Override
                        public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                            if(!drawPlan) return;
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
                            region = Core.atlas.find("ec-Compressor"+"-top2");
                        }
                        @Override
                        public TextureRegion[] icons(Block block) {
                            return new TextureRegion[]{};
                        }
                    }
                    );
            requirements(Category.crafting, with(copper, 30));
            size = 2;
            hasLiquids = false;
            itemCapacity = 18;
            craftEffect = Fx.pulverizeMedium;
            alwaysUnlocked = true;
            for (int i = 1; i < 10; i++) {
                int num = i;
                products.add(new Formula() {{
                    consumeItem(ECItems.get(item).get(num-1), 9);
                    outputItems = ItemStack.with(ECItems.get(item).get(num), 1);
                    craftTime = 60f;
                    craftEffect = Fx.pulverizeMedium;
                }});
            }

        }};
        for (TechNode techNode :item.techNodes){
            techNode.children.add(
                    node(anyMtiCrafter, () -> {})
            );
        }




    }

    //液体压缩器
    public static void liquidCompressor(String name0) {
        new AnyMtiCrafter(name0 + "Compressor") {{
            requirements(Category.crafting, with(Vars.content.item("ec-" + "copper" + 1), 60));
            size = 2;
            liquidCapacity = 18 * 60;
            alwaysUnlocked = true;
            products.add(new Formula() {{
                consumeLiquid(Vars.content.liquid(name0), 9f);
                outputLiquids = LiquidStack.with(Vars.content.liquid("ec-" + name0 + 1), 1f);
                craftTime = 60f;
                craftEffect = Fx.pulverizeMedium;
            }});
            for (int i = 2; i < 10; i++) {
                int num = i;
                int num0 = i - 1;
                products.add(new Formula() {{
                    consumeLiquid(Vars.content.liquid("ec-" + name0 + num0), 9f);
                    outputLiquids = LiquidStack.with(Vars.content.liquid("ec-" + name0 + num), 1f);
                    craftTime = 60f;
                    craftEffect = Fx.pulverizeMedium;
                }});
            }
            products.add(new Formula() {{
                consumeLiquid(Vars.content.liquid("ec-" + name0 + 1), 1f);
                outputLiquids = LiquidStack.with(Vars.content.liquid(name0), 9f);
                craftTime = 60f;
                craftEffect = Fx.pulverizeMedium;
            }});
            for (int i = 2; i < 10; i++) {
                int num = i;
                int num0 = i - 1;
                products.add(new Formula() {{
                    consumeLiquid(Vars.content.liquid("ec-" + name0 + num), 1f);
                    outputLiquids = LiquidStack.with(Vars.content.liquid("ec-" + name0 + num0), 9f);
                    craftTime = 60f;
                    craftEffect = Fx.pulverizeMedium;
                }});
            }

        }};

    }

    public static void liquidCompressor(Liquid liquid) {
        AnyMtiCrafter anyMtiCrafter = new AnyMtiCrafter(liquid.name + "Compressor") {{

            localizedName = liquid.localizedName+Core.bundle.get("string.Compressor.name");
            description = Core.bundle.get("string.Compressor.description");
            details = Core.bundle.get("string.Compressor.details");
            requirements(Category.crafting, with(ECItems.get(copper).get(1), 60));
            size = 2;
            liquidCapacity = 18 * 60;
            alwaysUnlocked = false;


            drawer = new DrawMulti(
                    new DrawRegion(){
                        @Override
                        public void load(Block block) {
                            region = Core.atlas.find("ec-Compressor");
                        }
                        @Override
                        public TextureRegion[] icons(Block block) {
                            return new TextureRegion[]{Core.atlas.find("ec-Compressor"),Core.atlas.find("ec-Compressor-icon")};
                        }
                    },
                    new DrawRegion(){
                        @Override
                        public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                            if(!drawPlan) return;
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
                            region = Core.atlas.find("ec-Compressor"+"-top0");
                        }
                        @Override
                        public TextureRegion[] icons(Block block) {
                            return new TextureRegion[]{};
                        }
                    },
                    new DrawRegion(){
                        @Override
                        public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                            if(!drawPlan) return;
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
                            region = Core.atlas.find("ec-Compressor"+"-top1");
                        }
                        @Override
                        public TextureRegion[] icons(Block block) {
                            return new TextureRegion[]{};
                        }
                    },
                    new DrawRegion(){
                        @Override
                        public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                            if(!drawPlan) return;
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
                            region = Core.atlas.find("ec-Compressor"+"-top2");
                        }
                        @Override
                        public TextureRegion[] icons(Block block) {
                            return new TextureRegion[]{};
                        }
                    }
            );

            for (int i = 1; i < 10; i++) {
                int num = i;
                products.add(new Formula() {{
                    consumeLiquid(ECLiquids.get(liquid).get(num-1), 9f);
                    outputLiquids = LiquidStack.with(ECLiquids.get(liquid).get(num), 1f);
                    craftTime = 60f;
                    craftEffect = Fx.pulverizeMedium;
                }});
            }
            for (int i = 1; i < 10; i++) {
                int num = i;
                products.add(new Formula() {{
                    consumeLiquid(ECLiquids.get(liquid).get(num), 1f);
                    outputLiquids = LiquidStack.with(ECLiquids.get(liquid).get(num-1), 9f);
                    craftTime = 60f;
                    craftEffect = Fx.pulverizeMedium;
                }});
            }

        }};
        for (TechNode techNode :liquid.techNodes){
            techNode.children.add(
                    node(anyMtiCrafter, () -> {})
            );
        }

    }

    //传送带
    public static void conveyor(String name, String material, float v, int num) {
        int health0 = 45;
        int conveyorBase = 4;
        new Conveyor(name + "conveyor" + num) {{
            requirements(Category.distribution, with(Vars.content.item("ec-" + material + num), 1));
            researchCost = with(Vars.content.item("ec-" + material + num), 5);
            health = (int) (health0 * Math.pow(conveyorBase, num));
            speed = (float) (v * Math.pow(conveyorBase, num) / 140);
            displayedSpeed = (float) (v * Math.pow(conveyorBase, num));
            itemCapacity = (int) (10 * Math.pow(conveyorBase, num));
            buildCostMultiplier = 2f;
        }};

    }

    public static void titaniumConveyor(int num) {
        int health0 = 65;
        int conveyorBase = 4;
        new StackConveyor("titanium-conveyor" + num) {{
            requirements(Category.distribution, with(Vars.content.item("ec-" + "copper" + num), 1, Vars.content.item("ec-" + "lead" + num), 1, Vars.content.item("ec-" + "titanium" + num), 1));
            health = (int) (health0 * Math.pow(conveyorBase, num));
            speed = 4.8f / 60f;
            itemCapacity = (int) ((11 * Math.pow(conveyorBase, num)) / 4.8f);
        }};


    }

    //钻头
    public static void drill(String name, String material, int drilltime, int num) {

        int drillBase = 3;
        new Drill(name + num) {{
            hardnessDrillMultiplier = (float) (50f / Math.pow(drillBase, num));
            size = 2;
            researchCost = with(Vars.content.item("ec-" + material + num), 10);
            requirements(Category.production, with(Vars.content.item("ec-" + material + num), 12));
            itemCapacity = (int) (10 * Math.pow(drillBase, num));
            drillTime = (float) (drilltime / Math.pow(drillBase, num));
            tier = 2 + num;
            envEnabled ^= Env.space;

            consumeLiquid(Liquids.water, 0.05f).boost();
        }};

    }

    //城墙
    public static void wall(String name, String material, int health0, int num) {
        int healthBase = 5;
        new Wall(name + "Wall" + num) {{
            requirements(Category.defense, with(Vars.content.item("ec-" + material + num), 6));
            health = (int) (health0 * 4 * Math.pow(healthBase, num));
            researchCostMultiplier = 0.1f;
            envDisabled |= Env.scorching;
        }};

    }

    //物品多重压缩器
    public static void itemMultiPress(String name0) {

        new AnyMtiCrafter(name0 + "MultiPress") {{
            requirements(Category.crafting, with(Vars.content.item("ec-" + "copper" + 1), 100));
            size = 3;
            useBlockDrawer = false;
            maxList = 5;
            itemCapacity = ((int) Math.pow(9, 9));
            hasItems = true;
            hasLiquids = true;
            hasPower = true;

            for (int i = 1; i < 10; i++) {
                int num = i;
                products.add(new Formula() {{
                    consumeItem(Vars.content.item(name0), (int) Math.pow(9, num));
                    outputItems = ItemStack.with(Vars.content.item("ec-" + name0 + num), 1);
                    int timeBase = num * num;
                    craftTime = 60f * timeBase;
                    craftEffect = Fx.pulverizeMedium;
                    consumePower(108f / 60);
                }});
            }

            for (int i = 1; i < 10; i++) {
                int num = i;
                products.add(new Formula() {{
                    consumeItem(Vars.content.item("ec-" + name0 + num), 1);
                    outputItems = ItemStack.with(Vars.content.item(name0), (int) Math.pow(9, num));
                    int timeBase = num * num;
                    craftTime = 60f * timeBase;
                    craftEffect = Fx.pulverizeMedium;
                    consumePower(108f / 60);
                }});
            }


        }};


    }

    public static void itemMultiPress(Item item) {

        AnyMtiCrafter anyMtiCrafter = new AnyMtiCrafter(item.name + "MultiPress") {{

            localizedName = Core.bundle.get("string.MultiPress.name0")+item.localizedName+Core.bundle.get("string.MultiPress.name1");
            description = Core.bundle.get("string.MultiPress.description");
            details = Core.bundle.get("string.MultiPress.details");
            requirements(Category.crafting, with(ECItems.get(copper).get(1), 100));

            useBlockDrawer = true;
            drawer = new DrawMulti(
                    new DrawRegion(){
                        @Override
                        public void load(Block block) {
                            region = Core.atlas.find("ec-MultiPress");
                        }
                        @Override
                        public TextureRegion[] icons(Block block) {
                            return new TextureRegion[]{Core.atlas.find("ec-MultiPress"),Core.atlas.find("ec-MultiPress-icon")};
                        }
                    },
                    new DrawRegion(){
                        @Override
                        public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                            if(!drawPlan) return;
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
                            region = Core.atlas.find("ec-MultiPress"+"-top0");
                        }
                        @Override
                        public TextureRegion[] icons(Block block) {
                            return new TextureRegion[]{};
                        }
                    },
                    new DrawRegion(){
                        @Override
                        public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                            if(!drawPlan) return;
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
                            region = Core.atlas.find("ec-MultiPress"+"-top1");
                        }
                        @Override
                        public TextureRegion[] icons(Block block) {
                            return new TextureRegion[]{};
                        }
                    },
                    new DrawRegion(){
                        @Override
                        public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
                            if(!drawPlan) return;
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
                            region = Core.atlas.find("ec-MultiPress"+"-top2");
                        }
                        @Override
                        public TextureRegion[] icons(Block block) {
                            return new TextureRegion[]{};
                        }
                    }
            );

            size = 3;
            maxList = 5;
            itemCapacity = ((int) Math.pow(9, 9));
            hasItems = true;
            hasLiquids = true;
            hasPower = true;

            for (int i = 1; i < 10; i++) {
                int num = i;
                products.add(new Formula() {{
                    consumeItem(item, (int) Math.pow(9, num));
                    outputItems = ItemStack.with(ECItems.get(item).get(num), 1);
                    int timeBase = num * num;
                    craftTime = 60f * timeBase;
                    craftEffect = Fx.pulverizeMedium;
                    consumePower(108f / 60);
                }});
            }
            for (int i = 1; i < 10; i++) {
                int num = i;
                products.add(new Formula() {{
                    consumeItem(ECItems.get(item).get(num), 1);
                    outputItems = ItemStack.with(item, (int) Math.pow(9, num));
                    int timeBase = num * num;
                    craftTime = 60f * timeBase;
                    craftEffect = Fx.pulverizeMedium;
                    consumePower(108f / 60);
                }});
            }


        }};
        for (TechNode techNode :Vars.content.block("ec-"+item.name+"Compressor").techNodes){
            techNode.children.add(
                    node(anyMtiCrafter, () -> {})
            );
        }


    }

    //液体多重压缩器   废弃
    public static void liquidmultipress(String liquid0) {

        new AnyMtiCrafter(liquid0 + "MultiPress") {{
            requirements(Category.crafting, with(Vars.content.item("ec-" + "copper" + 1), 100));
            size = 3;
            useBlockDrawer = false;
            maxList = 5;
            liquidCapacity = ((int) Math.pow(9, 9));
            hasLiquids = true;
            hasPower = true;

            for (int i = 1; i < 10; i++) {
                int num = i;
                products.add(new Formula() {{
                    consumeLiquid(Vars.content.liquid(liquid0), (float) (12 * Math.pow(9, num) / 60));
                    outputLiquids = LiquidStack.with(Vars.content.liquid("ec-" + liquid0 + num), 12f / 60);
                    int timeBase = num * num;
                    craftTime = 10f * timeBase;
                    craftEffect = Fx.pulverizeMedium;
                    consumePower(108f / 60);
                }});
            }
            for (int i = 1; i < 10; i++) {
                int num = i;
                products.add(new Formula() {{
                    consumeLiquid(Vars.content.liquid("ec-" + liquid0 + num), 12f / 60);
                    outputLiquids = LiquidStack.with(Vars.content.liquid(liquid0), (float) (12 * Math.pow(9, num) / 60));
                    int timeBase = num * num;
                    craftTime = 10f * timeBase;
                    craftEffect = Fx.pulverizeMedium;
                    consumePower(108f / 60);
                }});
            }

        }};


    }


    //单位
    public static void unit(String Name) throws IllegalAccessException {
        for (int i = 1; i < 10; i++) {
            int num = i;
            float healthBase = (float) Math.pow(5, num);
            float damageBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //获取原单位
            UnitType unit0 = Vars.content.unit(Name);
            //创建新单位
            UnitType unit = new UnitType(Name + num) {{
                localizedName = Core.bundle.get("string.Compress"+num)+unit0.localizedName;
                description = unit0.description;
                constructor = Vars.content.unit(Name).constructor;
            }};
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
                    Object value0 = field.get(unit0);
                    //判断是否为血量
                    switch (name0) {
                        case "health" ->
                            //对血量进行增强
                                field.set(unit, (float) value0 * healthBase);

                        //判断是否为物品容量
                        case "itemCapacity" ->
                            //对物品容量进行增强
                                field.set(unit, (int) ((int) value0 * healthBase));

                        //判断是否为护甲
                        case "armor" ->
                            //对护甲进行增强
                                field.set(unit, (float) value0 * healthBase);

                        //判断是否为挖掘等级
                        case "mineTier" -> {
                            //对挖掘等级进行增强
                            if ((int) value0 > 0) {
                                field.set(unit, (int) value0 + num);
                            }
                        }
                        //判断是否为挖掘速度
                        case "mineSpeed" -> {
                            //对挖掘等级进行增强
                            if ((float) value0 > 0) {
                                field.set(unit, (float) value0 * damageBase);
                            }
                        }
                        //判断是否为武器
                        case "weapons" -> {
                            //遍历武器
                            for (int j = 0; j < unit0.weapons.size; j++) {
                                //从原版复制武器
                                Weapon weapon = unit0.weapons.get(j).copy();
                                //从原版武器复制武器子弹(此处涉及浅拷贝知识)
                                weapon.bullet = unit0.weapons.get(j).bullet.copy();
                                bullet(weapon.bullet,num);

                                //判断武器类型
                                if (weapon instanceof RepairBeamWeapon){
                                    ((RepairBeamWeapon)weapon).repairSpeed *= damageBase;
                                }

                                //判断子弹类型
                                if (weapon.bullet instanceof BasicBulletType) {
                                    //对子弹大小进行增幅
                                    ((BasicBulletType) (weapon.bullet)).width *= sizeBase;
                                    ((BasicBulletType) (weapon.bullet)).height *= sizeBase;
                                }else if (weapon.bullet instanceof LaserBulletType){
                                    //对子弹大小进行增幅
                                    ((LaserBulletType) (weapon.bullet)).width *= sizeBase;
                                    ((LaserBulletType) (weapon.bullet)).length *= sizeBase;
                                }

                                //把增强后的武器添加到新单位中
                                unit.weapons.addAll(weapon);
                            }
                        }
                        //判断是否为能力
                        case "abilities" -> {
                            //遍历能力
                            for (int j = 0; j < unit0.abilities.size; j++) {
                                Ability ability0 = unit0.abilities.get(j);

                                Ability ability = ability0.copy();
                                if (ability0 instanceof RepairFieldAbility) {
                                    ((RepairFieldAbility)ability).amount *= damageBase;
                                    ((RepairFieldAbility)ability).range *= sizeBase;
                                } else if (ability0 instanceof SuppressionFieldAbility) {
                                    ((SuppressionFieldAbility)ability).orbRadius *= sizeBase;
                                } else if (ability0 instanceof ShieldArcAbility) {
                                    ((ShieldArcAbility)ability).regen *= damageBase;
                                    ((ShieldArcAbility)ability).max *= damageBase;

                                } else if (ability0 instanceof UnitSpawnAbility){
                                    ((UnitSpawnAbility)ability).unit = Vars.content.unit("ec-"+ ((UnitSpawnAbility)ability0).unit.name +num);
                                } else if (ability0 instanceof ForceFieldAbility){
                                    ((ForceFieldAbility)ability).max *= damageBase;
                                    ((ForceFieldAbility)ability).regen *= damageBase;

                                } else if (ability0 instanceof ShieldRegenFieldAbility) {
                                    ((ShieldRegenFieldAbility)ability).max *= damageBase;
                                    ((ShieldRegenFieldAbility)ability).amount *= damageBase;

                                }

                                unit.abilities.add(ability);
                            }

                        }


                        //其他没有自定义需求的属性
                        default ->
                            //将新单位的属性设置为和原版单位相同
                                field.set(unit, value0);
                    }
                }


            }


            //贴图后缀
            String[] sprites = {"", "-base", "-cell", "-leg"};
            //遍历贴图后缀
            for (String sprite : sprites) {
                //延时运行,来自I hope... 大佬
                Tool.forceRun(() -> {
                    //判断原版是否有该后缀贴图
                    if (!Core.atlas.has(Name + sprite)) return false;
                    //以原版贴图覆盖新单位贴图
                    Core.atlas.addRegion("ec-" + Name + num + sprite, Core.atlas.find(Name + sprite));
                    return true;
                });
            }


        }


    }

    public static void unit(UnitType unit) throws IllegalAccessException {
        Seq<UnitType> Units = new Seq<>();
        Units.add(unit);
        ECUnits.put(unit,Units);

        for (int i = 1; i < 10; i++) {
            int num = i;
            float healthBase = (float) Math.pow(5, num);
            float damageBase = (float) Math.pow(5, num);
            float sizeBase = (float) Math.pow(1.4, num);
            //创建新单位
            UnitType newunit = new UnitType(unit.name + num) {{
                localizedName = Core.bundle.get("string.Compress"+num)+unit.localizedName;
                description = unit.description;
                constructor = unit.constructor;
                researchRequirements();
            }};

            ECUnits.get(unit).add(newunit);

            for (TechNode techNode :ECUnits.get(unit).get(num - 1).techNodes){
                techNode.children.add(
                        nodeProduce(ECUnits.get(unit).get(num), () -> {})
                );
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
                    switch (name0) {
                        case "health" ->
                            //对血量进行增强
                                field.set(newunit, (float) value0 * healthBase);

                        //判断是否为物品容量
                        case "itemCapacity" ->
                            //对物品容量进行增强
                                field.set(newunit, (int) ((int) value0 * healthBase));

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
                                //从原版武器复制武器子弹(此处涉及浅拷贝知识)
                                weapon.bullet = unit.weapons.get(j).bullet.copy();
                                bullet(weapon.bullet,num);

                                //判断武器类型
                                if (weapon instanceof RepairBeamWeapon){
                                    ((RepairBeamWeapon)weapon).repairSpeed *= damageBase;
                                }

                                //判断子弹类型
                                if (weapon.bullet instanceof BasicBulletType) {
                                    //对子弹大小进行增幅
                                    ((BasicBulletType) (weapon.bullet)).width *= sizeBase;
                                    ((BasicBulletType) (weapon.bullet)).height *= sizeBase;
                                }else if (weapon.bullet instanceof LaserBulletType){
                                    //对子弹大小进行增幅
                                    ((LaserBulletType) (weapon.bullet)).width *= sizeBase;
                                    ((LaserBulletType) (weapon.bullet)).length *= sizeBase;
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
                                    ((RepairFieldAbility)ability).amount *= damageBase;
                                    ((RepairFieldAbility)ability).range *= sizeBase;
                                } else if (ability0 instanceof SuppressionFieldAbility) {
                                    ((SuppressionFieldAbility)ability).orbRadius *= sizeBase;
                                } else if (ability0 instanceof ShieldArcAbility) {
                                    ((ShieldArcAbility)ability).regen *= damageBase;
                                    ((ShieldArcAbility)ability).max *= damageBase;

                                } else if (ability0 instanceof UnitSpawnAbility){
                                    ((UnitSpawnAbility)ability).unit = Vars.content.unit("ec-"+ ((UnitSpawnAbility)ability0).unit.name +num);
                                } else if (ability0 instanceof ForceFieldAbility){
                                    ((ForceFieldAbility)ability).max *= damageBase;
                                    ((ForceFieldAbility)ability).regen *= damageBase;

                                } else if (ability0 instanceof ShieldRegenFieldAbility) {
                                    ((ShieldRegenFieldAbility)ability).max *= damageBase;
                                    ((ShieldRegenFieldAbility)ability).amount *= damageBase;

                                } else if (ability0 instanceof StatusFieldAbility) {
                                    ((StatusFieldAbility)ability).range *= sizeBase;
                                } else if (ability0 instanceof EnergyFieldAbility) {
                                    ((EnergyFieldAbility)ability).damage *= damageBase;
                                    ((EnergyFieldAbility)ability).range *= sizeBase;
                                    ((EnergyFieldAbility)ability).healPercent *= sizeBase;
                                    ((EnergyFieldAbility)ability).maxTargets *= (int) sizeBase;
                                    ((EnergyFieldAbility)ability).sameTypeHealMult = 1-((1-((EnergyFieldAbility)ability).sameTypeHealMult)/sizeBase);


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
            String[] sprites = {"", "-base", "-cell", "-leg"};
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



    //子弹强化
    public static BulletType bullet(BulletType bullet,float damageBase,float sizeBase){
        bullet.damage*=damageBase;
        bullet.splashDamage*=damageBase;
        bullet.splashDamageRadius*=sizeBase;
        bullet.lightning = (int) (sizeBase*bullet.lightning);
        bullet.lightningDamage*=damageBase;
        bullet.lightningLength = (int) (sizeBase*bullet.lightningLength);
        bullet.healAmount*=damageBase;
        bullet.healPercent*=sizeBase;
        bullet.intervalBullets = (int) (sizeBase*bullet.intervalBullets);
        bullet.fragBullets = (int) (sizeBase*bullet.fragBullets);
        bullet.statusDuration *= sizeBase;
        if (bullet.fragBullet != null) bullet.fragBullet = bullet(bullet.fragBullet.copy(),damageBase,sizeBase);
        if (bullet.intervalBullet != null) bullet.intervalBullet = bullet(bullet.intervalBullet.copy(),damageBase,sizeBase);
        return bullet;
    }

    public static void bullet(BulletType bullet,int num){
        float damageBase = (float) Math.pow(5,num);
        float sizeBase = (float) Math.pow(1.4,num);
        bullet(bullet,damageBase,sizeBase);
    }




}
