package com.noahcharlton.stationalpha.gui.scenes.buildmenu.blockmenu;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.Blocks;

import java.util.Arrays;
import java.util.List;

enum BlockGroup {

    STRUCTURE("Structure",
            Blocks.getWall(),
            Blocks.getDoor(),
            Blocks.getAirlock()),
    CRAFTING("Crafting",
            Blocks.getWorkbench(),
            Blocks.getSynthesizer(),
            Blocks.getComposter()),
    NATURAL("Natural",
            Blocks.getTreeSapling(),
            Blocks.getPotatoPlant(),
            Blocks.getWoodrootPlant()),
    MACHINES("Machines",
            Blocks.getCompressor(),
            Blocks.getDustCollector(),
            Blocks.getArcReactor()),
    SCIENCE("Science",
            Blocks.getAntiGravityExperiment()),
    MISC("Misc.",
            Blocks.getBedBlock(),
            Blocks.getSolarPanel(),
            Blocks.getBattery());

    final String name;
    final List<Block> blocks;

    BlockGroup(String name, Block... blocks) {
        this.name = name;
        this.blocks = Arrays.asList(blocks);
    }

    @Override
    public String toString() {
        return name;
    }
}
