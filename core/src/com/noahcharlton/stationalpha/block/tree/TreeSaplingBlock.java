package com.noahcharlton.stationalpha.block.tree;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class TreeSaplingBlock extends Block {

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("tree_sapling.png");
    }

    @Override
    public BlockContainer createContainer(Tile tile, BlockRotation blockRotation) {
        return new TreeSaplingContainer(tile, this, blockRotation);
    }

    @Override
    public String getDisplayName() {
        return "Sapling";
    }
}
