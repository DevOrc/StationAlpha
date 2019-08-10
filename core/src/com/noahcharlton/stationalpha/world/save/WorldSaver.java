package com.noahcharlton.stationalpha.world.save;

import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.world.Floor;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;

public class WorldSaver {

    private final World world;

    public WorldSaver(World world) {
        this.world = world;
    }

    void save(QuietXmlWriter writer) {
        saveTiles(writer);
    }

    private void saveTiles(QuietXmlWriter writer) {
        QuietXmlWriter tilesGroup = writer.element("Tiles");

        for(int x = 0; x < World.WORLD_TILE_SIZE; x++) {
            for(int y = 0; y < World.WORLD_TILE_SIZE; y++) {
                world.getTileAt(x, y).ifPresent(tile -> {
                    QuietXmlWriter element = saveTile(tile, tilesGroup);

                    tile.getFloor().ifPresent(floor -> saveFloor(floor, element));
                    tile.getContainer().ifPresent(container -> saveBlock(tile, container, element));
                    element.pop();
                });
            }
        }

        tilesGroup.pop();
    }

    void saveFloor(Floor floor, QuietXmlWriter element) {
        element.element("Floor", floor.name());
    }

    void saveBlock(Tile tile, BlockContainer container, QuietXmlWriter element){
        if(!container.getTile().equals(tile)){
            element.element("Container")
                    .attribute("rootX", container.getTile().getX())
                    .attribute("rootY", container.getTile().getY())
                    .pop();
        }else{
            QuietXmlWriter containerWriter = element.element("Container")
                .attribute("Block", container.getBlock().getID())
                .attribute("Rotation", container.getRotation().name());

            container.onSave(containerWriter);
            containerWriter.pop();
        }
    }

    QuietXmlWriter saveTile(Tile tile, QuietXmlWriter writer) {
        return writer.element("Tile")
                .attribute("x", tile.getX())
                .attribute("y", tile.getY())
                .attribute("oxygen", tile.getOxygenLevel())
                .attribute("manualConduit", tile.hasPlacedConduit())
                .attribute("power", tile.getPower());
    }
}
