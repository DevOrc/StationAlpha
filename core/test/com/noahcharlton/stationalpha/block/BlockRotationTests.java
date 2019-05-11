package com.noahcharlton.stationalpha.block;

import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class BlockRotationTests {

    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.INCLUDE, names = {"EAST", "WEST"}, value = BlockRotation.class)
    public void containerWidthAndHeightSwitchOnEastWestTest(BlockRotation rotation){
        World world = new World();
        BlockContainer workbench = Blocks.getWorkbench().createContainer(world.getTileAt(0, 0).get(), rotation);

        Assertions.assertEquals(1, workbench.getWidth());
        Assertions.assertEquals(3, workbench.getHeight());
    }

    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.INCLUDE, names = {"NORTH", "SOUTH"}, value = BlockRotation.class)
    public void containerWidthHeightStaySameForNorthSouthTest(BlockRotation rotation){
        World world = new World();
        BlockContainer workbench = Blocks.getWorkbench().createContainer(world.getTileAt(0, 0).get(), rotation);

        Assertions.assertEquals(3, workbench.getWidth());
        Assertions.assertEquals(1, workbench.getHeight());
    }

    @ParameterizedTest
    @MethodSource("getArguments")
    public void getNextTest(BlockRotation first, BlockRotation next){
        Assertions.assertEquals(next, first.getNext());
    }

    private static Stream<Arguments> getArguments(){
        return Stream.of(
                Arguments.of(BlockRotation.NORTH, BlockRotation.EAST),
                Arguments.of(BlockRotation.EAST, BlockRotation.SOUTH),
                Arguments.of(BlockRotation.SOUTH, BlockRotation.WEST),
                Arguments.of(BlockRotation.WEST, BlockRotation.NORTH)
        );
    }

}
