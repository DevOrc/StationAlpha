package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.block.Multiblock;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class BuildBlockTests {

    private final World world = new World();

    @Test
    void basicBuildBlock() {
        BuildBlock buildBlock = new BuildBlock(Blocks.getWall());
        Tile tile = world.getTileAt(5, 5).get();

        buildBlock.onClick(tile, Input.Buttons.LEFT);

        Assertions.assertSame(Blocks.getWall(), tile.getBlock().get());
    }

    @Test
    void basicDestroyBlock() {
        BuildBlock buildBlock = new BuildBlock(Blocks.getWall());
        Tile tile = world.getTileAt(5, 5).get();

        tile.setBlock(Blocks.getPotatoPlant());
        buildBlock.onClick(tile, Input.Buttons.RIGHT);

        Assertions.assertFalse(tile.getBlock().isPresent());
    }

    @Test
    void checkMultiblockBasicTest() {
        Multiblock multiblock = (Multiblock) Blocks.getWorkbench();
        Tile tile = world.getTileAt(0, 0).get();

        Assertions.assertTrue(BuildBlock.checkMultiblock(tile, multiblock));
    }

    @Test
    void checkMultiblockAtEdgeTest() {
        Multiblock multiblock = (Multiblock) Blocks.getWorkbench();
        Tile tile = world.getTileAt(World.WORLD_TILE_SIZE - 1, 0).get();

        Assertions.assertFalse(BuildBlock.checkMultiblock(tile, multiblock));
    }

    @Test
    void checkMultiblockCoversAnotherBlockTest() {
        Multiblock multiblock = (Multiblock) Blocks.getWorkbench();
        Tile tile = world.getTileAt(0, 0).get();
        world.getTileAt(1, 0).get().setBlock(Blocks.getWall());

        Assertions.assertFalse(BuildBlock.checkMultiblock(tile, multiblock));
    }

    @Test
    void buildMultiblockSameContainerTest() {
        BuildBlock buildBlock = new BuildBlock(Blocks.getWorkbench());
        buildBlock.onClick(world.getTileAt(0, 0).get(), Input.Buttons.LEFT);

        Assertions.assertSame(world.getTileAt(0, 0).get().getContainer().get(),
                world.getTileAt(1, 0).get().getContainer().get());
    }

    @ParameterizedTest(name = "destroyMultiblock(clickX = {0})")
    @ValueSource(ints = {0, 1})
    void destroyMultiblockTest(int destroyClickX) {
        BuildBlock buildBlock = new BuildBlock(Blocks.getWorkbench());
        buildBlock.onClick(world.getTileAt(0, 0).get(), Input.Buttons.LEFT);

        buildBlock.onClick(world.getTileAt(destroyClickX, 0).get(), Input.Buttons.RIGHT);

        Assertions.assertFalse(world.getTileAt(0, 0).get().getBlock().isPresent());
        Assertions.assertFalse(world.getTileAt(1, 0).get().getBlock().isPresent());

    }
}
