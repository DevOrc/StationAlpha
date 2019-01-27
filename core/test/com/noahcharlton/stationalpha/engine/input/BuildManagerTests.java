package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BuildManagerTests {

    private final BuildManager buildManager = new BuildManager();

    @Test
    void buildBasicTest() {
        Tile tile = new Tile(0,0);

        buildManager.build(tile, Input.Buttons.LEFT);

        Assertions.assertEquals(Blocks.getWall(), tile.getBlock().get());
    }

    @Test
    void buildWithIceTest() {
        Tile tile = new Tile(0,0);
        tile.setBlock(Blocks.getIce());

        buildManager.build(tile, Input.Buttons.LEFT);

        Assertions.assertEquals(Blocks.getWall(), tile.getBlock().get());
    }

    @Test
    void destroyBasicTest() {
        Tile tile = new Tile(0,0);
        tile.setBlock(Blocks.getIce());

        buildManager.build(tile, Input.Buttons.RIGHT);

        Assertions.assertFalse(tile.getBlock().isPresent());
    }
}
