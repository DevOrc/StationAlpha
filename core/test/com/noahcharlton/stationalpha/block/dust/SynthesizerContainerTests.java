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
import com.noahcharlton.stationalpha.worker.job.JobQueueTests;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.load.LoadTestUtils;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Optional;

public class SynthesizerContainerTests {

    private final World world = new World();
    private final Tile tile = world.getTileAt(0, 1).get();
    private SynthesizerContainer container;

    @BeforeEach
    void setUp() {
        world.getInventory().changeAmountForItem(Item.UNOBTAINIUM, 1);
        world.getInventory().changeAmountForItem(Item.COPPER, 5);
        world.getInventory().changeAmountForItem(Item.STEEL, 15);

        BuildBlock builder = new BuildBlock(Blocks.getSynthesizer());
        builder.onClick(tile, Input.Buttons.LEFT);

        container = (SynthesizerContainer) tile.getContainer().get();
    }

    @Test
    void onDestroyJobIsCancelledTest() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(
                Item.DIRT.stack(0), Item.LEAVES.stack(0), 250, RecipeType.CRAFT);
        SynthesizerJob job = new SynthesizerJob(tile, recipe, container);
        container.setCurrentJob(job);

        job.start();
        container.onDestroy();

        Assertions.assertEquals(Job.JobStage.PRE_START, job.getStage());
    }

    @Test
    void onDestroyJobNotAddedToQueueTest() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(
                Item.DIRT.stack(0), Item.LEAVES.stack(0), 250, RecipeType.CRAFT);
        SynthesizerJob job = new SynthesizerJob(tile, recipe, container);
        container.setCurrentJob(job);
        JobQueue.getInstance().addJob(job);

        container.onDestroy();

        JobQueueTests.assertNotInJobQueue(job);
    }

    @Test
    void jobRemovedOnFinish() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(
                Item.DIRT.stack(0), Item.LEAVES.stack(0), 250, RecipeType.CRAFT);
        SynthesizerJob job = new SynthesizerJob(tile, recipe, container);
        container.setCurrentJob(job);

        job.start();
        job.finish();
        container.onUpdate();

        Assertions.assertEquals(Optional.empty(), container.getCurrentJob());
    }

    @Test
    void startJobRemovesRequirementsTest() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT.stack(1), Item.SPACE_ROCK.stack(1),
                100, RecipeType.SYNTHESIZE);
        Inventory inventory = world.getInventory();
        inventory.setAmountForItem(Item.DIRT, 4);

        container.startJob(recipe, tile);

        Assertions.assertEquals(3, inventory.getAmountForItem(Item.DIRT));
    }

    @Test
    void startJobAddsJobToJobQueue() {
        JobQueue.getInstance().getJobQueue(WorkerRole.SCIENTIST).clear();
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT.stack(0), Item.SPACE_ROCK.stack(0),
                100, RecipeType.SYNTHESIZE);

        container.startJob(recipe, tile);

        Assertions.assertSame(JobQueue.getInstance().get(WorkerRole.SCIENTIST).get(), container.getCurrentJob().get());
    }

    @Test
    void doesNotStartJobIfNotEnoughResourcesTest() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.TEST_ITEM.stack(15), Item.SPACE_ROCK.stack(0), 100, RecipeType.SYNTHESIZE);
        world.getManufacturingManager().addRecipeToQueue(recipe);

        container.checkAndCreateJob();

        Assertions.assertEquals(Optional.empty(), container.getCurrentJob());
    }

    @Test
    void doesNotStartJobReAddsRecipeTest() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.TEST_ITEM.stack(15), Item.SPACE_ROCK.stack(0),
                100, RecipeType.SYNTHESIZE);
        world.getManufacturingManager().addRecipeToQueue(recipe);

        container.checkAndCreateJob();

        Assertions.assertSame(recipe, world.getManufacturingManager().getNextRecipe(RecipeType.SYNTHESIZE).get());
    }

    @Test
    void checkAndCreateJobTileNotAvailableDoesNotStartJob() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT.stack(-0), Item.SPACE_ROCK.stack(0),
                100, RecipeType.SYNTHESIZE);
        world.getManufacturingManager().addRecipeToQueue(recipe);
        world.getTileAt(1, 0).get().setBlock(Blocks.getWall());

        container.checkAndCreateJob();

        Assertions.assertEquals(Optional.empty(), container.getCurrentJob());
    }

    @Test
    void checkAndCreateJobTileNotAvailableReAddsRecipeToQueue() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT.stack(0), Item.SPACE_ROCK.stack(0),
                100, RecipeType.SYNTHESIZE);
        world.getManufacturingManager().addRecipeToQueue(recipe);
        world.getTileAt(1, 0).get().setBlock(Blocks.getWall());

        container.checkAndCreateJob();

        Assertions.assertSame(recipe, world.getManufacturingManager().getNextRecipe(RecipeType.SYNTHESIZE).get());
    }

    @Test
    void checkAndCreateJobEverythingValidCreatesJobTest() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT.stack(0), Item.SPACE_ROCK.stack(0),
                100, RecipeType.SYNTHESIZE);
        world.getManufacturingManager().addRecipeToQueue(recipe);

        container.checkAndCreateJob();

        Assertions.assertTrue(container.getCurrentJob().isPresent());
    }

    @Test
    void currentJobDefaultsToEmptyTest() {
        Assertions.assertEquals(Optional.empty(), container.getCurrentJob());
    }

    @Test
    void getDebugInfoNoRecipeTest() {
        String expected = "Currently Producing: None";
        String[] actual = container.getDebugInfo();

        Assertions.assertTrue(Arrays.asList(actual).contains(expected));
    }

    @Test
    void getDebugInfoOutputNameTest() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT.stack(0), Item.SPACE_ROCK.stack(0),
                100, RecipeType.SYNTHESIZE);
        world.getManufacturingManager().addRecipeToQueue(recipe);

        container.checkAndCreateJob();

        String expected = "Currently Producing: 0 Space Rock";
        String[] actual = container.getDebugInfo();

        Assertions.assertTrue(Arrays.asList(actual).contains(expected));
    }

    @Test
    void getDebugInfoZeroPercentTest() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT.stack(0), Item.SPACE_ROCK.stack(0),
                100, RecipeType.SYNTHESIZE);
        world.getManufacturingManager().addRecipeToQueue(recipe);

        container.checkAndCreateJob();

        String expected = "Progress: 0%";
        String[] actual = container.getDebugInfo();

        Assertions.assertTrue(Arrays.asList(actual).contains(expected));
    }

    @Test
    void getDebugInfo50PercentDoneTest() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT.stack(0), Item.SPACE_ROCK.stack(0),
                100, RecipeType.SYNTHESIZE);
        world.getManufacturingManager().addRecipeToQueue(recipe);

        container.checkAndCreateJob();

        for(int i = 0; i < recipe.getTime() / 2; i++){
            tile.setPower(25);
            container.getCurrentJob().get().update();
        }

        String expected = "Progress: 50%";
        String[] actual = container.getDebugInfo();

        Assertions.assertTrue(Arrays.asList(actual).contains(expected));
    }

    @Test
    void onSaveNoJobTest() {
        StringWriter writer = new StringWriter();

        container.setCurrentJob(null);
        container.onSave(new QuietXmlWriter(writer));

        Assertions.assertEquals("", writer.toString());
    }

    @Test
    void onSaveBasicJobTest() {
        StringWriter writer = new StringWriter();
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT.stack(0), Item.SPACE_ROCK.stack(0),
                100, RecipeType.SYNTHESIZE);
        world.getManufacturingManager().addRecipeToQueue(recipe);

        container.checkAndCreateJob();
        container.onSave(new QuietXmlWriter(writer));

        String expected = "<Job>\n\t<Input item=\"DIRT\" amount=\"0\"/>\n\t" +
                "<Output item=\"SPACE_ROCK\" amount=\"0\"/>\n\t<Type>SYNTHESIZE</Type>\n\t" +
                "<Time>100</Time>\n\t<Tick>0</Tick>\n</Job>\n";
        Assertions.assertEquals(expected, writer.toString());
    }

    @Test
    void onLoadNothingNoJobTest() {
        container.onLoad(LoadTestUtils.asChild(""));

        Assertions.assertFalse(container.getCurrentJob().isPresent());
    }

    @Test
    void onLoadSetsJobTickTest() {
        String xml = "<Job>\n\t<Input item=\"DIRT\" amount=\"0\"/>\n\t" +
                "<Output item=\"SPACE_ROCK\" amount=\"0\"/>\n\t<Type>SYNTHESIZE</Type>\n\t" +
                "<Time>100</Time>\n\t<Tick>52</Tick>\n</Job>\n";
        container.onLoad(LoadTestUtils.asChild(xml));

        Assertions.assertEquals(52, container.getCurrentJob().get().getTick());
    }

    @Test
    void onLoadSetsRecipeTest() {
        String xml = "<Job>\n\t<Input item=\"STEEL\" amount=\"32\"/>\n\t" +
                "<Output item=\"WOOD\" amount=\"11\"/>\n\t<Type>SYNTHESIZE</Type>\n\t" +
                "<Time>1235</Time>\n\t<Tick>0</Tick>\n</Job>\n";
        container.onLoad(LoadTestUtils.asChild(xml));

        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.STEEL.stack(32), Item.WOOD.stack(11),
                1235, RecipeType.SYNTHESIZE);
        Assertions.assertEquals(recipe, container.getCurrentJob().get().getRecipe());
    }
}
