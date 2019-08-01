package com.noahcharlton.stationalpha.gui.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ItemList extends GuiComponent {

    @Override
    protected void drawBackground(SpriteBatch batch) {
        HashMap<Item, Integer> inventory = World.getInstance().get().getInventory().getItems();


        List<Item> items = new ArrayList<>(Arrays.asList(Item.values()));
        items.removeIf(item -> item == Item.TEST_ITEM);

        for(int i = 0; i < items.size(); i++) {
            Item item = items.get(i);

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
