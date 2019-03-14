package com.noahcharlton.stationalpha.worker.job;

import com.noahcharlton.stationalpha.worker.Worker;

import java.util.Optional;

public class WorkerJobManager {

    private final Worker worker;

    private JobQueue jobQueue = JobQueue.getInstance();
    private Optional<Job> currentJob = Optional.empty();

    public WorkerJobManager(Worker worker) {
        this.worker = worker;
    }

    public void update() {
        currentJob.filter(job -> job.getStage() == Job.JobStage.IN_PROGRESS).ifPresent(Job::update);

        if(needsJob())
            getJobFromQueue();
    }

    public void getJobFromQueue(){
        jobQueue.get().ifPresent(this::setCurrentJob);
    }

    boolean needsJob(){
        if(currentJob.isPresent())
            return currentJob.get().getStage() == Job.JobStage.FINISHED;

        return true;
    }

    public void setCurrentJob(Job job) {
        this.currentJob.filter(j -> j.getStage() != Job.JobStage.FINISHED).ifPresent(Job::cancel);

        this.currentJob = Optional.ofNullable(job);
        this.currentJob.ifPresent(j -> j.setWorker(worker));
    }

    public Optional<Job> getCurrentJob() {
        return currentJob;
    }

    void setJobQueue(JobQueue jobQueue) {
        this.jobQueue = jobQueue;
    }

    public JobQueue getJobQueue() {
        return jobQueue;
    }
}
