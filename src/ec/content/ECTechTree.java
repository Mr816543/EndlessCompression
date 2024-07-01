package ec.content;

import ec.Blocks.ECWalls;
import mindustry.content.Planets;

import static mindustry.content.TechTree.*;
import static mindustry.content.TechTree.node;

public class ECTechTree {
    public static void load(){
        Planets.serpulo.techTree = nodeRoot("compress", ECBlocks.copperCompressor, () -> {
            /*
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
            */
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



        });
    }
}
