package ec.content;

import arc.graphics.Color;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.type.*;

public class ECLiquids {
    public static void load(){
        int liquidBase = 4;
        for (int i = 1 ; i < 10 ; i++ ){
            int num = i ;
            new Liquid("water"+num,load.liquidcolor("water",num,true)){{
                temperature = (float) (Vars.content.liquid("water").temperature*Math.pow(liquidBase,num));
                heatCapacity = (float) (0.4f*Math.pow(liquidBase,num));
                effect = StatusEffects.wet;
                boilPoint = 0.5f;
                gasColor = Color.grays(0.9f);
            }};
            new Liquid("slag"+num,load.liquidcolor("slag",num,true)){{
                temperature = (float) (1f*Math.pow(liquidBase,num));
                heatCapacity = (float) (Vars.content.liquid("slag").heatCapacity*Math.pow(liquidBase,num));
                viscosity = 0.7f;
                effect = StatusEffects.melting;
                lightColor = Color.valueOf("f0511d").a(0.4f);
            }};
            new Liquid("oil"+num, load.liquidcolor("oil",num,true)){{
                viscosity = 0.75f;
                flammability = 1.2f;
                explosiveness = (float) (1.2f*Math.pow(liquidBase,num));
                temperature = (float) (Vars.content.liquid("oil").temperature*Math.pow(liquidBase,num));
                heatCapacity = (float) (0.7f*Math.pow(liquidBase,num));
                barColor = Color.valueOf("6b675f");
                effect = StatusEffects.tarred;
                boilPoint = 0.65f;
                gasColor = Color.grays(0.4f);
            }};
            new Liquid("cryofluid"+num,load.liquidcolor("cryofluid",num,true)){{
                heatCapacity = (float) (0.9f*Math.pow(liquidBase,num));
                temperature = (float) (0.5f-0.25f*Math.pow(liquidBase,num));
                effect = StatusEffects.freezing;
                lightColor = Color.valueOf("0097f5").a(0.2f);
                boilPoint = 0.55f;
                gasColor = Color.valueOf("c1e8f5");
            }};
        };

    };
/*
    static int liquidBase = 10;
    //water
    public static Liquid water1= new Liquid("water1", Color.valueOf("5e6fbd")){{
        heatCapacity = (float) (0.4f*Math.pow(liquidBase,1));
        effect = StatusEffects.wet;
        boilPoint = 0.5f;
        gasColor = Color.grays(0.9f);
    }};
    public static Liquid water2= new Liquid("water2", Color.valueOf("6374c2")){{
        heatCapacity = (float) (0.4f*Math.pow(liquidBase,2));
        effect = StatusEffects.wet;
        boilPoint = 0.5f;
        gasColor = Color.grays(0.9f);
    }};
    public static Liquid water3= new Liquid("water3", Color.valueOf("6879c7")){{
        heatCapacity = (float) (0.4f*Math.pow(liquidBase,3));
        effect = StatusEffects.wet;
        boilPoint = 0.5f;
        gasColor = Color.grays(0.9f);
    }};
    public static Liquid water4= new Liquid("water4", Color.valueOf("6d7ecc")){{
        heatCapacity = (float) (0.4f*Math.pow(liquidBase,4));
        effect = StatusEffects.wet;
        boilPoint = 0.5f;
        gasColor = Color.grays(0.9f);
    }};
    public static Liquid water5= new Liquid("water5", Color.valueOf("7283d1")){{
        heatCapacity = (float) (0.4f*Math.pow(liquidBase,5));
        effect = StatusEffects.wet;
        boilPoint = 0.5f;
        gasColor = Color.grays(0.9f);
    }};
    public static Liquid water6= new Liquid("water6", Color.valueOf("7788d6")){{
        heatCapacity = (float) (0.4f*Math.pow(liquidBase,6));
        effect = StatusEffects.wet;
        boilPoint = 0.5f;
        gasColor = Color.grays(0.9f);
    }};
    public static Liquid water7= new Liquid("water7", Color.valueOf("7c8ddb")){{
        heatCapacity = (float) (0.4f*Math.pow(liquidBase,7));
        effect = StatusEffects.wet;
        boilPoint = 0.5f;
        gasColor = Color.grays(0.9f);
    }};
    public static Liquid water8= new Liquid("water8", Color.valueOf("8192e0")){{
        heatCapacity = (float) (0.4f*Math.pow(liquidBase,8));
        effect = StatusEffects.wet;
        boilPoint = 0.5f;
        gasColor = Color.grays(0.9f);
    }};
    public static Liquid water9= new Liquid("water9", Color.valueOf("8697e5")){{
        heatCapacity = (float) (0.4f*Math.pow(liquidBase,9));
        effect = StatusEffects.wet;
        boilPoint = 0.5f;
        gasColor = Color.grays(0.9f);
    }};

    //slag
    public static  Liquid slag1 = new Liquid("slag1", Color.valueOf("fa9c61")){{
        temperature = (float) (1f*Math.pow(liquidBase,1));
        viscosity = 0.7f;
        effect = StatusEffects.melting;
        lightColor = Color.valueOf("f0511d").a(0.4f);
    }};
    public static  Liquid slag2 = new Liquid("slag2", Color.valueOf("f5975c")){{
        temperature = (float) (1f*Math.pow(liquidBase,2));
        viscosity = 0.7f;
        effect = StatusEffects.melting;
        lightColor = Color.valueOf("f0511d").a(0.4f);
    }};
    public static  Liquid slag3 = new Liquid("slag3", Color.valueOf("f09257")){{
        temperature = (float) (1f*Math.pow(liquidBase,3));
        viscosity = 0.7f;
        effect = StatusEffects.melting;
        lightColor = Color.valueOf("f0511d").a(0.4f);
    }};
    public static  Liquid slag4 = new Liquid("slag4", Color.valueOf("eb8d52")){{
        temperature = (float) (1f*Math.pow(liquidBase,4));
        viscosity = 0.7f;
        effect = StatusEffects.melting;
        lightColor = Color.valueOf("f0511d").a(0.4f);
    }};
    public static  Liquid slag5 = new Liquid("slag5", Color.valueOf("e6884d")){{
        temperature = (float) (1f*Math.pow(liquidBase,5));
        viscosity = 0.7f;
        effect = StatusEffects.melting;
        lightColor = Color.valueOf("f0511d").a(0.4f);
    }};
    public static  Liquid slag6 = new Liquid("slag6", Color.valueOf("e18348")){{
        temperature = (float) (1f*Math.pow(liquidBase,6));
        viscosity = 0.7f;
        effect = StatusEffects.melting;
        lightColor = Color.valueOf("f0511d").a(0.4f);
    }};
    public static  Liquid slag7 = new Liquid("slag7", Color.valueOf("dc7e43")){{
        temperature = (float) (1f*Math.pow(liquidBase,7));
        viscosity = 0.7f;
        effect = StatusEffects.melting;
        lightColor = Color.valueOf("f0511d").a(0.4f);
    }};
    public static  Liquid slag8 = new Liquid("slag8", Color.valueOf("d7793e")){{
        temperature = (float) (1f*Math.pow(liquidBase,8));
        viscosity = 0.7f;
        effect = StatusEffects.melting;
        lightColor = Color.valueOf("f0511d").a(0.4f);
    }};
    public static  Liquid slag9 = new Liquid("slag9", Color.valueOf("d27439")){{
        temperature = (float) (1f*Math.pow(liquidBase,9));
        viscosity = 0.7f;
        effect = StatusEffects.melting;
        lightColor = Color.valueOf("f0511d").a(0.4f);
    }};

 */



}
