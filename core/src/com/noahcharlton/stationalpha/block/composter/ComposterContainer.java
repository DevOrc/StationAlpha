package com.noahcharlton.stationalpha.block.composter;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.item.RecipeType;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.world.ManufacturingManager;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class ComposterContainer extends BlockContainer {

    private final ManufacturingManager manager;

    private Optional<Job> currentJob = Optional.empty();
    private Optional<ManufacturingRecipe> currentRecipe = Optional.empty();
    private Optional<Integer> tick = Optional.empty();

    public ComposterContainer(Tile tile, Block block, BlockRotation rotation) {
        super(tile, block, rotation);

        this.manager = tile.getWorld().getManufacturingManager();
    }

    @Override
    public void onUpdate() {
        if(!currentRecipe.isPresent()) {
            startComposting();
        } else {
            tick.ifPresent(this::updateCompost);
        }
    }

    void updateCompost(int tick) {
        if(tick > 0) {
            this.tick = Optional.of(--tick);
        } else if(!currentJob.isPresent()) {
            createEndCompostJob();
        }
    }

    @Override
    public String[] getDebugInfo() {
        if(currentRecipe.isPresent() && tick.isPresent()){
            return combineDebugInfo(
                    "Currently Making: " + currentRecipe.get().getOutputItem().getDisplayName(),
                    "Progress: " + calcPercent(tick.get(), currentRecipe.get().getTime()) + "%"
            );
        }else if(currentRecipe.isPresent()){
            return combineDebugInfo("Currently Making: " + currentRecipe.get().getOutputItem().getDisplayName());
        }else{
            return combineDebugInfo("Currently Making: None");
        }
    }

    private int calcPercent(int tick, int time) {
        int timeLeft = time - tick;
        double percent = (double) (timeLeft) / time;
        percent *= 100.0;

        return (int) percent;
    }

    void createEndCompostJob() {
        Optional<Tile> target = getTile().getOpenAdjecent();

        currentJob = target.map(tile -> new CompostJob.EndCompostJob(this, tile, currentRecipe.get()));
    }

    void startComposting() {
        Optional<ManufacturingRecipe> recipe = manager.getNextRecipe(RecipeType.COMPOST);
        Optional<Tile> target = getTile().getOpenAdjecent();

        if(target.isPresent() && recipe.isPresent()) {
            if(!recipe.get().resourcesAvailable(getTile().getWorld().getInventory())) {
                manager.addRecipeToQueue(recipe.get());
                return;
            }

            createCompostJob(target.get(), recipe.get());
            currentRecipe = recipe;
            recipe.get().removeRequirements(getTile().getWorld().getInventory());

            return;
        }

        return;
    }

    private void createCompostJob(Tile target, ManufacturingRecipe recipe) {
        currentJob = Optional.of(new CompostJob.StartCompostJob(this, target, recipe));
    }

    public void onCompostFinished() {
        currentRecipe = Optional.empty();
        currentJob = Optional.empty();
        tick = Optional.empty();
    }

    public void onCompostStarted() {
        currentJob = Optional.empty();
        tick = currentRecipe.map(ManufacturingRecipe::getTime);
    }

    @Override
    public void onDestroy() {
        currentJob.ifPresent(Job::cancel);
    }

    public Optional<Job> getCurrentJob() {
        return currentJob;
    }

    /**
     * Used for testing only!
     */
    void setTick(int tick) {
        this.tick = Optional.of(tick);
    }

    /**
     * Used for testing only!
     */
    void setCurrentRecipe(ManufacturingRecipe currentRecipe) {
        this.currentRecipe = Optional.ofNullable(currentRecipe);
    }

    /**
     * Used for testing only!
     */
    void setCurrentJob(Job currentJob) {
        this.currentJob = Optional.ofNullable(currentJob);
    }

    public Optional<Integer> getTick() {
        return tick;
    }

    public Optional<ManufacturingRecipe> getCurrentRecipe() {
        return currentRecipe;
    }
}
