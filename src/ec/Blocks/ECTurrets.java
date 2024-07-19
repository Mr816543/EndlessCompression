package ec.Blocks;

import arc.Core;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Log;
import ec.content.ECItems;
import ec.content.ECLiquids;
import ec.content.load;
import mindustry.Vars;
import mindustry.entities.bullet.BulletType;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.LiquidTurret;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.UnitFactory;

public class ECTurrets {
    public static void load(){
        //初始化blocks
        Seq<Block> blocks = new Seq<>();
        //获取设置Compress-other-Mods(是否压缩其他mod)
        if (Core.settings.getBool("Compress-other-Mods")) {
            //是,则将blocks设置为全体方块
            blocks = Vars.content.blocks();
        } else {
            //否,则对全体方块进行遍历,将非mod的炮塔方块添加到blocks中
            for (Block block : Vars.content.blocks()){
                if (!block.isModded() && (block instanceof Turret))blocks.add(block);
            }
        }
        for (Block block : blocks){
            if (block instanceof ItemTurret){
                ObjectMap<Item, BulletType> newAmmoTypes = new ObjectMap<>();
                for (Item item:((ItemTurret)block).ammoTypes.keys()){
                    if (item.isModded()){
                        Log.info(block.name +":"+ item.name);
                    }else {for (int i = 1 ; i < 10 ; i ++){
                        Item ammoItem = ECItems.ECItems.get(item).get(i);
                        BulletType bulletType = ((ItemTurret)block).ammoTypes.get(item).copy();
                        load.bullet(bulletType,i);
                        newAmmoTypes.put(ammoItem,bulletType);
                    }}

                }
                ((ItemTurret)block).ammoTypes.putAll(newAmmoTypes);

            }
            else if (block instanceof LiquidTurret){
                ObjectMap<Liquid, BulletType> newAmmoTypes = new ObjectMap<>();
                for (Liquid liquid:((LiquidTurret)block).ammoTypes.keys()){
                    if (liquid.isModded()){
                        Log.info(block.name +":"+ liquid.name);
                    }else{for (int i = 1 ; i < 10 ; i ++){
                        Liquid ammoLiquid = ECLiquids.ECLiquids.get(liquid).get(i);
                        BulletType bulletType = ((LiquidTurret)block).ammoTypes.get(liquid);
                        load.bullet(bulletType,i);
                        newAmmoTypes.put(ammoLiquid,bulletType);
                    }}

                }
                ((LiquidTurret)block).ammoTypes.putAll(newAmmoTypes);
            }
        }





    }
}
