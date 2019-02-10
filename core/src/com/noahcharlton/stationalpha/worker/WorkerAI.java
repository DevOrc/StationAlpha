package com.noahcharlton.stationalpha.worker;

import com.noahcharlton.stationalpha.world.Tile;

public class WorkerAI {

    private final Worker worker;

    private final WorkerMovementManager movementManager;

    WorkerAI(Worker worker) {
        this.worker = worker;
        this.movementManager = new WorkerMovementManager(this, worker);
    }

    public void update(){
        if(!movementManager.onTargetTile() || !atTileOrigin()){
            movementManager.update();
        }
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
}
