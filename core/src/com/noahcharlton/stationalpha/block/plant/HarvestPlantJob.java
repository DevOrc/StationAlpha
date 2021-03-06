package com.noahcharlton.stationalpha.block.plant;

import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.worker.job.TickBasedJob;
import com.noahcharlton.stationalpha.world.Tile;

public class HarvestPlantJob extends TickBasedJob {

    private final PlantContainer container;

    public HarvestPlantJob(PlantContainer plant, Tile targetTile) {
        super(targetTile, 60);

        this.container = plant;
    }

    @Override
    public WorkerRole getRequiredRole() {
        return WorkerRole.GARDENER;
    }

    @Override
    public void finish() {
        super.finish();

        container.harvest();
    }

    @Override
    public String toString() {
        return "Harvesting: " + super.toString();
    }
}
