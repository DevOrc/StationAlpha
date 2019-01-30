package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class BuildManagerTests {

    private final BuildManager buildManager = new BuildManager();

    @Test
    void buildBasicWallTest() {
        Tile tile = new Tile(0,0, new World());

        buildManager.setAction(Optional.of(new BuildBlock(Blocks.getWall())));
        buildManager.build(tile, Input.Buttons.LEFT);

        Assertions.assertEquals(Blocks.getWall(), tile.getBlock().get());
    }

    @Test
    void buildBasicIceTest() {
        Tile tile = new Tile(0,0, new World());

        buildManager.setAction(Optional.of(new BuildBlock(Blocks.getIce())));
        buildManager.build(tile, Input.Buttons.LEFT);

        Assertions.assertEquals(Blocks.getIce(), tile.getBlock().get());
    }

    @Test
    void buildOverrideBlockTest() {
        Tile tile = new Tile(0,0, new World());
        tile.setBlock(Blocks.getIce());

        buildManager.setAction(Optional.of(new BuildBlock(Blocks.getWall())));
        buildManager.build(tile, Input.Buttons.LEFT);

        Assertions.assertEquals(Blocks.getWall(), tile.getBlock().get());
    }

    @Test
    void destroyBasicTest() {
        Tile tile = new Tile(0,0, new World());
        tile.setBlock(Blocks.getIce());

        buildManager.setAction(Optional.of(new BuildBlock(Blocks.getWall())));
        buildManager.build(tile, Input.Buttons.RIGHT);

        Assertions.assertFalse(tile.getBlock().isPresent());
    }
}
