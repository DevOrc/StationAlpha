package com.noahcharlton.stationalpha.world;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.LibGdxTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FloorTests extends LibGdxTest {

    @Test
    void verifyWoodFloorPath() {
        assertFloorTexture(Floor.WOOD);
    }

    @Test
    void verifyMetalFloorPath() {
        assertFloorTexture(Floor.METAL);
    }

    public void assertFloorTexture(Floor floor){
        Assertions.assertTrue(Gdx.files.internal("floor/" + floor.getFilename()).exists());
    }
}
