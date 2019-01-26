package com.noahcharlton.stationalpha.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.StationAlpha;
import com.noahcharlton.stationalpha.block.Blocks;

import java.util.Optional;
import java.util.Random;

public class World {

    public static final int WORLD_TILE_SIZE = 150;
    public static final int WORLD_PIXEL_SIZE = WORLD_TILE_SIZE * Tile.TILE_SIZE;

    private final Tile[][] tiles = new Tile[WORLD_TILE_SIZE][WORLD_TILE_SIZE];

    public World() {
        fillTiles();
    }

    private void fillTiles() {
        Random random = new Random();
        for(int x = 0; x < WORLD_TILE_SIZE; x++){
            for(int y = 0; y < WORLD_TILE_SIZE; y++){
                tiles[x][y] = new Tile(x, y );

                if(random.nextBoolean()){
                    tiles[x][y].setBlock(Blocks.getWall());
                }
            }
        }
    }

    public void render(SpriteBatch spriteBatch) {
        for(int x = 0; x < WORLD_TILE_SIZE; x++){
            for(int y = 0; y < WORLD_TILE_SIZE; y++){
                Tile tile = tiles[x][y];

                tile.getBlock().ifPresent(block -> block.render(spriteBatch, tile));
            }
        }
    }

    public static Optional<World> getInstance(){
        return StationAlpha.getInstance().getWorld();
    }
}
