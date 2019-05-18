package com.noahcharlton.stationalpha.worker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.gui.GuiComponent;

public class WorkerRenderer {

    public static void init(){
        WorkerRole.loadTextures();
    }

    public static void render(SpriteBatch batch, Worker worker){
        checkRendererInitialized();

        batch.draw(getRenderableRole(worker).getWorkerTexture().get(), worker.getPixelX(), worker.getPixelY());

        renderName(batch, worker);
    }

    static WorkerRole getRenderableRole(Worker worker) {
        if(worker.getRoles().size() > 0)
            return worker.getRoles().iterator().next();

        return WorkerRole.GENERAL;
    }

    private static void renderName(SpriteBatch batch, Worker worker) {
        BitmapFont font = GuiComponent.getFont();
        font.getData().setScale(.4f);
        font.setColor(Color.WHITE);

        font.draw(batch, worker.getName(), worker.getPixelX(), worker.getPixelY() - 10, 32, Align.center, false);
    }

    static void checkRendererInitialized() {
        if(WorkerRole.GENERAL.getWorkerTexture() == null) {
            throw new GdxRuntimeException("Renderer not initialized!");
        }
    }
}
