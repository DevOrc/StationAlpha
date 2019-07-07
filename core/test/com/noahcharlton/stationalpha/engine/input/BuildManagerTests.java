package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.BlockContainer;
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

        buildManager.setAction(Optional.of(new BuildFloor(Floor.GRASS)));
        buildManager.build(tile, Input.Buttons.LEFT);

        Assertions.assertEquals(Floor.GRASS, tile.getFloor().get());
    }

    @Test
    void buildOverrideFloorTest() {
        World world = new World();
        Tile tile = world.getTileAt(0, 0).get();
        tile.setFloor(Floor.WOOD);

        buildManager.setAction(Optional.of(new BuildFloor(Floor.GRASS)));
        buildManager.build(tile, Input.Buttons.LEFT);

        Assertions.assertEquals(Floor.GRASS, tile.getFloor().get());
    }

    @Test
    void destroyBasicFloorTest() {
        Tile tile = new Tile(0,0, new World());
        tile.setFloor(Floor.WOOD);

        buildManager.setAction(Optional.of(new BuildFloor(Floor.WOOD)));
        buildManager.build(tile, Input.Buttons.RIGHT);

        Assertions.assertFalse(tile.getBlock().isPresent());
    }

    @Test
    void doesNotSelectWhenHasBuildAction() {
        InputHandler.getInstance().setCurrentlySelected(Optional.empty());
        buildManager.setAction(Optional.of(new TestBuildAction()));
        World world = new World();
        Tile tile = world.getTileAt(0, 0).get();

        buildManager.build(tile, Input.Buttons.LEFT);

        Assertions.assertEquals(Optional.empty(), InputHandler.getInstance().getCurrentlySelected());
    }


    @Test
    void buildOnNoContainerSelectsTileTest() {
        World world = new World();
        Tile tile = world.getTileAt(0, 0).get();

        buildManager.build(tile, Input.Buttons.LEFT);

        Assertions.assertSame(tile, InputHandler.getInstance().getCurrentlySelected().get());
    }

    @Test
    void buildOnTileSelectsContainerTest() {
        World world = new World();
        Tile tile = world.getTileAt(0, 0).get();
        tile.setBlock(Blocks.getWall());
        BlockContainer container = tile.getContainer().get();

        buildManager.build(tile, Input.Buttons.LEFT);

        Assertions.assertSame(container, InputHandler.getInstance().getCurrentlySelected().get());
    }

    @Test
    void clickedOutsideWorldSelectsNothingTest() {
        InputHandler.getInstance().setCurrentlySelected(Optional.of(new Tile(0, 0, new World())));

        buildManager.onTileClicked(Input.Buttons.LEFT, Optional.empty());

        Assertions.assertEquals(Optional.empty(), InputHandler.getInstance().getCurrentlySelected());
    }
}
