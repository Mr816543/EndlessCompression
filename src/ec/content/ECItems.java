package ec.content;

import mindustry.type.Item;


@SuppressWarnings("SpellCheckingInspection")
public class ECItems {
    public static void load(){

        int attributeBase = 4;

        for(int i = 1 ; i < 10 ; i++){
            int num = i;
            new Item("copper"+num,load.itemcolor("copper",num,true)){{
                hardness = 1+num;
                cost = 0.5f;
            }};
            new Item("lead"+num,load.itemcolor("lead",num,true)){{
                hardness = 1+num;
                cost = 0.7f;
            }};
            new Item("sand"+num,load.itemcolor("sand",num,true)){{
                lowPriority = true;
                buildable = false;
            }};
            new Item("titanium"+num,load.itemcolor("titanium",num,true)){{
                hardness = 3+num;
                cost = 1f;
            }};
            new Item("metaglass"+num,load.itemcolor("metaglass",num,true)){{
                cost = 1.5f;
            }};
            new Item("scrap"+num,load.itemcolor("scrap",num,true)){};
            new Item("coal"+num,load.itemcolor("coal",num,false)){{
                explosiveness = (float) (0.2f*Math.pow(attributeBase,num));
                flammability = (float) (1f*Math.pow(attributeBase,num));
                hardness = 2+num;
                buildable = false;
            }};
            new Item("thorium"+num,load.itemcolor("thorium",num,true)){{
                explosiveness = (float) (0.2f*Math.pow(attributeBase,num));
                hardness = 4+num;
                radioactivity = (float) (1f*Math.pow(attributeBase,num));
                cost = 1.1f;
                healthScaling = 0.2f;
            }};
            new Item("surgeAlloy"+num,load.itemcolor("surge-alloy",num,true)){{
                cost = 1.2f;
                charge = (float) (0.75f*Math.pow(attributeBase,num));
                healthScaling = 0.25f;
            }};
            new Item("phaseFabric"+num,load.itemcolor("phase-fabric",num,true)){{
                cost = 1.3f;
                radioactivity = (float) (0.6f*Math.pow(attributeBase,num));
                healthScaling = 0.25f;
            }};
            new Item("graphite"+num,load.itemcolor("graphite",num,true)){{
                cost = 1f;
            }};
            new Item("silicon"+num,load.itemcolor("silicon",num,false)){{
                cost = 0.8f;
            }};
            new Item("pyratite"+num,load.itemcolor("pyratite",num,true)){{
                flammability = (float) (1.4f*Math.pow(attributeBase,num));
                explosiveness = (float) (0.4f*Math.pow(attributeBase,num));
                buildable = false;
            }};
            new Item("blastCompound"+num,load.itemcolor("blast-compound",num,true)){{
                flammability = (float) (0.4f*Math.pow(attributeBase,num));
                explosiveness = (float) (1.2f*Math.pow(attributeBase,num));
                buildable = false;
            }};
            new Item("sporePod"+num,load.itemcolor("spore-pod",num,false)){{
                flammability = (float) (1.15f*Math.pow(attributeBase,num));
                buildable = false;
            }};
            new Item("plastanium"+num,load.itemcolor("plastanium",num,true)){{
                flammability = (float) (0.1f*Math.pow(attributeBase,num));
                explosiveness = (float) (0.2f*Math.pow(attributeBase,num));
                cost = 1.3f;
                healthScaling = 0.1f;
            }};




        };

        for (int i = 0 ; i < 10 ;i++){
            int num = i ;
            new Item("power"+num,load.itemcolor("surge-alloy",0,true)){{
                charge = (float) (1*Math.pow(10,num));
            }};
        };

    };

/*
    //copper
    public static Item copper1 = new Item("copper1",Color.valueOf("d4986e")){{
        hardness = 1+1;
        cost = 0.5f;
    }};
    public static Item copper2 = new Item("copper2",Color.valueOf("cf9369")){{
        hardness = 1+2;
        cost = 0.5f;
    }};
    public static Item copper3 = new Item("copper3",Color.valueOf("ca8e64")){{
        hardness = 1+3;
        cost = 0.5f;
    }};
    public static Item copper4 = new Item("copper4",Color.valueOf("c5895f")){{
        hardness = 1+4;
        cost = 0.5f;
    }};
    public static Item copper5 = new Item("copper5",Color.valueOf("c0845a")){{
        hardness = 1+5;
        cost = 0.5f;
    }};
    public static Item copper6 = new Item("copper6",Color.valueOf("bb7f55")){{
        hardness = 1+6;
        cost = 0.5f;
    }};
    public static Item copper7 = new Item("copper7",Color.valueOf("b67a50")){{
        hardness = 1+7;
        cost = 0.5f;
    }};
    public static Item copper8 = new Item("copper8",Color.valueOf("b1754b")){{
        hardness = 1+8;
        cost = 0.5f;
    }};
    public static Item copper9 = new Item("copper9",Color.valueOf("ac7046")){{
        hardness = 1+9;
        cost = 0.5f;
    }};



    //lead
    public static Item lead1 = new Item("lead1",Color.valueOf("877aa4")){{
        hardness = 1+1;
        cost = 0.7f;
    }};
    public static Item lead2 = new Item("lead2",Color.valueOf("82759f")){{
        hardness = 1+2;
        cost = 0.7f;
    }};
    public static Item lead3 = new Item("lead3",Color.valueOf("7d709a")){{
        hardness = 1+3;
        cost = 0.7f;
    }};
    public static Item lead4 = new Item("lead4",Color.valueOf("786b95")){{
        hardness = 1+4;
        cost = 0.7f;
    }};
    public static Item lead5 = new Item("lead5",Color.valueOf("736690")){{
        hardness = 1+5;
        cost = 0.7f;
    }};
    public static Item lead6 = new Item("lead6",Color.valueOf("6e618b")){{
        hardness = 1+6;
        cost = 0.7f;
    }};
    public static Item lead7 = new Item("lead7",Color.valueOf("695c86")){{
        hardness = 1+7;
        cost = 0.7f;
    }};
    public static Item lead8 = new Item("lead8",Color.valueOf("645781")){{
        hardness = 1+8;
        cost = 0.7f;
    }};
    public static Item lead9 = new Item("lead9",Color.valueOf("5f527c")){{
        hardness = 1+9;
        cost = 0.7f;
    }};

    //sand
    public static Item sand1 = new Item("sand1",Color.valueOf("f2c69f")){{
        lowPriority = true;
        buildable = false;}};
    public static Item sand2 = new Item("sand2",Color.valueOf("edc19a")){{
        lowPriority = true;
        buildable = false;}};
    public static Item sand3 = new Item("sand3",Color.valueOf("e8bc95")){{
        lowPriority = true;
        buildable = false;}};
    public static Item sand4 = new Item("sand4",Color.valueOf("e3b790")){{
        lowPriority = true;
        buildable = false;}};
    public static Item sand5 = new Item("sand5",Color.valueOf("deb28b")){{
        lowPriority = true;
        buildable = false;}};
    public static Item sand6 = new Item("sand6",Color.valueOf("d9ad86")){{
        lowPriority = true;
        buildable = false;}};
    public static Item sand7 = new Item("sand7",Color.valueOf("d4a881")){{
        lowPriority = true;
        buildable = false;}};
    public static Item sand8 = new Item("sand8",Color.valueOf("cfa37c")){{
        lowPriority = true;
        buildable = false;}};
    public static Item sand9 = new Item("sand9",Color.valueOf("ca9e77")){{
        lowPriority = true;
        buildable = false;}};

    //titanium
    public static Item titanium1 = new Item("titanium1",Color.valueOf("889cde")){{
        hardness = 3+1;
        cost = 1f;
    }};
    public static Item titanium2 = new Item("titanium2",Color.valueOf("8397d9")){{
        hardness = 3+2;
        cost = 1f;
    }};
    public static Item titanium3 = new Item("titanium3",Color.valueOf("7e92d4")){{
        hardness = 3+3;
        cost = 1f;
    }};
    public static Item titanium4 = new Item("titanium4",Color.valueOf("798dcf")){{
        hardness = 3+4;
        cost = 1f;
    }};
    public static Item titanium5 = new Item("titanium5",Color.valueOf("7488ca")){{
        hardness = 3+5;
        cost = 1f;
    }};
    public static Item titanium6 = new Item("titanium6",Color.valueOf("6f83c5")){{
        hardness = 3+6;
        cost = 1f;
    }};
    public static Item titanium7 = new Item("titanium7",Color.valueOf("6a7ec0")){{
        hardness = 3+7;
        cost = 1f;
    }};
    public static Item titanium8 = new Item("titanium8",Color.valueOf("6579bb")){{
        hardness = 3+8;
        cost = 1f;
    }};
    public static Item titanium9 = new Item("titanium9",Color.valueOf("6074b6")){{
        hardness = 3+9;
        cost = 1f;
    }};


    //metaglass
    public static Item metaglass1 = new Item("metaglass1",Color.valueOf("e6e9f0")){{
        cost = 1.5f;
    }};
    public static Item metaglass2 = new Item("metaglass2",Color.valueOf("e1e4eb")){{
        cost = 1.5f;
    }};
    public static Item metaglass3 = new Item("metaglass3",Color.valueOf("dcdfe6")){{
        cost = 1.5f;
    }};
    public static Item metaglass4 = new Item("metaglass4",Color.valueOf("d7dae1")){{
        cost = 1.5f;
    }};
    public static Item metaglass5 = new Item("metaglass5",Color.valueOf("d2d5dc")){{
        cost = 1.5f;
    }};
    public static Item metaglass6 = new Item("metaglass6",Color.valueOf("cdd0d7")){{
        cost = 1.5f;
    }};
    public static Item metaglass7 = new Item("metaglass7",Color.valueOf("c8cbd2")){{
        cost = 1.5f;
    }};
    public static Item metaglass8 = new Item("metaglass8",Color.valueOf("c3c6cd")){{
        cost = 1.5f;
    }};
    public static Item metaglass9 = new Item("metaglass9",Color.valueOf("bec1c8")){{
        cost = 1.5f;
    }};


    //scrap
    public static Item scrap1 = new Item("scrap1",Color.valueOf("727272")){};
    public static Item scrap2 = new Item("scrap2",Color.valueOf("6d6d6d")){};
    public static Item scrap3 = new Item("scrap3",Color.valueOf("686868")){};
    public static Item scrap4 = new Item("scrap4",Color.valueOf("636363")){};
    public static Item scrap5 = new Item("scrap5",Color.valueOf("5e5e5e")){};
    public static Item scrap6 = new Item("scrap6",Color.valueOf("595959")){};
    public static Item scrap7 = new Item("scrap7",Color.valueOf("545454")){};
    public static Item scrap8 = new Item("scrap8",Color.valueOf("4f4f4f")){};
    public static Item scrap9 = new Item("scrap9",Color.valueOf("4a4a4a")){};

    static int attributeBase = 10;

    //coal
    public static Item coal1 = new Item("coal1",Color.valueOf("2c2c2c")){{
        explosiveness = (float) (0.2f*Math.pow(attributeBase,1));
        flammability = (float) (1f*Math.pow(attributeBase,1));
        hardness = 2+1;
        buildable = false;
    }};
    public static Item coal2 = new Item("coal2",Color.valueOf("313131")){{
        explosiveness = (float) (0.2f*Math.pow(attributeBase,2));
        flammability = (float) (1f*Math.pow(attributeBase,2));
        hardness = 2+2;
        buildable = false;
    }};
    public static Item coal3 = new Item("coal3",Color.valueOf("363636")){{
        explosiveness = (float) (0.2f*Math.pow(attributeBase,3));
        flammability = (float) (1f*Math.pow(attributeBase,3));
        hardness = 2+3;
        buildable = false;
    }};
    public static Item coal4 = new Item("coal4",Color.valueOf("3b3b3b")){{
        explosiveness = (float) (0.2f*Math.pow(attributeBase,4));
        flammability = (float) (1f*Math.pow(attributeBase,4));
        hardness = 2+4;
        buildable = false;
    }};
    public static Item coal5 = new Item("coal5",Color.valueOf("404040")){{
        explosiveness = (float) (0.2f*Math.pow(attributeBase,5));
        flammability = (float) (1f*Math.pow(attributeBase,5));
        hardness = 2+5;
        buildable = false;
    }};
    public static Item coal6 = new Item("coal6",Color.valueOf("454545")){{
        explosiveness = (float) (0.2f*Math.pow(attributeBase,6));
        flammability = (float) (1f*Math.pow(attributeBase,6));
        hardness = 2+6;
        buildable = false;
    }};
    public static Item coal7 = new Item("coal7",Color.valueOf("4a4a4a")){{
        explosiveness = (float) (0.2f*Math.pow(attributeBase,7));
        flammability = (float) (1f*Math.pow(attributeBase,7));
        hardness = 2+7;
        buildable = false;
    }};
    public static Item coal8 = new Item("coal8",Color.valueOf("4f4f4f")){{
        explosiveness = (float) (0.2f*Math.pow(attributeBase,8));
        flammability = (float) (1f*Math.pow(attributeBase,8));
        hardness = 2+8;
        buildable = false;
    }};
    public static Item coal9 = new Item("coal9",Color.valueOf("545454")){{
        explosiveness = (float) (0.2f*Math.pow(attributeBase,9));
        flammability = (float) (1f*Math.pow(attributeBase,9));
        hardness = 2+9;
        buildable = false;
    }};

    //thorium
    public static Item thorium1 = new Item("thorium1",Color.valueOf("f49ec2")){{
        explosiveness = (float) (0.2f*Math.pow(attributeBase,1));
        hardness = 4+1;
        radioactivity = (float) (1f*Math.pow(attributeBase,1));
        cost = 1.1f;
        healthScaling = 0.2f;
    }};
    public static Item thorium2 = new Item("thorium2",Color.valueOf("ef99bd")){{
        explosiveness = (float) (0.2f*Math.pow(attributeBase,2));
        hardness = 4+2;
        radioactivity = (float) (1f*Math.pow(attributeBase,2));
        cost = 1.1f;
        healthScaling = 0.2f;
    }};
    public static Item thorium3 = new Item("thorium3",Color.valueOf("ea94b8")){{
        explosiveness = (float) (0.2f*Math.pow(attributeBase,3));
        hardness = 4+3;
        radioactivity = (float) (1f*Math.pow(attributeBase,3));
        cost = 1.1f;
        healthScaling = 0.2f;
    }};
    public static Item thorium4 = new Item("thorium4",Color.valueOf("e58fb3")){{
        explosiveness = (float) (0.2f*Math.pow(attributeBase,4));
        hardness = 4+4;
        radioactivity = (float) (1f*Math.pow(attributeBase,4));
        cost = 1.1f;
        healthScaling = 0.2f;
    }};
    public static Item thorium5 = new Item("thorium5",Color.valueOf("e08aae")){{
        explosiveness = (float) (0.2f*Math.pow(attributeBase,5));
        hardness = 4+5;
        radioactivity = (float) (1f*Math.pow(attributeBase,5));
        cost = 1.1f;
        healthScaling = 0.2f;
    }};
    public static Item thorium6 = new Item("thorium6",Color.valueOf("db85a9")){{
        explosiveness = (float) (0.2f*Math.pow(attributeBase,6));
        hardness = 4+6;
        radioactivity = (float) (1f*Math.pow(attributeBase,6));
        cost = 1.1f;
        healthScaling = 0.2f;
    }};
    public static Item thorium7 = new Item("thorium7",Color.valueOf("d680a4")){{
        explosiveness = (float) (0.2f*Math.pow(attributeBase,7));
        hardness = 4+7;
        radioactivity = (float) (1f*Math.pow(attributeBase,7));
        cost = 1.1f;
        healthScaling = 0.2f;
    }};
    public static Item thorium8 = new Item("thorium8",Color.valueOf("d17b9f")){{
        explosiveness = (float) (0.2f*Math.pow(attributeBase,8));
        hardness = 4+8;
        radioactivity = (float) (1f*Math.pow(attributeBase,8));
        cost = 1.1f;
        healthScaling = 0.2f;
    }};
    public static Item thorium9 = new Item("thorium9",Color.valueOf("cc769a")){{
        explosiveness = (float) (0.2f*Math.pow(attributeBase,9));
        hardness = 4+9;
        radioactivity = (float) (1f*Math.pow(attributeBase,9));
        cost = 1.1f;
        healthScaling = 0.2f;
    }};

    //surgeAlloy
    public static Item surgeAlloy1 = new Item("surgeAlloy1",Color.valueOf("eee474")){{
        cost = 1.2f;
        charge = (float) (0.75f*Math.pow(attributeBase,1));
        healthScaling = 0.25f;
    }};
    public static Item surgeAlloy2 = new Item("surgeAlloy2",Color.valueOf("e9df6f")){{
        cost = 1.2f;
        charge = (float) (0.75f*Math.pow(attributeBase,2));
        healthScaling = 0.25f;
    }};
    public static Item surgeAlloy3 = new Item("surgeAlloy3",Color.valueOf("e4da6a")){{
        cost = 1.2f;
        charge = (float) (0.75f*Math.pow(attributeBase,3));
        healthScaling = 0.25f;
    }};
    public static Item surgeAlloy4 = new Item("surgeAlloy4",Color.valueOf("dfd565")){{
        cost = 1.2f;
        charge = (float) (0.75f*Math.pow(attributeBase,4));
        healthScaling = 0.25f;
    }};
    public static Item surgeAlloy5 = new Item("surgeAlloy5",Color.valueOf("dad060")){{
        cost = 1.2f;
        charge = (float) (0.75f*Math.pow(attributeBase,5));
        healthScaling = 0.25f;
    }};
    public static Item surgeAlloy6 = new Item("surgeAlloy6",Color.valueOf("d5cb5b")){{
        cost = 1.2f;
        charge = (float) (0.75f*Math.pow(attributeBase,6));
        healthScaling = 0.25f;
    }};
    public static Item surgeAlloy7 = new Item("surgeAlloy7",Color.valueOf("d0c656")){{
        cost = 1.2f;
        charge = (float) (0.75f*Math.pow(attributeBase,7));
        healthScaling = 0.25f;
    }};
    public static Item surgeAlloy8 = new Item("surgeAlloy8",Color.valueOf("cbc151")){{
        cost = 1.2f;
        charge = (float) (0.75f*Math.pow(attributeBase,8));
        healthScaling = 0.25f;
    }};
    public static Item surgeAlloy9 = new Item("surgeAlloy9",Color.valueOf("c6bc4c")){{
        cost = 1.2f;
        charge = (float) (0.75f*Math.pow(attributeBase,9));
        healthScaling = 0.25f;
    }};

    //phaseFabric
    public static Item phaseFabric1 = new Item("phaseFabric1",Color.valueOf("efb569")){{
        cost = 1.3f;
        radioactivity = (float) (0.6f*Math.pow(attributeBase,1));
        healthScaling = 0.25f;
    }};
    public static Item phaseFabric2 = new Item("phaseFabric2",Color.valueOf("eab064")){{
        cost = 1.3f;
        radioactivity = (float) (0.6f*Math.pow(attributeBase,2));
        healthScaling = 0.25f;
    }};
    public static Item phaseFabric3 = new Item("phaseFabric3",Color.valueOf("e5ab5f")){{
        cost = 1.3f;
        radioactivity = (float) (0.6f*Math.pow(attributeBase,3));
        healthScaling = 0.25f;
    }};
    public static Item phaseFabric4 = new Item("phaseFabric4",Color.valueOf("e0a65a")){{
        cost = 1.3f;
        radioactivity = (float) (0.6f*Math.pow(attributeBase,4));
        healthScaling = 0.25f;
    }};
    public static Item phaseFabric5 = new Item("phaseFabric5",Color.valueOf("dba155")){{
        cost = 1.3f;
        radioactivity = (float) (0.6f*Math.pow(attributeBase,5));
        healthScaling = 0.25f;
    }};
    public static Item phaseFabric6 = new Item("phaseFabric6",Color.valueOf("d69c50")){{
        cost = 1.3f;
        radioactivity = (float) (0.6f*Math.pow(attributeBase,6));
        healthScaling = 0.25f;
    }};
    public static Item phaseFabric7 = new Item("phaseFabric7",Color.valueOf("d1974b")){{
        cost = 1.3f;
        radioactivity = (float) (0.6f*Math.pow(attributeBase,7));
        healthScaling = 0.25f;
    }};
    public static Item phaseFabric8 = new Item("phaseFabric8",Color.valueOf("cc9246")){{
        cost = 1.3f;
        radioactivity = (float) (0.6f*Math.pow(attributeBase,8));
        healthScaling = 0.25f;
    }};
    public static Item phaseFabric9 = new Item("phaseFabric9",Color.valueOf("c78d41")){{
        cost = 1.3f;
        radioactivity = (float) (0.6f*Math.pow(attributeBase,9));
        healthScaling = 0.25f;
    }};

    //graphite
    public static Item graphite1 = new Item("graphite1",Color.valueOf("adc1cd")){{
        cost = 1f;
    }};
    public static Item graphite2 = new Item("graphite2",Color.valueOf("a8bcc8")){{
        cost = 1f;
    }};
    public static Item graphite3 = new Item("graphite3",Color.valueOf("a3b7c3")){{
        cost = 1f;
    }};
    public static Item graphite4 = new Item("graphite4",Color.valueOf("9eb2be")){{
        cost = 1f;
    }};
    public static Item graphite5 = new Item("graphite5",Color.valueOf("99adb9")){{
        cost = 1f;
    }};
    public static Item graphite6 = new Item("graphite6",Color.valueOf("94a8b4")){{
        cost = 1f;
    }};
    public static Item graphite7 = new Item("graphite7",Color.valueOf("8fa3af")){{
        cost = 1f;
    }};
    public static Item graphite8 = new Item("graphite8",Color.valueOf("8a9eaa")){{
        cost = 1f;
    }};
    public static Item graphite9 = new Item("graphite9",Color.valueOf("8599a5")){{
        cost = 1f;
    }};

    //silicon
    public static Item silicon1 = new Item("silicon1",Color.valueOf("585b61")){{
        cost = 0.8f;
    }};
    public static Item silicon2 = new Item("silicon2",Color.valueOf("5d6066")){{
        cost = 0.8f;
    }};
    public static Item silicon3 = new Item("silicon3",Color.valueOf("62656b")){{
        cost = 0.8f;
    }};
    public static Item silicon4 = new Item("silicon4",Color.valueOf("676a70")){{
        cost = 0.8f;
    }};
    public static Item silicon5 = new Item("silicon5",Color.valueOf("6c6f75")){{
        cost = 0.8f;
    }};
    public static Item silicon6 = new Item("silicon6",Color.valueOf("71747a")){{
        cost = 0.8f;
    }};
    public static Item silicon7 = new Item("silicon7",Color.valueOf("76797f")){{
        cost = 0.8f;
    }};
    public static Item silicon8 = new Item("silicon8",Color.valueOf("7b7e84")){{
        cost = 0.8f;
    }};
    public static Item silicon9 = new Item("silicon9",Color.valueOf("808389")){{
        cost = 0.8f;
    }};

    //pyratite
    public static Item pyratite1 = new Item("pyratite1",Color.valueOf("faa55a")){{
        flammability = (float) (1.4f*Math.pow(attributeBase,1));
        explosiveness = (float) (0.4f*Math.pow(attributeBase,1));
        buildable = false;
    }};
    public static Item pyratite2 = new Item("pyratite2",Color.valueOf("f5a055")){{
        flammability = (float) (1.4f*Math.pow(attributeBase,2));
        explosiveness = (float) (0.4f*Math.pow(attributeBase,2));
        buildable = false;
    }};
    public static Item pyratite3 = new Item("pyratite3",Color.valueOf("f09b50")){{
        flammability = (float) (1.4f*Math.pow(attributeBase,3));
        explosiveness = (float) (0.4f*Math.pow(attributeBase,3));
        buildable = false;
    }};
    public static Item pyratite4 = new Item("pyratite4",Color.valueOf("eb964b")){{
        flammability = (float) (1.4f*Math.pow(attributeBase,4));
        explosiveness = (float) (0.4f*Math.pow(attributeBase,4));
        buildable = false;
    }};
    public static Item pyratite5 = new Item("pyratite5",Color.valueOf("e69146")){{
        flammability = (float) (1.4f*Math.pow(attributeBase,5));
        explosiveness = (float) (0.4f*Math.pow(attributeBase,5));
        buildable = false;
    }};
    public static Item pyratite6 = new Item("pyratite6",Color.valueOf("e18c41")){{
        flammability = (float) (1.4f*Math.pow(attributeBase,6));
        explosiveness = (float) (0.4f*Math.pow(attributeBase,6));
        buildable = false;
    }};
    public static Item pyratite7 = new Item("pyratite7",Color.valueOf("dc873c")){{
        flammability = (float) (1.4f*Math.pow(attributeBase,7));
        explosiveness = (float) (0.4f*Math.pow(attributeBase,7));
        buildable = false;
    }};
    public static Item pyratite8 = new Item("pyratite8",Color.valueOf("d78237")){{
        flammability = (float) (1.4f*Math.pow(attributeBase,8));
        explosiveness = (float) (0.4f*Math.pow(attributeBase,8));
        buildable = false;
    }};
    public static Item pyratite9 = new Item("pyratite9",Color.valueOf("d27d32")){{
        flammability = (float) (1.4f*Math.pow(attributeBase,9));
        explosiveness = (float) (0.4f*Math.pow(attributeBase,9));
        buildable = false;
    }};

    //blastCompound
    public static Item blastCompound1 = new Item("blastCompound1",Color.valueOf("fa7459")){{
        flammability = (float) (0.4f*Math.pow(attributeBase,1));
        explosiveness = (float) (1.2f*Math.pow(attributeBase,1));
        buildable = false;
    }};
    public static Item blastCompound2 = new Item("blastCompound2",Color.valueOf("f56f54")){{
        flammability = (float) (0.4f*Math.pow(attributeBase,2));
        explosiveness = (float) (1.2f*Math.pow(attributeBase,2));
        buildable = false;
    }};
    public static Item blastCompound3 = new Item("blastCompound3",Color.valueOf("f06a4f")){{
        flammability = (float) (0.4f*Math.pow(attributeBase,3));
        explosiveness = (float) (1.2f*Math.pow(attributeBase,3));
        buildable = false;
    }};
    public static Item blastCompound4 = new Item("blastCompound4",Color.valueOf("eb654a")){{
        flammability = (float) (0.4f*Math.pow(attributeBase,4));
        explosiveness = (float) (1.2f*Math.pow(attributeBase,4));
        buildable = false;
    }};
    public static Item blastCompound5 = new Item("blastCompound5",Color.valueOf("e66045")){{
        flammability = (float) (0.4f*Math.pow(attributeBase,5));
        explosiveness = (float) (1.2f*Math.pow(attributeBase,5));
        buildable = false;
    }};
    public static Item blastCompound6 = new Item("blastCompound6",Color.valueOf("e15b40")){{
        flammability = (float) (0.4f*Math.pow(attributeBase,6));
        explosiveness = (float) (1.2f*Math.pow(attributeBase,6));
        buildable = false;
    }};
    public static Item blastCompound7 = new Item("blastCompound7",Color.valueOf("dc563b")){{
        flammability = (float) (0.4f*Math.pow(attributeBase,7));
        explosiveness = (float) (1.2f*Math.pow(attributeBase,7));
        buildable = false;
    }};
    public static Item blastCompound8 = new Item("blastCompound8",Color.valueOf("d75136")){{
        flammability = (float) (0.4f*Math.pow(attributeBase,8));
        explosiveness = (float) (1.2f*Math.pow(attributeBase,8));
        buildable = false;
    }};
    public static Item blastCompound9 = new Item("blastCompound9",Color.valueOf("d24c31")){{
        flammability = (float) (0.4f*Math.pow(attributeBase,9));
        explosiveness = (float) (1.2f*Math.pow(attributeBase,9));
        buildable = false;
    }};

    //sporePod
    public static Item sporePod1 = new Item("sporePod1",Color.valueOf("795cd3")){{
        flammability = (float) (1.15f*Math.pow(attributeBase,1));
        buildable = false;
    }};
    public static Item sporePod2 = new Item("sporePod2",Color.valueOf("7e61d8")){{
        flammability = (float) (1.15f*Math.pow(attributeBase,2));
        buildable = false;
    }};
    public static Item sporePod3 = new Item("sporePod3",Color.valueOf("8366dd")){{
        flammability = (float) (1.15f*Math.pow(attributeBase,3));
        buildable = false;
    }};
    public static Item sporePod4 = new Item("sporePod4",Color.valueOf("886be2")){{
        flammability = (float) (1.15f*Math.pow(attributeBase,4));
        buildable = false;
    }};
    public static Item sporePod5 = new Item("sporePod5",Color.valueOf("8d70e7")){{
        flammability = (float) (1.15f*Math.pow(attributeBase,5));
        buildable = false;
    }};
    public static Item sporePod6 = new Item("sporePod6",Color.valueOf("9275ec")){{
        flammability = (float) (1.15f*Math.pow(attributeBase,6));
        buildable = false;
    }};
    public static Item sporePod7 = new Item("sporePod7",Color.valueOf("977af1")){{
        flammability = (float) (1.15f*Math.pow(attributeBase,7));
        buildable = false;
    }};
    public static Item sporePod8 = new Item("sporePod8",Color.valueOf("9c7ff6")){{
        flammability = (float) (1.15f*Math.pow(attributeBase,8));
        buildable = false;
    }};
    public static Item sporePod9 = new Item("sporePod9",Color.valueOf("a184fb")){{
        flammability = (float) (1.15f*Math.pow(attributeBase,9));
        buildable = false;
    }};

    //plastanium
    public static Item plastanium1 = new Item("plastanium1",Color.valueOf("c6d47a")){{
        flammability = (float) (0.1f*Math.pow(attributeBase,1));
        explosiveness = (float) (0.2f*Math.pow(attributeBase,1));
        cost = 1.3f;
        healthScaling = 0.1f;
    }};
    public static Item plastanium2 = new Item("plastanium2",Color.valueOf("c1cf75")){{
        flammability = (float) (0.1f*Math.pow(attributeBase,2));
        explosiveness = (float) (0.2f*Math.pow(attributeBase,2));
        cost = 1.3f;
        healthScaling = 0.1f;
    }};
    public static Item plastanium3 = new Item("plastanium3",Color.valueOf("bcca70")){{
        flammability = (float) (0.1f*Math.pow(attributeBase,3));
        explosiveness = (float) (0.2f*Math.pow(attributeBase,3));
        cost = 1.3f;
        healthScaling = 0.1f;
    }};
    public static Item plastanium4 = new Item("plastanium4",Color.valueOf("b7c56b")){{
        flammability = (float) (0.1f*Math.pow(attributeBase,4));
        explosiveness = (float) (0.2f*Math.pow(attributeBase,4));
        cost = 1.3f;
        healthScaling = 0.1f;
    }};
    public static Item plastanium5 = new Item("plastanium5",Color.valueOf("b2c066")){{
        flammability = (float) (0.1f*Math.pow(attributeBase,5));
        explosiveness = (float) (0.2f*Math.pow(attributeBase,5));
        cost = 1.3f;
        healthScaling = 0.1f;
    }};
    public static Item plastanium6 = new Item("plastanium6",Color.valueOf("adbb61")){{
        flammability = (float) (0.1f*Math.pow(attributeBase,6));
        explosiveness = (float) (0.2f*Math.pow(attributeBase,6));
        cost = 1.3f;
        healthScaling = 0.1f;
    }};
    public static Item plastanium7 = new Item("plastanium7",Color.valueOf("a8b65c")){{
        flammability = (float) (0.1f*Math.pow(attributeBase,7));
        explosiveness = (float) (0.2f*Math.pow(attributeBase,7));
        cost = 1.3f;
        healthScaling = 0.1f;
    }};
    public static Item plastanium8 = new Item("plastanium8",Color.valueOf("a3b157")){{
        flammability = (float) (0.1f*Math.pow(attributeBase,8));
        explosiveness = (float) (0.2f*Math.pow(attributeBase,8));
        cost = 1.3f;
        healthScaling = 0.1f;
    }};
    public static Item plastanium9 = new Item("plastanium9",Color.valueOf("9eac52")){{
        flammability = (float) (0.1f*Math.pow(attributeBase,9));
        explosiveness = (float) (0.2f*Math.pow(attributeBase,9));
        cost = 1.3f;
        healthScaling = 0.1f;
    }};



 */







}
