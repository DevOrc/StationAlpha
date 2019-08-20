package com.noahcharlton.stationalpha.block.workbench;

import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.item.RecipeType;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobQueueTests;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.load.LoadTestUtils;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.Arrays;

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
    void onDestroyJobNotAddedToQueueTest() {
        setupRecipe();
        container.createJobFromRecipe();
        Job job = container.getJob().get();

        container.onDestroy();

        JobQueueTests.assertNotInJobQueue(job);
    }

    @Test
    void createJobNoRecipeInManagerTest() {
        Assertions.assertFalse(container.createJobFromRecipe());
    }

    @Test
    void notEnoughRockForRecipeDoesNotStartJobTest() {
        world.getManufacturingManager().addRecipeToQueue(
                new ManufacturingRecipe(Item.SPACE_ROCK.stack(11), Item.TEST_ITEM.stack(1), 1));

        Assertions.assertFalse(container.createJobFromRecipe());
    }

    @Test
    void notEnoughRockForRecipeReAddsRecipeToQueueTest() {
        world.getManufacturingManager().addRecipeToQueue(
                new ManufacturingRecipe(Item.SPACE_ROCK.stack(0), Item.TEST_ITEM.stack(1), 1));

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


    @Test
    void getDebugInfoNoRecipeTest() {
        String expected = "Currently Producing: None";
        String[] actual = container.getDebugInfo();

        Assertions.assertTrue(Arrays.asList(actual).contains(expected));
    }

    @Test
    void getDebugInfoOutputNameTest() {
        setupRecipe();
        container.createJobFromRecipe();

        String expected = "Currently Producing: 1 Test Item";
        String[] actual = container.getDebugInfo();

        Assertions.assertTrue(Arrays.asList(actual).contains(expected));
    }

    @Test
    void getDebugInfoZeroPercentTest() {
        setupRecipe();
        container.createJobFromRecipe();

        String expected = "Progress: 0%";
        String[] actual = container.getDebugInfo();

        Assertions.assertTrue(Arrays.asList(actual).contains(expected));
    }

    @Test
    void getDebugInfo100PercentDoneTest() {
        setupRecipe();
        container.createJobFromRecipe();

        container.getJob().get().update();

        String expected = "Progress: 100%";
        String[] actual = container.getDebugInfo();

        Assertions.assertTrue(Arrays.asList(actual).contains(expected));
    }

    private void setupRecipe(){
        world.getManufacturingManager().addRecipeToQueue(
                new ManufacturingRecipe(Item.SPACE_ROCK.stack(1), Item.TEST_ITEM.stack(1), 1));

        world.getInventory().setAmountForItem(Item.SPACE_ROCK, 1);
    }

    @Test
    void onSaveNoJobTest() {
        StringWriter writer = new StringWriter();

        container.onSave(new QuietXmlWriter(writer));

        Assertions.assertEquals("", writer.toString());
    }

    @Test
    void onSaveBasicJobTest() {
        StringWriter writer = new StringWriter();
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT.stack(0), Item.SPACE_ROCK.stack(0),
                100, RecipeType.CRAFT);
        world.getManufacturingManager().addRecipeToQueue(recipe);

        container.createJobFromRecipe();
        container.onSave(new QuietXmlWriter(writer));

        String expected = "<Job>\n\t<Input item=\"DIRT\" amount=\"0\"/>\n\t" +
                "<Output item=\"SPACE_ROCK\" amount=\"0\"/>\n\t<Type>CRAFT</Type>\n\t" +
                "<Time>100</Time>\n\t<Tick>0</Tick>\n</Job>\n";
        Assertions.assertEquals(expected, writer.toString());
    }

    @Test
    void onLoadNothingNoJobTest() {
        container.onLoad(LoadTestUtils.asChild(""));

        Assertions.assertFalse(container.getJob().isPresent());
    }

    @Test
    void onLoadSetsJobTickTest() {
        String xml = "<Job>\n\t<Input item=\"UNOBTAINIUM\" amount=\"0\"/>\n\t" +
                "<Output item=\"SPACE_DUST\" amount=\"0\"/>\n\t<Type>CRAFT</Type>\n\t" +
                "<Time>100</Time>\n\t<Tick>42</Tick>\n</Job>\n";
        container.onLoad(LoadTestUtils.asChild(xml));

        Assertions.assertEquals(42, container.getJob().get().getTick());
    }

    @Test
    void onLoadSetsRecipeTest() {
        String xml = "<Job>\n\t<Input item=\"WOOD\" amount=\"32\"/>\n\t" +
                "<Output item=\"DIRT\" amount=\"11\"/>\n\t<Type>CRAFT</Type>\n\t" +
                "<Time>1235</Time>\n\t<Tick>0</Tick>\n</Job>\n";
        container.onLoad(LoadTestUtils.asChild(xml));

        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.WOOD.stack(32), Item.DIRT.stack(11),
                1235, RecipeType.CRAFT);
        Assertions.assertEquals(recipe, container.getJob().get().getRecipe());
    }
}
