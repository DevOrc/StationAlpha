package com.noahcharlton.stationalpha.engine.input;

import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;

public class WorkerPathfindAction implements BuildAction{

    private final World world;

    public WorkerPathfindAction(World world) {
        this.world = world;
    }

    @Override
    public void onClick(Tile tile, int button) {
        for(Worker worker : world.getWorkers()){
            worker.getAi().setTargetTile(tile);
        }
    }

    @Override
    public String getName() {
        return "Worker Path";
    }
}
