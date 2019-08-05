package com.noahcharlton.stationalpha.gui.scenes.buildmenu.blockmenu;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.plant.PotatoPlant;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ItemStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BuildBlockSelectableTests {

    @Test
    void getDebugInfoEmptyRequirementTest() {
        Block testBlock = new PotatoPlant();
        BuildBlock builder = new BuildBlock(testBlock);
        BuildBlockSelectable selectable = new BuildBlockSelectable(builder);

        String[] expected = {"Rotation: NORTH", "Requirements: None"};

        Assertions.assertArrayEquals(expected, selectable.getDebugInfo());
    }

    @Test
    void getDebugInfoHasRequirementTest() {
        RequiredItemTestBlock testBlock = new RequiredItemTestBlock();
        BuildBlock builder = new BuildBlock(testBlock);
        BuildBlockSelectable selectable = new BuildBlockSelectable(builder);

        String[] expected = {"Rotation: NORTH", "Requirements:\n    4 Steel\n    4 Wood"};

        Assertions.assertArrayEquals(expected, selectable.getDebugInfo());
    }
}
class RequiredItemTestBlock extends Block {

    @Override
    public String getID() {
        return "";
    }

    @Override
    public List<ItemStack> getRequirements() {
        return Arrays.asList(Item.STEEL.stack(4), Item.WOOD.stack(4));
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.empty();
    }
}
