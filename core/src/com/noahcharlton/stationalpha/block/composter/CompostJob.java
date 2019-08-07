package com.noahcharlton.stationalpha.block.composter;

import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobQueue;
import com.noahcharlton.stationalpha.world.Tile;

public abstract class CompostJob extends Job {

    protected final ComposterContainer container;
    protected final ManufacturingRecipe recipe;

    public CompostJob(ComposterContainer container, Tile target, ManufacturingRecipe recipe) {
        super(target);

        this.container = container;
        this.recipe = recipe;

        JobQueue.getInstance().addJob(this);
    }

    @Override
    public WorkerRole getRequiredRole() {
        return WorkerRole.GARDENER;
    }

    @Override
    public void start() {
        super.start();

        finish();
    }

    static class EndCompostJob extends CompostJob {

        EndCompostJob(ComposterContainer container, Tile target, ManufacturingRecipe recipe) {
            super(container, target, recipe);
        }

        @Override
        public void finish() {
            super.finish();

            recipe.addProducts(getTarget().getWorld().getInventory());
            container.onCompostFinished();
        }
    }

    static class StartCompostJob extends CompostJob {

        StartCompostJob(ComposterContainer container, Tile target, ManufacturingRecipe recipe) {
            super(container, target, recipe);
        }

        @Override
        public void finish() {
            super.finish();

            container.onCompostStarted();
        }
    }

    @Override
    public String toString() {
        return "Composting: " + super.toString();
    }
}
