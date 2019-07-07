package com.noahcharlton.stationalpha.block;

import com.noahcharlton.stationalpha.HelpInfo;

import java.util.Optional;

public class IceBlock extends Block {

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

    @Override
    public Optional<String> getHelpInfo() {
        return Optional.of(HelpInfo.ICE_INFO);
    }
}
