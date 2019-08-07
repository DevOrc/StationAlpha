package com.noahcharlton.stationalpha.block.plant;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.worker.job.JobQueue;
import com.noahcharlton.stationalpha.world.Floor;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;

import java.util.Optional;
import java.util.Random;

public class PlantContainer extends BlockContainer {

    private static final Random random = new Random();
    private static final int OXYGEN_REQUIREMENT = 15;
    private final Plant plant;

    private Optional<HarvestPlantJob> job = Optional.empty();

    private int ticksPerStage;
    private int stage;
    private int tick;

    public PlantContainer(Tile tile, Plant plant, BlockRotation rotation) {
        super(tile, plant, rotation);

        this.plant = plant;
        this.ticksPerStage = (int) (plant.getMinimumTicksPerStage() * (1f + (random.nextFloat() / 2f)));
        this.tick = ticksPerStage;
    }

    @Override
    public String[] getDebugInfo() {
        return combineDebugInfo(
                "Percent: " + calcPercent() + "%",
                "Stage : " + (stage + 1) + " / " + plant.getStageCount()
        );
    }

    private int calcPercent() {
        double percent =  1 - ((double) tick / ticksPerStage);
        percent /= plant.getStageCount();
        percent *= 100;
        percent += (100.0 / plant.getStageCount()) * stage;

        return (int) percent;
    }

    @Override
    public void onUpdate() {
        if(onDirtFloor() && hasOxygen())
            tick--;
        else
            killPlant();

        if(tick <= 0){
            stage++;
            tick = ticksPerStage;
        }

        if(stage >= plant.getStageCount()){
            stage = plant.getStageCount() - 1;
            tick = 0;

            if(!job.isPresent())
                createJob();
        }
    }

    private void killPlant() {
        this.getTile().setBlock(Blocks.getDeadPlant());
    }

    private void createJob() {
        getTile().getOpenAdjecent().ifPresent(t -> {
            job = Optional.of(new HarvestPlantJob(this, t));

            JobQueue.getInstance().addJob(job.get());
        });
    }

    void harvest() {
        stage = 0;

        addProductToInventory();
        job = Optional.empty();
    }

    void addProductToInventory() {
        Inventory inventory = getTile().getWorld().getInventory();

        inventory.changeAmountForItem(plant.getProduct(), 1);
    }

    @Override
    public void onSave(QuietXmlWriter writer) {
        writer.element("Data")
                .attribute("tick", tick)
                .attribute("stage", stage)
                .attribute("ticksPerStage", ticksPerStage)
                .pop();
    }

    @Override
    public void onLoad(XmlReader.Element element) {
        XmlReader.Element data = element.getChildByName("Data");

        tick = data.getIntAttribute("tick");
        stage = data.getIntAttribute("stage");
        ticksPerStage = data.getIntAttribute("ticksPerStage");
    }

    private boolean hasOxygen() {
        return getTile().getOxygenLevel() > OXYGEN_REQUIREMENT;
    }

    boolean onDirtFloor() {
        return getTile().getFloor().filter(floor -> floor == Floor.DIRT).isPresent();
    }

    public int getStage() {
        return stage;
    }

    public int getTick() {
        return tick;
    }

    public int getTicksPerStage() {
        return ticksPerStage;
    }
}
