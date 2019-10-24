package com.noahcharlton.stationalpha.gui.scenes.buildmenu

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.noahcharlton.stationalpha.engine.InGameIcon
import com.noahcharlton.stationalpha.gui.GuiComponent
import com.noahcharlton.stationalpha.gui.components.ComponentGroup
import com.noahcharlton.stationalpha.gui.components.Pane
import com.noahcharlton.stationalpha.gui.components.layout.LayoutManager
import com.noahcharlton.stationalpha.item.Item
import com.noahcharlton.stationalpha.world.World
import java.util.*


class InventoryMenu : ComponentGroup(), BuildMenu {

    private val layoutManager = ItemLayoutManager()

    init {
        setDrawBorder(true, true, true, true)
        setLayoutManager(layoutManager)

        addChildren()
    }

    private fun addChildren() {
        Item.values().forEach { item ->
            if(item != Item.TEST_ITEM)
                this.addGui(InventoryItemBox(item))
        }
    }

    override fun updatePosition() {
        x = (Gdx.graphics.width / 2) - (width / 2)
        y = (Gdx.graphics.height / 2) - (height / 2)
    }

    override fun updateSize() {}

    override fun getHotKey(): Optional<Int> {
        return Optional.of(Input.Keys.E);
    }

    override fun getIcon(): InGameIcon {
        return InGameIcon.INVENTORY;
    }

    override fun getName(): String {
        return "Inventory (Press E)"
    }

    override fun getComponent(): GuiComponent {
        return this;
    }
}

class InventoryItemBox(private val item: Item) : Pane(){

    companion object {
        const val ICON_SIZE = 50;
        const val WIDTH = 120;
        const val HEIGHT = 150;
    }

    override fun drawForeground(batch: SpriteBatch) {
        val iconX = x + (width / 2) - (ICON_SIZE / 2)
        val iconY = y + height - 20 - ICON_SIZE

        batch.draw(item.texture, iconX.toFloat(), iconY.toFloat(), ICON_SIZE.toFloat(), ICON_SIZE.toFloat())

        setFontData(.5f, Color.WHITE)
        drawCenteredText(batch, item.displayName, 50);
        drawCenteredText(batch, getItemCount(), 25);
    }

    private fun getItemCount(): String {
        return "" + World.getInstance().map { world ->
            world.inventory.getAmountForItem(item)
        }.orElse(0)
    }

    override fun updatePosition() {}

    override fun updateSize() {
        width = WIDTH
        height = HEIGHT
    }
}

class ItemLayoutManager : LayoutManager {

    companion object {
        const val PADDING = 4;
        const val COLUMNS = 4;
        const val SPACING = 5;
    }

    override fun layout(parent: GuiComponent, children: ArrayList<GuiComponent>) {
        parent.width = (PADDING * 2) + (COLUMNS * (InventoryItemBox.WIDTH + SPACING)) + SPACING

        var x = parent.x + PADDING + SPACING
        var height =  PADDING + SPACING + InventoryItemBox.HEIGHT

        children.forEach { child ->
            child.x = x
            child.y = parent.y + parent.height - height

            x += InventoryItemBox.WIDTH + SPACING;

            if(x > parent.x + parent.width - SPACING - PADDING){
                x = parent.x + PADDING + SPACING
                height += SPACING + InventoryItemBox.HEIGHT
            }
        }
        height += SPACING + PADDING

        parent.height = height
    }

}