package com.noahcharlton.stationalpha.worker;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;

public class WorkerRenderer {

    private static ManagedTexture workerTexture;

    public static void init(){
        workerTexture = new ManagedTexture(getPath());
    }

    static String getPath(){
        return "worker_helmet.png";
    }

    public static void render(SpriteBatch batch, Worker worker){
        checkRendererInitialized();

        batch.draw(workerTexture.get(), worker.getPixelX(), worker.getPixelY());
    }

    static void checkRendererInitialized() {
        if(workerTexture == null) {
            throw new GdxRuntimeException("Renderer not initialized!");
        }
    }
}
