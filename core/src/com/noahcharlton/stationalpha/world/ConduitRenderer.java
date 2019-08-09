package com.noahcharlton.stationalpha.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.engine.ShapeUtil;

import java.util.List;

public class ConduitRenderer {

    enum Direction {NORTH, SOUTH, EAST, WEST}

    public static void render(World world, SpriteBatch batch){
        for(int x = 0; x < World.WORLD_TILE_SIZE; x++){
            for(int y = 0; y < World.WORLD_TILE_SIZE; y++){
                world.getTileAt(x, y).ifPresent(tile -> renderTile(tile, batch));
            }
        }
    }

    private static void renderTile(Tile tile, SpriteBatch batch) {
        int baseX = tile.getX() * Tile.TILE_SIZE;
        int baseY = tile.getY() * Tile.TILE_SIZE;

        if(tile.hasConduit()){
            ShapeUtil.drawRect(baseX + 12, baseY + 12, 8, 8, Color.RED, batch);

            List<Tile> tileList = tile.getAdjacent();
            tileList.forEach(neighbor -> drawArm(tile, neighbor, batch));
        }


    }

    private static void drawArm(Tile tile, Tile neighbor, SpriteBatch batch) {
        if(!neighbor.hasConduit())
            return;

        Direction direction = getDirection(tile, neighbor);
        int baseX = (tile.getX() * Tile.TILE_SIZE);
        int baseY = (tile.getY() * Tile.TILE_SIZE);

        switch(direction){
            case NORTH:
                ShapeUtil.drawRect(baseX + 12, baseY + 12, 8, Tile.TILE_SIZE - 12, Color.RED, batch);
                break;
            case SOUTH:
                ShapeUtil.drawRect(baseX + 12, baseY, 8, Tile.TILE_SIZE - 12, Color.RED, batch);
                break;
            case EAST:
                ShapeUtil.drawRect(baseX + 12, baseY + 12, Tile.TILE_SIZE - 12, 8, Color.RED, batch);
                break;
            case WEST:
                ShapeUtil.drawRect(baseX, baseY + 12, Tile.TILE_SIZE - 12, 8, Color.RED, batch);
                break;
        }
    }

    static Direction getDirection(Tile root, Tile neighbor) {
        if(root.getX() < neighbor.getX())
            return Direction.EAST;
        if(root.getX() > neighbor.getX())
            return Direction.WEST;
        if(root.getY() < neighbor.getY())
            return Direction.NORTH;
        if(root.getY() > neighbor.getY())
            return Direction.SOUTH;

        throw new GdxRuntimeException(
                String.format("Could not find direction! Root=%s Neighbor=%s", root, neighbor));
    }
}
