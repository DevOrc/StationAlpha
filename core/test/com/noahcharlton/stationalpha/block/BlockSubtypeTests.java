package com.noahcharlton.stationalpha.block;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.LibGdxTest;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class BlockSubtypeTests extends LibGdxTest {

    @ParameterizedTest
    @MethodSource("getBlockArguments")
    void createContainerIsNotNullTest(Block block) {
        World world = new World();

        Assertions.assertNotNull(block.createContainer(world.getTileAt(0, 0).get()));
    }

    @ParameterizedTest
    @MethodSource("getTextureBlockArguments")
    void blockTextureTest(Block block) {
        assertBlockTexture(block);
    }

    public void assertBlockTexture(Block block){
        Assertions.assertTrue(Gdx.files.internal("blocks/" + block.getTextureFileName().get()).exists());
    }

    public static Stream<Block> getBlockArguments(){
        return Blocks.getBlocks().stream();
    }

    public static Stream<Block> getTextureBlockArguments(){
        return getBlockArguments().filter(block -> block.getTextureFileName().isPresent());
    }
}
