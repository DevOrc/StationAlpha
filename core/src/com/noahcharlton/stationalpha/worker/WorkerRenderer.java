package com.noahcharlton.stationalpha.worker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;
import com.noahcharlton.stationalpha.gui.GuiComponent;

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

        renderName(batch, worker);
    }

    private static void renderName(SpriteBatch batch, Worker worker) {
        BitmapFont font = GuiComponent.getFont();
        font.getData().setScale(.4f);
        font.setColor(Color.WHITE);

        font.draw(batch, worker.getName(), worker.getPixelX(), worker.getPixelY() - 10, 32, Align.center, false);
    }

    static void checkRendererInitialized() {
        if(workerTexture == null) {
            throw new GdxRuntimeException("Renderer not initialized!");
        }
    }
}
