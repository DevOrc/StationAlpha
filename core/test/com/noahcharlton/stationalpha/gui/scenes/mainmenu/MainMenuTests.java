package com.noahcharlton.stationalpha.gui.scenes.mainmenu;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.LibGdxTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class MainMenuTests extends LibGdxTest {

    @ParameterizedTest
    @MethodSource("fontPathProvider")
    void fontFilePathTest(String path) {
        Assertions.assertTrue(Gdx.files.internal(path).exists());
    }

    static Stream<String> fontPathProvider() {
        return Stream.of(MainMenu.fontPaths);
    }
}
