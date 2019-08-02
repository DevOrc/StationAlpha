package com.noahcharlton.stationalpha.gui.scenes.buildmenu.blockmenu;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.item.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class BuildBlockSelectableTests {

    @Test
    void getDebugInfoEmptyRequirementTest() {
        RequiredItemTestBlock testBlock = new RequiredItemTestBlock(null);
        BuildBlock builder = new BuildBlock(testBlock);
        BuildBlockSelectable selectable = new BuildBlockSelectable(builder);

        String[] expected = {"Rotation: NORTH", "Requirement: None"};

        Assertions.assertArrayEquals(expected, selectable.getDebugInfo());
    }

    @Test
    void getDebugInfoHasRequirementTest() {
        RequiredItemTestBlock testBlock = new RequiredItemTestBlock(Item.STEEL);
        BuildBlock builder = new BuildBlock(testBlock);
        BuildBlockSelectable selectable = new BuildBlockSelectable(builder);

        String[] expected = {"Rotation: NORTH", "Requirement: Steel"};

        Assertions.assertArrayEquals(expected, selectable.getDebugInfo());
    }
}
class RequiredItemTestBlock extends Block {

    private final Optional<Item> requiredItem;

    public RequiredItemTestBlock(Item requiredItem) {
        this.requiredItem = Optional.ofNullable(requiredItem);
    }

    @Override
    public Optional<Item> getRequiredItem() {
        return requiredItem;
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.empty();
    }
}
