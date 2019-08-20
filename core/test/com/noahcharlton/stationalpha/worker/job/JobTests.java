package com.noahcharlton.stationalpha.worker.job;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.LibGdxTest;
import com.noahcharlton.stationalpha.worker.TestWorker;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JobTests extends LibGdxTest {

    protected Job job;

    public Job getJob() {
        return new Job(new Tile(0, 0, new World()));
    }

    @BeforeEach
    protected void setUp() {
        job = getJob();
    }

    @Test
    void hasToStringMethod() {
        String info = job.toString();

        //Check if default toString is used
        Assertions.assertFalse(info.contains("com.noahcharlton.stationalpha"));
    }

    @Test
    protected void assignedWorkerEmptyByDefaultTest() {
        Assertions.assertFalse(job.getAssignedWorker().isPresent());
    }

    @Test
    protected void jobDefaultStateTest() {
        Assertions.assertEquals(Job.JobStage.PRE_START, job.getStage());
    }

    @Test
    protected void jobStartBasicTest() {
        job.start();

        Assertions.assertEquals(Job.JobStage.IN_PROGRESS, job.getStage());
    }

    @Test
    protected void jobCannotStartTwiceTest() {
        job.start();

        Assertions.assertThrows(GdxRuntimeException.class, job::start);
    }

    @Test
    protected void finishBasicTest() {
        job.start();
        job.finish();

        Assertions.assertEquals(Job.JobStage.FINISHED, job.getStage());
    }

    @Test
    protected void jobCannotStartAfterFinished() {
        job.start();
        job.finish();

        Assertions.assertThrows(GdxRuntimeException.class, job::start);
    }

    @Test
    protected void jobCancelRemovesWorkerTest() {
        job.setWorker(new TestWorker());
        job.cancel();

        Assertions.assertFalse(job.getAssignedWorker().isPresent());
    }

    @Test
    protected void jobCancelSetsStateToPreStart() {
        job.start();
        job.cancel();

        Assertions.assertEquals(Job.JobStage.PRE_START, job.getStage());
    }

    @Test
    protected void permanentCancelDoesNotAddToJobQueueTest() {
        JobQueue.getInstance().getJobQueue(getJob().getRequiredRole()).clear();

        new TestWorker().getAi().getJobManager().setCurrentJob(job);
        job.permanentEnd();

        JobQueueTests.assertNotInJobQueue(job);
    }
}
