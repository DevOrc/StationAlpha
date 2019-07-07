package com.noahcharlton.stationalpha.block;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Objects;
import java.util.Optional;

public abstract class Block {

    private final Optional<ManagedTexture> texture;
    private final BlockRenderer renderer;

    private boolean isOpaque;

    public Block() {
        this.texture = loadTexture();
        this.renderer = createRenderer();

        Objects.requireNonNull(renderer, "Renderer cannot be null!");
    }

    private Optional<ManagedTexture> loadTexture() {
        return getTextureFileName().map(path -> new ManagedTexture("blocks/" + path));
    }

    public BlockContainer createContainer(Tile tile, BlockRotation blockRotation){
        return new BlockContainer(tile, this, blockRotation);
    }

    public static BlockContainer getContainerFromTile(Tile tile){
        return tile.getContainer().orElseThrow(() -> new GdxRuntimeException("Tile must have container!"));
    }

    protected BlockRenderer createRenderer(){
        return new DefaultBlockRenderer(this);
    }

    protected abstract Optional<String> getTextureFileName();

    public Optional<ManagedTexture> getTexture() {
        return texture;
    }

    public String getDisplayName(){
        return getTextureFileName().orElse(getTextureFileName().orElse("Unnamed Block"));
    }

    public int getDimensionedWidth(){
        return 1;
    }

    public int getDimensionedHeight(){
        return 1;
    }

    public Optional<Item> getRequiredItem(){
        return Optional.empty();
    }

    public boolean isPlayerBuildable(){
        return true;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

    protected void setOpaque(boolean opaque) {
        isOpaque = opaque;
    }

    public boolean isOpaque() {
        return isOpaque;
    }

    public final void render(SpriteBatch spriteBatch, Tile tile) {
        renderer.renderBlock(spriteBatch, tile);
    }

    public Optional<String> getHelpInfo() {
        return Optional.empty();
    }
}
