package com.noahcharlton.zulu.widget

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.noahcharlton.zulu.Direction
import com.noahcharlton.zulu.RenderUtil

interface Border {

    fun renderBorder(widget: Widget, batch: SpriteBatch);

    fun getSize(direction: Direction): Int;

}

class EmptyBorder : Border {

    override fun renderBorder(widget: Widget, batch: SpriteBatch) {
    }

    override fun getSize(direction: Direction): Int {
        return 0;
    }

}

class LineBorder(var thickness: Int = 2,
                 var color: Color = Color.RED,
                 var renderNorth: Boolean = true,
                 var renderEast: Boolean = true,
                 var renderWest: Boolean = true,
                 var renderSouth: Boolean = true) : Border {

    override fun renderBorder(widget: Widget, batch: SpriteBatch) {
        if (renderNorth) {
            RenderUtil.drawRect(widget.pos.x, widget.pos.y + widget.size.height - thickness,
                    widget.size.width, thickness, color, batch)
        }

        if (renderSouth) {
            RenderUtil.drawRect(widget.pos.x, widget.pos.y, widget.size.width, thickness, color, batch)
        }

        if (renderWest) {
            RenderUtil.drawRect(widget.pos.x, widget.pos.y, thickness, widget.size.height, color, batch)
        }

        if (renderEast) {
            RenderUtil.drawRect(widget.pos.x + widget.size.width - thickness, widget.pos.y, thickness,
                    widget.size.height, color, batch)
        }
    }

    override fun getSize(direction: Direction): Int {
        return when (direction) {
            Direction.NORTH -> if (renderNorth) thickness else 0
            Direction.SOUTH -> if (renderSouth) thickness else 0
            Direction.EAST -> if (renderEast) thickness else 0
            Direction.WEST -> if (renderWest) thickness else 0
        }
    }

}