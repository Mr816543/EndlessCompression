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





        });
    }
}
