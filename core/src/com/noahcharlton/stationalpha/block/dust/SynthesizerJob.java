package com.noahcharlton.stationalpha.block.dust;

import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.worker.job.TickBasedJob;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.Tile;

public class SynthesizerJob extends TickBasedJob {

    private final SynthesizerContainer container;
    private final ManufacturingRecipe recipe;

    public SynthesizerJob(Tile target, ManufacturingRecipe recipe, SynthesizerContainer container) {
        super(target, recipe.getTime());

        this.recipe = recipe;
        this.container = container;
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
    public void update() {
        if(container.usePower())
            super.update();
    }

    @Override
    public void finish() {
        super.finish();

        Inventory inventory = getTarget().getWorld().getInventory();

        recipe.addProducts(inventory);
    }

    @Override
    public String toString() {
        return "Synthesizing: " + super.toString();
    }

    public void setTick(int tick){
        this.tick = tick;
    }

    public ManufacturingRecipe getRecipe() {
        return recipe;
    }
}
