package com.noahcharlton.stationalpha.block.plant;

import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Floor;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlantContainerTests {

    private final PlantContainer plantContainer;
    private final World world;
    private final Tile tile;

    public PlantContainerTests() {
        world = new World();
        tile = world.getTileAt(0, 0).get();
        tile.setBlock(Blocks.getPotatoPlant());
        plantContainer = (PlantContainer) tile.getContainer().get();
    }

    @Test
    void onDirtFloorTest() {
        tile.setFloor(Floor.DIRT);

        Assertions.assertTrue(plantContainer.onDirtFloor());
    }

    @Test
    void onDirtFloorNonDirtTest() {
        tile.setFloor(Floor.METAL);

        Assertions.assertFalse(plantContainer.onDirtFloor());
    }

    @Test
    void onDirtFloorNoFloorTest() {
        tile.setFloor(null);

        Assertions.assertFalse(plantContainer.onDirtFloor());
    }

    @Test
    void onDirtFloorTicksTest() {
        tile.setFloor(Floor.DIRT);
        tile.changeOxygenLevel(25);
        plantContainer.onUpdate();

        Assertions.assertEquals(plantContainer.getTicksPerStage() - 1, plantContainer.getTick());
    }

    @Test
    void notEnoughOxygenDoesNotTickTest() {
        tile.setFloor(Floor.DIRT);
        plantContainer.onUpdate();

        Assertions.assertEquals(plantContainer.getTicksPerStage(), plantContainer.getTick());
    }

    @Test
    void notOnDirtFloorDoesNotTickTest() {
        tile.setFloor(Floor.METAL);
        tile.changeOxygenLevel(25);
        plantContainer.onUpdate();

        Assertions.assertEquals(plantContainer.getTicksPerStage(), plantContainer.getTick());
    }

    @Test
    void onStageIncreaseTest() {
        tile.setFloor(Floor.DIRT);
        tile.changeOxygenLevel(25);

        for(int i = 0; i <= plantContainer.getTicksPerStage(); i++){
            plantContainer.onUpdate();
        }

        Assertions.assertEquals(1, plantContainer.getStage());
    }

    @Test
    void tickResetsOnStageIncreaseTest() {
        tile.setFloor(Floor.DIRT);
        Plant plant = (Plant) Blocks.getPotatoPlant();

        for(int i = 0; i <= plant.getMinimumTicksPerStage(); i++){
            plantContainer.onUpdate();
        }

        Assertions.assertEquals(plantContainer.getTicksPerStage(), plantContainer.getTick());
    }

    @Test
    void noOxygenKillsPlantTest() {
        tile.setFloor(Floor.DIRT);
        tile.changeOxygenLevel(-50f);

        plantContainer.onUpdate();

        Assertions.assertEquals(Blocks.getDeadPlant(), tile.getBlock().get());
    }

    @Test
    void noDirtKillsPlantTest() {
        tile.setFloor(Floor.METAL);
        tile.changeOxygenLevel(25f);

        plantContainer.onUpdate();

        Assertions.assertEquals(Blocks.getDeadPlant(), tile.getBlock().get());
    }
}
