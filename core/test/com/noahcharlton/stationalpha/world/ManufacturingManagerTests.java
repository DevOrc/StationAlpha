package com.noahcharlton.stationalpha.world;

import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ManufacturingManagerTests {

    private final World world = new World();
    private final ManufacturingManager manager = world.getManufacturingManager();

    @Test
    void basicQueueTest() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.TEST_ITEM, 1, Item.TEST_ITEM, 5, 100);
        manager.addRecipeToQueue(recipe);

        Assertions.assertSame(recipe, manager.getNextRecipe().get());
    }
}
