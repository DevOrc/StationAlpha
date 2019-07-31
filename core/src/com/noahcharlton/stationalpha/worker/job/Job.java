package com.noahcharlton.stationalpha.worker.job;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.world.Tile;
import org.apache.logging.log4j.LogManager;

import java.util.Optional;

public class Job {

    public enum JobStage{PRE_START, IN_PROGRESS, FINISHED}

    private final Optional<JobRenderer> renderer;
    private final Tile target;

    private Optional<Worker> assignedWorker = Optional.empty();
    private JobStage jobStage;

    protected Job(Tile target) {
        this.target = target;
        this.jobStage = JobStage.PRE_START;
        this.renderer = createRenderer();
    }

    public void start(){
        if(jobStage != JobStage.PRE_START)
            throw new GdxRuntimeException("Must be in Pre-start to get started");

        jobStage = JobStage.IN_PROGRESS;
    }

    public Optional<JobRenderer> createRenderer(){
        return Optional.empty();
    }

    public void update(){}

    public void finish(){
        jobStage = JobStage.FINISHED;
    }

    protected void setWorker(Worker worker) {
        this.assignedWorker = Optional.of(worker);
    }

    public void cancel(){
        assignedWorker.ifPresent(worker -> worker.getAi().getJobManager().setCurrentJob(null));

        assignedWorker = Optional.empty();
        jobStage = JobStage.PRE_START;
    }

    @Override
    public String toString() {
        LogManager.getLogger(Job.class).warn("Unimplemented toString method for job!");
        return "Basic Job";
    }

    public WorkerRole getRequiredRole(){
        return WorkerRole.GENERAL;
    }

    public JobStage getStage() {
        return jobStage;
    }

    public Optional<Worker> getAssignedWorker() {
        return assignedWorker;
    }

    public Optional<JobRenderer> getRenderer() {
        return renderer;
    }

    public Tile getTarget() {
        return target;
    }
}
