package com.noahcharlton.zulu

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.GdxRuntimeException

object RenderUtil {

    private lateinit var dot: Texture

    init {
        if (Gdx.graphics != null) {
            val map = Pixmap(1, 1, Pixmap.Format.RGB565)

            map.setColor(Color.WHITE)
            map.drawPixel(0, 0)

            dot = Texture(map)
        }
    }

    fun drawRect(x: Int, y: Int, width: Int, height: Int, color: Color, b: SpriteBatch) {
        if (!b.isDrawing)
            throw GdxRuntimeException("Batch must be drawing to draw a rect!")

        b.color = color
        b.draw(dot, x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
        b.color = Color.WHITE
    }

    fun drawLine(x1: Int, y1: Int, x2: Int, y2: Int, color: Color, b: SpriteBatch) {
        if (x1 > x2) {
            throw IllegalArgumentException("x1 must be lower than x2!")
        }

        val thickness = 2
        val slope = (y2.toDouble() - y1) / (x2 - x1)

        for (x in x1 until x2) {
            val yDecimal = slope * (x - x1) + y1 - 1

            val y = (yDecimal - thickness / 2).toInt()

            b.color = color
            b.draw(dot, x.toFloat(), y.toFloat(), 1f, (thickness + 1).toFloat())
            b.color = Color.WHITE
        }
        Gdx.gl.glLineWidth(1f)
    }

    fun drawLine(p1: Point, p2: Point, color: Color, b: SpriteBatch){
        drawLine(p1.x, p1.y, p2.x, p2.y, color, b)
    }

    fun drawRect(p1: Point, size: Size, color: Color, b: SpriteBatch){
        drawRect(p1.x, p1.y, size.width, size.height, color, b)
    }

    fun drawRect(rect: Rectangle, color: Color, b: SpriteBatch){
        drawRect(rect.x, rect.y, rect.width, rect.height, color, b)
    }
}
