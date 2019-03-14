package com.noahcharlton.stationalpha.worker.job;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Optional;

public class JobQueue {

    private static final JobQueue instance = new JobQueue();

    private final ArrayDeque<Job> jobQueue = new ArrayDeque<>();

    public JobQueue() {
    }

    public void addJob(Job job){
        Objects.requireNonNull(job, "Job cannot be null!");
        jobQueue.add(job);
    }

    public Optional<Job> get(){
        if(jobQueue.isEmpty())
            return Optional.empty();
        return Optional.of(jobQueue.removeFirst());
    }

    ArrayDeque<Job> getJobQueue() {
        return jobQueue;
    }

    public static JobQueue getInstance() {
        return instance;
    }
}
