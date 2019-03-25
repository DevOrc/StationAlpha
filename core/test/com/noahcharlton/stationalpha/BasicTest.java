package com.noahcharlton.stationalpha;

import com.badlogic.gdx.Gdx;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BasicTest extends LibGdxTest{

    @Test
    void stationAlphaInstanceTest() {
        Assertions.assertNotNull(StationAlpha.getInstance());
    }

    @Test
    void gdxFilesInstanceTest() {
        Assertions.assertNotNull(Gdx.files);
    }
}
