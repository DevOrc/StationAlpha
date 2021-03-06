package com.noahcharlton.stationalpha.block.sapling;

import com.noahcharlton.stationalpha.HelpInfo;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.science.ResearchItem;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class TreeSaplingBlock extends Block {

    public TreeSaplingBlock() {
        setOpaque(false);
        setPassable(true);
        setAutoBuild(true);
    }

    @Override
    public Optional<ResearchItem> getRequiredResearch() {
        return Optional.of(ResearchItem.BASIC_GARDENING);
    }

    @Override
    public String getID() {
        return "tree_sapling";
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("tree_sapling.png");
    }

    @Override
    public BlockContainer createContainer(Tile tile, BlockRotation blockRotation) {
        return new TreeSaplingContainer(tile, this, blockRotation);
    }

    @Override
    public Optional<String> getHelpInfo() {
        return Optional.of(HelpInfo.get("sapling_info"));
    }

    @Override
    public String getDisplayName() {
        return "Sapling";
    }
}
