package com.noahcharlton.stationalpha.worker;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.block.bed.BedContainer;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.job.TestJob;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class WorkerNeedsManagerTests {

    private final Worker worker = new TestWorker();
    private final WorkerNeedsManager needsManager = worker.getAi().getNeedsManager();

    @Test
    void diesWithoutFoodTest() {
        for(int i = 0; i < 1200; i++){
            needsManager.update();
        }

        Assertions.assertTrue(worker.isDead());
    }

    @Test
    void foodTickResetsTest() {
        for(int i = 0; i < 1200; i++){
            needsManager.update();
        }

        Assertions.assertEquals(0, needsManager.getFoodTick());
    }

    @Test
    void createSleepJobTargetIsAdjacentToPillow() {
        World world = worker.getWorld();
        new BuildBlock(Blocks.getBedBlock()).onClick(world.getTileAt(0, 0).get(), Input.Buttons.LEFT);
        worker.setBedroom(Optional.of((BedContainer) world.getTileAt(0, 0).get().getContainer().get()));

        SleepJob job = needsManager.createSleepJob();

         Assertions.assertEquals(new Tile(0, 1, world), job.getTarget());
    }

    @Test
    void createSleepJobBedNotAccessibleSleepsOnCurrentTile() {
        World world = worker.getWorld();
        new BuildBlock(Blocks.getBedBlock()).onClick(world.getTileAt(0, 0).get(), Input.Buttons.LEFT);
        world.getTileAt(0, 1).get().setBlock(Blocks.getWall());
        worker.setBedroom(Optional.of((BedContainer) world.getTileAt(0, 0).get().getContainer().get()));

        worker.setPixelX(Tile.TILE_SIZE * 5);
        worker.setPixelY(Tile.TILE_SIZE * 4);
        SleepJob job = needsManager.createSleepJob();

        Assertions.assertEquals(new Tile(5, 4, world), job.getTarget());
    }

    @Test
    void createSleepJobWithoutBedroomSleepsOnCurrentTile() {
        worker.setPixelX(Tile.TILE_SIZE * 8);
        worker.setPixelY(Tile.TILE_SIZE * 6);

        worker.setBedroom(Optional.empty());
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
    void finishSleepResetsSleepTick() {
        needsManager.updateSleep();
        Assumptions.assumeFalse(needsManager.getSleepTick() == WorkerNeedsManager.SLEEP_RESET);

        needsManager.finishSleep();

        Assertions.assertTrue(needsManager.getSleepTick() == WorkerNeedsManager.SLEEP_RESET);
    }

    @Test
    void startSleepingAfterTirednessThreshold() {
        needsManager.setSleepTick(0);

        needsManager.update();

        Assertions.assertTrue(worker.getAi().getJobManager().getCurrentJob().get() instanceof SleepJob);
    }

    @Test
    void potatoDecreasesOnEatTest() {
        worker.getWorld().getInventory().setAmountForItem(Item.POTATO, 1);

        for(int i = 0; i < 1200; i++){
            needsManager.update();
        }

        Assertions.assertEquals(0, worker.getWorld().getInventory().getAmountForItem(Item.POTATO));
    }
}
