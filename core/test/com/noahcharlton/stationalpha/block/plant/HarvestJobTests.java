package com.noahcharlton.stationalpha.block.plant;

import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobTests;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HarvestJobTests extends JobTests {

    private World world;
    private PlantContainer plant;

    @Test
    void onFinishStageResets() {
        for(int  i = 0; i < (plant.getTick() + 1) * plant.getStage(); i++){
            plant.onUpdate();
        }

        getJob().start();
        getJob().finish();

        Assertions.assertEquals(0, plant.getStage());
    }

    @Test
    void onFinishTickResets() {
        for(int  i = 0; i < (plant.getTick() + 1) * plant.getStage(); i++){
            plant.onUpdate();
        }

        getJob().start();
        getJob().finish();

        Assertions.assertEquals(plant.getTicksPerStage(), plant.getTick());
    }

    @Test
    void onFinishInventoryAdd() {
        for(int  i = 0; i < (plant.getTick() + 1) * plant.getStage(); i++){
            plant.onUpdate();
        }

        getJob().start();
        getJob().finish();

        Inventory inventory = world.getInventory();
        Assertions.assertEquals(5, inventory.getAmountForItem(Item.POTATO));
    }

    @Override
    public Job getJob() {
        world = new World();
        Tile tile = world.getTileAt(5, 5).get();
        tile.setBlock(Blocks.getPotatoPlant());
        plant = (PlantContainer) tile.getContainer().get();
        Tile adjacent = world.getTileAt(4, 5).get();


        return new HarvestPlantJob(plant, adjacent);
    }
}
