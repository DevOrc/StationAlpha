package com.noahcharlton.stationalpha;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessFiles;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.engine.input.PlayerActions;
import com.noahcharlton.stationalpha.item.ManufacturingRecipes;

public class LibGdxTest {

    static {
        Gdx.files = new HeadlessFiles();

        new StationAlpha();
        Blocks.init();
        ManufacturingRecipes.init();
        PlayerActions.init();
    }
}
