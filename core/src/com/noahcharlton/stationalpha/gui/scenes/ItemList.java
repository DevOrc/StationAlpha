package com.noahcharlton.stationalpha.gui.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.World;

import java.util.HashMap;

public class ItemList extends GuiComponent {

    @Override
    protected void drawBackground(SpriteBatch batch) {
        HashMap<Item, Integer> inventory = World.getInstance().get().getInventory().getItems();

        Item[] items = Item.values();

        for(int i = 0; i < items.length; i++) {
            Item item = items[i];

            renderItem(i, item, inventory.get(item), batch);
        }
    }

    private void renderItem(int i, Item item, int count, SpriteBatch b) {
        int y = Gdx.graphics.getHeight() - (i * 40) - 40;

        b.draw(item.getTexture(), 10, y);

        setFontData(.5f, Color.WHITE);
        font.draw(b, item + ": " + count, 50, y + 20);
    }

    @Override
    protected void drawForeground(SpriteBatch batch) {

    }
}
