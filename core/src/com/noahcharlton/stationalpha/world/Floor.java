package com.noahcharlton.stationalpha.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;
import com.noahcharlton.stationalpha.item.Item;

import java.util.Optional;

public enum Floor {

    METAL("metal.png", "Metal", Optional.of(Item.STEEL)),
    WOOD("wood.png", "Wood", Optional.of(Item.WOOD)),
    DIRT("dirt.png", "Soil", Optional.of(Item.DIRT)),
    BRICK("brick.png", "Brick", Optional.empty()),
    GRASS("grass.png", "Grass", Optional.empty());

    private final Optional<Item> requiredItem;
    private final String displayName;
    private final String filename;
    private final ManagedTexture texture;

    Floor(String filename, String displayName, Optional<Item> item) {
        this.filename = filename;
        this.displayName = displayName;
        this.requiredItem = item;
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
