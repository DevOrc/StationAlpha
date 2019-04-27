package com.noahcharlton.stationalpha.world;

import com.noahcharlton.stationalpha.block.Block;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class TileOxygenTests {

    private final World world = new World();
    private final Tile tile = world.getTileAt(3, 3).get();

    @Test
    void changeOxygenLevelOver100Trims() {
        tile.setFloor(Floor.METAL);
        tile.changeOxygenLevel(250);

        Assertions.assertEquals(100, tile.getOxygenLevel());
    }

    @Test
    void changeOxygenLevelBelowZeroTrims() {
        tile.setFloor(Floor.METAL);
        tile.changeOxygenLevel(-100);

        Assertions.assertEquals(0, tile.getOxygenLevel());
    }

    @Test
    void noFloorRemovesAllOxygenTest() {
        tile.changeOxygenLevel(22);

        Assertions.assertEquals(0, tile.getOxygenLevel());
    }

    @Test
    void opaqueWall() {
        tile.setFloor(Floor.METAL);
        tile.setBlock(new OpaqueTestBlock());
        tile.changeOxygenLevel(22);

        Assertions.assertEquals(0, tile.getOxygenLevel());
    }

    @Test
    void transferOxygenBasicTest() {
        Tile src = world.getTileAt(0, 0).get();
        src.setFloor(Floor.METAL);
        Tile dest = world.getTileAt(1, 0).get();
        dest.setFloor(Floor.METAL);

        src.changeOxygenLevel(50);
        dest.changeOxygenLevel(48);
        Tile.transferOxygen(dest, src);

        Assertions.assertEquals(src.getOxygenLevel(),  dest.getOxygenLevel());
    }

    @Test
    void transferOxygenMaxSpreadTest() {
        Tile src = world.getTileAt(0, 0).get();
        src.setFloor(Floor.METAL);
        Tile dest = world.getTileAt(1, 0).get();
        dest.setFloor(Floor.METAL);

        src.changeOxygenLevel(100);
        dest.changeOxygenLevel(0);
        Tile.transferOxygen(dest, src);

        Assertions.assertEquals(src.getOxygenLevel(),  85);
        Assertions.assertEquals(dest.getOxygenLevel(),  15);
    }

    @Test
    void transferOxygenDestOxygenHigherFailsTest() {
        Tile src = world.getTileAt(0, 0).get();
        src.setFloor(Floor.METAL);
        Tile dest = world.getTileAt(1, 0).get();
        dest.setFloor(Floor.METAL);

        src.changeOxygenLevel(0);
        dest.changeOxygenLevel(100);

        Assertions.assertThrows(IllegalArgumentException.class, () -> Tile.transferOxygen(dest, src));
    }
}
class OpaqueTestBlock extends Block {

    public OpaqueTestBlock() {
        setOpaque(true);
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.empty();
    }

}
