package com.noahcharlton.stationalpha;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessFiles;
import com.noahcharlton.stationalpha.block.Blocks;

public class LibGdxTest {

    static {
        Gdx.files = new HeadlessFiles();

        new StationAlpha();
        Blocks.init();
    }
}
