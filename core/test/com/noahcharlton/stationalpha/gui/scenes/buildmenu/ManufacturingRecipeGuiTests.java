package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class ManufacturingRecipeGuiTests {

    private final ManufacturingRecipeGui gui = new ManufacturingRecipeGui();

    @Test
    void setSameRecipeTwiceSetsToEmptyTest() {
        ManufacturingRecipe recipe =
                new ManufacturingRecipe(Item.TEST_ITEM, 1, Item.TEST_ITEM, 1, 1);

        gui.setRecipe(recipe);
        gui.setRecipe(recipe);

        Assertions.assertFalse(gui.getCurrentRecipe().isPresent());
    }

    @Test
    void defaultToEmptyCurrentRecipe() {
        Assertions.assertFalse(gui.getCurrentRecipe().isPresent());
    }

    @Test
    void makeRecipeBasicTest() {
        World world = new World();

        gui.setRecipe(new ManufacturingRecipe(Item.POTATO, 1, Item.POTATO, 1, 1));
        gui.makeRecipe(25, Optional.of(world));

        Assertions.assertEquals(25, world.getManufacturingManager().getRecipeQueue().size());
    }

    @Test
    void makeRecipeEmptyWorldTest() {
        gui.makeRecipe(1, Optional.empty());
    }

    @Test
    void makeRecipeEmptyRecipeTest() {
        gui.makeRecipe(1, Optional.of(new World()));
    }
}
