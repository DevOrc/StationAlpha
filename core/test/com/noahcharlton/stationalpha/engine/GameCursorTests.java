package com.noahcharlton.stationalpha.engine;

import com.badlogic.gdx.Gdx;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class GameCursorTests {

    @ParameterizedTest
    @EnumSource(GameCursor.class)
    void textureFileExistsTest(GameCursor cursor) {
        String path = cursor.getTexture().getPath();

        Assertions.assertTrue(Gdx.files.internal(path).exists());
    }
}
