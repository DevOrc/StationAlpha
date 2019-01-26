package com.noahcharlton.stationalpha.block;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Objects;

public abstract class Block {

    private final ManagedTexture texture;
    private final BlockRenderer renderer;

    public Block() {
        this.texture = loadTexture();
        this.renderer = createRenderer();

        Objects.requireNonNull(renderer, "Renderer cannot be null!");
    }

    private ManagedTexture loadTexture() {
        return new ManagedTexture("blocks/" + getTextureName());
    }

    protected BlockRenderer createRenderer(){
        return new DefaultBlockRenderer(this);
    }

    protected abstract String getTextureName();

    ManagedTexture getTexture() {
        return texture;
    }

    public void render(SpriteBatch spriteBatch, Tile tile) {
        renderer.render(spriteBatch, tile);
    }
}
