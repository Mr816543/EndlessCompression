package ec.Tools;

import arc.Core;
import arc.graphics.Color;
import arc.util.Log;
import mindustry.Vars;

import java.util.Random;

public class GradualDisplayName {
    public static void load(){
        Random random = new Random();
        String r = Integer.toHexString(random.nextInt(256));
        String g = Integer.toHexString(random.nextInt(256));
        String b = Integer.toHexString(random.nextInt(256));
        var startColor = Color.valueOf(r+g+b+"ff"); //初始颜色

        var shiftValue = random.nextInt(10)+10; //颜色跨度，360度为一次轮回

        var mod = Vars.mods.locateMod("ec");//mod.(h)json里面写的name

        var st = mod.meta.displayName;

        var fin = new java.lang.StringBuilder();

        var version = mod.meta.version;


        for(var i = 0; i < st.length(); i++){

            var s = java.lang.String.valueOf(st.charAt(i));

            var c = startColor.shiftHue(i * ((float) shiftValue /st.length()));

            var ci = c.rgb888();

            var ct = Integer.toHexString(ci);

            var fct = "[" + "#" + ct + "]";

            fin.append(fct).append(s);

        }
        fin.append("\n").append(Core.bundle.get("string.version")).append("[accent]").append(version).append("[#ffffff]");


        mod.meta.displayName = fin.toString();
        //作者：guiY归某人 https://www.bilibili.com/read/cv27308140/?spm_id_from=333.999.0.0 出处：bilibili




















































        if ((r+g+b).equals("ffffff")){
            mod.meta.displayName = Core.bundle.get("Egg");
            mod.meta.description = Core.bundle.get("Egg1");

            var st0 = mod.meta.displayName;

            var fin0 = new java.lang.StringBuilder();

            for(var i = 0; i < st0.length(); i++){

                var s = java.lang.String.valueOf(st0.charAt(i));

                var c = startColor.shiftHue(i * ((float) shiftValue /st0.length()));

                var ci = c.rgb888();

                var ct = Integer.toHexString(ci);

                var fct = "[" + "#" + ct + "]";

                fin0.append(fct).append(s);

            }
            mod.meta.description = fin0.toString();
        }

    }
}
