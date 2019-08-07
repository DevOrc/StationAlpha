package com.noahcharlton.stationalpha.block.composter;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.item.RecipeType;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.TestJob;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.load.LoadTestUtils;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
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
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT.stack(1), Item.DIRT.stack(1), 100);
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
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT.stack(1), Item.DIRT.stack(1), 123);

        container.setCurrentRecipe(recipe);

        container.onCompostStarted();

        Assertions.assertEquals(Optional.of(123), container.getTick());
    }

    @Test
    void startCompostingNoJobDoesNotCreateJobTest() {
        container.attemptToStartComposting();

        Assertions.assertEquals(Optional.empty(), container.getCurrentJob());
    }

    @Test
    void startCompostingNoOpenTileDoesNotCreateJobTest() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT.stack(0), Item.DIRT.stack(0), 100);
        world.getManufacturingManager().addRecipeToQueue(recipe);

        world.getTileAt(0, 1).get().setBlock(Blocks.getWall());
        world.getTileAt(1, 0).get().setBlock(Blocks.getWall());
        container.attemptToStartComposting();

        Assertions.assertEquals(Optional.empty(), container.getCurrentJob());
    }

    @Test
    void startCompostingNotEnoughResourcesDoesNotCreateJobTest() {
        world.getTileAt(0, 1).get().setBlock(Blocks.getWall());
        world.getTileAt(1, 0).get().setBlock(Blocks.getWall());
        world.getManufacturingManager().addRecipeToQueue(new ManufacturingRecipe(Item.DIRT.stack(1),
                Item.DIRT.stack(0), 123, RecipeType.COMPOST));

        container.attemptToStartComposting();

        Assertions.assertEquals(Optional.empty(), container.getCurrentJob());
    }

    @Test
    void createCompostingJobNoOpenTileReAddsRecipeTestTest() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT.stack(0), Item.DIRT.stack(0),
                100, RecipeType.COMPOST);

        world.getTileAt(0, 1).get().setBlock(Blocks.getWall());
        world.getTileAt(1, 0).get().setBlock(Blocks.getWall());
        container.createCompostingJob(recipe);

        Assertions.assertSame(recipe, world.getManufacturingManager().getNextRecipe(RecipeType.COMPOST).get());
    }

    @Test
    void attemptToStartCompostingNotEnoughResourcesReAddsRecipeTest() {
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT.stack(1), Item.DIRT.stack(0),
                123, RecipeType.COMPOST);
        world.getManufacturingManager().addRecipeToQueue(recipe);

        container.attemptToStartComposting();

        Assertions.assertSame(recipe, world.getManufacturingManager().getNextRecipe(RecipeType.COMPOST).get());
    }

    @Test
    void startCompostingBasicTest() {
        world.getManufacturingManager().addRecipeToQueue(new ManufacturingRecipe(Item.DIRT.stack(0),
                Item.DIRT.stack(0), 123, RecipeType.COMPOST));

        container.attemptToStartComposting();

        Assertions.assertTrue(container.getCurrentJob().isPresent());
    }

    @Test
    void startCompostingRemovesRequirementsTest() {
        world.getManufacturingManager().addRecipeToQueue(new ManufacturingRecipe(Item.DIRT.stack(3),
                Item.DIRT.stack(0), 123, RecipeType.COMPOST));
        world.getInventory().changeAmountForItem(Item.DIRT, 5);

        container.attemptToStartComposting();

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
        ManufacturingRecipe recipe = new ManufacturingRecipe(Item.DIRT.stack(1),
                Item.DIRT.stack(1), 123);
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

    @Test
    void onSaveNoRecipeSavesNothingTest() {
        StringWriter writer = new StringWriter();
        container.setCurrentRecipe(null);

        container.onSave(new QuietXmlWriter(writer));

        Assertions.assertEquals("", writer.toString());
    }

    @Test
    void onSaveNoTickHasNoTickTest() {
        ManufacturingRecipe recipe =
                new ManufacturingRecipe(Item.LEAVES.stack(0), Item.LEAVES.stack(0), 50, RecipeType.CRAFT);
        StringWriter writer = new StringWriter();
        container.setCurrentRecipe(recipe);

        container.onSave(new QuietXmlWriter(writer));

        XmlReader.Element output = LoadTestUtils.asElement(writer.toString());
        Assertions.assertFalse(output.hasChild("Tick"));
    }

    @Test
    void onSaveHasTickTest() {
        ManufacturingRecipe recipe =
                new ManufacturingRecipe(Item.LEAVES.stack(0), Item.LEAVES.stack(0), 50, RecipeType.CRAFT);
        StringWriter writer = new StringWriter();
        container.setCurrentRecipe(recipe);
        container.setTick(542);

        container.onSave(new QuietXmlWriter(writer));

        XmlReader.Element output = LoadTestUtils.asElement(writer.toString());
        Assertions.assertEquals(542, output.getInt("Tick"));
    }

    @Test
    void loadRecipeNoTickCreatesJobTest() {
        String xml = "<Recipe>" +
                "<Time>25000</Time>" +
                "<Type>SYNTHESIZE</Type>" +
                "<Input item=\"LEAVES\" amount=\"5\"/>" +
                "<Output item=\"DIRT\" amount=\"3\"/>" +
                "</Recipe>";

        container.onLoad(LoadTestUtils.asChild(xml));

        Assertions.assertTrue(container.getCurrentJob().isPresent());
    }

    @Test
    void onLoadNoRecipeTest() {
        container.onLoad(LoadTestUtils.asChild(""));

        Assertions.assertFalse(container.getCurrentRecipe().isPresent());
    }

    @Test
    void onLoadNoRecipeNoTickTest() {
        container.onLoad(LoadTestUtils.asChild(""));

        Assertions.assertFalse(container.getTick().isPresent());
    }

    @Test
    void onLoadNoRecipeNoJobTest() {
        container.onLoad(LoadTestUtils.asChild(""));

        Assertions.assertFalse(container.getCurrentJob().isPresent());
    }

    @Test
    void loadTickNoTickTest() {
        container.loadTick(LoadTestUtils.asChild(""));

        Assertions.assertFalse(container.getTick().isPresent());
    }

    @Test
    void loadTickRecipeTickTest() {
        container.loadTick(LoadTestUtils.asChild("<Tick>19295</Tick>"));

        Assertions.assertEquals(19295, (int) container.getTick().get());
    }
}
