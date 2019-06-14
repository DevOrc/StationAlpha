package com.noahcharlton.stationalpha.block.tree;

import com.noahcharlton.stationalpha.block.Block;

import java.util.Optional;

public class TreeBlock extends Block {

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
    protected Optional<String> getTextureFileName() {
        return Optional.of("tree.png");
    }
}
