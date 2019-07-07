package com.noahcharlton.stationalpha.block;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.ShapeUtil;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;

import java.util.Optional;

public class WallBlock extends Block implements BlockRenderer{

    private static final Color innerWallColor = new Color(112f/255f, 112f/255f, 112f/255f, 1f);

    @Override
    public Optional<Item> getRequiredItem() {
        return Optional.of(Item.STEEL);
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.of("wall.png");
    }

    @Override
    public String getDisplayName() {
        return "Wall";
    }

    @Override
    public void renderBlock(SpriteBatch spriteBatch, Tile tile) {
        World world = World.getInstance().get();

        int x = tile.getX() * Tile.TILE_SIZE;
        int y = tile.getY() * Tile.TILE_SIZE;


        spriteBatch.draw(getTexture().get().get(), x, y);

        if(shouldConnectNorth(tile, world)){
            renderNorth(x, y, spriteBatch);
        }

        if(shouldConnectSouth(tile, world)){
            renderSouth(x, y , spriteBatch);
        }

        if(shouldConnectEast(tile, world)){
            renderEast(x, y, spriteBatch);
        }

        if(shouldConnectWest(tile, world)){
            renderWest(x, y, spriteBatch);
        }
    }

    private void renderEast(int x, int y, SpriteBatch spriteBatch) {
        ShapeUtil.drawRect(x, y + 4, 5, 24, innerWallColor, spriteBatch);
    }

    private void renderWest(int x, int y, SpriteBatch spriteBatch) {
        ShapeUtil.drawRect(x + 28, y + 4, 5, 24, innerWallColor, spriteBatch);
    }

    private void renderSouth(int x, int y, SpriteBatch spriteBatch) {
        ShapeUtil.drawRect(x + 4, y, 24, 5, innerWallColor, spriteBatch);
    }

    private void renderNorth(int x, int y, SpriteBatch spriteBatch) {
        ShapeUtil.drawRect(x + 4, y + 28, 24, 5, innerWallColor, spriteBatch);
    }

    static boolean shouldConnectNorth(Tile tile, World world){
        return isWallBlock(world, tile.getX(), tile.getY() + 1);
    }

    static boolean shouldConnectSouth(Tile tile, World world){
        return isWallBlock(world, tile.getX(), tile.getY() - 1);
    }

    static boolean shouldConnectEast(Tile tile, World world){
        return isWallBlock(world, tile.getX() - 1, tile.getY());
    }

    static boolean shouldConnectWest(Tile tile, World world){
        return isWallBlock(world, tile.getX() + 1, tile.getY());
    }

    static boolean isWallBlock(World world, int x, int y){
        return world.getTileAt(x, y).map( northTile ->
                northTile.getBlock().map(block -> block instanceof WallBlock).orElse(false)).orElse(false);
    }

    @Override
    protected BlockRenderer createRenderer() {
        return this;
    }
}
