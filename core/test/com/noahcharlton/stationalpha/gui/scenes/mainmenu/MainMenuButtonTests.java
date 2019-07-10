package com.noahcharlton.stationalpha.gui.scenes.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainMenuButtonTests {

    @Test
    void textureExistsTest() {
        String path = MainMenuButton.texture.getPath();

        Assertions.assertTrue(Gdx.files.internal(path).exists());
    }

    @Test
    void onClickTest() {
        MainMenuButton button = new MainMenuButton("", () -> {
            throw new GdxRuntimeException("");
        });

        Assertions.assertThrows(GdxRuntimeException.class,
                () -> button.handleClick(5, 5, true));
    }
}
