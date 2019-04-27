package com.noahcharlton.stationalpha.block.door;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRenderer;
import com.noahcharlton.stationalpha.engine.ShapeUtil;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class DoorBlock extends Block implements BlockRenderer {

    public DoorBlock() {
        setOpaque(true);
    }

    @Override
    public void renderBlock(SpriteBatch batch, Tile tile) {
        DoorContainer container = (DoorContainer) this.getContainerFromTile(tile);

        switch(container.getOrientation()){
            case NORTH_SOUTH:
                drawNorthSouth(batch, tile);
                break;
            case EAST_WEST:
                drawEastWest(batch, tile);
                break;
            default:
                throw new GdxRuntimeException("Test");
        }
    }

    private void drawEastWest(SpriteBatch batch, Tile tile) {
        int x = tile.getX() * Tile.TILE_SIZE;
        int y = tile.getY() * Tile.TILE_SIZE;

        ShapeUtil.drawRect(x, y + 12, Tile.TILE_SIZE, 8, Color.FOREST, batch);
    }

    private void drawNorthSouth(SpriteBatch batch, Tile tile) {
        int x = tile.getX() * Tile.TILE_SIZE;
        int y = tile.getY() * Tile.TILE_SIZE;

        ShapeUtil.drawRect(x + 12, y, 8, Tile.TILE_SIZE, Color.FOREST, batch);
    }

    @Override
    protected BlockRenderer createRenderer() {
        return this;
    }

    @Override
    public BlockContainer createContainer(Tile tile) {
        return new DoorContainer(tile, this);
    }

    @Override
    protected Optional<String> getTextureFileName() {
        return Optional.empty();
    }

    @Override
    public String getDisplayName() {
        return "Door";
    }
}
