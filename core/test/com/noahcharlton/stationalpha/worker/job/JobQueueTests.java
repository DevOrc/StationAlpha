package com.noahcharlton.stationalpha.worker.job;

import com.noahcharlton.stationalpha.worker.WorkerRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Optional;

public class JobQueueTests {

    private final JobQueue jobQueue = new JobQueue();

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