package ec.content;

import arc.Core;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.type.Liquid;

public class ECLiquids {
    public static ObjectMap<Liquid, Seq<Liquid>> ECLiquids = new ObjectMap<>();

    public static void load() throws IllegalAccessException {
        Seq<Liquid> liquids = new Seq<>();

        //判断设置"Compress-other-Mods"(是否对其他Mod生效)的状态
        if (Core.settings.getBool("Compress-other-Mods")) {
            //把全部液体添加进liquids中
            liquids = Vars.content.liquids().copy();
        } else {
            //遍历全部液体
            for (Liquid liquid : Vars.content.liquids()) {
                //把非Mod液体添加进liquids中
                if (!liquid.isModded()) {
                    liquids.add(liquid);
                }
            }
        }

        //遍历liquids
        for (Liquid liquid : liquids) {
            //运行加载压缩液体的方法
            load.liquid(liquid);
            //运行加载液体压缩器的方法
            load.liquidCompressor(liquid);
        }
    }
}
