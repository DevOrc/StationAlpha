package com.noahcharlton.stationalpha.block.tree;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRenderer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class TreeBlock extends Block {

    ManagedTexture fullTreeTexture = new ManagedTexture("blocks/tree.png");
    ManagedTexture saplingTexture = new ManagedTexture("blocks/tree_sapling.png");

    public TreeBlock() {
        setOpaque(true);
    }

    @Override
    protected int getDimensionedWidth() {
        return 3;
    }

    @Override
    protected int getDimensionedHeight() {
        return 3;
    }

    @Override
    public String getDisplayName() {
        return "Tree";
    }

    @Override
    public BlockContainer createContainer(Tile tile, BlockRotation blockRotation) {
        return new TreeContainer(tile, this, blockRotation);
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.empty();
    }

    @Override
    protected BlockRenderer createRenderer() {
        return new TreeRenderer(this);
    }
}
