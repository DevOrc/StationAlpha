package com.noahcharlton.stationalpha.block.bed;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.SleepJob;
import com.noahcharlton.stationalpha.worker.TestWorker;
import com.noahcharlton.stationalpha.worker.job.TestJob;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

public class BedRendererTests {

    private final BedRenderer renderer = new BedRenderer(Blocks.getBedBlock());

    @Test
    void isOccupiedEmptyWorkerReturnsFalseTest() {
        Assertions.assertFalse(renderer.isBedOccupied(null));
    }

    @Test
    void isOccupiedNonSleepJobTest() {
        TestWorker worker = new TestWorker();
        worker.getAi().getJobManager().setCurrentJob(new TestJob());

        Assertions.assertFalse(renderer.isBedOccupied(null));
    }

    @Test
    void isOccupiedSleepJobPreStartReturnsFalseTest() {
        TestWorker worker = new TestWorker();
        SleepJob sleepJob = new SleepJob(null, worker);
        worker.getAi().getJobManager().setCurrentJob(sleepJob);

        Assertions.assertFalse(renderer.isBedOccupied(worker));
    }

    @Test
    void isOccupiedSleepJobFinishedReturnsFalseTest() {
        TestWorker worker = new TestWorker();
        SleepJob sleepJob = new SleepJob(null, worker);
        worker.getAi().getJobManager().setCurrentJob(sleepJob);

        sleepJob.finish();

        Assertions.assertFalse(renderer.isBedOccupied(worker));
    }

    @Test
    void isOccupiedBedNotAccessibleReturnsFalseTest() {
        World world = new World();
        world.getInventory().changeAmountForItem(Item.STEEL, 1);
        BuildBlock buildBlock = new BuildBlock(Blocks.getBedBlock());
        buildBlock.onClick(world.getTileAt(0 ,0).get(), Input.Buttons.LEFT);
        BedContainer container = (BedContainer) world.getTileAt(0,0).get().getContainer().get();

        TestWorker worker = new TestWorker();
        SleepJob sleepJob = new SleepJob(null, worker);
        worker.getAi().getJobManager().setCurrentJob(sleepJob);

        sleepJob.start();
        worker.setBedroom(container);
        Assumptions.assumeTrue(renderer.isBedOccupied(worker));

        world.getTileAt(0, 1).get().setBlock(Blocks.getWall());

        Assertions.assertFalse(renderer.isBedOccupied(worker));
    }

    @Test
    void isOccupiedBasicTest() {
        World world = new World();
        world.getInventory().changeAmountForItem(Item.STEEL, 1);
        BuildBlock buildBlock = new BuildBlock(Blocks.getBedBlock());
        buildBlock.onClick(world.getTileAt(0 ,0).get(), Input.Buttons.LEFT);
        BedContainer container = (BedContainer) world.getTileAt(0,0).get().getContainer().get();

        TestWorker worker = new TestWorker();
        SleepJob sleepJob = new SleepJob(null, worker);
        worker.getAi().getJobManager().setCurrentJob(sleepJob);
        worker.setBedroom(container);

        sleepJob.start();

        Assertions.assertTrue(renderer.isBedOccupied(worker));
    }
}
