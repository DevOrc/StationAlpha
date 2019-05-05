package com.noahcharlton.stationalpha.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class ShapeUtil {

    private static Texture dot;

    static {
        Pixmap map = new Pixmap(1, 1, Pixmap.Format.RGB565);

        map.setColor(Color.WHITE);
        map.drawPixel(0, 0);

        dot = new Texture(map);
    }

    public static final void drawRect(int x, int y, int width, int height, Color color, SpriteBatch b){
        if(!b.isDrawing())
            throw new GdxRuntimeException("Batch must be drawing to draw a rect!");

        b.setColor(color);
        b.draw(dot, x, y, width, height);
        b.setColor(Color.WHITE);
    }

    public static void drawRotated(SpriteBatch b, Texture t, int x, int y, int rotX, int rotY, int rotation){
        int width = t.getWidth();
        int height = t.getHeight();

        b.draw(t, x, y, rotX, rotY, width, height, 1, 1, rotation,
                0, 0, width, height, false, false);
    }



}
