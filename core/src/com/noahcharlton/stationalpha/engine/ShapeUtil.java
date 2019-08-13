package com.noahcharlton.stationalpha.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class ShapeUtil {

    public static Texture dot;

    static {
        if(Gdx.graphics != null){
            Pixmap map = new Pixmap(1, 1, Pixmap.Format.RGB565);

            map.setColor(Color.WHITE);
            map.drawPixel(0, 0);

            dot = new Texture(map);
        }
    }

    public static final void drawRect(int x, int y, int width, int height, Color color, SpriteBatch b){
        if(!b.isDrawing())
            throw new GdxRuntimeException("Batch must be drawing to draw a rect!");

        b.setColor(color);
        b.draw(dot, x, y, width, height);
        b.setColor(Color.WHITE);
    }

    public static final void drawLine(int x1, int y1, int x2, int y2,  Color color, SpriteBatch b){
        if(x1 > x2 || y1 > y2){
            throw new IllegalArgumentException("Point 1 must be lower than point 2!");
        }

        int thickness = 2;
        double slope = ((double) y2 - y1) / (x2 - x1);

        for(int x = x1; x < x2; x++){
            double yDecimal = ((slope * (x - x1)) + y1) - 1;

            int y = (int) (yDecimal - (thickness / 2));

            b.setColor(color);
            b.draw(dot, x, y, 1, thickness + 1);
            b.setColor(Color.WHITE);
        }
        Gdx.gl.glLineWidth(1);
    }

}
