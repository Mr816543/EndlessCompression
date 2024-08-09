package ec.content;


import arc.Core;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.blocks.campaign.LaunchPad;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.ArmoredConveyor;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.MassDriver;
import mindustry.world.blocks.distribution.StackConveyor;
import mindustry.world.blocks.liquid.ArmoredConduit;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.sandbox.ItemSource;
import mindustry.world.blocks.sandbox.PowerSource;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.blocks.storage.Unloader;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.RepairTurret;
import mindustry.world.blocks.units.UnitFactory;


@SuppressWarnings("ALL")
public class ECBlocks {
    public static ObjectMap<Block, Seq<Block>> ECBlocks = new ObjectMap<>();

    public static void load() throws IllegalAccessException, NoSuchFieldException {
        Seq<Block> blocks = new Seq<>();

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
            boolean compress = true;
            Class<?> clazz = block.getClass().getSuperclass();
            if (false) {
            } else if (clazz.equals(Drill.class)) load.drill(block);
            else if (clazz.equals(Wall.class)) load.wall(block);
            else if (clazz.equals(UnitFactory.class)) load.UnitFactory(block);
            else if (clazz.equals(Reconstructor.class)) load.Reconstructor(block);
            else if (clazz.equals(BeamDrill.class)) load.BeamDrill(block);
            else if (clazz.equals(Conveyor.class)) load.conveyor(block);
            else if (clazz.equals(ArmoredConveyor.class)) load.armoredConveyor(block);
            else if (clazz.equals(StackConveyor.class)) load.stackConveyor(block);
            else if (clazz.equals(Pump.class)) load.Pump(block);
            else if (clazz.equals(SolidPump.class)) load.solidPump(block);
            else if (clazz.equals(ConsumeGenerator.class)) load.consumeGenerator(block);
            else if (clazz.equals(SolarGenerator.class)) load.SolarGenerator(block);
            else if (clazz.equals(NuclearReactor.class)) load.NuclearReactor(block);
            else if (clazz.equals(PowerNode.class)) load.powerNode(block);
            else if (clazz.equals(Conduit.class)) load.conduit(block);
            else if (clazz.equals(ArmoredConduit.class)) load.armoredConduit(block);
            else if (clazz.equals(LightBlock.class)) load.lightBlock(block);
            else if (clazz.equals(Battery.class)) load.battery(block);
            else if (clazz.equals(CoreBlock.class)) load.coreBlock(block);
            else if (clazz.equals(Unloader.class)) load.Unloader(block);
            else if (clazz.equals(Separator.class)) load.Separator(block);
            else if (clazz.equals(OverdriveProjector.class)) load.OverdriveProjector(block);
            else if (clazz.equals(ForceProjector.class)) load.ForceProjector(block);
            else if (clazz.equals(GenericCrafter.class)) load.genericCrafter(block);

            else if (clazz.equals(ItemTurret.class)) load.ItemTurret(block);
            else if (clazz.equals(LiquidTurret.class)) load.LiquidTurret(block);

            else if (clazz.equals(StorageBlock.class)) load.StorageBlock(block);
            else if (clazz.equals(ThermalGenerator.class)) load.ThermalGenerator(block);
            else if (clazz.equals(LiquidRouter.class)) load.LiquidRouter(block);
            else if (clazz.equals(MendProjector.class)) load.MendProjector(block);
            else if (clazz.equals(ImpactReactor.class)) load.ImpactReactor(block);
            else if (clazz.equals(MassDriver.class)) load.MassDriver(block);
            else if (clazz.equals(PowerSource.class)) load.PowerSource(block);
            else if (clazz.equals(ItemSource.class)) load.ItemSource(block);

            else if (clazz.equals(PowerTurret.class)) load.PowerTurret(block);
            else if (clazz.equals(TractorBeamTurret.class)) load.TractorBeamTurret(block);
            else if (clazz.equals(PointDefenseTurret.class)) load.PointDefenseTurret(block);

            else if (clazz.equals(AttributeCrafter.class)) load.AttributeCrafter(block);
            else if (clazz.equals(LaunchPad.class)) load.LaunchPad(block);
            else if (clazz.equals(RepairTurret.class)) load.RepairTurret(block);
            else if (clazz.equals(Fracker.class)) load.Fracker(block);
            else if (clazz.equals(Door.class)) load.Door(block);

            else if (clazz.equals(LaserTurret.class)) load.LaserTurret(block);
            else if (clazz.equals(ShockMine.class)) load.ShockMine(block);







            else compress = false;

            if (compress = true){
                if (block.isModded()){
                    Log.info(Core.bundle.get("log.compress") + "[" + block.minfo.mod.name + "]" + block.localizedName + Core.bundle.get("log.compress.end"));
                }

            }


            // if (block instanceof Turret) load.turret(block);
            //else if (block instanceof GenericCrafter) {
            //      load.genericCrafter(block);
            // }

            // else if (block instanceof Wall) if (!(block instanceof Door)) load.wall(block);
            //else if (block instanceof UnitFactory) load.unitFactorys(block);
            // else if (block instanceof Reconstructor) load.Reconstructor(block);

            //else if (block instanceof Drill) load.drill(block);
            //else if (block instanceof BeamDrill) load.BeamDrill(block);
            //else if (block instanceof Conveyor) {
            //if (block instanceof ArmoredConveyor) load.armoredConveyor(block);
            //else load.conveyor(block);
            // } else if (block instanceof StackConveyor) load.stackConveyor(block);
            //else if (block instanceof Pump) {
            // if (block instanceof SolidPump) load.solidPump(block);
            // else load.Pump(block);
            //}
            // else if (block instanceof PowerGenerator) {
            //    if (block instanceof ConsumeGenerator) load.consumeGenerator(block);
            //   else if (block instanceof SolarGenerator) load.SolarGenerator(block);
            //   else if (block instanceof NuclearReactor) load.NuclearReactor(block);
            // }


            //else if (block instanceof PowerNode) load.powerNode(block);
            //else if (block instanceof Conduit) {
            //    if (block instanceof ArmoredConduit) load.armoredConduit(block);
            //   else load.conduit(block);
            // }


            //else if (block instanceof LightBlock) load.lightBlock(block);
            //else if (block instanceof Battery) load.battery(block);
            //else if (block instanceof CoreBlock) load.coreBlock(block);
            //else if (block instanceof Unloader) load.Unloader(block);
            //  else if (block instanceof Separator) load.Separator(block);
            //else if (block instanceof OverdriveProjector) load.OverdriveProjector(block);
            // else if (block instanceof ForceProjector) load.ForceProjector(block);

        }


        for (Item item : ECItems.All) {
            //运行加载物品压缩器的方法
            load.itemCompressor(item);
            //运行加载多重物品压缩器的方法
            load.itemMultiPress(item);
        }

        for (Liquid liquid : ECLiquids.All) {
            //运行加载液体压缩器的方法
            load.liquidCompressor(liquid);
            load.liquidMultiPress(liquid);
        }
    }
}
