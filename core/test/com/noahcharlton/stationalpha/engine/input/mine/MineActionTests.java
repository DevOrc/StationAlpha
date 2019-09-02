package com.noahcharlton.stationalpha.engine.input.mine;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.BuildBlock;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.worker.job.JobQueue;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class MineActionTests {

    private final JobQueue jobQueue = new JobQueue();
    private final World world = new World();

    private final MineAction mineAction = new MineAction.Builder()
            .setBlock(Blocks.getIce())
            .setOutput(Item.SPACE_ROCK)
            .setOutputAmount(3)
            .setJobQueue(jobQueue)
            .setRole(WorkerRole.GENERAL)
            .build();

    @Test
    void onClickBasicTest() {
        Tile tile = world.getTileAt(5, 5).get();
        tile.setBlock(Blocks.getIce());

        mineAction.onClick(tile, Input.Buttons.LEFT);

        Assertions.assertTrue(jobQueue.get(WorkerRole.GENERAL).isPresent());
    }

    @Test
    void onMultipleClickJobQueueSizeOneTest() {
        Tile tile = world.getTileAt(5, 5).get();
        tile.setBlock(Blocks.getIce());

        mineAction.onClick(tile, Input.Buttons.LEFT);
        mineAction.onClick(tile, Input.Buttons.LEFT);
        mineAction.onClick(tile, Input.Buttons.LEFT);

        Assertions.assertEquals(1, jobQueue.getJobQueue(WorkerRole.GENERAL).size());
    }

    @Test
    void onClickBlockWithRightNothingTest() {
        Tile tile = world.getTileAt(5, 5).get();
        tile.setBlock(Blocks.getIce());

        mineAction.onClick(tile, Input.Buttons.RIGHT);

        Assertions.assertFalse(jobQueue.get(WorkerRole.GENERAL).isPresent());
    }

    @Test
    void onClickEmptyTileWithRightCancelsActionTest() {
        InputHandler.getInstance().setBuildAction(mineAction);
        Tile tile = world.getTileAt(5, 5).get();

        mineAction.onClick(tile, Input.Buttons.RIGHT);

        Assertions.assertFalse(InputHandler.getInstance().getBuildManager().getAction().isPresent());
    }

    @Test
    void onClickAirNothingTest() {
        Tile tile = world.getTileAt(5, 5).get();

        mineAction.onClick(tile, Input.Buttons.LEFT);

        Assertions.assertFalse(jobQueue.get(WorkerRole.GENERAL).isPresent());
    }

    @Test
    void onClickNonIceBlockTest() {
        Tile tile = world.getTileAt(5, 5).get();
        tile.setBlock(Blocks.getWall());

        mineAction.onClick(tile, Input.Buttons.LEFT);

        Assertions.assertFalse(jobQueue.get(WorkerRole.GENERAL).isPresent());
    }

    @Test
    void onClickNoOpenAdjacentTilesTest() {
        Tile tile = world.getTileAt(5, 5).get();
        tile.setBlock(Blocks.getIce());

        world.getTileAt(4, 5).get().setBlock(Blocks.getWall());
        world.getTileAt(6, 5).get().setBlock(Blocks.getWall());
        world.getTileAt(5, 6).get().setBlock(Blocks.getWall());
        world.getTileAt(5, 4).get().setBlock(Blocks.getWall());

        mineAction.onClick(tile, Input.Buttons.LEFT);

        Assertions.assertFalse(jobQueue.get(WorkerRole.GENERAL).isPresent());
    }

    @ParameterizedTest(name = "\"({0}, {1})\" is on edge: {2}")
    @MethodSource("getEdgeContainerArgs")
    void isEdgeOfContainerTest(int x, int y, boolean expected) {
        BuildBlock buildBlock = new BuildBlock(Blocks.getTreeBlock());
        buildBlock.onClick(world.getTileAt(1, 1).get(), Input.Buttons.LEFT);
        BlockContainer container = world.getTileAt(1, 1).get().getContainer().get();

        boolean actual = MineAction.isEdgeOfContainer(container, x, y);

        Assertions.assertEquals(expected, actual);
    }

    static Stream<Arguments> getEdgeContainerArgs() {
        return Stream.of(
                Arguments.of(0, 0, true),
                Arguments.of(1, 0, true),
                Arguments.of(2, 0, true),
                Arguments.of(0, 1, true),
                Arguments.of(1, 1, false),
                Arguments.of(2, 1, true),
                Arguments.of(0, 2, true),
                Arguments.of(1, 2, true),
                Arguments.of(2, 2, true)
        );
    }
}
