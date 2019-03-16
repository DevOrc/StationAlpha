package com.noahcharlton.stationalpha.block;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.LibGdxTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BlockTextureTests extends LibGdxTest {

    @BeforeAll
    static void setUp() {
        Blocks.init();
    }

    @Test
    public void wallTextureTest(){
        assertBlockTexture(Blocks.getWall());
    }

    @Test
    public void iceTextureTest(){
        assertBlockTexture(Blocks.getIce());
    }

    @Test
    void compressorTextureTest() {
        assertBlockTexture(Blocks.getCompressor());
    }

    public void assertBlockTexture(Block block){
        Assertions.assertTrue(Gdx.files.internal("blocks/" + block.getTextureFileName().get()).exists());
    }
}
