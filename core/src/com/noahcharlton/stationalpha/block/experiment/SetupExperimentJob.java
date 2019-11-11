package com.noahcharlton.stationalpha.block.experiment;

import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.worker.job.TickBasedJob;
import com.noahcharlton.stationalpha.world.Tile;

public class SetupExperimentJob extends TickBasedJob {

    private static final int SETUP_LENGTH = 120;

    private final ExperimentContainer container;

    public SetupExperimentJob(Tile target, ExperimentContainer container) {
        super(target, SETUP_LENGTH);

        this.container = container;

        if(!container.getExperiment().isPresent())
            throw new IllegalStateException("Cannot have a setup job without an experiment!");
    }

    @Override
    public WorkerRole getRequiredRole() {
        return WorkerRole.SCIENTIST;
    }

    @Override
    public void finish() {
        super.finish();

        container.startExperiment();
    }

    @Override
    public String toString() {
        return "Preparing: " + super.toString();
    }
}
