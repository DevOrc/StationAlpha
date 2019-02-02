package com.noahcharlton.stationalpha.worker;

import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class WorkerAI {

    private final Worker worker;
    private Optional<Tile> targetTile = Optional.empty();

    WorkerAI(Worker worker) {
        this.worker = worker;
    }

    public void update(){
        if(!onTargetTile()){

        }
    }

    boolean onTargetTile() {
        return targetTile.map(tile -> tile.equals(worker.getTileOn())).orElse(true);
    }

    public void setTargetTile(Tile targetTile) {
        this.targetTile = Optional.ofNullable(targetTile);
    }
}
