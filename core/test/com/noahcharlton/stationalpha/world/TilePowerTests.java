package com.noahcharlton.stationalpha.world;

import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.block.power.PoweredContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TilePowerTests {

    private final World world = new World();
    private final Tile tile = world.getTileAt(3, 3).get();

    @Test
    void setPowerNoConduitTest() {
        tile.setConduit(false);

        tile.setPower(22);

        Assertions.assertEquals(0, tile.getPower());
    }

    @Test
    void setPowerMaxAt25() {
        tile.setConduit(true);

        tile.setPower(45);

        Assertions.assertEquals(25, tile.getPower());
    }

    @Test
    void setPowerCannotGoBelowZero() {
        tile.setConduit(true);

        tile.setPower(-10);

        Assertions.assertEquals(0, tile.getPower());
    }

    @Test
    void poweredContainerAddsConduit() {
        tile.setBlock(Blocks.getWall(), new TestPowerContainer(tile));

        tile.setConduit(false);

        Assertions.assertTrue(tile.hasConduit());
    }

    @Test
    void powerInDebugInfoTest() {
        tile.setConduit(true);

        String[] expected = new String[]{"Floor: None", "Oxygen: 0.0", "Power: 0"};
        Assertions.assertArrayEquals(expected, tile.getDebugInfo());
    }

    @Test
    void transferPowerBasicTest() {
        Tile src = world.getTileAt(0, 0).get();
        src.setConduit(true);
        Tile dest = world.getTileAt(1, 0).get();
        dest.setConduit(true);

        src.setPower(18);
        dest.setPower(16);
        Tile.transferPower(dest, src);

        Assertions.assertEquals(src.getPower(),  dest.getPower());
    }

    @Test
    void transferPowerMaxSpreadTest() {
        Tile src = world.getTileAt(0, 0).get();
        src.setConduit(true);
        Tile dest = world.getTileAt(1, 0).get();
        dest.setConduit(true);

        src.setPower(15);
        dest.setPower(0);
        Tile.transferPower(dest, src);

        Assertions.assertEquals(10, src.getPower());
        Assertions.assertEquals(5, dest.getPower());
    }

    public static class TestPowerContainer extends BlockContainer implements PoweredContainer{

        public TestPowerContainer(Tile tile) {
            super(tile, Blocks.getWall());
        }
    }
}

