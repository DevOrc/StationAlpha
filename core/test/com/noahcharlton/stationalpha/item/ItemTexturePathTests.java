package com.noahcharlton.stationalpha.item;

import com.badlogic.gdx.Gdx;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class ItemTexturePathTests {

    @ParameterizedTest(name = "{0}")
    @EnumSource(Item.class)
    void pathTest(Item item) {
        Assertions.assertTrue(Gdx.files.internal("items/" + item.getId() + ".png").exists());
    }
}
