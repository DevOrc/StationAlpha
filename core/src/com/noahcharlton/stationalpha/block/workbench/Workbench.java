package com.noahcharlton.stationalpha.block.workbench;

import com.noahcharlton.stationalpha.block.*;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class Workbench extends Block implements Multiblock {

    @Override
    public int getHeight() {
        return 1;
    }

    @Override
    public int getWidth() {
        return 2;
    }

    @Override
    public Optional<BlockContainer> createContainer(Tile tile) {
        return Optional.of(new WorkbenchContainer(tile, this));
    }

    @Override
    protected BlockRenderer createRenderer() {
        return new MultiblockRenderer(this);
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("workbench.png");
    }
}