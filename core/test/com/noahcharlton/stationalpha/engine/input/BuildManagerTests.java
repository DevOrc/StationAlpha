package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.Floor;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class BuildManagerTests {

    private final BuildManager buildManager = new BuildManager();

    @Test
    void buildBasicWallTest() {
        World world = new World();
        world.getInventory().changeAmountForItem(Item.STEEL, 1);
        Tile tile = world.getTileAt(0, 0).get();

        buildManager.setAction(Optional.of(new BuildBlock(Blocks.getWall())));
        buildManager.build(tile, Input.Buttons.LEFT);

        Assertions.assertEquals(Blocks.getWall(), tile.getBlock().get());
    }

    @Test
    void buildBasicIceTest() {
        World world = new World();
        Tile tile = world.getTileAt(0, 0).get();

        buildManager.setAction(Optional.of(new BuildBlock(Blocks.getIce())));
        buildManager.build(tile, Input.Buttons.LEFT);

        Assertions.assertEquals(Blocks.getIce(), tile.getBlock().get());
    }

    @Test
    void buildOverrideBlockTest() {
        World world = new World();
        Tile tile = world.getTileAt(0, 0).get();
        tile.setBlock(Blocks.getWall());

        buildManager.setAction(Optional.of(new BuildBlock(Blocks.getWall())));
        buildManager.build(tile, Input.Buttons.LEFT);

        Assertions.assertEquals(Blocks.getWall(), tile.getBlock().get());
    }

    @Test
    void destroyBasicWallTest() {
        World world = new World();
        Tile tile = world.getTileAt(0, 0).get();
        tile.setBlock(Blocks.getIce());

        buildManager.setAction(Optional.of(new BuildBlock(Blocks.getWall())));
        buildManager.build(tile, Input.Buttons.RIGHT);

        Assertions.assertFalse(tile.getBlock().isPresent());
    }

    @Test
    void buildBasicFloorTest() {
        World world = new World();
        Tile tile = world.getTileAt(0, 0).get();

        buildManager.setAction(Optional.of(new BuildFloor(Floor.WOOD)));
        buildManager.build(tile, Input.Buttons.LEFT);

        Assertions.assertEquals(Floor.WOOD, tile.getFloor().get());
    }

    @Test
    void buildOverrideFloorTest() {
        World world = new World();
        Tile tile = world.getTileAt(0, 0).get();
        tile.setFloor(Floor.WOOD);

        buildManager.setAction(Optional.of(new BuildFloor(Floor.METAL)));
        buildManager.build(tile, Input.Buttons.LEFT);

        Assertions.assertEquals(Floor.METAL, tile.getFloor().get());
    }

    @Test
    void destroyBasicFloorTest() {
        Tile tile = new Tile(0,0, new World());
        tile.setFloor(Floor.WOOD);

        buildManager.setAction(Optional.of(new BuildFloor(Floor.WOOD)));
        buildManager.build(tile, Input.Buttons.RIGHT);

        Assertions.assertFalse(tile.getBlock().isPresent());
    }
}
