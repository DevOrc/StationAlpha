package com.noahcharlton.stationalpha.block.plant;

import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.worker.job.JobQueue;
import com.noahcharlton.stationalpha.world.Floor;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class PlantContainer extends BlockContainer {

    private static final int OXYGEN_REQUIREMENT = 15;
    private final Plant plant;

    private Optional<HarvestPlantJob> job = Optional.empty();

    private int stage;
    private int tick;

    public PlantContainer(Tile tile, Plant plant) {
        super(tile, plant);

        this.plant = plant;
    }

    @Override
    public void onUpdate() {
        if(onDirtFloor() && hasOxygen())
            tick++;

        if(tick > plant.getTicksPerStage()){
            stage++;
            tick = 0;
        }

        if(stage >= plant.getStageCount()){
            stage = plant.getStageCount() - 1;

            if(!job.isPresent())
                createJob();
        }
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
}
