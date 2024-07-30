package ec.content;


import arc.Core;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Door;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.blocks.distribution.ArmoredConveyor;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.StackConveyor;
import mindustry.world.blocks.liquid.ArmoredConduit;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.SolidPump;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.units.UnitFactory;


@SuppressWarnings("ALL")
public class ECBlocks {
    public static ObjectMap<Block, Seq<Block>> ECBlocks = new ObjectMap<>();

    public static void load() throws IllegalAccessException, NoSuchFieldException {
        Seq<Block> blocks = new Seq<>();

        for (Item item : ECItems.ECItems.keys()) {
            //运行加载物品压缩器的方法
            load.itemCompressor(item);
            //运行加载多重物品压缩器的方法
            load.itemMultiPress(item);
        }

        for (Liquid liquid : ECLiquids.ECLiquids.keys()) {
            //运行加载液体压缩器的方法
            load.liquidCompressor(liquid);
        }

        //判断设置"Compress-other-Mods"(是否对其他Mod生效)的状态
        if (Core.settings.getBool("Compress-other-Mods")) {
            //把全部方块添加进blocks中
            blocks = Vars.content.blocks().copy();
        } else {
            //遍历全部方块
            for (Block block : Vars.content.blocks()) {
                //把非Mod方块添加进blocks中
                if (!block.isModded()) {
                    blocks.add(block);
                }
            }
        }

        //遍历blocks
        for (Block block : blocks) {
            if (block instanceof Wall) {
                if (!(block instanceof Door)) {
                    load.wall(block);
                }
            } else if (block instanceof GenericCrafter) {
                load.genericCrafter(block);
            } else if (block instanceof Turret) {
                load.turret(block);
            } else if (block instanceof UnitFactory) {
                load.unitFactorys(block);
            } else if (block instanceof Drill) {
                load.drill(block);
            } else if (block instanceof Conveyor) {
                if (block instanceof ArmoredConveyor) {
                    load.armoredConveyor(block);
                } else {
                    load.conveyor(block);
                }
            } else if (block instanceof StackConveyor) {
                load.stackConveyor(block);
            } else if (block instanceof SolidPump) {
                load.solidPump(block);
            } else if (block instanceof PowerGenerator) {
                if (block instanceof ConsumeGenerator) {
                    load.consumeGenerator(block);
                } else {

                }
            } else if (block instanceof PowerNode) {
                load.powerNode(block);
            } else if (block instanceof Conduit) {
                if (block instanceof ArmoredConduit) {
                    load.armoredConduit(block);
                } else {
                    load.conduit(block);
                }
            } else if (block instanceof LightBlock) {
                load.lightBlock(block);
            } else if (block instanceof Battery) {
                load.battery(block);
            } else if (block instanceof CoreBlock) {
                load.coreBlock(block);
            }
        }
    }
}
