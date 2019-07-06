package com.noahcharlton.stationalpha.block.bed;

import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.worker.SleepJob;
import com.noahcharlton.stationalpha.worker.TestWorker;
import com.noahcharlton.stationalpha.worker.job.TestJob;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class BedRendererTests {

    private final BedRenderer renderer = new BedRenderer(Blocks.getBedBlock());

    @Test
    void isOccupiedEmptyWorkerReturnsFalseTest() {
        Assertions.assertFalse(renderer.isBedOccupied(Optional.empty()));
    }

    @Test
    void isOccupiedNonSleepJobTest() {
        TestWorker worker = new TestWorker();
        worker.getAi().getJobManager().setCurrentJob(new TestJob());

        Assertions.assertFalse(renderer.isBedOccupied(Optional.of(worker)));
    }

    @Test
    void isOccupiedSleepJobPreStartReturnsFalseTest() {
        TestWorker worker = new TestWorker();
        SleepJob sleepJob = new SleepJob(null, worker);
        worker.getAi().getJobManager().setCurrentJob(sleepJob);

        Assertions.assertFalse(renderer.isBedOccupied(Optional.of(worker)));
    }

    @Test
    void isOccupiedSleepJobFinishedReturnsFalseTest() {
        TestWorker worker = new TestWorker();
        SleepJob sleepJob = new SleepJob(null, worker);
        worker.getAi().getJobManager().setCurrentJob(sleepJob);

        sleepJob.finish();

        Assertions.assertFalse(renderer.isBedOccupied(Optional.of(worker)));
    }

    @Test
    void isOccupiedBasicTest() {
        TestWorker worker = new TestWorker();
        SleepJob sleepJob = new SleepJob(null, worker);
        worker.getAi().getJobManager().setCurrentJob(sleepJob);

        sleepJob.start();

        Assertions.assertTrue(renderer.isBedOccupied(Optional.of(worker)));
    }
}
