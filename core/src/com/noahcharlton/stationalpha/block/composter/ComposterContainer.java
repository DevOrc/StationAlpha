package com.noahcharlton.stationalpha.block.composter;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.item.ItemStack;
import com.noahcharlton.stationalpha.item.ManufacturingRecipe;
import com.noahcharlton.stationalpha.item.RecipeType;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.world.ManufacturingManager;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;

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
            attemptToStartComposting();
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
            ItemStack output = currentRecipe.get().getOutput();
            return combineDebugInfo(
                    "Currently Making: " + output.getAmount() + " " + output.getItem().getDisplayName(),
                    "Progress: " + calcPercent(tick.get(), currentRecipe.get().getTime()) + "%"
            );
        }else if(currentRecipe.isPresent()){
            ItemStack output = currentRecipe.get().getOutput();
            String producing = "Currently Making: " + output.getAmount() + " " + output.getItem().getDisplayName();

            return combineDebugInfo(producing);
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

    void attemptToStartComposting(){
        manager.getNextRecipe(RecipeType.COMPOST).ifPresent(recipe -> {
            if(recipe.resourcesAvailable(getTile().getWorld().getInventory())){
                createCompostingJob(recipe);
            }else{
                manager.addRecipeToQueue(recipe);
            }
        });
    }

    void createCompostingJob(ManufacturingRecipe recipe) {
        Optional<Tile> target = getTile().getOpenAdjecent();

        if(target.isPresent()){
            createCompostJob(target.get(), recipe);
            currentRecipe = Optional.of(recipe);
            recipe.removeRequirements(getTile().getWorld().getInventory());
        }else{
            manager.addRecipeToQueue(recipe);
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
        currentJob.ifPresent(Job::permanentEnd);
    }

    @Override
    public void onSave(QuietXmlWriter writer) {
        currentRecipe.ifPresent(recipe -> {
            QuietXmlWriter recipeWriter = writer.element("Recipe");

            tick.ifPresent(tick -> recipeWriter.element("Tick", tick));
            recipe.writeRecipe(recipeWriter);
            recipeWriter.pop();
        });
    }

    @Override
    public void onLoad(XmlReader.Element element) {
        XmlReader.Element recipe = element.getChildByName("Recipe");

        if(recipe != null){
            loadTick(recipe);

            if(tick.isPresent()){
                setCurrentRecipe(ManufacturingRecipe.loadRecipe(recipe));
            }else{
                createCompostingJob(ManufacturingRecipe.loadRecipe(recipe));
            }
        }else{
            this.currentRecipe = Optional.empty();
            this.tick = Optional.empty();
            this.currentJob = Optional.empty();
        }
    }

    void loadTick(XmlReader.Element recipe) {
        int tick = recipe.getInt("Tick", -1);

        if(tick >= 0){
            setTick(tick);
        }else{
            this.tick = Optional.empty();
        }
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
