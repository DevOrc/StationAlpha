package com.noahcharlton.stationalpha.block.mineable;

import com.noahcharlton.stationalpha.HelpInfo;
import com.noahcharlton.stationalpha.engine.InGameIcon;

import java.util.Optional;

public class IceBlock extends MineableBlock {

    public IceBlock() {
        super(InGameIcon.PICK_AXE);
    }

    @Override
    public String getID() {
        return "space_rock";
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
    public Optional<String> getHelpInfo() {
        return Optional.of(HelpInfo.ICE_INFO);
    }
}
