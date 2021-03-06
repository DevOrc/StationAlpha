package com.noahcharlton.stationalpha.worker.job;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.worker.SleepJob;
import com.noahcharlton.stationalpha.worker.TestWorker;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.opentest4j.AssertionFailedError;

import java.util.Optional;

public class JobQueueTests {

    private final JobQueue jobQueue = new JobQueue();

    @ParameterizedTest
    @EnumSource(WorkerRole.class)
    void clearBasicTest(WorkerRole role) {
        TestRoleJob job = new TestRoleJob(role);

        jobQueue.addJob(job);
        jobQueue.clear();

        Assertions.assertEquals(0, jobQueue.getJobQueue(role).size());
    }

    @Test
    void cannotAddSleepJobToQueue() {
        SleepJob job = new SleepJob(null, new TestWorker());

        Assertions.assertThrows(GdxRuntimeException.class, () -> {
           jobQueue.addJob(job);
        });
    }

    @Test
    void hasQueueForAllRolesTest() {
        for(WorkerRole role : WorkerRole.values()){
            Assertions.assertNotNull(jobQueue.getJobQueue(role));
        }
    }

    @ParameterizedTest
    @EnumSource(WorkerRole.class)
    void basicAddJobTest(WorkerRole workerRole) {
        jobQueue.addJob(new TestRoleJob(workerRole));

        Assertions.assertEquals(1, jobQueue.getJobQueue(workerRole).size());
    }

    @Test
    void nullJobWontAddTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            jobQueue.addJob(null);
        });
    }

    @ParameterizedTest
    @EnumSource(WorkerRole.class)
    void multipleJobFirstInFirstOutTest(WorkerRole role) {
        Job one = new TestRoleJob(role);
        Job two = new TestRoleJob(role);

        jobQueue.addJob(one);
        jobQueue.addJob(two);

        Assertions.assertSame(one, jobQueue.get(role).get());
        Assertions.assertSame(two, jobQueue.get(role).get());
    }

    @ParameterizedTest
    @EnumSource(WorkerRole.class)
    void getReturnsEmptyOptionalWhenQueueEmptyTest(WorkerRole role) {
        Assertions.assertEquals(Optional.empty(), jobQueue.get(role));
    }

    @Test
    void assertNotInJobQueueTrueTest() {
        Job job = new TestJob();
        assertNotInJobQueue(job);
    }

    @Test
    void assertNotInJobQueueFalseTest() {
        Job job = new TestJob();
        JobQueue.getInstance().addJob(job);

        Assertions.assertThrows(AssertionFailedError.class, () -> assertNotInJobQueue(job));

    }

    public static void assertNotInJobQueue(Job job){
        Assertions.assertFalse(JobQueue.getInstance().getJobQueue(job.getRequiredRole()).contains(job));
    }
}
class TestRoleJob extends TestJob{

    private final WorkerRole role;

    public TestRoleJob(WorkerRole role) {
        this.role = role;
    }

    public WorkerRole getRequiredRole() {
        return role;
    }
}