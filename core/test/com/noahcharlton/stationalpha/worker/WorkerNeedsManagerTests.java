package com.noahcharlton.stationalpha.worker;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.block.bed.BedContainer;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.gui.scenes.message.MessageQueue;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.job.TestJob;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class WorkerNeedsManagerTests {

    private final Worker worker = new TestWorker();
    private final WorkerNeedsManager needsManager = worker.getAi().getNeedsManager();

    @Test
    void diesWithoutFoodTest() {
        needsManager.setFoodTick(0);
        needsManager.update();

        Assertions.assertTrue(worker.isDead());
    }

    @Test
    void foodTickResetsTest() {
        needsManager.setFoodTick(0);

        Assertions.assertTrue(WorkerNeedsManager.FOOD_RESET >= needsManager.getFoodTick());
    }

    @Test
    void createSleepJobTargetIsAdjacentToPillow() {
        World world = worker.getWorld();
        world.getInventory().fillAllItems();
        new BuildBlock(Blocks.getBedBlock()).onClick(world.getTileAt(0, 0).get(), Input.Buttons.LEFT);
        worker.setBedroom((BedContainer) world.getTileAt(0, 0).get().getContainer().get());

        SleepJob job = needsManager.createSleepJob();

         Assertions.assertEquals(new Tile(0, 1, world), job.getTarget());
    }

    @Test
    void createSleepJobBedNotAccessibleSleepsOnCurrentTile() {
        World world = worker.getWorld();
        world.getInventory().fillAllItems();
        new BuildBlock(Blocks.getBedBlock()).onClick(world.getTileAt(0, 0).get(), Input.Buttons.LEFT);
        world.getTileAt(0, 1).get().setBlock(Blocks.getWall());
        worker.setBedroom((BedContainer) world.getTileAt(0, 0).get().getContainer().get());

        worker.setPixelX(Tile.TILE_SIZE * 5);
        worker.setPixelY(Tile.TILE_SIZE * 4);
        SleepJob job = needsManager.createSleepJob();

        Assertions.assertEquals(new Tile(5, 4, world), job.getTarget());
    }

    @Test
    void createSleepJobWithoutBedroomSleepsOnCurrentTile() {
        worker.setPixelX(Tile.TILE_SIZE * 8);
        worker.setPixelY(Tile.TILE_SIZE * 6);

        worker.setBedroom(null);
        SleepJob job = needsManager.createSleepJob();

        Assertions.assertEquals(new Tile(8, 6, worker.getWorld()), job.getTarget());
    }

    @Test
    void isSleepingWithNoJobReturnsFalse() {
        worker.getAi().getJobManager().setCurrentJob(null);

        Assertions.assertFalse(needsManager.isSleeping());
    }

    @Test
    void isSleepingNonSleepJobReturnsFalse() {
        worker.getAi().getJobManager().setCurrentJob(new TestJob());

        Assertions.assertFalse(needsManager.isSleeping());
    }

    @Test
    void isSleepingWithJobReturnsTrue() {
        worker.getAi().getJobManager().setCurrentJob(needsManager.createSleepJob());

        Assertions.assertTrue(needsManager.isSleeping());
    }

    @Test
    void isSleepingJobFinishedReturnsFalse() {
        SleepJob job = needsManager.createSleepJob();
        worker.getAi().getJobManager().setCurrentJob(job);

        job.finish();

        Assertions.assertFalse(needsManager.isSleeping());
    }

    @Test
    void resetSleepTimeBasicTest() {
        needsManager.updateSleep();
        Assumptions.assumeFalse(needsManager.getSleepTick() == WorkerNeedsManager.SLEEP_RESET);

        needsManager.resetSleepTime();

        Assertions.assertTrue(needsManager.getSleepTick() >= WorkerNeedsManager.SLEEP_RESET);
    }

    @Test
    void startSleepingAfterTirednessThreshold() {
        needsManager.setSleepTick(0);

        needsManager.update();

        Assertions.assertTrue(worker.getAi().getJobManager().getCurrentJob().get() instanceof SleepJob);
    }

    @ParameterizedTest
    @EnumSource(value = Item.class, names = {"POTATO", "WOODROOT"})
    void itemDecreasesOnEatTest(Item item) {
        worker.getWorld().getInventory().setAmountForItem(item, 1);

        needsManager.setFoodTick(0);
        needsManager.update();

        Assertions.assertEquals(0, worker.getWorld().getInventory().getAmountForItem(item));
    }

    @Test
    void doesNotGetHungryWhileSleepingTest() {
        needsManager.setSleepTick(0);
        int foodTick = needsManager.getFoodTick();

        needsManager.update();

        Assertions.assertEquals(foodTick, needsManager.getFoodTick());
    }

    @Test
    void showMessageWhenLessThan10PotatoesTest() {
        MessageQueue.getInstance().getMessages().clear();
        worker.getWorld().getInventory().setAmountForItem(Item.POTATO, 10);

        needsManager.eat();
        needsManager.eat();

        Assertions.assertEquals(1, MessageQueue.getInstance().getMessages().size());
    }
}
