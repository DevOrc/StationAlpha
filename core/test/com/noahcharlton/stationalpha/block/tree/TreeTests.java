package com.noahcharlton.stationalpha.block.tree;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.LibGdxTest;
import com.noahcharlton.stationalpha.block.Blocks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TreeTests extends LibGdxTest {

    private final TreeBlock block = (TreeBlock) Blocks.getTreeBlock();

    @Test
    void saplingTextureTest() {
        Assertions.assertTrue(Gdx.files.internal(block.saplingTexture.getPath()).exists());
    }

    @Test
    void fullTreeTextureTest() {
        Assertions.assertTrue(Gdx.files.internal(block.fullTreeTexture.getPath()).exists());
    }
}
