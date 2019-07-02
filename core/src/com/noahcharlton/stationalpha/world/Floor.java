package com.noahcharlton.stationalpha.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;

public enum Floor {

    METAL("metal.png", "Metal"),
    WOOD("wood.png", "Wood"),
    DIRT("dirt.png", "Soil"),
    BRICK("brick.png", "Brick"),
    GRASS("grass.png", "Grass");

    private final String displayName;
    private final String filename;
    private final ManagedTexture texture;

    Floor(String filename, String displayName) {
        this.filename = filename;
        this.displayName = displayName;
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
}
