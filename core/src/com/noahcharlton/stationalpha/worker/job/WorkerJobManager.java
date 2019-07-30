package com.noahcharlton.stationalpha.worker.job;

import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.worker.WorkerRole;

import java.util.Iterator;
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
        Iterator<WorkerRole> roles = worker.getRoles().iterator();

        while(roles.hasNext()){
            Optional<Job> job = jobQueue.get(roles.next());

            if(job.isPresent()){
                this.setCurrentJob(job.get());
                break;
            }
        }
    }

    boolean needsJob(){
        if(currentJob.isPresent())
            return currentJob.get().getStage() == Job.JobStage.FINISHED;

        return true;
    }

    public void setCurrentJob(Job job) {
        Optional<Job> oldJob = this.currentJob;
        this.currentJob = Optional.empty();//Prevent infinite loop

        if(oldJob.filter(j -> j.getStage() != Job.JobStage.FINISHED).isPresent()){
            oldJob.get().cancel();
            JobQueue.getInstance().addJob(oldJob.get());
        }

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
