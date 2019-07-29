package com.noahcharlton.stationalpha.block.workbench;

import com.noahcharlton.stationalpha.HelpInfo;
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
    public int getDimensionedWidth() {
        return 3;
    }

    @Override
    public int getDimensionedHeight() {
        return 1;
    }

    @Override
    public String getDisplayName() {
        return "Workbench";
    }

    @Override
    public Optional<String> getHelpInfo() {
        return Optional.of(HelpInfo.WORKBENCH_INFO);
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("workbench.png");
    }
}