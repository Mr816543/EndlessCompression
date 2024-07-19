package ec.Blocks;

import arc.Core;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.UnitFactory;

import static ec.content.ECUnitTypes.ECUnits;
import static ec.content.ECItems.ECItems;
import static mindustry.content.Blocks.*;

public class ECUnitFactorys {
    public static void load(){
        Seq<Block> blocks = new Seq<>();
        if (Core.settings.getBool("Compress-other-Mods")){
            blocks = Vars.content.blocks();
        }
        else{
            for (Block block : Vars.content.blocks()){
                if (!block.isModded() && (block instanceof UnitFactory || block instanceof Reconstructor))blocks.add(block);
            }
        }
        for (Block block : blocks){
            if (block instanceof UnitFactory unitFactory){
                for (UnitFactory.UnitPlan unitPlan : unitFactory.plans.copy()){
                    for (int i = 1 ; i < 10 ; i++ ){
                        if (ECUnits.get(unitPlan.unit)!=null){
                            UnitType unit = ECUnits.get(unitPlan.unit).get(i);
                            float time = unitPlan.time;
                            ItemStack[] requirements = new ItemStack[unitPlan.requirements.length];
                            for (int j = 0 ; j < unitPlan.requirements.length;j++){
                                ItemStack itemStack = unitPlan.requirements[j];
                                Item item = ECItems.get(itemStack.item).get(i);
                                requirements[j]= new ItemStack(item,unitPlan.requirements[j].amount);
                            }
                            ((UnitFactory) block).plans.add(new UnitFactory.UnitPlan(unit,time,requirements));
                        }
                    }
                }
            }
            else if (block instanceof Reconstructor reconstructor){
                Seq<UnitType[]> newunitTypes = new Seq<>();
                for (UnitType[] unitTypes : reconstructor.upgrades){
                    if ((ECUnits.get(unitTypes[0])!=null)&&(ECUnits.get(unitTypes[1])!=null)){
                        for (int i = 1 ; i < 10 ; i++){
                            newunitTypes.add(new UnitType[]{ECUnits.get(unitTypes[0]).get(i),ECUnits.get(unitTypes[1]).get(i)});
                        }
                    }
                }
                reconstructor.upgrades.add(newunitTypes);
            }
        }
    }
}
