package com.noahcharlton.stationalpha.world;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.LibGdxTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class FloorTests extends LibGdxTest {

    @ParameterizedTest(name = "{0}")
    @EnumSource(Floor.class)
    void verifyTexture(Floor floor) {
        assertFloorTexture(floor);
    }

    public void assertFloorTexture(Floor floor){
        Assertions.assertTrue(Gdx.files.internal("floor/" + floor.getFilename()).exists());
    }
}
