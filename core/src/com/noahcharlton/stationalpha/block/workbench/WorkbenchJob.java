package com.noahcharlton.stationalpha.block.workbench;

import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.worker.job.TickBasedJob;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Objects;

public class WorkbenchJob extends TickBasedJob {

    private final ManufacturingRecipe recipe;

    WorkbenchJob(Tile target, ManufacturingRecipe recipe) {
        super(target, recipe.getTime());

        this.recipe = Objects.requireNonNull(recipe);
    }

    @Override
    public WorkerRole getRequiredRole() {
        return WorkerRole.ENGINEER;
    }

    @Override
    public void finish() {
        super.finish();

        recipe.addProducts(getTarget().getWorld().getInventory());
    }

    public ManufacturingRecipe getRecipe() {
        return recipe;
    }
}
