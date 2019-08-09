package com.noahcharlton.stationalpha.world;

import com.badlogic.gdx.utils.GdxRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ConduitRendererTests {

    private final World world = new World();
    private final Tile root = world.getTileAt(1, 1).get();

    @Test
    void getDirectionSameTileFailsTest(){
        Assertions.assertThrows(GdxRuntimeException.class, () -> ConduitRenderer.getDirection(root, root));
    }

    @ParameterizedTest
    @MethodSource("getDirectionTestArgs")
    void getDirectionTest(int neighborX, int neighborY, ConduitRenderer.Direction expected) {
        Tile neighbor = world.getTileAt(neighborX, neighborY).get();

        Assertions.assertEquals(expected, ConduitRenderer.getDirection(root, neighbor));
    }

    public static Stream<Arguments> getDirectionTestArgs(){
        return Stream.of(
                Arguments.of(1, 0, ConduitRenderer.Direction.SOUTH),
                Arguments.of(1, 2, ConduitRenderer.Direction.NORTH),
                Arguments.of(0, 1, ConduitRenderer.Direction.WEST),
                Arguments.of(2, 1, ConduitRenderer.Direction.EAST)
        );
    }
}
