package ec.content;

import arc.Core;
import arc.graphics.Color;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.type.*;

import static mindustry.content.Liquids.*;

public class ECLiquids {
    public static Seq<Liquid> AllLiquids;
    public static ObjectMap<Liquid,Seq<Liquid>> ECLiquids = new ObjectMap<>();
    public static void load() throws IllegalAccessException {
        //判断设置"Compress-other-Mods"(是否对其他Mod生效)的状态
        if (Core.settings.getBool("Compress-other-Mods")){
            //根据全部液体的数量创建一个字符串组AllLiquids用于储存全部液体的名字
            AllLiquids = Vars.content.liquids().copy();
            //初始化
            //遍历AllLiquids
            for (Liquid liquid : AllLiquids){
                //根据每个液体的注册名运行加载压缩液体的方法
                load.liquid(liquid);
                //根据每个液体的注册名运行加载液体压缩器的方法
                load.liquidCompressor(liquid);
            }
        }
        //如果设置为false,则遍历原版液体,根据原版液体的注册名运行加载压缩液体的方法
        else{
            //原版液体
            AllLiquids = new Seq<>(new Liquid[]{
                    water,slag,oil,cryofluid,
                    neoplasm,arkycite,gallium,ozone,
                    hydrogen,nitrogen,cyanogen});
            //遍历原版液体
            for (Liquid liquid : AllLiquids){
                //根据原版液体的注册名运行加载压缩液体的方法
                load.liquid(liquid);
                //根据每个液体的注册名运行加载液体压缩器的方法
                load.liquidCompressor(liquid);
            }
        }
    };
}
