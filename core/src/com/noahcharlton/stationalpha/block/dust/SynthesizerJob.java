package com.noahcharlton.stationalpha.block.dust;

import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.worker.job.TickBasedJob;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.Tile;

public class SynthesizerJob extends TickBasedJob {

    private final ManufacturingRecipe recipe;

    public SynthesizerJob(Tile target, ManufacturingRecipe recipe) {
        super(target, recipe.getTime());

        this.recipe = recipe;
    }

    @Override
    public WorkerRole getRequiredRole() {
        return WorkerRole.SCIENTIST;
    }

    @Override
    public void start() {
        //Preserve tick, even if the job is restarted...
        int tickLocal = getTick();
        super.start();
        tick = tickLocal;
    }

    @Override
    public void finish() {
        super.finish();

        Inventory inventory = getTarget().getWorld().getInventory();

        recipe.addProducts(inventory);
    }
}
