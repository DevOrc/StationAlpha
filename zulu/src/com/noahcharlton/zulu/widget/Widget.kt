package com.noahcharlton.zulu.widget

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.noahcharlton.zulu.*

abstract class Widget{

     val size = Size()
     val pos = Point()
     var visible = true

    abstract fun render(spriteBatch: SpriteBatch)

    open fun update(){}
    open fun onEvent(){}
}

class EmptyWidget: Widget(){

    override fun render(spriteBatch: SpriteBatch) {

    }

    override fun update() {
        size.set(0, 0)
        pos.set(0, 0)
    }
}

class PaintedWidget: Widget(){

    var backgroundColor = Color.WHITE;
    var border: Border = EmptyBorder()

    override fun render(spriteBatch: SpriteBatch) {
        RenderUtil.drawRect(pos, size, backgroundColor, spriteBatch)

        border.renderBorder(this, spriteBatch)
    }

    fun getContentRect() : Rectangle{
        val northSize = border.getSize(Direction.NORTH)
        val southSize = border.getSize(Direction.SOUTH)
        val eastSize = border.getSize(Direction.EAST)
        val westSize = border.getSize(Direction.WEST)

        val width = size.width - eastSize - westSize
        val height = size.height - northSize - southSize

        return Rectangle(pos.x + westSize, pos.y + southSize, width, height)
    }

}