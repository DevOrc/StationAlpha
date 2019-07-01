package com.noahcharlton.stationalpha.item;

import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ManufacturingRecipeTests {

    private final ManufacturingRecipe recipe = new ManufacturingRecipe(Item.SPACE_ROCK, 1,
            Item.STEEL, 3, 100);

    private final Inventory inventory = new World().getInventory();

    @Test
    void isResourcesAvailableFalseTest() {
        Assertions.assertFalse(recipe.resourcesAvailable(inventory));
    }

    @Test
    void isResourcesAvailableTrueTest() {
        inventory.changeAmountForItem(Item.SPACE_ROCK, 1);

        Assertions.assertTrue(recipe.resourcesAvailable(inventory));
    }

    @Test
    void removeRequirementsBasicTest() {
        inventory.changeAmountForItem(Item.SPACE_ROCK, 4);

        recipe.removeRequirements(inventory);

        Assertions.assertEquals(3, inventory.getAmountForItem(Item.SPACE_ROCK));
    }

    @Test
    void addProductsBasicTest() {
        recipe.addProducts(inventory);

        Assertions.assertEquals(3, inventory.getAmountForItem(Item.STEEL));
    }
}
