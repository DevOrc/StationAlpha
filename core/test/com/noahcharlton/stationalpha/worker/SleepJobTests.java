package com.noahcharlton.stationalpha.worker;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.block.bed.BedContainer;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobTests;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

public class SleepJobTests extends JobTests {

    private World world;
    private Worker worker;
    private SleepJob sleepJob;

    @Override
    protected void assignedWorkerEmptyByDefaultTest() {
        //Sleep Jobs (unlike most jobs) have a worker on object construction
        Assertions.assertTrue(sleepJob.getAssignedWorker().isPresent());
    }

    @Override
    protected void permanentCancelDoesNotAddToJobQueueTest() {
        //Should not run cause sleep job will fail if added to the queue
    }

    @Test
    void finishesAfter360TicksTest() {
        for(int i = 0; i < 360; i++) {
            sleepJob.update();
        }

        Assertions.assertEquals(Job.JobStage.FINISHED, sleepJob.getStage());
    }

    @Test
    void onFinishResetsSleepTick() {
        worker.getAi().getNeedsManager().update();
        worker.getAi().getNeedsManager().setSleepTick(25);
        sleepJob.finish();

        Assertions.assertTrue(WorkerNeedsManager.SLEEP_RESET <=
                worker.getAi().getNeedsManager().getSleepTick());
    }

    @Test
    void hasAccessibleBedroomBasicTest() {
        World world = new World();
        world.getInventory().fillAllItems();
        BuildBlock buildBlock = new BuildBlock(Blocks.getBedBlock());
        buildBlock.onClick(world.getTileAt(0, 0).get(), Input.Buttons.LEFT);
        BedContainer container = (BedContainer) world.getTileAt(0, 0).get().getContainer().get();

        TestWorker worker = new TestWorker();
        worker.setBedroom(container);
        SleepJob sleepJob = new SleepJob(null, worker);

        Assertions.assertTrue(sleepJob.hasAccessibleBedroom());
    }

    @Test
    void hasAccessibleBedroomBlockedTest() {
        World world = new World();
        world.getInventory().fillAllItems();
        BuildBlock buildBlock = new BuildBlock(Blocks.getBedBlock());
        buildBlock.onClick(world.getTileAt(0, 0).get(), Input.Buttons.LEFT);
        BedContainer container = (BedContainer) world.getTileAt(0, 0).get().getContainer().get();

        TestWorker worker = new TestWorker();
        worker.setBedroom(container);
        SleepJob sleepJob = new SleepJob(null, worker);
        Assumptions.assumeTrue(sleepJob.hasAccessibleBedroom());

        world.getTileAt(0, 1).get().setBlock(Blocks.getWall());

        Assertions.assertFalse(sleepJob.hasAccessibleBedroom());
    }

    @Test
    void hasAccessibleBedroomNoBedroomTest() {
        TestWorker worker = new TestWorker();
        SleepJob sleepJob = new SleepJob(null, worker);

        Assertions.assertFalse(sleepJob.hasAccessibleBedroom());
    }

    @Override
    public Job getJob() {
        world = new World();
        new BuildBlock(Blocks.getBedBlock()).onClick(world.getTileAt(0, 0).get(), Input.Buttons.LEFT);

        worker = new TestWorker();
        sleepJob = new SleepJob(world.getTileAt(0, 0).get().getOpenAdjecent().get(), worker);

        return sleepJob;
    }
}
