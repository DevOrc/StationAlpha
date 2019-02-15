package com.noahcharlton.stationalpha.worker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WorkerRendererTests {

    @Test
    void workerTextureExistsTest() {
        Assertions.assertTrue(Gdx.files.internal(WorkerRenderer.getPath()).exists());
    }

    @Test
    void checkRendererInitializedBasicTest() {
        Assertions.assertThrows(GdxRuntimeException.class, WorkerRenderer::checkRendererInitialized);
    }
}
