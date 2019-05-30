package com.noahcharlton.stationalpha.worker.job;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class Job {

    public enum JobStage{PRE_START, IN_PROGRESS, FINISHED}

    private final Tile target;

    private Optional<Worker> assignedWorker;
    private JobStage jobStage;

    protected Job(Tile target) {
        this.target = target;
        this.jobStage = JobStage.PRE_START;
    }

    public void start(){
        if(jobStage != JobStage.PRE_START)
            throw new GdxRuntimeException("Must be in Pre-start to get started");

        jobStage = JobStage.IN_PROGRESS;
    }

    public void update(){}

    public void finish(){
        jobStage = JobStage.FINISHED;
    }

    void setWorker(Worker worker) {
        this.assignedWorker = Optional.of(worker);
    }

    public void cancel(){
        assignedWorker = Optional.empty();
        jobStage = JobStage.PRE_START;
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

    public Tile getTarget() {
        return target;
    }
}
