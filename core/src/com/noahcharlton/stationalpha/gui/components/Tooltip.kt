package com.noahcharlton.stationalpha.gui.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.noahcharlton.stationalpha.engine.ShapeUtil
import com.noahcharlton.stationalpha.gui.GuiComponent

abstract class Tooltip(val text: String) {

    companion object {
        const val HOVER_VISIBLE_TIME = 750;
    }

    private var showTooltipTime = 0L;

    fun updateAndRender(batch: SpriteBatch, comp: GuiComponent) {
        if (!comp.isHovering) {
            showTooltipTime = System.currentTimeMillis() + HOVER_VISIBLE_TIME;
        }

        if (System.currentTimeMillis() > showTooltipTime) {
            render(batch, comp);
        }
    }

    protected open fun render(batch: SpriteBatch, comp: GuiComponent) {}

}

class DefaultTooltip(text: String) : Tooltip(text) {

    companion object {
        const val SPACING = 20;
        const val INNER_SPACING = 5;
    }

    private var x = 0;
    private var y = 0;
    private var width = 0;
    private var height = 0;

    override fun render(batch: SpriteBatch, comp: GuiComponent) {
        layout(comp)
        drawBackground(batch)
        drawText(batch)
    }

    private fun drawText(batch: SpriteBatch) {
        val font = GuiComponent.getFont()
        font.color = Color.WHITE
        font.data.setScale(.5f)

        val x = x + INNER_SPACING;
        val y = y + height - INNER_SPACING;

        val textSize = font.draw(batch, text, x.toFloat(), y.toFloat())
        width = textSize.width.toInt() + (2 * INNER_SPACING)
        height = textSize.height.toInt() + (2 * INNER_SPACING)
    }

    private fun drawBackground(batch: SpriteBatch) {
        ShapeUtil.drawRect(x, y, width, height, Color.BLACK, batch)
    }

    private fun layout(comp: GuiComponent) {
        this.x = comp.x + (comp.width / 2) - (width / 2)
        this.y = comp.y + comp.height + SPACING
    }

}

class EmptyTooltip : Tooltip("") {

}
