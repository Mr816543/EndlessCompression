package ec.content;

import arc.Core;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

import static mindustry.content.UnitTypes.*;

public class ECUnitTypes {
    public static ObjectMap<UnitType,Seq<UnitType>> ECUnits = new ObjectMap<>();
    public static void load() throws IllegalAccessException {
        Seq<UnitType> units = new Seq<>();
        //判断设置"Compress-other-Mods"(是否对其他Mod生效)的状态
        if (Core.settings.getBool("Compress-other-Mods")) {
            //把全部单位添加进units中
            units = Vars.content.units().copy();
        }else {
            //遍历全部单位
            for (UnitType unit : Vars.content.units()){
                //把非Mod单位添加进units中
                if (!unit.isModded()){
                    units.add(unit);
                }
            }
        }
        //遍历units
        for (UnitType unit : units) {
            //运行加载压缩单位的方法
            load.unit(unit);
        }
    }
}
