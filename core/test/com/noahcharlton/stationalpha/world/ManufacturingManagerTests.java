package com.noahcharlton.stationalpha.world;

import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.item.RecipeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class ManufacturingManagerTests {

    private final World world = new World();
    private final ManufacturingManager manager = world.getManufacturingManager();

    @ParameterizedTest
    @EnumSource(RecipeType.class)
    void allTypesHaveAQueueTest(RecipeType type) {
        Assertions.assertNotNull(manager.getQueueForType(type));
    }

    @ParameterizedTest
    @EnumSource(RecipeType.class)
    void basicQueueTest(RecipeType type) {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT.stack(1),
                Item.DIRT.stack(1), 0, type);

        manager.addRecipeToQueue(recipe);

        Assertions.assertSame(recipe, manager.getNextRecipe(type).get());
    }

    @Test
    void unmodifiableQueueTest() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> manager.getQueues().put(null, null));
    }
}
