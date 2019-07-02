package com.noahcharlton.stationalpha.block.workbench;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class Workbench extends Block {
    @Override
    public BlockContainer createContainer(Tile tile, BlockRotation rotation) {
        return new WorkbenchContainer(tile, this, rotation);
    }

    @Override
    protected int getDimensionedWidth() {
        return 3;
    }

    @Override
    protected int getDimensionedHeight() {
        return 1;
    }

    @Override
    public String getDisplayName() {
        return "Workbench";
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("workbench.png");
    }
}