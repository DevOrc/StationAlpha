package com.noahcharlton.stationalpha.world.save;

import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;

public class WorldSaver {

    private final World world;
    private QuietXmlWriter writer;

    public WorldSaver(World world, QuietXmlWriter writer) {
        this.world = world;
        this.writer = writer;
    }

    void save() {
        saveTiles();
    }

    private void saveTiles() {
        for(int x = 0; x < World.WORLD_TILE_SIZE; x++) {
            for(int y = 0; y < World.WORLD_TILE_SIZE; y++) {
                world.getTileAt(x, y).ifPresent(this::saveTile);
            }
        }
    }

    void saveTile(Tile tile) {
        writer.element("Tile")
                .attribute("x", tile.getX())
                .attribute("y", tile.getY())
                .attribute("oxygen", tile.getOxygenLevel())
                .pop();
    }
}
