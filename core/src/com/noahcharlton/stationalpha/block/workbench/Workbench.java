package com.noahcharlton.stationalpha.block.workbench;

import com.noahcharlton.stationalpha.block.*;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class Workbench extends Block {
    @Override
    public BlockContainer createContainer(Tile tile) {
        return new WorkbenchContainer(tile, this);
    }

    @Override
    protected int getDimensionedWidth() {
        return 2;
    }

    @Override
    protected int getDimensionedHeight() {
        return 1;
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("workbench.png");
    }
}