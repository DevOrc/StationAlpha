package com.noahcharlton.stationalpha.block.mineable;

import com.noahcharlton.stationalpha.engine.InGameIcon;

import java.util.Optional;

public class TreeBlock extends MineableBlock {

    public TreeBlock() {
        super(InGameIcon.AXE);
        setOpaque(false);
    }

    @Override
    public String getDisplayName() {
        return "Tree";
    }

    @Override
    public int getDimensionedWidth() {
        return 3;
    }

    @Override
    public int getDimensionedHeight() {
        return 3;
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("tree.png");
    }
}
