package com.noahcharlton.stationalpha.block.bed;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRenderer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class BedBlock extends Block {

    @Override
    public String getDisplayName() {
        return "Bed";
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("bed.png");
    }

    @Override
    protected int getDimensionedHeight() {
        return 1;
    }

    @Override
    protected int getDimensionedWidth() {
        return 2;
    }

    @Override
    public BlockContainer createContainer(Tile tile, BlockRotation blockRotation) {
        return new BedContainer(tile, this, blockRotation);
    }

    @Override
    protected BlockRenderer createRenderer() {
        return new BedRenderer(this);
    }
}
