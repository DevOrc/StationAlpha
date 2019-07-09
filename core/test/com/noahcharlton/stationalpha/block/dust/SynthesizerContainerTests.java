package com.noahcharlton.stationalpha.block.dust;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.item.RecipeType;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobQueue;
import com.noahcharlton.stationalpha.worker.job.TestJob;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class SynthesizerContainerTests {

    private final World world = new World();
    private final Tile tile = world.getTileAt(0, 1).get();
    private SynthesizerContainer container;

    @BeforeEach
    void setUp() {
        world.getInventory().changeAmountForItem(Item.UNOBTAINIUM, 1);
        BuildBlock builder = new BuildBlock(Blocks.getSynthesizer());
        builder.onClick(tile, Input.Buttons.LEFT);

        container = (SynthesizerContainer) tile.getContainer().get();
    }

    @Test
    void onDestroyJobIsCancelledTest() {
        TestJob job = new TestJob();
        container.setCurrentJob(Optional.of(job));

        job.start();
        container.onDestroy();

        Assertions.assertEquals(Job.JobStage.PRE_START, job.getStage());
    }

    @Test
    void jobRemovedOnFinish() {
        TestJob job = new TestJob();
        container.setCurrentJob(Optional.of(job));

        job.start();
        job.finish();
        container.onUpdate();

        Assertions.assertEquals(Optional.empty(), container.getCurrentJob());
    }

    @Test
    void startJobRemovesRequirementsTest() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT, 1, Item.SPACE_ROCK, 1,
                100, RecipeType.SYNTHESIZE);
        Inventory inventory = world.getInventory();
        inventory.setAmountForItem(Item.DIRT, 4);

        container.startJob(recipe, tile);

        Assertions.assertEquals(3, inventory.getAmountForItem(Item.DIRT));
    }

    @Test
    void startJobAddsJobToJobQueue() {
        JobQueue.getInstance().getJobQueue(WorkerRole.SCIENTIST).clear();
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT, 0, Item.SPACE_ROCK, 0,
                100, RecipeType.SYNTHESIZE);

        container.startJob(recipe, tile);

        Assertions.assertSame(JobQueue.getInstance().get(WorkerRole.SCIENTIST).get(), container.getCurrentJob().get());
    }

    @Test
    void checkAndCreateJobTileNotAvailableDoesNotStartJob() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT, 0, Item.SPACE_ROCK, 0,
                100, RecipeType.SYNTHESIZE);
        world.getManufacturingManager().addRecipeToQueue(recipe);
        world.getTileAt(1, 0).get().setBlock(Blocks.getWall());

        container.checkAndCreateJob();

        Assertions.assertEquals(Optional.empty(), container.getCurrentJob());
    }

    @Test
    void checkAndCreateJobTileNotAvailableReAddsRecipeToQueue() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT, 0, Item.SPACE_ROCK, 0,
                100, RecipeType.SYNTHESIZE);
        world.getManufacturingManager().addRecipeToQueue(recipe);
        world.getTileAt(1, 0).get().setBlock(Blocks.getWall());

        container.checkAndCreateJob();

        Assertions.assertSame(recipe, world.getManufacturingManager().getNextRecipe(RecipeType.SYNTHESIZE).get());
    }

    @Test
    void checkAndCreateJobEverythingValidCreatesJobTest() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT, 0, Item.SPACE_ROCK, 0,
                100, RecipeType.SYNTHESIZE);
        world.getManufacturingManager().addRecipeToQueue(recipe);

        container.checkAndCreateJob();

        Assertions.assertTrue(container.getCurrentJob().isPresent());
    }

    @Test
    void currentJobDefaultsToEmptyTest() {
        Assertions.assertEquals(Optional.empty(), container.getCurrentJob());
    }
}
