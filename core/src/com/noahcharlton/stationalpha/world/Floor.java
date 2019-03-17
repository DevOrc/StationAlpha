package com.noahcharlton.stationalpha.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;

public enum Floor {

    METAL("metal.png"), WOOD("wood.png"), DIRT("dirt.png"), BRICK("brick.png");

    private final String filename;
    private final ManagedTexture texture;

    Floor(String filename) {
        this.filename = filename;
        this.texture = new ManagedTexture("floor/" + filename);
    }

    public void render(SpriteBatch batch, Tile tile){
        int x = tile.getX() * Tile.TILE_SIZE;
        int y = tile.getY() * Tile.TILE_SIZE;

        batch.draw(texture.get(), x, y);
    }

    String getFilename() {
        return filename;
    }
}
