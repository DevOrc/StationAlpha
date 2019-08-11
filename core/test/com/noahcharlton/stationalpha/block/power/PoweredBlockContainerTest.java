package com.noahcharlton.stationalpha.block.power;

import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PoweredBlockContainerTest {

    private final World world = new World();
    private final Tile tile = world.getTileAt(0, 0).get();
    private final TestPoweredBlockContainer container = new TestPoweredBlockContainer(tile);

    @BeforeEach
    void setUp() {
        tile.setBlock(Blocks.getTreeBlock(), container);

        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                world.getTileAt(x, y).get().setConduit(true);
            }
        }
    }

    @Test
    void hasPowerFalseTest() {
        container.setPowerPerTick(10);
        tile.setPower(5);

        Assertions.assertFalse(container.hasPower());
    }

    @Test
    void hasPowerBasicTest() {
        container.setPowerPerTick(12);
        tile.setPower(25);

        Assertions.assertTrue(container.hasPower());
    }

    @Test
    void hasPowerNonRootTileTest() {
        container.setPowerPerTick(11);
        world.getTileAt(1, 1).get().setPower(15);

        Assertions.assertTrue(container.hasPower());
    }

    @Test
    void hasPowerMultipleTilesTest() {
        container.setPowerPerTick(20);
        world.getTileAt(1, 0).get().setPower(15);
        world.getTileAt(1, 1).get().setPower(10);

        Assertions.assertTrue(container.hasPower());
    }

    @Test
    void usePowerBasicTest() {
        container.setPowerPerTick(22);
        tile.setPower(25);

        Assertions.assertTrue(container.usePower());
    }

    @Test
    void usePowerRemovesPowerTest() {
        container.setPowerPerTick(15);
        tile.setPower(18);

        container.usePower();

        Assertions.assertEquals(3, tile.getPower());
    }

    @Test
    void usePowerMultipleTileTest() {
        container.setPowerPerTick(23);
        world.getTileAt(1, 0).get().setPower(15);
        world.getTileAt(1, 1).get().setPower(10);

        Assertions.assertTrue(container.usePower());
    }

    @Test
    void usePowerMultipleTileRemovesPowerTest() {
        container.setPowerPerTick(23);
        world.getTileAt(1, 0).get().setPower(15);
        world.getTileAt(1, 1).get().setPower(10);

        container.usePower();

        Assertions.assertEquals(0, world.getTileAt(1, 0).get().getPower());
        Assertions.assertEquals(2, world.getTileAt(1, 1).get().getPower());
    }

    @Test
    void usePowerNotEnoughPowerTest() {
        container.setPowerPerTick(22);
        tile.setPower(11);

        Assertions.assertFalse(container.usePower());
    }

    @Test
    void usePowerDoesNotRemoveIfNotEnoughTest() {
        container.setPowerPerTick(22);
        tile.setPower(11);

        container.usePower();

        Assertions.assertEquals(11, tile.getPower());
    }
}
class TestPoweredBlockContainer extends PoweredBlockContainer{

    private int powerPerTick;

    public TestPoweredBlockContainer(Tile tile) {
        super(tile, Blocks.getTreeBlock(), BlockRotation.NORTH);
    }

    @Override
    public int getPowerPerTick() {
        return powerPerTick;
    }

    public void setPowerPerTick(int powerPerTick) {
        this.powerPerTick = powerPerTick;
    }
}
