package com.noahcharlton.stationalpha.engine;

import com.badlogic.gdx.Gdx;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class InGameIconTests {

    @ParameterizedTest
    @EnumSource(value = InGameIcon.class)
    void texturePathExistsTest(InGameIcon icon) {
        Assertions.assertTrue(Gdx.files.internal(icon.getPath()).exists());
    }
}
