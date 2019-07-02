package com.noahcharlton.stationalpha.block;

import java.util.Optional;

public class IceBlock extends Block {

    public IceBlock() {
        setOpaque(true);
    }

    @Override
    public String getDisplayName() {
        return "Space Rock";
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("ice.png");
    }

    @Override
    public boolean isPlayerBuildable() {
        return false;
    }
}
