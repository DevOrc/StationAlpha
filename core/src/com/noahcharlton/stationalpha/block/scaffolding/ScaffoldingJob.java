package com.noahcharlton.stationalpha.block.scaffolding;

import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.worker.job.TickBasedJob;
import com.noahcharlton.stationalpha.world.Tile;

public class ScaffoldingJob extends TickBasedJob {

    private static final int LENGTH = 90;

    private final ScaffoldingContainer container;

    public ScaffoldingJob(Tile target, ScaffoldingContainer container) {
        super(target, LENGTH);

        this.container = container;
    }

    @Override
    public void finish() {
        super.finish();

        container.finishBuilding();
    }

    @Override
    public WorkerRole getRequiredRole() {
        return WorkerRole.ENGINEER;
    }

    @Override
    public String toString() {
        return "Building: " + super.toString();
    }
}
