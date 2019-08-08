package com.noahcharlton.stationalpha.block.dust;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.item.RecipeType;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobQueue;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;

import java.util.Optional;

public class SynthesizerContainer extends BlockContainer {

    private Optional<SynthesizerJob> currentJob = Optional.empty();

    public SynthesizerContainer(Tile tile, Block block, BlockRotation rotation) {
        super(tile, block, rotation);
    }

    @Override
    public String[] getDebugInfo() {
        String[] data = currentJob.map(this::getInfoFromJob).orElse(new String[]{"Currently Producing: None"});

        return combineDebugInfo(data);
    }

    private String[] getInfoFromJob(SynthesizerJob job) {
        ManufacturingRecipe recipe = job.getRecipe();
        double percent = (double) job.getTick() / job.getJobDuration() * 100.0;

        String producing = recipe.getOutput().getAmount() + " " + recipe.getOutput().getItem().getDisplayName();

        return new String[]{
                "Currently Producing: " + producing,
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
        Optional<Tile> tile = getJobTile();

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

    private Optional<Tile> getJobTile() {
        World world = getTile().getWorld();

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

    @Override
    public void onSave(QuietXmlWriter writer) {
        currentJob.ifPresent(job -> {
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
            currentJob = Optional.empty();
        }
    }

    private void loadJob(XmlReader.Element jobElement) {
        ManufacturingRecipe recipe = ManufacturingRecipe.loadRecipe(jobElement);
        int tick = jobElement.getInt("Tick");

        Optional<Tile> jobTile = getJobTile();

        if(jobTile.isPresent()){
            SynthesizerJob job = new SynthesizerJob(jobTile.get(), recipe);
            job.setTick(tick);

            setCurrentJob(job);
            JobQueue.getInstance().addJob(job);
        }else{
            getTile().getWorld().getManufacturingManager().addRecipeToQueue(recipe);
        }
    }

    public Optional<SynthesizerJob> getCurrentJob() {
        return currentJob;
    }

    /**
     * Used for testing only!
     *
     * @param currentJob
     */
    void setCurrentJob(SynthesizerJob currentJob) {
        this.currentJob = Optional.ofNullable(currentJob);
    }
}
