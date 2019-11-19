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

    open fun layout(){}
}

class EmptyWidget: Widget(){

    override fun render(spriteBatch: SpriteBatch) {

    }

    override fun update() {
        size.set(0, 0)
        pos.set(0, 0)
    }
}

open class PaintedWidget(
        var backgroundColor: Color = Color.WHITE,
        var border: Border = EmptyBorder()): Widget(){

    var paddingNorth: Int = 5
    var paddingSouth: Int = 5
    var paddingEast: Int = 5
    var paddingWest: Int = 5

    override fun render(spriteBatch: SpriteBatch) {
        RenderUtil.drawRect(pos, size, backgroundColor, spriteBatch)

        border.renderBorder(this, spriteBatch)
    }

    fun getContentRect() : Rectangle{
        val northSize = border.getSize(Direction.NORTH) + paddingNorth
        val southSize = border.getSize(Direction.SOUTH) + paddingSouth
        val eastSize = border.getSize(Direction.EAST) + paddingEast
        val westSize = border.getSize(Direction.WEST) + paddingWest

        val width = size.width - eastSize - westSize
        val height = size.height - northSize - southSize

        return Rectangle(pos.x + westSize, pos.y + southSize, width, height)
    }

    fun setPadding(value: Int){
        setPadding(value, value, value, value)
    }

    fun setPadding(north: Int, south: Int, west: Int, east: Int){
        paddingNorth = north
        paddingSouth = south
        paddingWest = west
        paddingEast = east
    }
}