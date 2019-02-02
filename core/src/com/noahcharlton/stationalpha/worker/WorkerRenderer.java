package com.noahcharlton.stationalpha.worker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.ShapeUtil;

public class WorkerRenderer {

    public static void render(SpriteBatch batch, Worker worker){
        ShapeUtil.drawRect(worker.getPixelX(), worker.getPixelY(), 30, 48, Color.CHARTREUSE, batch);
    }

}
