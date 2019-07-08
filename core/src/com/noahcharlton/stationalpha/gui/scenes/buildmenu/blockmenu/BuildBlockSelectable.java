package com.noahcharlton.stationalpha.gui.scenes.buildmenu.blockmenu;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.engine.input.Selectable;
import com.noahcharlton.stationalpha.item.Item;

import java.util.Optional;

public class BuildBlockSelectable implements Selectable {

    private final BuildBlock buildBlock;
    private final Block block;

    public BuildBlockSelectable(BuildBlock buildBlock) {
        this.buildBlock = buildBlock;
        this.block = buildBlock.getBlock();
    }

    @Override
    public String getTitle() {
        return "Build: " + block;
    }

    @Override
    public String getDesc() {
        return "";
    }

    @Override
    public String[] getDebugInfo() {
            return new String[]{
                    "Rotation: " + buildBlock.getRotation(),
                    "Requirement: " + block.getRequiredItem().map(Item::getDisplayName).orElse("None"),
            };
    }

    @Override
    public Optional<String> getHelpInfo() {
        return block.getHelpInfo();
    }
}

