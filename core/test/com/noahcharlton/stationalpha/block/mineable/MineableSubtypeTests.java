package com.noahcharlton.stationalpha.block.mineable;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class MineableSubtypeTests {

    @ParameterizedTest
    @MethodSource(value = "getMineableBlocks")
    void hasMineableContainerTest(Block block) {
        Tile tile = new Tile(0, 0, new World());

        Assertions.assertTrue(block.createContainer(tile, BlockRotation.NORTH) instanceof MineableBlockContainer);
    }

    public static Stream<Block> getMineableBlocks(){
        return Blocks.getBlocks().stream().filter(block -> block instanceof MineableBlock);
    }
}
