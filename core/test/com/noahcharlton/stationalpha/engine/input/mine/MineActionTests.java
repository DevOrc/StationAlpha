package com.noahcharlton.stationalpha.engine.input.mine;

import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.worker.job.JobQueue;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MineActionTests {

    private final JobQueue jobQueue = new JobQueue();
    private final World world = new World();

    private final MineAction mineAction = new MineAction.Builder()
            .setBlock(Blocks.getIce())
            .setOutput(Item.SPACE_ROCK)
            .setOutputAmount(3)
            .setJobQueue(jobQueue)
            .build();

    @Test
    void onClickBasicTest() {
        Tile tile = world.getTileAt(5, 5).get();
        tile.setBlock(Blocks.getIce());

        mineAction.onClick(tile, Input.Buttons.LEFT);

        Assertions.assertTrue(jobQueue.get(WorkerRole.GENERAL).isPresent());
    }

    @Test
    void onClickWithRightNothingTest() {
        Tile tile = world.getTileAt(5, 5).get();
        tile.setBlock(Blocks.getIce());

        mineAction.onClick(tile, Input.Buttons.RIGHT);

        Assertions.assertFalse(jobQueue.get(WorkerRole.GENERAL).isPresent());
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
}
