package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.item.RecipeType;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ManufacturingRecipeGuiTests {

    private final ManufacturingRecipeGui gui = new ManufacturingRecipeGui();

    @Test
    void setSameRecipeTwiceSetsToEmptyTest() {
        ManufacturingRecipe recipe =
                new ManufacturingRecipe(Item.TEST_ITEM.stack(1), Item.TEST_ITEM.stack(1), 1);

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

        gui.setRecipe(new ManufacturingRecipe(Item.POTATO.stack(1), Item.POTATO.stack(1), 1));
        gui.makeRecipe(25, world);

        Assertions.assertEquals(25, world.getManufacturingManager().getQueueForType(RecipeType.CRAFT).size());
    }

    @Test
    void makeRecipeEmptyWorldTest() {
        gui.setRecipe(new ManufacturingRecipe(Item.POTATO.stack(1), Item.POTATO.stack(1), 1));

        gui.makeRecipe(1);
    }

    @Test
    void makeRecipeEmptyRecipeTest() {
        gui.makeRecipe(1, new World());
    }
}
