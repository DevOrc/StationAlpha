package com.noahcharlton.stationalpha.block;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.LibGdxTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BlockTests extends LibGdxTest {

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

    public void assertBlockTexture(Block block){
        System.out.println(Gdx.files.internal("blocks/" + block.getTextureFileName()));
        Assertions.assertTrue(Gdx.files.internal("blocks/" + block.getTextureFileName()).exists());
    }
}
