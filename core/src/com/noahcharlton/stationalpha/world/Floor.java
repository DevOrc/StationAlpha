package com.noahcharlton.stationalpha.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;
import com.noahcharlton.stationalpha.item.Item;

import java.util.Optional;

public enum Floor {

    METAL("metal.png", "Metal", Item.STEEL),
    WOOD("wood.png", "Wood", Item.WOOD),
    DIRT("dirt.png", "Soil", Item.DIRT),
    GRASS("grass.png", "Grass", Item.DIRT);

    private final Optional<Item> requiredItem;
    private final String displayName;
    private final String filename;
    private final ManagedTexture texture;

    Floor(String filename, String displayName, Item item) {
        this.filename = filename;
        this.displayName = displayName;
        this.requiredItem = Optional.ofNullable(item);
        this.texture = new ManagedTexture("floor/" + filename);
    }

    public void render(SpriteBatch batch, Tile tile){
        int x = tile.getX() * Tile.TILE_SIZE;
        int y = tile.getY() * Tile.TILE_SIZE;

        batch.draw(texture.get(), x, y);
    }

    @Override
    public String toString() {
        return displayName;
    }

    String getFilename() {
        return filename;
    }

    public Optional<Item> getRequiredItem() {
        return requiredItem;
    }
}
