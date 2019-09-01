package com.noahcharlton.stationalpha.worker.job;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.worker.SleepJob;
import com.noahcharlton.stationalpha.worker.WorkerRole;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class JobQueue {

    private static final JobQueue instance = new JobQueue();

    private final HashMap<WorkerRole, ArrayDeque<Job>> jobs = new HashMap<>();

    public JobQueue() {
        for(WorkerRole role : WorkerRole.values()){
            jobs.put(role, new ArrayDeque<>());
        }
    }

    public void addJob(Job job){
        if(job instanceof SleepJob){
            throw new GdxRuntimeException("Cannot add sleep job to queue!");
        }

        Objects.requireNonNull(job, "Job cannot be null!");

        jobs.get(job.getRequiredRole()).add(job);
    }

    public Optional<Job> get(WorkerRole role){
        ArrayDeque<Job> jobQueue = jobs.get(role);

        if(jobQueue.isEmpty())
            return Optional.empty();

        return Optional.of(jobQueue.removeFirst());
    }


    public void clear() {
        jobs.values().forEach(ArrayDeque::clear);
    }

    public ArrayDeque<Job> getJobQueue(WorkerRole role) {
        return jobs.get(role);
    }

    public static JobQueue getInstance() {
        return instance;
    }
}
