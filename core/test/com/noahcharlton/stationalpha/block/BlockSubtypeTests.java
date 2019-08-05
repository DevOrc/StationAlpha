package com.noahcharlton.stationalpha.block;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.LibGdxTest;
import com.noahcharlton.stationalpha.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class BlockSubtypeTests extends LibGdxTest {

    @Test
    void noDuplicateIDTest() {
        Set<String> IDs = new HashSet<>();

        for(Block block : Blocks.getBlocks()){
            if(IDs.contains(block.getID()))
                throw new RuntimeException("Duplicate ID: '" + block.getID() + "' for " + block.getDisplayName());

            IDs.add(block.getID());
        }
    }

    @ParameterizedTest
    @MethodSource("getBlockArguments")
    void nonNullIDTest(Block block) {
        Assertions.assertNotNull(block.getID());
    }

    @ParameterizedTest
    @MethodSource("getBlockArguments")
    void createContainerIsNotNullTest(Block block) {
        World world = new World();

        Assertions.assertNotNull(block.createContainer(world.getTileAt(0, 0).get(), BlockRotation.NORTH));
    }

    @ParameterizedTest
    @MethodSource("getBlockArguments")
    void nonNegativeDimensionedWidth(Block block) {
        Assertions.assertTrue(block.getDimensionedWidth() > 0);
    }

    @ParameterizedTest
    @MethodSource("getBlockArguments")
    void nonNegativeDimensionedHeight(Block block) {
        Assertions.assertTrue(block.getDimensionedHeight() > 0);
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
