package com.noahcharlton.stationalpha.block.plant;

import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Floor;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.load.LoadTestUtils;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

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

    @Test
    void onSaveBasicTest() {
        StringWriter stringWriter = new StringWriter();

        plantContainer.onSave(new QuietXmlWriter(stringWriter));

        String expected = String.format("<Data tick=\"%d\" stage=\"%d\" ticksPerStage=\"%d\"/>\n",
                plantContainer.getTick(), plantContainer.getStage(), plantContainer.getTicksPerStage());
        Assertions.assertEquals(expected, stringWriter.toString());
    }

    @Test
    void onLoadTickTest() {
        String xml = "<Data tick=\"24\" stage=\"3\" ticksPerStage=\"643\"/>\n";

        plantContainer.onLoad(LoadTestUtils.asChild(xml));

        Assertions.assertEquals(24, plantContainer.getTick());
    }

    @Test
    void onLoadStageTest() {
        String xml = "<Data tick=\"52\" stage=\"2\" ticksPerStage=\"235\"/>\n";

        plantContainer.onLoad(LoadTestUtils.asChild(xml));

        Assertions.assertEquals(2, plantContainer.getStage());
    }

    @Test
    void onLoadTicksPerStageTest() {
        String xml = "<Data tick=\"4\" stage=\"5\" ticksPerStage=\"876\"/>\n";

        plantContainer.onLoad(LoadTestUtils.asChild(xml));

        Assertions.assertEquals(876, plantContainer.getTicksPerStage());
    }
}
