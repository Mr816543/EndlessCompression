package ec.content;

import arc.Core;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

import static mindustry.content.UnitTypes.*;

public class ECUnitTypes {
    public static Seq<UnitType> AllUnits;
    public static ObjectMap<UnitType,Seq<UnitType>> ECUnits = new ObjectMap<>();
    public static void load() throws IllegalAccessException {
        //判断设置"Compress-other-Mods"(是否对其他Mod生效)的状态
        if (Core.settings.getBool("Compress-other-Mods")) {

            AllUnits = Vars.content.units().copy();
            //遍历所有单位名字
            for (UnitType unit : AllUnits) {
                //根据每个单位的注册名运行加载压缩单位的方法
                load.unit(unit);
            }
        } else {
            //原版全部单位
            AllUnits = new Seq<>(new UnitType[]{
                    dagger, mace, fortress, nova,
                    pulsar, quasar, crawler, atrax,
                    spiroct, arkyid, toxopid, flare,
                    horizon, zenith, antumbra, eclipse,
                    mono, poly, mega, quad,
                    oct, risso, minke, bryde,
                    sei, omura, retusa, oxynoe,
                    cyerce, aegires, navanax, alpha,
                    beta, gamma, scepter, reign,
                    vela, corvus, stell, locus,
                    precept, vanquish, conquer, merui,
                    cleroi, anthicus, tecta, collaris,
                    elude, avert, obviate, quell,
                    disrupt, evoke, incite, emanate,
                    manifold, assemblyDrone, latum, renale
            });
            //遍历原版单位
            for (UnitType unit : AllUnits) {
                //根据每个单位的注册名运行加载压缩单位的方法
                load.unit(unit);
            }
        }


    }


}
