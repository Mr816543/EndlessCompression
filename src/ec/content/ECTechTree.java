package ec.content;

import ec.Blocks.ECWalls;
import mindustry.Vars;
import mindustry.content.Planets;

import static mindustry.content.TechTree.*;
import static mindustry.content.TechTree.node;

public class ECTechTree {
    public static void load(){
        ECPlanets.compress.techTree = nodeRoot("compress", Vars.content.block("ec-copperCompressor"), () -> {

            //copper
            nodeProduce(Vars.content.item("ec-copper1"), () -> {
                nodeProduce(Vars.content.item("ec-copper2"), () -> {
                    nodeProduce(Vars.content.item("ec-copper3"), () -> {
                        nodeProduce(Vars.content.item("ec-copper4"), () -> {
                            nodeProduce(Vars.content.item("ec-copper5"), () -> {
                                nodeProduce(Vars.content.item("ec-copper6"), () -> {
                                    nodeProduce(Vars.content.item("ec-copper7"), () -> {
                                        nodeProduce(Vars.content.item("ec-copper8"), () -> {
                                            nodeProduce(Vars.content.item("ec-copper9"), () -> {});
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
            node(Vars.content.block("ec-copperWall1"), () -> {
                node(Vars.content.block("ec-copperWall2"), () -> {
                    node(Vars.content.block("ec-copperWall3"), () -> {
                        node(Vars.content.block("ec-copperWall4"), () -> {
                            node(Vars.content.block("ec-copperWall5"), () -> {
                                node(Vars.content.block("ec-copperWall6"), () -> {
                                    node(Vars.content.block("ec-copperWall7"), () -> {
                                        node(Vars.content.block("ec-copperWall8"), () -> {
                                            node(Vars.content.block("ec-copperWall9"), () -> {});
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //lead
            node(Vars.content.block("ec-leadCompressor"), () -> {
                nodeProduce(Vars.content.item("ec-lead1"), () -> {
                    nodeProduce(Vars.content.item("ec-lead2"), () -> {
                        nodeProduce(Vars.content.item("ec-lead3"), () -> {
                            nodeProduce(Vars.content.item("ec-lead4"), () -> {
                                nodeProduce(Vars.content.item("ec-lead5"), () -> {
                                    nodeProduce(Vars.content.item("ec-lead6"), () -> {
                                        nodeProduce(Vars.content.item("ec-lead7"), () -> {
                                            nodeProduce(Vars.content.item("ec-lead8"), () -> {
                                                nodeProduce(Vars.content.item("ec-lead9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //sand
            node(Vars.content.block("ec-sandCompressor"), () -> {
                nodeProduce(Vars.content.item("ec-sand1"), () -> {
                    nodeProduce(Vars.content.item("ec-sand2"), () -> {
                        nodeProduce(Vars.content.item("ec-sand3"), () -> {
                            nodeProduce(Vars.content.item("ec-sand4"), () -> {
                                nodeProduce(Vars.content.item("ec-sand5"), () -> {
                                    nodeProduce(Vars.content.item("ec-sand6"), () -> {
                                        nodeProduce(Vars.content.item("ec-sand7"), () -> {
                                            nodeProduce(Vars.content.item("ec-sand8"), () -> {
                                                nodeProduce(Vars.content.item("ec-sand9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //titanium
            node(Vars.content.block("ec-titaniumCompressor"), () -> {
                nodeProduce(Vars.content.item("ec-titanium1"), () -> {
                    nodeProduce(Vars.content.item("ec-titanium2"), () -> {
                        nodeProduce(Vars.content.item("ec-titanium3"), () -> {
                            nodeProduce(Vars.content.item("ec-titanium4"), () -> {
                                nodeProduce(Vars.content.item("ec-titanium5"), () -> {
                                    nodeProduce(Vars.content.item("ec-titanium6"), () -> {
                                        nodeProduce(Vars.content.item("ec-titanium7"), () -> {
                                            nodeProduce(Vars.content.item("ec-titanium8"), () -> {
                                                nodeProduce(Vars.content.item("ec-titanium9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
                node(Vars.content.block("ec-titaniumWall1"), () -> {
                    node(Vars.content.block("ec-titaniumWall2"), () -> {
                        node(Vars.content.block("ec-titaniumWall3"), () -> {
                            node(Vars.content.block("ec-titaniumWall4"), () -> {
                                node(Vars.content.block("ec-titaniumWall5"), () -> {
                                    node(Vars.content.block("ec-titaniumWall6"), () -> {
                                        node(Vars.content.block("ec-titaniumWall7"), () -> {
                                            node(Vars.content.block("ec-titaniumWall8"), () -> {
                                                node(Vars.content.block("ec-titaniumWall9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
                node(Vars.content.block("ec-door1"), () -> {
                    node(Vars.content.block("ec-door2"), () -> {
                        node(Vars.content.block("ec-door3"), () -> {
                            node(Vars.content.block("ec-door4"), () -> {
                                node(Vars.content.block("ec-door5"), () -> {
                                    node(Vars.content.block("ec-door6"), () -> {
                                        node(Vars.content.block("ec-door7"), () -> {
                                            node(Vars.content.block("ec-door8"), () -> {
                                                node(Vars.content.block("ec-door9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //metaglass
            node(Vars.content.block("ec-metaglassCompressor"), () -> {
                nodeProduce(Vars.content.item("ec-metaglass1"), () -> {
                    nodeProduce(Vars.content.item("ec-metaglass2"), () -> {
                        nodeProduce(Vars.content.item("ec-metaglass3"), () -> {
                            nodeProduce(Vars.content.item("ec-metaglass4"), () -> {
                                nodeProduce(Vars.content.item("ec-metaglass5"), () -> {
                                    nodeProduce(Vars.content.item("ec-metaglass6"), () -> {
                                        nodeProduce(Vars.content.item("ec-metaglass7"), () -> {
                                            nodeProduce(Vars.content.item("ec-metaglass8"), () -> {
                                                nodeProduce(Vars.content.item("ec-metaglass9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //scrap
            node(Vars.content.block("ec-scrapCompressor"), () -> {
                nodeProduce(Vars.content.item("ec-scrap1"), () -> {
                    nodeProduce(Vars.content.item("ec-scrap2"), () -> {
                        nodeProduce(Vars.content.item("ec-scrap3"), () -> {
                            nodeProduce(Vars.content.item("ec-scrap4"), () -> {
                                nodeProduce(Vars.content.item("ec-scrap5"), () -> {
                                    nodeProduce(Vars.content.item("ec-scrap6"), () -> {
                                        nodeProduce(Vars.content.item("ec-scrap7"), () -> {
                                            nodeProduce(Vars.content.item("ec-scrap8"), () -> {
                                                nodeProduce(Vars.content.item("ec-scrap9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //coal
            node(Vars.content.block("ec-coalCompressor"), () -> {
                nodeProduce(Vars.content.item("ec-coal1"), () -> {
                    nodeProduce(Vars.content.item("ec-coal2"), () -> {
                        nodeProduce(Vars.content.item("ec-coal3"), () -> {
                            nodeProduce(Vars.content.item("ec-coal4"), () -> {
                                nodeProduce(Vars.content.item("ec-coal5"), () -> {
                                    nodeProduce(Vars.content.item("ec-coal6"), () -> {
                                        nodeProduce(Vars.content.item("ec-coal7"), () -> {
                                            nodeProduce(Vars.content.item("ec-coal8"), () -> {
                                                nodeProduce(Vars.content.item("ec-coal9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //thorium
            node(Vars.content.block("ec-thoriumCompressor"), () -> {
                nodeProduce(Vars.content.item("ec-thorium1"), () -> {
                    nodeProduce(Vars.content.item("ec-thorium2"), () -> {
                        nodeProduce(Vars.content.item("ec-thorium3"), () -> {
                            nodeProduce(Vars.content.item("ec-thorium4"), () -> {
                                nodeProduce(Vars.content.item("ec-thorium5"), () -> {
                                    nodeProduce(Vars.content.item("ec-thorium6"), () -> {
                                        nodeProduce(Vars.content.item("ec-thorium7"), () -> {
                                            nodeProduce(Vars.content.item("ec-thorium8"), () -> {
                                                nodeProduce(Vars.content.item("ec-thorium9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
                node(Vars.content.block("ec-thoriumWall1"), () -> {
                    node(Vars.content.block("ec-thoriumWall2"), () -> {
                        node(Vars.content.block("ec-thoriumWall3"), () -> {
                            node(Vars.content.block("ec-thoriumWall4"), () -> {
                                node(Vars.content.block("ec-thoriumWall5"), () -> {
                                    node(Vars.content.block("ec-thoriumWall6"), () -> {
                                        node(Vars.content.block("ec-thoriumWall7"), () -> {
                                            node(Vars.content.block("ec-thoriumWall8"), () -> {
                                                node(Vars.content.block("ec-thoriumWall9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });

            });

            //surgeAlloy
            node(Vars.content.block("ec-surgeAlloyCompressor"), () -> {
                nodeProduce(Vars.content.item("ec-surgeAlloy1"), () -> {
                    nodeProduce(Vars.content.item("ec-surgeAlloy2"), () -> {
                        nodeProduce(Vars.content.item("ec-surgeAlloy3"), () -> {
                            nodeProduce(Vars.content.item("ec-surgeAlloy4"), () -> {
                                nodeProduce(Vars.content.item("ec-surgeAlloy5"), () -> {
                                    nodeProduce(Vars.content.item("ec-surgeAlloy6"), () -> {
                                        nodeProduce(Vars.content.item("ec-surgeAlloy7"), () -> {
                                            nodeProduce(Vars.content.item("ec-surgeAlloy8"), () -> {
                                                nodeProduce(Vars.content.item("ec-surgeAlloy9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
                node(Vars.content.block("ec-surgeWall1"), () -> {
                    node(Vars.content.block("ec-surgeWall2"), () -> {
                        node(Vars.content.block("ec-surgeWall3"), () -> {
                            node(Vars.content.block("ec-surgeWall4"), () -> {
                                node(Vars.content.block("ec-surgeWall5"), () -> {
                                    node(Vars.content.block("ec-surgeWall6"), () -> {
                                        node(Vars.content.block("ec-surgeWall7"), () -> {
                                            node(Vars.content.block("ec-surgeWall8"), () -> {
                                                node(Vars.content.block("ec-surgeWall9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //phaseFabric
            node(Vars.content.block("ec-phaseFabricCompressor"), () -> {
                nodeProduce(Vars.content.item("ec-phaseFabric1"), () -> {
                    nodeProduce(Vars.content.item("ec-phaseFabric2"), () -> {
                        nodeProduce(Vars.content.item("ec-phaseFabric3"), () -> {
                            nodeProduce(Vars.content.item("ec-phaseFabric4"), () -> {
                                nodeProduce(Vars.content.item("ec-phaseFabric5"), () -> {
                                    nodeProduce(Vars.content.item("ec-phaseFabric6"), () -> {
                                        nodeProduce(Vars.content.item("ec-phaseFabric7"), () -> {
                                            nodeProduce(Vars.content.item("ec-phaseFabric8"), () -> {
                                                nodeProduce(Vars.content.item("ec-phaseFabric9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
                node(Vars.content.block("ec-phaseWall1"), () -> {
                    node(Vars.content.block("ec-phaseWall2"), () -> {
                        node(Vars.content.block("ec-phaseWall3"), () -> {
                            node(Vars.content.block("ec-phaseWall4"), () -> {
                                node(Vars.content.block("ec-phaseWall5"), () -> {
                                    node(Vars.content.block("ec-phaseWall6"), () -> {
                                        node(Vars.content.block("ec-phaseWall7"), () -> {
                                            node(Vars.content.block("ec-phaseWall8"), () -> {
                                                node(Vars.content.block("ec-phaseWall9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //graphite
            node(Vars.content.block("ec-graphiteCompressor"), () -> {
                nodeProduce(Vars.content.item("ec-graphite1"), () -> {
                    nodeProduce(Vars.content.item("ec-graphite2"), () -> {
                        nodeProduce(Vars.content.item("ec-graphite3"), () -> {
                            nodeProduce(Vars.content.item("ec-graphite4"), () -> {
                                nodeProduce(Vars.content.item("ec-graphite5"), () -> {
                                    nodeProduce(Vars.content.item("ec-graphite6"), () -> {
                                        nodeProduce(Vars.content.item("ec-graphite7"), () -> {
                                            nodeProduce(Vars.content.item("ec-graphite8"), () -> {
                                                nodeProduce(Vars.content.item("ec-graphite9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //silicon
            node(Vars.content.block("ec-siliconCompressor"), () -> {
                nodeProduce(Vars.content.item("ec-silicon1"), () -> {
                    nodeProduce(Vars.content.item("ec-silicon2"), () -> {
                        nodeProduce(Vars.content.item("ec-silicon3"), () -> {
                            nodeProduce(Vars.content.item("ec-silicon4"), () -> {
                                nodeProduce(Vars.content.item("ec-silicon5"), () -> {
                                    nodeProduce(Vars.content.item("ec-silicon6"), () -> {
                                        nodeProduce(Vars.content.item("ec-silicon7"), () -> {
                                            nodeProduce(Vars.content.item("ec-silicon8"), () -> {
                                                nodeProduce(Vars.content.item("ec-silicon9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //pyratite
            node(Vars.content.block("ec-pyratiteCompressor"), () -> {
                nodeProduce(Vars.content.item("ec-pyratite1"), () -> {
                    nodeProduce(Vars.content.item("ec-pyratite2"), () -> {
                        nodeProduce(Vars.content.item("ec-pyratite3"), () -> {
                            nodeProduce(Vars.content.item("ec-pyratite4"), () -> {
                                nodeProduce(Vars.content.item("ec-pyratite5"), () -> {
                                    nodeProduce(Vars.content.item("ec-pyratite6"), () -> {
                                        nodeProduce(Vars.content.item("ec-pyratite7"), () -> {
                                            nodeProduce(Vars.content.item("ec-pyratite8"), () -> {
                                                nodeProduce(Vars.content.item("ec-pyratite9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //blastCompound
            node(Vars.content.block("ec-blastCompoundCompressor"), () -> {
                nodeProduce(Vars.content.item("ec-blastCompound1"), () -> {
                    nodeProduce(Vars.content.item("ec-blastCompound2"), () -> {
                        nodeProduce(Vars.content.item("ec-blastCompound3"), () -> {
                            nodeProduce(Vars.content.item("ec-blastCompound4"), () -> {
                                nodeProduce(Vars.content.item("ec-blastCompound5"), () -> {
                                    nodeProduce(Vars.content.item("ec-blastCompound6"), () -> {
                                        nodeProduce(Vars.content.item("ec-blastCompound7"), () -> {
                                            nodeProduce(Vars.content.item("ec-blastCompound8"), () -> {
                                                nodeProduce(Vars.content.item("ec-blastCompound9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //sporePod
            node(Vars.content.block("ec-sporePodCompressor"), () -> {
                nodeProduce(Vars.content.item("ec-sporePod1"), () -> {
                    nodeProduce(Vars.content.item("ec-sporePod2"), () -> {
                        nodeProduce(Vars.content.item("ec-sporePod3"), () -> {
                            nodeProduce(Vars.content.item("ec-sporePod4"), () -> {
                                nodeProduce(Vars.content.item("ec-sporePod5"), () -> {
                                    nodeProduce(Vars.content.item("ec-sporePod6"), () -> {
                                        nodeProduce(Vars.content.item("ec-sporePod7"), () -> {
                                            nodeProduce(Vars.content.item("ec-sporePod8"), () -> {
                                                nodeProduce(Vars.content.item("ec-sporePod9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //plastanium
            node(Vars.content.block("ec-plastaniumCompressor"), () -> {
                nodeProduce(Vars.content.item("ec-plastanium1"), () -> {
                    nodeProduce(Vars.content.item("ec-plastanium2"), () -> {
                        nodeProduce(Vars.content.item("ec-plastanium3"), () -> {
                            nodeProduce(Vars.content.item("ec-plastanium4"), () -> {
                                nodeProduce(Vars.content.item("ec-plastanium5"), () -> {
                                    nodeProduce(Vars.content.item("ec-plastanium6"), () -> {
                                        nodeProduce(Vars.content.item("ec-plastanium7"), () -> {
                                            nodeProduce(Vars.content.item("ec-plastanium8"), () -> {
                                                nodeProduce(Vars.content.item("ec-plastanium9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
                node(Vars.content.block("ec-plastaniumWall1"), () -> {
                    node(Vars.content.block("ec-plastaniumWall2"), () -> {
                        node(Vars.content.block("ec-plastaniumWall3"), () -> {
                            node(Vars.content.block("ec-plastaniumWall4"), () -> {
                                node(Vars.content.block("ec-plastaniumWall5"), () -> {
                                    node(Vars.content.block("ec-plastaniumWall6"), () -> {
                                        node(Vars.content.block("ec-plastaniumWall7"), () -> {
                                            node(Vars.content.block("ec-plastaniumWall8"), () -> {
                                                node(Vars.content.block("ec-plastaniumWall9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //water
            node(Vars.content.block("ec-waterCompressor"), () -> {
                nodeProduce(Vars.content.liquid("ec-water1"), () -> {
                    nodeProduce(Vars.content.liquid("ec-water2"), () -> {
                        nodeProduce(Vars.content.liquid("ec-water3"), () -> {
                            nodeProduce(Vars.content.liquid("ec-water4"), () -> {
                                nodeProduce(Vars.content.liquid("ec-water5"), () -> {
                                    nodeProduce(Vars.content.liquid("ec-water6"), () -> {
                                        nodeProduce(Vars.content.liquid("ec-water7"), () -> {
                                            nodeProduce(Vars.content.liquid("ec-water8"), () -> {
                                                nodeProduce(Vars.content.liquid("ec-water9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
            //slag
            node(Vars.content.block("ec-slagCompressor"), () -> {
                nodeProduce(Vars.content.liquid("ec-slag1"), () -> {
                    nodeProduce(Vars.content.liquid("ec-slag2"), () -> {
                        nodeProduce(Vars.content.liquid("ec-slag3"), () -> {
                            nodeProduce(Vars.content.liquid("ec-slag4"), () -> {
                                nodeProduce(Vars.content.liquid("ec-slag5"), () -> {
                                    nodeProduce(Vars.content.liquid("ec-slag6"), () -> {
                                        nodeProduce(Vars.content.liquid("ec-slag7"), () -> {
                                            nodeProduce(Vars.content.liquid("ec-slag8"), () -> {
                                                nodeProduce(Vars.content.liquid("ec-slag9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
            //oil
            node(Vars.content.block("ec-oilCompressor"), () -> {
                nodeProduce(Vars.content.liquid("ec-oil1"), () -> {
                    nodeProduce(Vars.content.liquid("ec-oil2"), () -> {
                        nodeProduce(Vars.content.liquid("ec-oil3"), () -> {
                            nodeProduce(Vars.content.liquid("ec-oil4"), () -> {
                                nodeProduce(Vars.content.liquid("ec-oil5"), () -> {
                                    nodeProduce(Vars.content.liquid("ec-oil6"), () -> {
                                        nodeProduce(Vars.content.liquid("ec-oil7"), () -> {
                                            nodeProduce(Vars.content.liquid("ec-oil8"), () -> {
                                                nodeProduce(Vars.content.liquid("ec-oil9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
            //cryofluid
            node(Vars.content.block("ec-cryofluidCompressor"), () -> {
                nodeProduce(Vars.content.liquid("ec-cryofluid1"), () -> {
                    nodeProduce(Vars.content.liquid("ec-cryofluid2"), () -> {
                        nodeProduce(Vars.content.liquid("ec-cryofluid3"), () -> {
                            nodeProduce(Vars.content.liquid("ec-cryofluid4"), () -> {
                                nodeProduce(Vars.content.liquid("ec-cryofluid5"), () -> {
                                    nodeProduce(Vars.content.liquid("ec-cryofluid6"), () -> {
                                        nodeProduce(Vars.content.liquid("ec-cryofluid7"), () -> {
                                            nodeProduce(Vars.content.liquid("ec-cryofluid8"), () -> {
                                                nodeProduce(Vars.content.liquid("ec-cryofluid9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //conveyor
            node(Vars.content.block("ec-conveyor1"), () -> {
                node(Vars.content.block("ec-conveyor2"), () -> {
                    node(Vars.content.block("ec-conveyor3"), () -> {
                        node(Vars.content.block("ec-conveyor4"), () -> {
                            node(Vars.content.block("ec-conveyor5"), () -> {
                                node(Vars.content.block("ec-conveyor6"), () -> {
                                    node(Vars.content.block("ec-conveyor7"), () -> {
                                        node(Vars.content.block("ec-conveyor8"), () -> {
                                            node(Vars.content.block("ec-conveyor9"), () -> {});
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //mechanicalDrill
            node(Vars.content.block("ec-mechanicalDrill1"), () -> {
                node(Vars.content.block("ec-mechanicalDrill2"), () -> {
                    node(Vars.content.block("ec-mechanicalDrill3"), () -> {
                        node(Vars.content.block("ec-mechanicalDrill4"), () -> {
                            node(Vars.content.block("ec-mechanicalDrill5"), () -> {
                                node(Vars.content.block("ec-mechanicalDrill6"), () -> {
                                    node(Vars.content.block("ec-mechanicalDrill7"), () -> {
                                        node(Vars.content.block("ec-mechanicalDrill8"), () -> {
                                            node(Vars.content.block("ec-mechanicalDrill9"), () -> {});
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //conveyor
            node(Vars.content.block("ec-conveyor1"), () -> {
                node(Vars.content.block("ec-conveyor2"), () -> {
                    node(Vars.content.block("ec-conveyor3"), () -> {
                        node(Vars.content.block("ec-conveyor4"), () -> {
                            node(Vars.content.block("ec-conveyor5"), () -> {
                                node(Vars.content.block("ec-conveyor6"), () -> {
                                    node(Vars.content.block("ec-conveyor7"), () -> {
                                        node(Vars.content.block("ec-conveyor8"), () -> {
                                            node(Vars.content.block("ec-conveyor9"), () -> {});
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //mechanicalDrill
            node(Vars.content.block("ec-mechanicalDrill1"), () -> {
                node(Vars.content.block("ec-mechanicalDrill2"), () -> {
                    node(Vars.content.block("ec-mechanicalDrill3"), () -> {
                        node(Vars.content.block("ec-mechanicalDrill4"), () -> {
                            node(Vars.content.block("ec-mechanicalDrill5"), () -> {
                                node(Vars.content.block("ec-mechanicalDrill6"), () -> {
                                    node(Vars.content.block("ec-mechanicalDrill7"), () -> {
                                        node(Vars.content.block("ec-mechanicalDrill8"), () -> {
                                            node(Vars.content.block("ec-mechanicalDrill9"), () -> {});
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });



        });
    }
}
