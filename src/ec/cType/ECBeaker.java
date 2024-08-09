package ec.cType;

import arc.Core;
import arc.func.Func;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.ImageButton;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.layout.Table;
import arc.struct.EnumSet;
import arc.struct.ObjectMap;
import arc.struct.OrderedMap;
import arc.struct.Seq;
import arc.util.Align;
import arc.util.Eachable;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import ec.Tools.Get;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.core.World;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.Effect;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.logic.LAccess;
import mindustry.logic.Ranged;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.type.LiquidStack;
import mindustry.ui.Bar;
import mindustry.ui.Fonts;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.blocks.ControlBlock;
import mindustry.world.consumers.*;
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawDefault;
import mindustry.world.meta.*;

import java.util.Random;

import static ec.Tools.Get.statToTable;
import static ec.Tools.Get.statTurnTable;
import static mindustry.Vars.content;
import static mindustry.Vars.tilesize;

/**
 * @author guiY
 */
public class ECBeaker extends Block {

    public static String ModName = "ec";
    public boolean multiProduct = false;
    public boolean acceptNoConsistent = false;
    /**
     * 根据配方智能选择容量 @816543
     */
    public boolean AiItemCapacity = false;
    public boolean AiLiquidCapacity = false;
    /**
     * 配方表 {@link Formula}
     */
    public Seq<Formula> products = new Seq<>();
    /**
     * 方块的drawer，如果{@link ECBeaker#useBlockDrawer}为false，则使用配方内的drawer
     */
    public DrawBlock drawer = new DrawDefault();
    /**
     * 是否使用方块自己的内的{@link ECBeaker#drawer}
     */
    public boolean useBlockDrawer = true;
    /**
     * 是否拥有多个液体输出需要不同方向，请参考{@link Formula#liquidOutputDirections}来决定这个参数的值
     */
    public boolean hasDoubleOutput = false;
    /**
     * 是否自动为液体添加bar
     */
    public boolean autoAddBar = true;
    /**
     * 是否使用液体的悬浮显示
     */
    public boolean useLiquidTable = true;
    /**
     * 液体悬浮显示的背景颜色
     */
    public Color liquidTableBack = Pal.gray.cpy().a(0.5f);
    /**
     * 最多一次显示多少配方
     */
    public int maxList = 4;

    public ECBeaker(String name) {
        super(name);

        update = true;
        solid = true;
        hasItems = true;
        ambientSound = Sounds.machine;
        sync = true;
        ambientSoundVolume = 0.03f;
        flags = EnumSet.of(BlockFlag.factory);
        drawArrow = false;

        configurable = true;
        saveConfig = true;

        config(int[].class, (ECBeakerBuild ent, int[] i) -> {
            if (i.length != 2) return;

            ent.rotation = i[0];

            if (products.size <= 0 || i[1] == -1) ent.formula = null;
            else ent.formula = products.get(i[1]);
        });
    }

    public static String name(String add) {
        return ModName + "-" + add;
    }

    @Override
    public void init() {
        if (products.size > 0) {
            for (var product : products) {
                product.owner = this;
                product.init();
                if (product.outputLiquids != null) {
                    hasLiquids = true;
                }
                if (product.outputItems != null) {
                    hasItems = true;
                }
                if (product.consumePower != null) {
                    hasPower = true;
                    consume(new ConsumePowerDynamic(b -> b instanceof ECBeakerBuild ab ? ab.formulaPower() : 0));
                }
            }
        }
        super.init();
        hasConsumers = products.size > 0;
    }

