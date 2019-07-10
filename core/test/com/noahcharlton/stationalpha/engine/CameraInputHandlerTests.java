package com.noahcharlton.stationalpha.engine;

import com.badlogic.gdx.graphics.OrthographicCamera;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CameraInputHandlerTests {

    private final OrthographicCamera camera = new OrthographicCamera();

    @Test
    void trimZoomDoNothingIfInRange() {
        camera.zoom = 2.25f;

        CameraInputHandler.trimZoom(camera);

        Assertions.assertEquals(2.25f, camera.zoom);
    }

    @Test
    void trimZoomIfSmallerThanRange() {
        camera.zoom = -.5f;

        CameraInputHandler.trimZoom(camera);

        Assertions.assertEquals(.5f, camera.zoom);
    }

    @Test
    void trimZoomIfBiggerThanRange() {
        camera.zoom = 10.5f;

        CameraInputHandler.trimZoom(camera);

        Assertions.assertEquals(5.75f, camera.zoom);
    }
}
