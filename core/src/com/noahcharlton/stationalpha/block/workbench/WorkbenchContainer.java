package com.noahcharlton.stationalpha.block.workbench;

import com.badlogic.gdx.utils.GdxRuntimeException;
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

public class WorkbenchContainer extends BlockContainer {

    private Optional<WorkbenchJob> job = Optional.empty();

    public WorkbenchContainer(Tile tile, Block block, BlockRotation rotation) {
        super(tile, block, rotation);
    }

    @Override
    public void onDestroy() {
        job.ifPresent(Job::cancel);
    }

    @Override
    public void onUpdate() {
        if(!job.isPresent()) {
            createJobFromRecipe();
        }

        job.ifPresent(x -> this.checkJob());
    }

    private void checkJob() {
        if(job.map(job -> job.getStage() == Job.JobStage.FINISHED).orElse(false)) {
            job = Optional.empty();
        }
    }

    /**
     * @return if the job was created; used for unit testing
     */
    boolean createJobFromRecipe() {
        Optional<Tile> openAdjacent = getWorkingTile(getTile(), getRotation());

        if(!openAdjacent.isPresent()) {
            return false;
        }

        Optional<ManufacturingRecipe> nextRecipe = getTile().getWorld().getManufacturingManager()
                .getNextRecipe(RecipeType.CRAFT);

        if(nextRecipe.isPresent()){
            return createJobFromRecipe(openAdjacent, nextRecipe.get());
        }else{
            return false;
        }
    }

    private boolean createJobFromRecipe(Optional<Tile> openAdjacent, ManufacturingRecipe recipe) {
        World world = getTile().getWorld();

        if(!recipe.resourcesAvailable(world.getInventory())){
            world.getManufacturingManager().addRecipeToQueue(recipe);
            return false;
        }

        recipe.removeRequirements(world.getInventory());
        WorkbenchJob job = new WorkbenchJob(openAdjacent.get(), recipe);

        JobQueue.getInstance().addJob(job);
        this.job = Optional.of(job);

        return true;
    }

    private Optional<Tile> getWorkingTile(Tile rootTile, BlockRotation rotation) {
        Optional<Tile> workingTile;
        World world = rootTile.getWorld();
        int x = rootTile.getX();
        int y = rootTile.getY();

        switch(rotation) {
            case NORTH:
                workingTile = world.getTileAt(x + 1, y - 1);
                break;
            case SOUTH:
                workingTile = world.getTileAt(x + 1, y + 1);
                break;
            case EAST:
                workingTile = world.getTileAt(x + 1, y + 1);
                break;
            case WEST:
                workingTile = world.getTileAt(x - 1, y + 1);
                break;
            default:
                throw new GdxRuntimeException("Unknown Variant: " + rotation);
        }

        return workingTile.filter(t -> !t.getBlock().isPresent());
    }

    Optional<WorkbenchJob> getJob() {
        return job;
    }
}
