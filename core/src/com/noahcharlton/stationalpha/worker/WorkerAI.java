package com.noahcharlton.stationalpha.worker;

import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.WorkerJobManager;
import com.noahcharlton.stationalpha.world.Tile;

public class WorkerAI {

    private final Worker worker;

    private final WorkerMovementManager movementManager;
    private final WorkerJobManager jobManager;
    private final WorkerNeedsManager needsManager;

    WorkerAI(Worker worker) {
        this.worker = worker;
        this.movementManager = new WorkerMovementManager(this, worker);
        this.jobManager = new WorkerJobManager(worker);
        this.needsManager = new WorkerNeedsManager(worker);
    }

    public void update(){
        if( movementManager.onTargetTile() && atTileOrigin()){
            checkStartJob();
        }else{
            movementManager.update();
        }

        needsManager.update();
        jobManager.update();
        jobManager.getCurrentJob().ifPresent(job -> movementManager.setTargetTile(job.getTarget()));
    }

    private void checkStartJob() {
        jobManager.getCurrentJob().filter(job -> job.getStage() == Job.JobStage.PRE_START).ifPresent(Job::start);
    }

    boolean atTileOrigin() {
        return worker.getPixelX() % Tile.TILE_SIZE == 0 && worker.getPixelY() % Tile.TILE_SIZE == 0;
    }

    public boolean onTargetTile(){
        return movementManager.onTargetTile();
    }

    public void setTargetTile(Tile tile){
        movementManager.setTargetTile(tile);
    }

    public WorkerMovementManager getMovementManager() {
        return movementManager;
    }

    public WorkerJobManager getJobManager() {
        return jobManager;
    }

    public WorkerNeedsManager getNeedsManager() {
        return needsManager;
    }
}
