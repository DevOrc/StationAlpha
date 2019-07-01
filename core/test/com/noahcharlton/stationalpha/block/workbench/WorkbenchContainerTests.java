package com.noahcharlton.stationalpha.block.workbench;

import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.item.RecipeType;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WorkbenchContainerTests {

    private final World world = new World();
    private final Tile tile = world.getTileAt(1, 1).get();
    private final WorkbenchContainer container = new WorkbenchContainer(tile, Blocks.getWorkbench(), BlockRotation.NORTH);

    @Test
    void onDestroyCancelJobTest() {
        setupRecipe();
        container.createJobFromRecipe();
        container.onDestroy();

        Assertions.assertTrue(container.getJob().get().getStage() == Job.JobStage.PRE_START);
        Assertions.assertFalse(container.getJob().get().getAssignedWorker().isPresent());
    }

    @Test
    void createJobNoRecipeInManagerTest() {
        Assertions.assertFalse(container.createJobFromRecipe());
    }

    @Test
    void notEnoughRockForRecipeDoesNotStartJobTest() {
        world.getManufacturingManager().addRecipeToQueue(
                new ManufacturingRecipe(Item.SPACE_ROCK, 1, Item.TEST_ITEM, 1, 1));

        Assertions.assertFalse(container.createJobFromRecipe());
    }

    @Test
    void notEnoughRockForRecipeReAddsRecipeToQueueTest() {
        world.getManufacturingManager().addRecipeToQueue(
                new ManufacturingRecipe(Item.SPACE_ROCK, 1, Item.TEST_ITEM, 1, 1));

        Assertions.assertTrue(world.getManufacturingManager().getNextRecipe(RecipeType.CRAFT).isPresent());
    }

    @Test
    void createJobNoSpaceReturnsFalse() {
        world.getTileAt(1, 0).get().setBlock(Blocks.getWall());

        Assertions.assertFalse(container.createJobFromRecipe());
    }

    @Test
    void createJobBasicTest() {
        setupRecipe();

        Assertions.assertTrue(container.createJobFromRecipe());
    }

    @Test
    void createJobDecreaseRockTest() {
        setupRecipe();
        container.createJobFromRecipe();

        Assertions.assertEquals(0, world.getInventory().getAmountForItem(Item.SPACE_ROCK));
    }

    @Test
    void emptyJobWhenFinished() {
        setupRecipe();
        container.createJobFromRecipe();

        container.getJob().get().finish();
        container.onUpdate();

        Assertions.assertFalse(container.getJob().isPresent());
    }

    private void setupRecipe(){
        world.getManufacturingManager().addRecipeToQueue(
                new ManufacturingRecipe(Item.SPACE_ROCK, 1, Item.TEST_ITEM, 1, 1));

        world.getInventory().setAmountForItem(Item.SPACE_ROCK, 1);
    }
}
