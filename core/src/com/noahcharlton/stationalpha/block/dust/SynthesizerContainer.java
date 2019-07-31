package com.noahcharlton.stationalpha.block.dust;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.item.RecipeType;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobQueue;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;

import java.util.Optional;

public class SynthesizerContainer extends BlockContainer {

    private Optional<Job> currentJob = Optional.empty();

    public SynthesizerContainer(Tile tile, Block block, BlockRotation rotation) {
        super(tile, block, rotation);
    }

    @Override
    public String[] getDebugInfo() {
        String[] data = currentJob.map(this::getInfoFromJob).orElse(new String[]{"Currently Producing: None"});

        return combineDebugInfo(data);
    }

    private String[] getInfoFromJob(Job job) {
        SynthesizerJob synthesizerJob = (SynthesizerJob) job;
        ManufacturingRecipe recipe = synthesizerJob.getRecipe();
        double percent = (double) synthesizerJob.getTick() / synthesizerJob.getJobDuration() * 100.0;

        return new String[]{
                "Currently Producing: " + recipe.getOutputItem().getDisplayName(),
                "Progress: " + ((int) percent) + "%"
        };
    }

    @Override
    public void onUpdate() {
        if(currentJob.isPresent()) {
            updateJob();
            return;
        }

        checkAndCreateJob();
    }

    void checkAndCreateJob() {
        World world = getTile().getWorld();

        Optional<ManufacturingRecipe> recipe = world.getManufacturingManager().getNextRecipe(RecipeType.SYNTHESIZE);
        Optional<Tile> tile = getJobTile(world);

        boolean recipeValid = recipe.filter(r -> r.resourcesAvailable(world.getInventory())).isPresent();

        if(recipeValid && tile.filter(t -> !t.getBlock().isPresent()).isPresent()) {
            startJob(recipe.get(), tile.get());
        } else {
            recipe.ifPresent(r -> world.getManufacturingManager().addRecipeToQueue(r));
        }
    }

    void startJob(ManufacturingRecipe recipe, Tile tile) {
        World world = tile.getWorld();

        recipe.removeRequirements(world.getInventory());
        currentJob = Optional.of(new SynthesizerJob(tile, recipe));

        JobQueue.getInstance().addJob(currentJob.get());
    }

    private Optional<Tile> getJobTile(World world) {
        int x = getTile().getX();
        int y = getTile().getY();

        switch(getRotation()) {
            case NORTH:
                return world.getTileAt(x + 1, y - 1);
            case EAST:
                return world.getTileAt(x + 3, y + 1);
            case SOUTH:
                return world.getTileAt(x + 1, y + 3);
            case WEST:
                return world.getTileAt(x - 1, y + 1);
        }

        return Optional.empty();
    }

    private void updateJob() {
        if(currentJob.filter(job -> job.getStage() == Job.JobStage.FINISHED).isPresent()) {
            currentJob = Optional.empty();
        }
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
     *
     * @param currentJob
     */
    void setCurrentJob(Optional<Job> currentJob) {
        this.currentJob = currentJob;
    }
}