    @Override
    public void setBars() {
        super.setBars();
        removeBar("items");
        removeBar("liquid");
        removeBar("power");
        if (consPower != null) {
            addBar("power", (ECBeakerBuild entity) -> new Bar(
                    () -> Core.bundle.get("bar.ec-mti-power") + (entity.formulaPower() > 0.01f ? Core.bundle.get("bar.ec-mti-power-need") : Core.bundle.get("bar.ec-mti-power-noNeed")),
                    () -> Pal.powerBar,
                    () -> Mathf.zero(consPower.requestedPower(entity)) && entity.power.graph.getPowerProduced() + entity.power.graph.getBatteryStored() > 0f ? 1f : entity.power.status)
            );
        }
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.output, table -> {
            table.row();

            for (int i = 0; i < products.size; i++) {
                var p = products.get(i);
                int finalI = i + 1;
                table.table(Styles.grayPanel, info -> {
                    info.left().defaults().left();
                    info.add(Core.bundle.get("bar.ec-formula") + finalI + ":").row();
                    Stats stat = new Stats();
                    stat.timePeriod = p.craftTime;
                    if (p.hasConsumers) for (var c : p.consumers) {
                        c.display(stat);
                    }
                    if ((hasItems && itemCapacity > 0) || p.outputItems != null) {
                        stat.add(Stat.productionTime, p.craftTime / 60f, StatUnit.seconds);
                    }

                    if (p.outputItems != null) {
                        stat.add(Stat.output, StatValues.items(p.craftTime, p.outputItems));
                    }

                    if (p.outputLiquids != null) {
                        stat.add(Stat.output, StatValues.liquids(1f, p.outputLiquids));
                    }
                    info.table(st -> statTurnTable(stat, st)).pad(8).left();
                }).growX().left().pad(10);
                table.row();
            }
        });
    }

    @Override
    public void load() {
        super.load();
        if (useBlockDrawer) drawer.load(this);
        else {
            if (products.size > 0) for (var p : products) {
                p.drawer.load(this);
            }
        }
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        if (useBlockDrawer) drawer.drawPlan(this, plan, list);
        else {
            if (products.size > 0) products.get(0).drawer.drawPlan(this, plan, list);
            else super.drawPlanRegion(plan, list);
        }
    }

    @Override
    protected TextureRegion[] icons() {
        return useBlockDrawer ? drawer.icons(this) : products.size > 0 ? products.get(0).drawer.icons(this) : super.icons();
    }

    public static class Formula {
        public Consume[] consumers = {}, optionalConsumers = {}, nonOptionalConsumers = {}, updateConsumers = {};
        public @Nullable
        ConsumePower consumePower;
        public float craftTime;
        public boolean hasConsumers;
        public float consPower = 0;
        public ItemStack[] outputItems;
        public ItemStack[] consItems;
        public LiquidStack[] outputLiquids;
        public LiquidStack[] consLiquids;
        public int[] liquidOutputDirections = {-1};
        public boolean ignoreLiquidFullness = false;
        public boolean dumpExtraLiquid = true;
        //液体运输只看liquidCapacity
        //public float maxLiquid = 10f;
        //所以不如直接全让建筑自己决定得了，哪有建筑不固定容量的，神经（
        //public int maxItem = 10;
        public ECBeaker owner = null;
        public float warmupSpeed = 0.02f;
        public float updateEffectChance = 0.05f;
        public Effect updateEffect = Fx.none;
        public Effect craftEffect = Fx.none;
        public DrawBlock drawer = new DrawDefault();
        public ObjectMap<Item, Boolean> itemFilter = new ObjectMap<>();
        public ObjectMap<Liquid, Boolean> liquidFilter = new ObjectMap<>();
        protected Seq<Consume> consumeBuilder = new Seq<>();
        protected OrderedMap<String, Func<Building, Bar>> barMap = new OrderedMap<>();

        public void init() {

            if (consItems != null) consumeBuilder.add(new ConsumeItems(consItems));
            if (consLiquids != null) consumeBuilder.add(new ConsumeLiquids(consLiquids));
            if (consPower > 0) {
                ConsumePower consume = new ConsumePower(consPower,0,false);
                consumeBuilder.removeAll(b -> b instanceof ConsumePower);
                consumePower = consume;
                consumeBuilder.add(consume);
            }


            consumers = consumeBuilder.toArray(Consume.class);
            optionalConsumers = consumeBuilder.select(consume -> consume.optional && !consume.ignore()).toArray(Consume.class);
            nonOptionalConsumers = consumeBuilder.select(consume -> !consume.optional && !consume.ignore()).toArray(Consume.class);
            updateConsumers = consumeBuilder.select(consume -> consume.update && !consume.ignore()).toArray(Consume.class);
            hasConsumers = consumers.length > 0;

            if (owner.autoAddBar) {
                if (liquidFilter.size > 0) {
                    for (Liquid l : liquidFilter.keys().toSeq()) {
                        addLiquidBar(l);
                    }
                }
                if (outputLiquids != null && outputLiquids.length > 0) {
                    for (LiquidStack l : outputLiquids) {
                        addLiquidBar(l.liquid);
                    }
                }
            }
        }

        public void setApply(UnlockableContent content) {
            if (content instanceof Item item) {
                itemFilter.put(item, true);
            }
            if (content instanceof Liquid liquid) {
                liquidFilter.put(liquid, true);
            }
        }

        public Iterable<Func<Building, Bar>> listBars() {
            return barMap.values();
        }

        public void addBar(String name, Func<Building, Bar> sup) {
            barMap.put(name, sup);
        }

        public void addLiquidBar(Liquid liq) {
            addBar("liquid-" + liq.name, entity -> !liq.unlockedNow() ? null : new Bar(
                    () -> liq.localizedName,
                    liq::barColor,
                    () -> entity.liquids.get(liq) / owner.liquidCapacity
            ));
        }

        public boolean getConsumeItem(Item item) {
            if (consItems!=null)for (ItemStack itemStack : consItems) {
                if (itemStack.item == item) return true;
            }
            return false;
            //return itemFilter.containsKey(item) && itemFilter.get(item);
        }

        public boolean getConsumeLiquid(Liquid liquid) {
            if (consLiquids!=null)for (LiquidStack liquidStack : consLiquids){
                if (liquidStack.liquid == liquid) return true;
            }
            return false;
            //return liquidFilter.containsKey(liquid) && liquidFilter.get(liquid);
        }

        public void consumeLiquid(Liquid liquid, float amount) {
            setApply(liquid);
            consume(new ConsumeLiquid(liquid, amount));
        }

        public void consumeLiquids(LiquidStack... stacks) {
            if (stacks.length > 0) for (var s : stacks) setApply(s.liquid);
            consume(new ConsumeLiquids(stacks));
        }

        public void consumePower(float powerPerTick) {
            consume(new ConsumePower(powerPerTick, 0.0f, false));
        }

        public void consumeItem(Item item) {
            setApply(item);
            consumeItem(item, 1);
        }

        public void consumeItem(Item item, int amount) {
            setApply(item);
            consume(new ConsumeItems(new ItemStack[]{new ItemStack(item, amount)}));
        }

        public void consumeItems(ItemStack... items) {
            if (items.length > 0) for (var s : items) setApply(s.item);
            consume(new ConsumeItems(items));
        }

        public <T extends Consume> void consume(T consume) {
            if (consume instanceof ConsumePower) {
                consumeBuilder.removeAll(b -> b instanceof ConsumePower);
                consumePower = (ConsumePower) consume;
            }
            consumeBuilder.add(consume);
        }
    }

    public class ECBeakerBuild extends Building {
        public Seq<Formula> consistent = new Seq<>();
        public int thisItemCapacity;
        public float thisLiquidCapacity;
        public Formula formula = products.size > 0 ? products.get(0) : null;
        public float[] progress = new float[products.size];
        public float totalProgress;
        public float warmup;

        public int[] configs = {0, 0};
        public int lastRotation = -1;

        public TextureRegionDrawable[] rotationIcon = {Icon.right, Icon.up, Icon.left, Icon.down};

        int drawTimer = 0;
        Random random = new Random();
        int randomNum = 0;
        @Override
        public void draw() {
            if (useBlockDrawer) drawer.draw(this);

            drawTimer += 1;
            if (drawTimer >= 15 ){
                drawTimer %= 15;
                randomNum = random.nextInt(products.size);
            }

            if (consistent.size == 0) {;
                products.get(randomNum).drawer.draw(this);

            }
            else consistent.get(0).drawer.draw(this);
        }

        @Override
        public void drawStatus() {

            if (this.block.enableDrawStatus && products.size > 0) {
                float multiplier = block.size > 1 ? 1 : 0.64f;
                float brcx = x + (float) (block.size * 8) / 2f - 8f * multiplier / 2f;
                float brcy = y - (float) (block.size * 8) / 2f + 8f * multiplier / 2f;
                Draw.z(71.0F);
                Draw.color(Pal.gray);
                Fill.square(brcx, brcy, 2.5f * multiplier, 45);
                Draw.color(status().color);
                Fill.square(brcx, brcy, 1.5f * multiplier, 45);
                Draw.color();
            }
        }

        @Override
        public BlockStatus status() {
            if (!this.enabled) {
                return BlockStatus.logicDisable;
            } else if (this.allCannot()) {
                return BlockStatus.noInput;
            } else if (!this.shouldConsume()) {
                return BlockStatus.noOutput;
            } else if (!(this.efficiency <= 0.0F) && this.productionValid()) {
                return Vars.state.tick / 30.0 % 1.0 < (double) this.efficiency ? BlockStatus.active : BlockStatus.noInput;
            } else {
                return BlockStatus.noInput;
            }
        }

        public float warmupTarget() {
            return 1f;
        }

        public float formulaPower() {
            if (consistent.size == 0) return 0;
            for (Formula formula : consistent){
                var consumePower = formula.consumePower;
                if (consumePower == null) return 0;
                return consumePower.usage;
            }
            return 0;
        }

        @Override
        public void dumpLiquid(Liquid liquid, float scaling, int outputDir) {
            int dump = this.cdump;
            if (!(this.liquids.get(liquid) <= 1.0E-4F)) {
                if (!Vars.net.client() && Vars.state.isCampaign() && this.team == Vars.state.rules.defaultTeam) {
                    liquid.unlock();
                }

                for(int i = 0; i < this.proximity.size; ++i) {
                    this.incrementDump(this.proximity.size);
                    Building other = this.proximity.get((i + dump) % this.proximity.size);
                    if (outputDir == -1 || (outputDir + this.rotation) % 4 == this.relativeTo(other)) {
                        other = other.getLiquidDestination(this, liquid);
                        if (other != null && other.block.hasLiquids && this.canDumpLiquid(other, liquid) && other.liquids != null) {
                            float ofract = other.liquids.get(liquid) / other.block.liquidCapacity;
                            float fract = this.liquids.get(liquid) / thisLiquidCapacity;
                            if (ofract < fract) {
                                this.transferLiquid(other, (fract - ofract) * thisLiquidCapacity / scaling, liquid);
                            }
                        }
                    }
                }

            }
        }
        @Override
        public void handleLiquid(Building source, Liquid liquid, float amount) {
            float flow = Math.min(thisLiquidCapacity - this.liquids.get(liquid), amount);
            this.liquids.add(liquid, flow);
        }

        @Override
        public void transferLiquid(Building next, float amount, Liquid liquid) {
            float flow = Math.min(next.block.liquidCapacity - next.liquids.get(liquid), amount);
            if (next.acceptLiquid(this, liquid)) {
                next.handleLiquid(this, liquid, flow);
                this.liquids.remove(liquid, flow);
            }
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid) {
            boolean b = false;
            if (consistent.size!=0 && !acceptNoConsistent){
                for (Formula formula : consistent) {
                    if (formula.getConsumeLiquid(liquid) && this.liquids.get(liquid) < thisLiquidCapacity) b = true;
                }
            } else for (Formula formula : products) {
                if (formula.getConsumeLiquid(liquid) && this.liquids.get(liquid) < thisLiquidCapacity) b = true;
            }
            return b;
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            boolean b = false;
            if (consistent.size!=0 && !acceptNoConsistent){
                for (Formula formula : consistent) {
                    if (formula.getConsumeItem(item) && this.items.get(item) < this.getMaximumAccepted(item)) b = true;
                }
            } else for (Formula formula : products) {
                if (formula.getConsumeItem(item) && this.items.get(item) < this.getMaximumAccepted(item)) b = true;
            }
            return b;
        }

        @Override
        public void updateTile() {


            if (lastRotation != rotation) {
                Fx.placeBlock.at(x, y, size);
                lastRotation = rotation;
            }


            if (products.size == 0) return;
            //判断配方是否符合,符合则添加到consistent
            for (Formula formula : products) {
                boolean isConsistent = true;
                if (formula.consItems != null) {
                    for (ItemStack itemStack : formula.consItems) {
                        if (items.get(itemStack.item) < itemStack.amount) {
                            isConsistent = false;
                            break;
                        }
                    }
                }

                if (formula.consLiquids != null) {
                    for (LiquidStack liquidStack : formula.consLiquids) {
                        if (liquids.get(liquidStack.liquid) < liquidStack.amount) {
                            isConsistent = false;
                            break;
                        }
                    }
                }
                consistent.remove(formula);
                if (isConsistent) {
                    if (!multiProduct) consistent.removeAll(consistent);
                    consistent.add(formula);
                    break;
                }
            }


            for (Formula formula : consistent) {
                int id = products.indexOf(formula);
                if (efficiency > 0) {
                    progress[id] += getProgressIncrease(formula.craftTime, formula);
                    warmup = Mathf.approachDelta(warmup, warmupTarget(), formula.warmupSpeed);

                    if (formula.outputLiquids != null) {
                        float inc = getProgressIncrease(1f);
                        for (var output : formula.outputLiquids) {
                            handleLiquid(this, output.liquid, Math.min(output.amount * inc, thisLiquidCapacity - liquids.get(output.liquid)));
                        }
                    }

                    if (wasVisible && Mathf.chanceDelta(formula.updateEffectChance)) {
                        formula.updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
                    }
                } else {
                    warmup = Mathf.approachDelta(warmup, 0f, formula.warmupSpeed);
                }

                totalProgress += warmup * Time.delta;
                dumpOutputs(formula);
                if (progress[id] >= 1f) {
                    consume(formula);
                    craft(formula);
                }

            }


            //物品容量修改
            if (AiItemCapacity&&consistent.size>0) {
                int MaxItem = 0;
                for (Formula formula:consistent){
                    for (ItemStack itemStack : formula.consItems) MaxItem = Math.max(MaxItem, itemStack.amount);
                    for (ItemStack itemStack : formula.outputItems) MaxItem = Math.max(MaxItem, itemStack.amount);
                }
                thisItemCapacity = MaxItem * 2;
            } else thisItemCapacity = itemCapacity;

            if (AiLiquidCapacity&&consistent.size>0) {
                float MaxLiquid = 0;
                float mostLiquid = 0;
                for (Formula formula:consistent){
                    for (LiquidStack liquidStack:formula.consLiquids) MaxLiquid = Math.max(MaxLiquid,liquidStack.amount);
                    for (LiquidStack liquidStack:formula.outputLiquids) MaxLiquid = Math.max(MaxLiquid,liquidStack.amount);
                }
                thisLiquidCapacity = MaxLiquid * 2;
            } else thisLiquidCapacity = liquidCapacity;


        }




        @Override
        public int explosionItemCap() {
            return thisItemCapacity;
        }

        @Override
        public int getMaximumAccepted(Item item) {
            return thisItemCapacity;
        }

        @Override
        public double sense(LAccess sensor) {
            double var10000;
            switch (sensor) {
                case x:
                    var10000 = (double) World.conv(this.x);
                    break;
                case y:
                    var10000 = (double) World.conv(this.y);
                    break;
                case color:
                    var10000 = Color.toDoubleBits(this.team.color.r, this.team.color.g, this.team.color.b, 1.0F);
                    break;
                case dead:
                    var10000 = !this.isValid() ? 1.0 : 0.0;
                    break;
                case team:
                    var10000 = (double) this.team.id;
                    break;
                case health:
                    var10000 = (double) this.health;
                    break;
                case maxHealth:
                    var10000 = (double) this.maxHealth;
                    break;
                case efficiency:
                    var10000 = (double) this.efficiency;
                    break;
                case timescale:
                    var10000 = (double) this.timeScale;
                    break;
                case range:
                    if (this instanceof Ranged) {
                        Ranged r = (Ranged) this;
                        var10000 = (double) (r.range() / 8.0F);
                    } else {
                        var10000 = 0.0;
                    }
                    break;
                case rotation:
                    var10000 = (double) this.rotation;
                    break;
                case totalItems:
                    var10000 = this.items == null ? 0.0 : (double) this.items.total();
                    break;
                case totalLiquids:
                    var10000 = this.liquids == null ? 0.0 : (double) this.liquids.currentAmount();
                    break;
                case totalPower:
                    var10000 = this.power != null && this.block.consPower != null ? (double) (this.power.status * (this.block.consPower.buffered ? this.block.consPower.capacity : 1.0F)) : 0.0;
                    break;
                case itemCapacity:
                    var10000 = this.block.hasItems ? (double) thisItemCapacity : 0.0;
                    break;
                case liquidCapacity:
                    var10000 = this.block.hasLiquids ? (double) this.block.liquidCapacity : 0.0;
                    break;
                case powerCapacity:
                    var10000 = this.block.consPower != null ? (double) this.block.consPower.capacity : 0.0;
                    break;
                case powerNetIn:
                    var10000 = this.power == null ? 0.0 : (double) (this.power.graph.getLastScaledPowerIn() * 60.0F);
                    break;
                case powerNetOut:
                    var10000 = this.power == null ? 0.0 : (double) (this.power.graph.getLastScaledPowerOut() * 60.0F);
                    break;
                case powerNetStored:
                    var10000 = this.power == null ? 0.0 : (double) this.power.graph.getLastPowerStored();
                    break;
                case powerNetCapacity:
                    var10000 = this.power == null ? 0.0 : (double) this.power.graph.getLastCapacity();
                    break;
                case enabled:
                    var10000 = this.enabled ? 1.0 : 0.0;
                    break;
                case controlled:
                    byte var5;
                    label86:
                    {
                        if (this instanceof ControlBlock) {
                            ControlBlock c = (ControlBlock) this;
                            if (c.isControlled()) {
                                var5 = 2;
                                break label86;
                            }
                        }

                        var5 = 0;
                    }

                    var10000 = (double) var5;
                    break;
                case payloadCount:
                    var10000 = this.getPayload() != null ? 1.0 : 0.0;
                    break;
                case size:
                    var10000 = (double) this.block.size;
                    break;
                default:
                    var10000 = Double.NaN;
            }

            return var10000;
        }

        @Override
        public float totalProgress() {
            return totalProgress;
        }

        public float getProgressIncrease(float baseTime, Formula formula) {
            if (formula.ignoreLiquidFullness) {
                return super.getProgressIncrease(baseTime);
            }

            float scaling = 1f, max = 1f;
            if (formula.outputLiquids != null) {
                max = 0f;
                for (var s : formula.outputLiquids) {
                    float value = (liquidCapacity - liquids.get(s.liquid)) / (s.amount * edelta());
                    scaling = Math.min(scaling, value);
                    max = Math.max(max, value);
                }
            }

            return super.getProgressIncrease(baseTime) * (formula.dumpExtraLiquid ? Math.min(max, 1f) : scaling);
        }

        public void craft(Formula formula) {

            int id = products.indexOf(formula);

            if (formula.outputItems != null) {
                for (var output : formula.outputItems) {
                    for (int i = 0; i < output.amount; i++) {
                        offload(output.item);
                    }
                }
            }

            if (wasVisible) {
                formula.craftEffect.at(x, y);
            }
            progress[id] %= 1f;
        }

        public void dumpOutputs(Formula formula) {
            if (formula.outputItems != null) {
                for (ItemStack output : formula.outputItems) {
                    for (int i = 0; i < Math.min(items.get(output.item), 816543 / 600); i++) dump(output.item);
                }
            }

            if (formula.outputLiquids != null) {
                for (int i = 0; i < formula.outputLiquids.length; i++) {
                    int dir = formula.liquidOutputDirections.length > i ? formula.liquidOutputDirections[i] : -1;
                    dumpLiquid(formula.outputLiquids[i].liquid, 2f, dir);
                }
            }
        }

        public boolean allCannot() {
            return consistent.size == 0;
        }

        @Override
        public boolean shouldConsume() {
            boolean a = false;
            boolean b = false;
            boolean c = true;
            boolean d = true;

            if (consistent.size == 0) return b;
            for (Formula formula : consistent) {


                if (formula.consItems != null) {
                    for (var cons : formula.consItems) {
                        if (items.get(cons.item) >= cons.amount) a = true;
                    }
                }

                if (formula.consLiquids != null) {
                    for (var cons : formula.consLiquids) {
                        if (liquids.get(cons.liquid) >= cons.amount) b = true;
                    }
                }


                if (formula.outputItems != null) {
                    for (var output : formula.outputItems) {
                        if (items.get(output.item) + output.amount > thisItemCapacity) c = false;
                    }
                }

                if (formula.outputLiquids != null && !formula.ignoreLiquidFullness) {
                    boolean allFull = true;
                    for (var output : formula.outputLiquids) {
                        if (liquids.get(output.liquid) >= thisLiquidCapacity - 0.001f) {
                            if (!formula.dumpExtraLiquid) d = false;
                        } else {
                            allFull = false;
                        }
                    }
                    if (allFull) d = false;

                }
            }
            if (enabled) return (a || b) && c && d;
            else return false;
        }

        public void consume(Formula formula) {
            if (formula == null) return;

            Consume[] c = formula.consumers;
            for (Consume cons : c) {
                cons.trigger(this);
            }

        }

        @Override
        public void displayConsumption(Table table) {
            super.displayConsumption(table);
        }

        private void rebuild(Table table) {
            table.clear();

            if (consistent.size == 0) return;
            for (Formula formula : consistent){
                Consume[] c = formula.consumers;
                for (Consume cons : c) {
                    if (!cons.optional || !cons.booster) {
                        cons.build(this, table);
                    }
                }
            }
        }

        @Override
        public void drawSelect() {
            super.drawSelect();
            if (consistent.size == 0 || !useLiquidTable) return;
            float width = 0, height = 32, pad = 4, tw = 32;
            for (Formula formula : consistent){

                if (formula.outputLiquids != null) {
                    for (int i = 0; i < formula.outputLiquids.length; i++) {
                        int dir = formula.liquidOutputDirections.length > i ? formula.liquidOutputDirections[i] : -1;

                        if (dir != -1) {
                            Draw.rect(
                                    formula.outputLiquids[i].liquid.fullIcon,
                                    x + Geometry.d4x(dir + rotation) * (size * tilesize / 2f + 4),
                                    y + Geometry.d4y(dir + rotation) * (size * tilesize / 2f + 4),
                                    8f, 8f
                            );
                        }
                    }
                }
                var canBar = Core.atlas.find(name("can"));

                if (formula.consLiquids != null) for (int i = 0; i < formula.consLiquids.length; i++) width += tw;
                if (formula.outputLiquids != null) for (int i = 0; i < formula.outputLiquids.length; i++) width += tw;
                if (width > 0) {
                    Draw.color(liquidTableBack);
                    float realW = width + pad * 2, realH = height + pad * 2;
                    float realX = x + size / 2f * tilesize - realW / 2;
                    float realY = y + size / 2f * tilesize;
                    float vts = tw / 4f;
                    float boPad = 1;
                    Fill.rect(x, realY + realH / 2, realW, realH);
                    for (var l : formula.consLiquids) {
                        if (formula.getConsumeLiquid(l.liquid)) {
                            float ly = realY + pad;
                            Draw.color();
                            Draw.rect(l.liquid.uiIcon, realX, ly);
                            Get.drawTiledFramesBar(vts, (height * Math.min(liquids.get(l.liquid) / thisLiquidCapacity, 1.1f)), realX + vts / 2f, ly, l.liquid, 1);
                            Draw.color();
                            Draw.alpha(1);
                            Draw.rect(canBar, realX + vts, ly + height / 2f, vts + boPad, height + boPad);
                            Fonts.outline.draw(l.liquid.localizedName, realX, ly - 1, Color.white, 0.2f, false, Align.center);
                            realX += tw;
                        }
                    }
                    if (formula.outputLiquids != null) {
                        for (int i = 0; i < formula.outputLiquids.length; i++) {
                            var ls = formula.outputLiquids[i];
                            float ly = realY + pad;
                            Draw.color();
                            Draw.rect(ls.liquid.uiIcon, realX, ly);
                            Get.drawTiledFramesBar(vts, (height * Math.min((liquids.get(ls.liquid) / thisLiquidCapacity), 1.1f)), realX + vts / 2f, ly, ls.liquid, 1);
                            Draw.color();
                            Draw.alpha(1);
                            Draw.rect(canBar, realX + vts / 2f + vts / 2f, ly + height / 2f, vts + 1, height + 2);
                            Fonts.outline.draw(ls.liquid.localizedName, realX, ly - 1, Color.white, 0.2f, false, Align.center);
                            realX += tw;
                        }
                    }
                }
            }

        }

        @Override
        public void displayBars(Table table) {
            super.displayBars(table);
            if (consistent.size == 0) return;
            for (Formula formula : consistent){
                Formula[] lastFormula = {formula};
                table.update(() -> {
                    if (lastFormula[0] != formula) {
                        rebuildBar(table);
                        lastFormula[0] = formula;
                    }
                });
            }
            rebuildBar(table);
        }

        private void rebuildBar(Table table) {
            table.clear();

            for (Func<Building, Bar> buildingBarFunc : block.listBars()) {
                Bar result = buildingBarFunc.get(this);
                if (result != null) {
                    table.add(result).growX();
                    table.row();
                }
            }
            if (consistent.size == 0) return;
            for (Formula formula : consistent){
                if (formula.barMap.size > 0) for (var bar : formula.listBars()) {
                    Bar result = bar.get(self());
                    if (result == null) continue;
                    table.add(result).growX();
                    table.row();
                }
            }
        }

        @Override
        public boolean shouldAmbientSound() {
            return efficiency > 0;
        }

        public void updateConsumption() {
            if (consistent.size == 0) return;
            for (var formula : consistent) {
                if (formula.hasConsumers && !cheating()) {
                    if (!enabled) {
                        potentialEfficiency = efficiency = optionalEfficiency = 0;
                    } else {
                        boolean update = shouldConsume() && productionValid();
                        float minEfficiency = 1f;
                        efficiency = optionalEfficiency = 1f;
                        Consume[] c = formula.nonOptionalConsumers;
                        int cl = c.length;

                        int i;
                        Consume cons;
                        for (i = 0; i < cl; i++) {
                            cons = c[i];
                            minEfficiency = Math.min(minEfficiency, cons.efficiency(this));
                        }

                        c = formula.optionalConsumers;
                        cl = c.length;

                        for (i = 0; i < cl; i++) {
                            cons = c[i];
                            optionalEfficiency = Math.min(optionalEfficiency, cons.efficiency(this));
                        }

                        efficiency = minEfficiency;
                        optionalEfficiency = Math.min(optionalEfficiency, minEfficiency);
                        potentialEfficiency = efficiency;
                        if (!update) {
                            efficiency = optionalEfficiency = 0.0F;
                        }

                        updateEfficiencyMultiplier();
                        if (update && efficiency > 0.0F) {
                            c = formula.updateConsumers;
                            cl = c.length;

                            for (i = 0; i < cl; i++) {
                                cons = c[i];
                                cons.update(this);
                            }
                        }

                    }
                } else {
                    potentialEfficiency = enabled && productionValid() ? 1f : 0f;
                    efficiency = optionalEfficiency = shouldConsume() ? potentialEfficiency : 0f;
                    updateEfficiencyMultiplier();
                }
            }
        }

        //按钮配置
        @Override
        public void buildConfiguration(Table table) {
            Table rtc = new Table();
            rtc.left().defaults().size(55);

            Table cont = new Table().top();
            cont.left().defaults().left().growX();

            Runnable rebuild = () -> {
                rtc.clearChildren();
                if (hasDoubleOutput) {
                    for (int i = 0; i < rotationIcon.length; i++) {
                        ImageButton button = new ImageButton();
                        int I = i;
                        button.table(img -> img.image(rotationIcon[I]).color(Color.white).size(40).pad(10f));
                        button.changed(() -> {
                            configs[0] = I;
                            configure(configs);
                        });
                        button.update(() -> button.setChecked(rotation == I));
                        button.setStyle(Styles.clearNoneTogglei);
                        rtc.add(button).tooltip("" + i * 90 + "°");
                    }
                }

                cont.clearChildren();
                if (consistent.size > 0) for (var f : consistent) {
                    ImageButton button = new ImageButton();
                    button.table(info -> {
                        info.left();
                        info.table(from -> {
                            Stats stat = new Stats();
                            stat.timePeriod = f.craftTime;
                            if (f.hasConsumers) for (var c : f.consumers) {
                                c.display(stat);
                            }
                            statToTable(stat, from);
                        }).left().pad(6);
                        info.row();
                        info.table(to -> {
                            if (f.outputItems != null) {
                                StatValues.items(f.craftTime, f.outputItems).display(to);
                            }

                            if (f.outputLiquids != null) {
                                StatValues.liquids(1f, f.outputLiquids).display(to);
                            }
                        }).left().pad(6);
                    }).grow().left().pad(5);
                    button.setStyle(Styles.clearNoneTogglei);
                    button.changed(() -> {
                        configs[1] = products.indexOf(f);
                        configure(configs);
                    });
                    button.update(() -> button.setChecked(formula == f));
                    cont.add(button);
                    cont.row();
                }
            };

            rebuild.run();

            Table main = new Table().background(Styles.black6);

            main.add(rtc).left().row();

            ScrollPane pane = new ScrollPane(cont, Styles.smallPane);
            pane.setScrollingDisabled(true, false);

            if (block != null) {
                pane.setScrollYForce(block.selectScroll);
                pane.update(() -> block.selectScroll = pane.getScrollY());
            }

            pane.setOverscroll(false, false);
            main.add(pane).maxHeight(100 * maxList);
            table.top().add(main);
        }


        @Override
        public int[] config() {
            return configs;
        }

        @Override
        public void configure(Object value) {
            super.configure(value);
            deselect();
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            for (int i = 0; i < products.size; i++) {
                write.f(progress[i]);
            }
            write.f(warmup);
            write.i(lastRotation);
            write.i(formula == null || !consistent.contains(formula) ? -1 : consistent.indexOf(formula));
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            for (int i = 0; i < products.size; i++) {
                progress[i] = read.f();
            }
            warmup = read.f();
            lastRotation = read.i();
            int i = read.i();
            formula = i == -1 ? null : products.get(i);
            configs[0] = rotation;
            configs[1] = i;
        }
    }
}