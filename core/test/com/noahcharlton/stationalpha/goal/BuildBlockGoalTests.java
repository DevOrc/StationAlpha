package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BuildBlockGoalTests {

    private final Block block = Blocks.getWall();
    private final BuildBlockGoal goal = new BuildBlockGoal(block);
    private final World world = new World();

    @Test
    void basicNotCompletedTest() {
        goal.update(world);

        Assertions.assertFalse(goal.isCompleted());
    }

    @Test
    void basicCompletedTest() {
        world.getTileAt(23, 23).get().setBlock(block);

        goal.update(world);

        Assertions.assertTrue(goal.isCompleted());
    }
}
