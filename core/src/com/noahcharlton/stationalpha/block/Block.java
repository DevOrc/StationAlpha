package com.noahcharlton.stationalpha.block;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;
import com.noahcharlton.stationalpha.item.ItemStack;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class Block {

    private final Optional<ManagedTexture> texture;
    private final BlockRenderer renderer;

    private boolean isPassable = false;
    private boolean isOpaque = true;

    public Block() {
        this.texture = loadTexture();
        this.renderer = createRenderer();

        Objects.requireNonNull(renderer, "Renderer cannot be null!");
    }

    public abstract String getID();

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

    public List<ItemStack> getRequirements(){
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Block)) return false;
        Block block = (Block) o;
        return getID().equals(block.getID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isOpaque());
    }

    protected void setOpaque(boolean opaque) {
        isOpaque = opaque;
    }

    protected void setPassable(boolean passable) {
        isPassable = passable;
    }

    public boolean isPassable() {
        return isPassable;
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
