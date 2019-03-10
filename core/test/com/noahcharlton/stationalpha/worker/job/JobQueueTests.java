package com.noahcharlton.stationalpha.worker.job;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class JobQueueTests {

    private final JobQueue jobQueue = new JobQueue();

    @Test
    void basicAddJobTest() {
        jobQueue.addJob(new TestJob());

        Assertions.assertEquals(1, jobQueue.getJobQueue().size());
    }

    @Test
    void nullJobWontAddTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            jobQueue.addJob(null);
        });
    }

    @Test
    void multipleJobFirstInFirstOutTest() {
        Job one = new TestJob();
        Job two = new TestJob();

        jobQueue.addJob(one);
        jobQueue.addJob(two);

        Assertions.assertSame(one, jobQueue.get().get());
        Assertions.assertSame(two, jobQueue.get().get());
    }

    @Test
    void getReturnsEmptyOptionalWhenQueueEmptyTest() {
        Assertions.assertEquals(Optional.empty(), jobQueue.get());
    }
}
