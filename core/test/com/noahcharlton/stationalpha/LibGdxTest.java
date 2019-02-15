package com.noahcharlton.stationalpha;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessFiles;

public class LibGdxTest {

    static {
        Gdx.files = new HeadlessFiles();
    }


}
