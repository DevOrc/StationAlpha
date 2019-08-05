package com.noahcharlton.stationalpha.item;

import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ManufacturingRecipeTests {

    private final ManufacturingRecipe recipe = new ManufacturingRecipe(Item.SPACE_ROCK.stack(1),
            Item.STEEL.stack(3), 100);

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

    @Test
    void negativeTimeDoesNotBuildTest() {
        ManufacturingRecipe.Builder builder = createDefaultBuilder();

        builder.setTime(-1);

        Assertions.assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void timeZeroDoesNotBuildTest() {
        ManufacturingRecipe.Builder builder = createDefaultBuilder();

        builder.setTime(0);

        Assertions.assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void nullInputDoesNotBuildTest() {
        ManufacturingRecipe.Builder builder = createDefaultBuilder();

        builder.setInput(null);

        Assertions.assertThrows(NullPointerException.class, builder::build);
    }

    @Test
    void nullOutputDoesNotBuildTest() {
        ManufacturingRecipe.Builder builder = createDefaultBuilder();

        builder.setOutput(null);

        Assertions.assertThrows(NullPointerException.class, builder::build);
    }

    @Test
    void nullTypeDoesNotBuildTest() {
        ManufacturingRecipe.Builder builder = createDefaultBuilder();

        builder.setType(null);

        Assertions.assertThrows(NullPointerException.class, builder::build);
    }

    @Test
    void defaultBuilderBuildsTest() {
       createDefaultBuilder().build();
    }

    private ManufacturingRecipe.Builder createDefaultBuilder(){
        return ManufacturingRecipe.createBuilder()
                .setInput(Item.LEAVES.stack(0))
                .setOutput(Item.LEAVES.stack(0))
                .setType(RecipeType.CRAFT)
                .setTime(1);
    }
}
