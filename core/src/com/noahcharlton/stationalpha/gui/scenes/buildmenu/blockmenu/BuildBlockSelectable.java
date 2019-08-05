package com.noahcharlton.stationalpha.gui.scenes.buildmenu.blockmenu;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.engine.input.Selectable;
import com.noahcharlton.stationalpha.item.ItemStack;

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
                    getRequirementAsString(),
            };
    }

    private String getRequirementAsString() {
        StringBuilder builder = new StringBuilder("Requirements:");

        if(block.getRequirements().isEmpty()){
            return "Requirements: None";
        }

        for(ItemStack stack: block.getRequirements()){
            builder.append("\n    ");
            builder.append(stack.getAmount());
            builder.append(" ");
            builder.append(stack.getItem().getDisplayName());
            builder.append("");
        }

        return builder.toString();
    }

    @Override
    public Optional<String> getHelpInfo() {
        return block.getHelpInfo();
    }
}

