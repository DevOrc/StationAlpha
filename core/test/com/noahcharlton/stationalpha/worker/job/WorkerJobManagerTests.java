package com.noahcharlton.stationalpha.worker.job;

import com.noahcharlton.stationalpha.worker.TestWorker;
import com.noahcharlton.stationalpha.worker.Worker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class WorkerJobManagerTests {

    private final Worker worker = new TestWorker();
    private final WorkerJobManager workerJobManager = worker.getAi().getJobManager();

    @Test
    void jobDefaultsToEmptyTest() {
        Assertions.assertEquals(Optional.empty(), workerJobManager.getCurrentJob());
    }

    @Test
    public void setJobCancelsPreviousJobIfInProgress(){
        Job job = new TestJob();
        workerJobManager.setCurrentJob(job);
        job.start();

        workerJobManager.setCurrentJob(null);

        Assertions.assertEquals(Job.JobStage.PRE_START, job.getStage());
    }

    @Test
    public void setJobCancelsPreviousJobIfNotStarted(){
        Job job = new TestJob();
        workerJobManager.setCurrentJob(job);

        Assertions.assertSame(worker, job.getAssignedWorker().get());
        workerJobManager.setCurrentJob(null);

        Assertions.assertFalse(job.getAssignedWorker().isPresent());
    }

    @Test
    public void setJobAssignsWorkerToJob(){
        Job job = new TestJob();
        workerJobManager.setCurrentJob(job);

        Assertions.assertSame(worker, job.getAssignedWorker().get());
    }

    @Test
    void needsJobEmptyTest() {
        Assertions.assertTrue(workerJobManager.needsJob());
    }

    @Test
    void needsJobCurrentJobFinished() {
        Job job = new TestJob();
        workerJobManager.setCurrentJob(job);
        job.finish();

        Assertions.assertTrue(workerJobManager.needsJob());
    }

    @Test
    void needsJobFalseWhileNotFinished() {
        Job job = new TestJob();
        workerJobManager.setCurrentJob(job);

        Assertions.assertFalse(workerJobManager.needsJob());
    }

    @Test
    void getJobFromQueueBasicTest() {
        JobQueue jobQueue = new JobQueue();
        Job job = new TestJob();
        jobQueue.addJob(job);

        workerJobManager.setJobQueue(jobQueue);
        workerJobManager.getJobFromQueue();

        Assertions.assertSame(job, workerJobManager.getCurrentJob().get());
    }

    @Test
    void getJobFromQueueEmptyTest() {
        JobQueue jobQueue = new JobQueue();

        workerJobManager.setJobQueue(jobQueue);
        workerJobManager.getJobFromQueue();

        Assertions.assertTrue(workerJobManager.needsJob());
    }
}
