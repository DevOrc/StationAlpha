package com.noahcharlton.stationalpha.block.composter;

import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.item.RecipeType;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.TestJob;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class ComposterContainerTests {

    private final World world = new World();
    private final Tile tile = world.getTileAt(0, 0).get();
    private ComposterContainer container;

    @BeforeEach
    void setup(){
        tile.setBlock(Blocks.getComposter());

        container = (ComposterContainer) tile.getContainer().get();
    }

    @Test
    void currentRecipeEmptyByDefault() {
        Assertions.assertEquals(container.getCurrentRecipe(), Optional.empty());
    }

    @Test
    void currentJobEmptyByDefault() {
        Assertions.assertEquals(container.getCurrentJob(), Optional.empty());
    }

    @Test
    void tickEmptyByDefault() {
        Assertions.assertEquals(container.getTick(), Optional.empty());
    }

    @Test
    void jobCanceledOnContainerDestroyed() {
        Job job = new TestJob();
        job.start();
        container.setCurrentJob(job);

        container.onDestroy();

        Assertions.assertEquals(Job.JobStage.PRE_START, job.getStage());
    }

    @Test
    void compostStartedSetJobToEmpty() {
        Job job = new TestJob();
        container.setCurrentJob(job);

        container.onCompostStarted();

        Assertions.assertEquals(Optional.empty(), container.getCurrentJob());
    }

    @Test
    void compostFinishedRecipeReset() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT, 1,
                Item.DIRT, 1, 100);
        container.setCurrentRecipe(recipe);

        container.onCompostFinished();

        Assertions.assertEquals(Optional.empty(), container.getCurrentRecipe());
    }

    @Test
    void compostFinishedJobReset() {
        Job job = new TestJob();
        container.setCurrentJob(job);

        container.onCompostFinished();

        Assertions.assertEquals(Optional.empty(), container.getCurrentJob());
    }

    @Test
    void compostFinishedTickReset() {
        container.setTick(100);

        container.onCompostFinished();

        Assertions.assertEquals(Optional.empty(), container.getTick());
    }

    @Test
    void tickSetOnCompostStart() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT, 1,
                Item.DIRT, 1, 123);
        container.setCurrentRecipe(recipe);

        container.onCompostStarted();

        Assertions.assertEquals(Optional.of(123), container.getTick());
    }

    @Test
    void startCompostingNoJobDoesNotCreateJobTest() {
        container.startComposting();

        Assertions.assertEquals(Optional.empty(), container.getCurrentJob());
    }

    @Test
    void startCompostingNoOpenTileDoesNotCreateJobTest() {
        world.getTileAt(0, 1).get().setBlock(Blocks.getWall());
        world.getTileAt(1, 0).get().setBlock(Blocks.getWall());
        world.getManufacturingManager().addRecipeToQueue(new ManufacturingRecipe(Item.DIRT, 0,
                Item.DIRT, 0, 123, RecipeType.COMPOST));

        container.startComposting();

        Assertions.assertEquals(Optional.empty(), container.getCurrentJob());
    }

    @Test
    void startCompostingNotEnoughResourcesDoesNotCreateJobTest() {
        world.getTileAt(0, 1).get().setBlock(Blocks.getWall());
        world.getTileAt(1, 0).get().setBlock(Blocks.getWall());
        world.getManufacturingManager().addRecipeToQueue(new ManufacturingRecipe(Item.DIRT, 1,
                Item.DIRT, 0, 123, RecipeType.COMPOST));

        container.startComposting();

        Assertions.assertEquals(Optional.empty(), container.getCurrentJob());
    }

    @Test
    void startCompostingBasicTest() {
        world.getManufacturingManager().addRecipeToQueue(new ManufacturingRecipe(Item.DIRT, 0,
                Item.DIRT, 0, 123, RecipeType.COMPOST));

        container.startComposting();

        Assertions.assertTrue(container.getCurrentJob().isPresent());
    }

    @Test
    void startCompostingRemovesRequirementsTest() {
        world.getManufacturingManager().addRecipeToQueue(new ManufacturingRecipe(Item.DIRT, 3,
                Item.DIRT, 0, 123, RecipeType.COMPOST));
        world.getInventory().changeAmountForItem(Item.DIRT, 5);

        container.startComposting();

        Assertions.assertEquals(2, world.getInventory().getAmountForItem(Item.DIRT));
    }

    @Test
    void updateCompostSubtractsTickWhenAboveZeroTest() {
        container.setTick(103);

        container.updateCompost(103);

        Assertions.assertEquals(Optional.of(102), container.getTick());
    }

    @Test
    void updateCompostZeroTickCreatesEndJobTest() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT, 1,
                Item.DIRT, 1, 123);
        container.setCurrentRecipe(recipe);
        container.updateCompost(0);

        Assertions.assertTrue(container.getCurrentJob().isPresent());
    }

    @Test
    void cannotCreateEndJobWhenAllTilesFullTest() {
        world.getTileAt(0, 1).get().setBlock(Blocks.getWall());
        world.getTileAt(1, 0).get().setBlock(Blocks.getWall());

        container.createEndCompostJob();

        Assertions.assertEquals(Optional.empty(), container.getCurrentJob());
    }
}
