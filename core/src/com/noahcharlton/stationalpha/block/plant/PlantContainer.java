package com.noahcharlton.stationalpha.block.plant;

import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.world.Floor;
import com.noahcharlton.stationalpha.world.Tile;

public class PlantContainer extends BlockContainer {

    private static final int OXYGEN_REQUIREMENT = 15;
    private final Plant plant;

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
            stage = 0;
        }
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
