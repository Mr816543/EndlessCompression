package ec.content;

import arc.func.Boolf;
import ec.Blocks.ECWalls;
import mindustry.Vars;
import mindustry.content.Planets;
import mindustry.content.TechTree;
import mindustry.ctype.UnlockableContent;

import static mindustry.content.TechTree.*;
import static mindustry.content.TechTree.node;

public class ECTechTree {
    public static void load(){
        Planets.serpulo.techTree = nodeRoot("compress", Vars.content.block("ec-copperCompressor"), () -> {

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
                node(Vars.content.block("ec-copperMultiPress"), () -> {});
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
                node(Vars.content.block("ec-leadMultiPress"), () -> {});
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
                node(Vars.content.block("ec-sandMultiPress"), () -> {});
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
                node(Vars.content.block("ec-titaniumMultiPress"), () -> {});
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
                node(Vars.content.block("ec-metaglassMultiPress"), () -> {});
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
                node(Vars.content.block("ec-scrapMultiPress"), () -> {});
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
                node(Vars.content.block("ec-coalMultiPress"), () -> {});
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
                node(Vars.content.block("ec-thoriumMultiPress"), () -> {});
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

            //surge-alloy
            node(Vars.content.block("ec-surge-alloyCompressor"), () -> {
                nodeProduce(Vars.content.item("ec-surge-alloy1"), () -> {
                    nodeProduce(Vars.content.item("ec-surge-alloy2"), () -> {
                        nodeProduce(Vars.content.item("ec-surge-alloy3"), () -> {
                            nodeProduce(Vars.content.item("ec-surge-alloy4"), () -> {
                                nodeProduce(Vars.content.item("ec-surge-alloy5"), () -> {
                                    nodeProduce(Vars.content.item("ec-surge-alloy6"), () -> {
                                        nodeProduce(Vars.content.item("ec-surge-alloy7"), () -> {
                                            nodeProduce(Vars.content.item("ec-surge-alloy8"), () -> {
                                                nodeProduce(Vars.content.item("ec-surge-alloy9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
                node(Vars.content.block("ec-surge-alloyMultiPress"), () -> {});
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

            //phase-fabric
            node(Vars.content.block("ec-phase-fabricCompressor"), () -> {
                nodeProduce(Vars.content.item("ec-phase-fabric1"), () -> {
                    nodeProduce(Vars.content.item("ec-phase-fabric2"), () -> {
                        nodeProduce(Vars.content.item("ec-phase-fabric3"), () -> {
                            nodeProduce(Vars.content.item("ec-phase-fabric4"), () -> {
                                nodeProduce(Vars.content.item("ec-phase-fabric5"), () -> {
                                    nodeProduce(Vars.content.item("ec-phase-fabric6"), () -> {
                                        nodeProduce(Vars.content.item("ec-phase-fabric7"), () -> {
                                            nodeProduce(Vars.content.item("ec-phase-fabric8"), () -> {
                                                nodeProduce(Vars.content.item("ec-phase-fabric9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
                node(Vars.content.block("ec-phase-fabricMultiPress"), () -> {});
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
                node(Vars.content.block("ec-graphiteMultiPress"), () -> {});
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
                node(Vars.content.block("ec-siliconMultiPress"), () -> {});
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
                node(Vars.content.block("ec-pyratiteMultiPress"), () -> {});
            });

            //blast-compound
            node(Vars.content.block("ec-blast-compoundCompressor"), () -> {
                nodeProduce(Vars.content.item("ec-blast-compound1"), () -> {
                    nodeProduce(Vars.content.item("ec-blast-compound2"), () -> {
                        nodeProduce(Vars.content.item("ec-blast-compound3"), () -> {
                            nodeProduce(Vars.content.item("ec-blast-compound4"), () -> {
                                nodeProduce(Vars.content.item("ec-blast-compound5"), () -> {
                                    nodeProduce(Vars.content.item("ec-blast-compound6"), () -> {
                                        nodeProduce(Vars.content.item("ec-blast-compound7"), () -> {
                                            nodeProduce(Vars.content.item("ec-blast-compound8"), () -> {
                                                nodeProduce(Vars.content.item("ec-blast-compound9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
                node(Vars.content.block("ec-blast-compoundMultiPress"), () -> {});
            });

            //spore-pod
            node(Vars.content.block("ec-spore-podCompressor"), () -> {
                nodeProduce(Vars.content.item("ec-spore-pod1"), () -> {
                    nodeProduce(Vars.content.item("ec-spore-pod2"), () -> {
                        nodeProduce(Vars.content.item("ec-spore-pod3"), () -> {
                            nodeProduce(Vars.content.item("ec-spore-pod4"), () -> {
                                nodeProduce(Vars.content.item("ec-spore-pod5"), () -> {
                                    nodeProduce(Vars.content.item("ec-spore-pod6"), () -> {
                                        nodeProduce(Vars.content.item("ec-spore-pod7"), () -> {
                                            nodeProduce(Vars.content.item("ec-spore-pod8"), () -> {
                                                nodeProduce(Vars.content.item("ec-spore-pod9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
                node(Vars.content.block("ec-spore-podMultiPress"), () -> {});
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
                node(Vars.content.block("ec-plastaniumMultiPress"), () -> {});
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
                //node(Vars.content.block("ec-waterMultiPress"), () -> {});
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
                //node(Vars.content.block("ec-slagMultiPress"), () -> {});
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
                //node(Vars.content.block("ec-oilMultiPress"), () -> {});
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
                //node(Vars.content.block("ec-cryofluidMultiPress"), () -> {});
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
                //titanium-conveyor
                node(Vars.content.block("ec-titanium-conveyor1"), () -> {
                    node(Vars.content.block("ec-titanium-conveyor2"), () -> {
                        node(Vars.content.block("ec-titanium-conveyor3"), () -> {
                            node(Vars.content.block("ec-titanium-conveyor4"), () -> {
                                node(Vars.content.block("ec-titanium-conveyor5"), () -> {
                                    node(Vars.content.block("ec-titanium-conveyor6"), () -> {
                                        node(Vars.content.block("ec-titanium-conveyor7"), () -> {
                                            node(Vars.content.block("ec-titanium-conveyor8"), () -> {
                                                node(Vars.content.block("ec-titanium-conveyor9"), () -> {});
                                            });
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
                node(Vars.content.block("ec-combustion-generator"), () -> {

                    node(Vars.content.block("ec-powerCompressor"), () -> {});
                    node(Vars.content.block("ec-power-producer"), () -> {});

                    //power-node
                    node(Vars.content.block("ec-power-node1"), () -> {
                        node(Vars.content.block("ec-power-node2"), () -> {
                            node(Vars.content.block("ec-power-node3"), () -> {
                                node(Vars.content.block("ec-power-node4"), () -> {
                                    node(Vars.content.block("ec-power-node5"), () -> {
                                        node(Vars.content.block("ec-power-node6"), () -> {
                                            node(Vars.content.block("ec-power-node7"), () -> {
                                                node(Vars.content.block("ec-power-node8"), () -> {
                                                    node(Vars.content.block("ec-power-node9"), () -> {});
                                                });
                                            });
                                        });
                                    });
                                });
                            });
                        });
                        //battery
                        node(Vars.content.block("ec-battery1"), () -> {
                            node(Vars.content.block("ec-battery2"), () -> {
                                node(Vars.content.block("ec-battery3"), () -> {
                                    node(Vars.content.block("ec-battery4"), () -> {
                                        node(Vars.content.block("ec-battery5"), () -> {
                                            node(Vars.content.block("ec-battery6"), () -> {
                                                node(Vars.content.block("ec-battery7"), () -> {
                                                    node(Vars.content.block("ec-battery8"), () -> {
                                                        node(Vars.content.block("ec-battery9"), () -> {});
                                                    });
                                                });
                                            });
                                        });
                                    });
                                });
                            });
                        });
                        node(Vars.content.block("ec-steam-generator"), () -> {

                            node(Vars.content.block("ec-differential-generator"), () -> {});
                        });
                    });
                });
            });

            //units
            //dagger
            node(Vars.content.unit("ec-dagger1"), () -> {
                node(Vars.content.unit("ec-dagger2"), () -> {
                    node(Vars.content.unit("ec-dagger3"), () -> {
                        node(Vars.content.unit("ec-dagger4"), () -> {
                            node(Vars.content.unit("ec-dagger5"), () -> {
                                node(Vars.content.unit("ec-dagger6"), () -> {
                                    node(Vars.content.unit("ec-dagger7"), () -> {
                                        node(Vars.content.unit("ec-dagger8"), () -> {
                                            node(Vars.content.unit("ec-dagger9"), () -> {});
                                        });
                                    });
                                });
                            });
                        });
                    });
                });

                //nova
                node(Vars.content.unit("ec-nova1"), () -> {
                    node(Vars.content.unit("ec-nova2"), () -> {
                        node(Vars.content.unit("ec-nova3"), () -> {
                            node(Vars.content.unit("ec-nova4"), () -> {
                                node(Vars.content.unit("ec-nova5"), () -> {
                                    node(Vars.content.unit("ec-nova6"), () -> {
                                        node(Vars.content.unit("ec-nova7"), () -> {
                                            node(Vars.content.unit("ec-nova8"), () -> {
                                                node(Vars.content.unit("ec-nova9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });

                //crawler
                node(Vars.content.unit("ec-crawler1"), () -> {
                    node(Vars.content.unit("ec-crawler2"), () -> {
                        node(Vars.content.unit("ec-crawler3"), () -> {
                            node(Vars.content.unit("ec-crawler4"), () -> {
                                node(Vars.content.unit("ec-crawler5"), () -> {
                                    node(Vars.content.unit("ec-crawler6"), () -> {
                                        node(Vars.content.unit("ec-crawler7"), () -> {
                                            node(Vars.content.unit("ec-crawler8"), () -> {
                                                node(Vars.content.unit("ec-crawler9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });

                //flare
                node(Vars.content.unit("ec-flare1"), () -> {
                    node(Vars.content.unit("ec-flare2"), () -> {
                        node(Vars.content.unit("ec-flare3"), () -> {
                            node(Vars.content.unit("ec-flare4"), () -> {
                                node(Vars.content.unit("ec-flare5"), () -> {
                                    node(Vars.content.unit("ec-flare6"), () -> {
                                        node(Vars.content.unit("ec-flare7"), () -> {
                                            node(Vars.content.unit("ec-flare8"), () -> {
                                                node(Vars.content.unit("ec-flare9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });

                //mono
                node(Vars.content.unit("ec-mono1"), () -> {
                    node(Vars.content.unit("ec-mono2"), () -> {
                        node(Vars.content.unit("ec-mono3"), () -> {
                            node(Vars.content.unit("ec-mono4"), () -> {
                                node(Vars.content.unit("ec-mono5"), () -> {
                                    node(Vars.content.unit("ec-mono6"), () -> {
                                        node(Vars.content.unit("ec-mono7"), () -> {
                                            node(Vars.content.unit("ec-mono8"), () -> {
                                                node(Vars.content.unit("ec-mono9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });

                //risso
                node(Vars.content.unit("ec-risso1"), () -> {
                    node(Vars.content.unit("ec-risso2"), () -> {
                        node(Vars.content.unit("ec-risso3"), () -> {
                            node(Vars.content.unit("ec-risso4"), () -> {
                                node(Vars.content.unit("ec-risso5"), () -> {
                                    node(Vars.content.unit("ec-risso6"), () -> {
                                        node(Vars.content.unit("ec-risso7"), () -> {
                                            node(Vars.content.unit("ec-risso8"), () -> {
                                                node(Vars.content.unit("ec-risso9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });

                //retusa
                node(Vars.content.unit("ec-retusa1"), () -> {
                    node(Vars.content.unit("ec-retusa2"), () -> {
                        node(Vars.content.unit("ec-retusa3"), () -> {
                            node(Vars.content.unit("ec-retusa4"), () -> {
                                node(Vars.content.unit("ec-retusa5"), () -> {
                                    node(Vars.content.unit("ec-retusa6"), () -> {
                                        node(Vars.content.unit("ec-retusa7"), () -> {
                                            node(Vars.content.unit("ec-retusa8"), () -> {
                                                node(Vars.content.unit("ec-retusa9"), () -> {});
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });


            //production
            node(Vars.content.block("ec-graphite-press"), () -> {
                /*
                node(Vars.content.block("ec-pneumaticDrill, Seq.with(new SectorComplete(frozenForest))"), () -> {
                    node(Vars.content.block("ec-cultivator, Seq.with(new SectorComplete(biomassFacility))"), () -> {

                    });

                    node(Vars.content.block("ec-laserDrill"), () -> {
                        node(Vars.content.block("ec-blastDrill, Seq.with(new SectorComplete(nuclearComplex))"), () -> {

                        });

                        node(Vars.content.block("ec-waterExtractor, Seq.with(new SectorComplete(saltFlats))"), () -> {
                            node(Vars.content.block("ec-oilExtractor"), () -> {

                            });
                        });
                    });
                });

                 */
                node(Vars.content.block("ec-pyratite-mixer"), () -> {
                    node(Vars.content.block("ec-blast-mixer"), () -> {

                    });
                });

                node(Vars.content.block("ec-silicon-smelter"), () -> {

                    node(Vars.content.block("ec-spore-press"), () -> {
                        node(Vars.content.block("ec-coal-centrifuge"), () -> {
                            /*
                            node(Vars.content.block("ec-multi-press"), () -> {
                                node(Vars.content.block("ec-silicon-srucible"), () -> {

                                });
                            });
                            */
                        });

                        node(Vars.content.block("ec-plastanium-compressor"), () -> {
                            node(Vars.content.block("ec-phase-weaver"), () -> {

                            });
                        });
                    });

                    node(Vars.content.block("ec-kiln"), () -> {
                        node(Vars.content.block("ec-pulverizer"), () -> {
                            node(Vars.content.block("ec-melter"), () -> {
                                node(Vars.content.block("ec-surge-smelter"), () -> {

                                });

                                node(Vars.content.block("ec-separator"), () -> {
                                    node(Vars.content.block("ec-disassembler"), () -> {

                                    });
                                });

                                node(Vars.content.block("ec-cryofluid-mixer"), () -> {

                                });
                            });
                        });
                    });
                    /*
                    //logic disabled until further notice
                    node(Vars.content.block("ec-microProcessor"), () -> {
                        node(Vars.content.block("ec-switchBlock"), () -> {
                            node(Vars.content.block("ec-message"), () -> {
                                node(Vars.content.block("ec-logicDisplay"), () -> {
                                    node(Vars.content.block("ec-largeLogicDisplay"), () -> {

                                    });
                                });

                                node(Vars.content.block("ec-memoryCell"), () -> {
                                    node(Vars.content.block("ec-memoryBank"), () -> {

                                    });
                                });
                            });

                            node(Vars.content.block("ec-logicProcessor"), () -> {
                                node(Vars.content.block("ec-hyperProcessor"), () -> {

                                });
                            });
                        });
                    });

                    node(Vars.content.block("ec-illuminator"), () -> {

                    });
                    */
                });
            });





        });

    }
    /*
    public static void TechTree(UnlockableContent A, UnlockableContent B){
        Planets.serpulo.techTree.children.find()
    }

     */
}
