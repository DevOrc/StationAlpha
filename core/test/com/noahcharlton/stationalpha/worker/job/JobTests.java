package com.noahcharlton.stationalpha.worker.job;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.worker.TestWorker;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JobTests {

    private Job job;

    public Job getJob() {
        return new Job(new Tile(0, 0, new World()));
    }

    @BeforeEach
    void setUp() {
        job = getJob();
    }

    @Test
    void jobDefaultStateTest() {
        Assertions.assertEquals(Job.JobStage.PRE_START, job.getStage());
    }

    @Test
    void jobStartBasicTest() {
        job.start();

        Assertions.assertEquals(Job.JobStage.IN_PROGRESS, job.getStage());
    }

    @Test
    void jobCannotStartTwiceTest() {
        job.start();

        Assertions.assertThrows(GdxRuntimeException.class, job::start);
    }

    @Test
    void finishBasicTest() {
        job.start();
        job.finish();

        Assertions.assertEquals(Job.JobStage.FINISHED, job.getStage());
    }

    @Test
    void jobCannotStartAfterFinished() {
        job.start();
        job.finish();

        Assertions.assertThrows(GdxRuntimeException.class, job::start);
    }

    @Test
    void jobCancelRemovesWorkerTest() {
        job.setWorker(new TestWorker());
        job.cancel();

        Assertions.assertFalse(job.getAssignedWorker().isPresent());
    }

    @Test
    void jobCancelSetsStateToPreStart() {
        job.start();
        job.cancel();

        Assertions.assertEquals(Job.JobStage.PRE_START, job.getStage());
    }
}
