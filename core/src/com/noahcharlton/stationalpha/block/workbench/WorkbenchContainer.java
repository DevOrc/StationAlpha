package com.noahcharlton.stationalpha.block.workbench;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.item.ItemStack;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.item.RecipeType;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobQueue;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;

import java.util.Optional;

public class WorkbenchContainer extends BlockContainer {

    private Optional<WorkbenchJob> job = Optional.empty();

    public WorkbenchContainer(Tile tile, Block block, BlockRotation rotation) {
        super(tile, block, rotation);
    }

    @Override
    public String[] getDebugInfo() {
        String[] data = job.map(this::getInfoFromJob).orElse(new String[]{"Currently Producing: None"});

        return combineDebugInfo(data);
    }

    private String[] getInfoFromJob(WorkbenchJob job) {
        ManufacturingRecipe recipe = job.getRecipe();
        double percent = (double) job.getTick() / job.getJobDuration() * 100.0;

        ItemStack output = recipe.getOutput();

        return new String[]{
                "Currently Producing: " + output.getAmount() + " " + output.getItem(),
                "Progress: " + ((int) percent) + "%"
        };
    }

    @Override
    public void onDestroy() {
        job.ifPresent(Job::permanentEnd);
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
        Optional<Tile> openAdjacent = getWorkingTile();

        if(!openAdjacent.isPresent()) {
            return false;
        }

        Optional<ManufacturingRecipe> nextRecipe = getTile().getWorld().getManufacturingManager()
                .getNextRecipe(RecipeType.CRAFT);

        return nextRecipe.filter(recipe -> createJobFromRecipe(openAdjacent.get(), recipe)).isPresent();
    }

    private boolean createJobFromRecipe(Tile openAdjacent, ManufacturingRecipe recipe) {
        World world = getTile().getWorld();

        if(!recipe.resourcesAvailable(world.getInventory())){
            world.getManufacturingManager().addRecipeToQueue(recipe);
            return false;
        }

        recipe.removeRequirements(world.getInventory());
        WorkbenchJob job = new WorkbenchJob(openAdjacent, recipe);

        JobQueue.getInstance().addJob(job);
        this.job = Optional.of(job);

        return true;
    }

    private Optional<Tile> getWorkingTile() {
        Optional<Tile> workingTile;
        World world = getTile().getWorld();
        int x = getTile().getX();
        int y = getTile().getY();

        switch(getRotation()) {
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
                throw new GdxRuntimeException("Unknown Variant: " + getRotation());
        }

        return workingTile.filter(t -> !t.getBlock().isPresent());
    }


    @Override
    public void onSave(QuietXmlWriter writer) {
        job.ifPresent(job -> {
            QuietXmlWriter element = writer.element("Job");

            job.getRecipe().writeRecipe(element);
            element.element("Tick", job.getTick());

            element.pop();
        });
    }

    @Override
    public void onLoad(XmlReader.Element element) {
        if(element.hasChild("Job")){
            loadJob(element.getChildByName("Job"));
        }else{
            job = Optional.empty();
        }
    }

    private void loadJob(XmlReader.Element jobElement) {
        ManufacturingRecipe recipe = ManufacturingRecipe.loadRecipe(jobElement);
        int tick = jobElement.getInt("Tick");

        Optional<Tile> jobTile = getWorkingTile();

        if(jobTile.isPresent()){
            WorkbenchJob job = new WorkbenchJob(jobTile.get(), recipe);
            job.setTick(tick);

            this.job = Optional.of(job);
            JobQueue.getInstance().addJob(job);
        }else{
            getTile().getWorld().getManufacturingManager().addRecipeToQueue(recipe);
        }
    }

    Optional<WorkbenchJob> getJob() {
        return job;
    }
}
