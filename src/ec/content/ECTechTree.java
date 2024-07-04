package ec.content;

import ec.Blocks.ECWalls;
import mindustry.content.Planets;

import static mindustry.content.TechTree.*;
import static mindustry.content.TechTree.node;

public class ECTechTree {
    public static void load(){
        Planets.serpulo.techTree = nodeRoot("compress", ECBlocks.copperCompressor, () -> {

            //copper
            nodeProduce(ECItems.copper1, () -> {
                nodeProduce(ECItems.copper2, () -> {
                    nodeProduce(ECItems.copper3, () -> {
                        nodeProduce(ECItems.copper4, () -> {
                            nodeProduce(ECItems.copper5, () -> {
                                nodeProduce(ECItems.copper6, () -> {
                                    nodeProduce(ECItems.copper7, () -> {
                                        nodeProduce(ECItems.copper8, () -> {
                                            nodeProduce(ECItems.copper9, () -> {});
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
            node(ECBlocks.copperWall1, () -> {
                node(ECBlocks.copperWall2, () -> {
                    node(ECBlocks.copperWall3, () -> {
                        node(ECBlocks.copperWall4, () -> {
                            node(ECBlocks.copperWall5, () -> {
                                node(ECBlocks.copperWall6, () -> {
                                    node(ECBlocks.copperWall7, () -> {
                                        node(ECBlocks.copperWall8, () -> {
                                            node(ECBlocks.copperWall9, () -> {});
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //lead
            node(ECBlocks.leadCompressor, () -> {
                nodeProduce(ECItems.lead1, () -> {
                    nodeProduce(ECItems.lead2, () -> {
                        nodeProduce(ECItems.lead3, () -> {
                            nodeProduce(ECItems.lead4, () -> {
                                nodeProduce(ECItems.lead5, () -> {
                                    nodeProduce(ECItems.lead6, () -> {
                                        nodeProduce(ECItems.lead7, () -> {
                                            nodeProduce(ECItems.lead8, () -> {
                                                nodeProduce(ECItems.lead9, () -> {});
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
            node(ECBlocks.sandCompressor, () -> {
                nodeProduce(ECItems.sand1, () -> {
                    nodeProduce(ECItems.sand2, () -> {
                        nodeProduce(ECItems.sand3, () -> {
                            nodeProduce(ECItems.sand4, () -> {
                                nodeProduce(ECItems.sand5, () -> {
                                    nodeProduce(ECItems.sand6, () -> {
                                        nodeProduce(ECItems.sand7, () -> {
                                            nodeProduce(ECItems.sand8, () -> {
                                                nodeProduce(ECItems.sand9, () -> {});
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
            node(ECBlocks.titaniumCompressor, () -> {
                nodeProduce(ECItems.titanium1, () -> {
                    nodeProduce(ECItems.titanium2, () -> {
                        nodeProduce(ECItems.titanium3, () -> {
                            nodeProduce(ECItems.titanium4, () -> {
                                nodeProduce(ECItems.titanium5, () -> {
                                    nodeProduce(ECItems.titanium6, () -> {
                                        nodeProduce(ECItems.titanium7, () -> {
                                            nodeProduce(ECItems.titanium8, () -> {
                                                nodeProduce(ECItems.titanium9, () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
                node(ECBlocks.titaniumWall1, () -> {
                    node(ECBlocks.titaniumWall2, () -> {
                        node(ECBlocks.titaniumWall3, () -> {
                            node(ECBlocks.titaniumWall4, () -> {
                                node(ECBlocks.titaniumWall5, () -> {
                                    node(ECBlocks.titaniumWall6, () -> {
                                        node(ECBlocks.titaniumWall7, () -> {
                                            node(ECBlocks.titaniumWall8, () -> {
                                                node(ECBlocks.titaniumWall9, () -> {});
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
            node(ECBlocks.metaglassCompressor, () -> {
                nodeProduce(ECItems.metaglass1, () -> {
                    nodeProduce(ECItems.metaglass2, () -> {
                        nodeProduce(ECItems.metaglass3, () -> {
                            nodeProduce(ECItems.metaglass4, () -> {
                                nodeProduce(ECItems.metaglass5, () -> {
                                    nodeProduce(ECItems.metaglass6, () -> {
                                        nodeProduce(ECItems.metaglass7, () -> {
                                            nodeProduce(ECItems.metaglass8, () -> {
                                                nodeProduce(ECItems.metaglass9, () -> {});
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
            node(ECBlocks.scrapCompressor, () -> {
                nodeProduce(ECItems.scrap1, () -> {
                    nodeProduce(ECItems.scrap2, () -> {
                        nodeProduce(ECItems.scrap3, () -> {
                            nodeProduce(ECItems.scrap4, () -> {
                                nodeProduce(ECItems.scrap5, () -> {
                                    nodeProduce(ECItems.scrap6, () -> {
                                        nodeProduce(ECItems.scrap7, () -> {
                                            nodeProduce(ECItems.scrap8, () -> {
                                                nodeProduce(ECItems.scrap9, () -> {});
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
            node(ECBlocks.coalCompressor, () -> {
                nodeProduce(ECItems.coal1, () -> {
                    nodeProduce(ECItems.coal2, () -> {
                        nodeProduce(ECItems.coal3, () -> {
                            nodeProduce(ECItems.coal4, () -> {
                                nodeProduce(ECItems.coal5, () -> {
                                    nodeProduce(ECItems.coal6, () -> {
                                        nodeProduce(ECItems.coal7, () -> {
                                            nodeProduce(ECItems.coal8, () -> {
                                                nodeProduce(ECItems.coal9, () -> {});
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
            node(ECBlocks.thoriumCompressor, () -> {
                nodeProduce(ECItems.thorium1, () -> {
                    nodeProduce(ECItems.thorium2, () -> {
                        nodeProduce(ECItems.thorium3, () -> {
                            nodeProduce(ECItems.thorium4, () -> {
                                nodeProduce(ECItems.thorium5, () -> {
                                    nodeProduce(ECItems.thorium6, () -> {
                                        nodeProduce(ECItems.thorium7, () -> {
                                            nodeProduce(ECItems.thorium8, () -> {
                                                nodeProduce(ECItems.thorium9, () -> {});
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
            node(ECBlocks.surgeAlloyCompressor, () -> {
                nodeProduce(ECItems.surgeAlloy1, () -> {
                    nodeProduce(ECItems.surgeAlloy2, () -> {
                        nodeProduce(ECItems.surgeAlloy3, () -> {
                            nodeProduce(ECItems.surgeAlloy4, () -> {
                                nodeProduce(ECItems.surgeAlloy5, () -> {
                                    nodeProduce(ECItems.surgeAlloy6, () -> {
                                        nodeProduce(ECItems.surgeAlloy7, () -> {
                                            nodeProduce(ECItems.surgeAlloy8, () -> {
                                                nodeProduce(ECItems.surgeAlloy9, () -> {});
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
            node(ECBlocks.phaseFabricCompressor, () -> {
                nodeProduce(ECItems.phaseFabric1, () -> {
                    nodeProduce(ECItems.phaseFabric2, () -> {
                        nodeProduce(ECItems.phaseFabric3, () -> {
                            nodeProduce(ECItems.phaseFabric4, () -> {
                                nodeProduce(ECItems.phaseFabric5, () -> {
                                    nodeProduce(ECItems.phaseFabric6, () -> {
                                        nodeProduce(ECItems.phaseFabric7, () -> {
                                            nodeProduce(ECItems.phaseFabric8, () -> {
                                                nodeProduce(ECItems.phaseFabric9, () -> {});
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
            node(ECBlocks.graphiteCompressor, () -> {
                nodeProduce(ECItems.graphite1, () -> {
                    nodeProduce(ECItems.graphite2, () -> {
                        nodeProduce(ECItems.graphite3, () -> {
                            nodeProduce(ECItems.graphite4, () -> {
                                nodeProduce(ECItems.graphite5, () -> {
                                    nodeProduce(ECItems.graphite6, () -> {
                                        nodeProduce(ECItems.graphite7, () -> {
                                            nodeProduce(ECItems.graphite8, () -> {
                                                nodeProduce(ECItems.graphite9, () -> {});
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
            node(ECBlocks.siliconCompressor, () -> {
                nodeProduce(ECItems.silicon1, () -> {
                    nodeProduce(ECItems.silicon2, () -> {
                        nodeProduce(ECItems.silicon3, () -> {
                            nodeProduce(ECItems.silicon4, () -> {
                                nodeProduce(ECItems.silicon5, () -> {
                                    nodeProduce(ECItems.silicon6, () -> {
                                        nodeProduce(ECItems.silicon7, () -> {
                                            nodeProduce(ECItems.silicon8, () -> {
                                                nodeProduce(ECItems.silicon9, () -> {});
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
            node(ECBlocks.pyratiteCompressor, () -> {
                nodeProduce(ECItems.pyratite1, () -> {
                    nodeProduce(ECItems.pyratite2, () -> {
                        nodeProduce(ECItems.pyratite3, () -> {
                            nodeProduce(ECItems.pyratite4, () -> {
                                nodeProduce(ECItems.pyratite5, () -> {
                                    nodeProduce(ECItems.pyratite6, () -> {
                                        nodeProduce(ECItems.pyratite7, () -> {
                                            nodeProduce(ECItems.pyratite8, () -> {
                                                nodeProduce(ECItems.pyratite9, () -> {});
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
            node(ECBlocks.blastCompoundCompressor, () -> {
                nodeProduce(ECItems.blastCompound1, () -> {
                    nodeProduce(ECItems.blastCompound2, () -> {
                        nodeProduce(ECItems.blastCompound3, () -> {
                            nodeProduce(ECItems.blastCompound4, () -> {
                                nodeProduce(ECItems.blastCompound5, () -> {
                                    nodeProduce(ECItems.blastCompound6, () -> {
                                        nodeProduce(ECItems.blastCompound7, () -> {
                                            nodeProduce(ECItems.blastCompound8, () -> {
                                                nodeProduce(ECItems.blastCompound9, () -> {});
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
            node(ECBlocks.sporePodCompressor, () -> {
                nodeProduce(ECItems.sporePod1, () -> {
                    nodeProduce(ECItems.sporePod2, () -> {
                        nodeProduce(ECItems.sporePod3, () -> {
                            nodeProduce(ECItems.sporePod4, () -> {
                                nodeProduce(ECItems.sporePod5, () -> {
                                    nodeProduce(ECItems.sporePod6, () -> {
                                        nodeProduce(ECItems.sporePod7, () -> {
                                            nodeProduce(ECItems.sporePod8, () -> {
                                                nodeProduce(ECItems.sporePod9, () -> {});
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
            node(ECBlocks.plastaniumCompressor, () -> {
                nodeProduce(ECItems.plastanium1, () -> {
                    nodeProduce(ECItems.plastanium2, () -> {
                        nodeProduce(ECItems.plastanium3, () -> {
                            nodeProduce(ECItems.plastanium4, () -> {
                                nodeProduce(ECItems.plastanium5, () -> {
                                    nodeProduce(ECItems.plastanium6, () -> {
                                        nodeProduce(ECItems.plastanium7, () -> {
                                            nodeProduce(ECItems.plastanium8, () -> {
                                                nodeProduce(ECItems.plastanium9, () -> {});
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
            node(ECBlocks.conveyor1, () -> {
                node(ECBlocks.conveyor2, () -> {
                    node(ECBlocks.conveyor3, () -> {
                        node(ECBlocks.conveyor4, () -> {
                            node(ECBlocks.conveyor5, () -> {
                                node(ECBlocks.conveyor6, () -> {
                                    node(ECBlocks.conveyor7, () -> {
                                        node(ECBlocks.conveyor8, () -> {
                                            node(ECBlocks.conveyor9, () -> {});
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });

            //mechanicalDrill
            node(ECBlocks.mechanicalDrill1, () -> {
                node(ECBlocks.mechanicalDrill2, () -> {
                    node(ECBlocks.mechanicalDrill3, () -> {
                        node(ECBlocks.mechanicalDrill4, () -> {
                            node(ECBlocks.mechanicalDrill5, () -> {
                                node(ECBlocks.mechanicalDrill6, () -> {
                                    node(ECBlocks.mechanicalDrill7, () -> {
                                        node(ECBlocks.mechanicalDrill8, () -> {
                                            node(ECBlocks.mechanicalDrill9, () -> {});
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
