package com.noahcharlton.stationalpha.world.load;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.Blocks;
import com.noahcharlton.stationalpha.world.Floor;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;

import java.util.Optional;

public class WorldLoader {

    public static void load(XmlReader.Element tiles, World world) {
        for(XmlReader.Element tile: tiles.getChildrenByName("Tile")){
            parseTile(tile, world);
        }
    }

    private static void parseTile(XmlReader.Element tileXML, World world) {
        Optional<Tile> tile = getTileFromElement(tileXML, world);

        if(tile.isPresent()){
            loadTileFloor(tile.get(), tileXML);
            loadTileOxygen(tile.get(), tileXML);

            Optional<XmlReader.Element> block = Optional.ofNullable(tileXML.getChildByName("Container"));
            block.ifPresent(element -> loadTileBlock(tile.get(), element));
        }else{
            throw new GdxRuntimeException("Invalid tile in save file: " + tileXML);
        }
    }

    static Optional<Tile> getTileFromElement(XmlReader.Element tileXML, World world) {
        int x = tileXML.getInt("x");
        int y = tileXML.getInt("y");

        return world.getTileAt(x, y);
    }

    private static void loadTileBlock(Tile tile, XmlReader.Element containerElement) {
        String blockID = containerElement.getAttribute("Block", null);

        if(blockID == null){
            loadContainerCopy(tile, containerElement);
        }else{
            loadContainerRoot(tile, containerElement);
        }
    }

    static void loadContainerRoot(Tile tile, XmlReader.Element containerElement) {
        String blockID = containerElement.getAttribute("Block");
        BlockRotation rotation = BlockRotation.valueOf(containerElement.getAttribute("Rotation"));
        Block block = Blocks.getByID(blockID)
                .orElseThrow(() -> new GdxRuntimeException("Unknown block found in save file: " + blockID));

        BlockContainer container = block.createContainer(tile, rotation);
        container.onLoad(containerElement);

        tile.setBlock(block, container);
    }

    static void loadContainerCopy(Tile tile, XmlReader.Element containerElement) {
        int rootX = containerElement.getInt("rootX");
        int rootY = containerElement.getInt("rootY");

        tile.getWorld().getTileAt(rootX, rootY).flatMap(Tile::getContainer)
                .ifPresent(blockContainer -> tile.setBlock(blockContainer.getBlock(), blockContainer));
    }

    static void loadTileFloor(Tile tile, XmlReader.Element tileXML) {
        String floor = tileXML.get("Floor", null);

        if(floor != null){
            tile.setFloor(Floor.valueOf(floor));
        }
    }

    static void loadTileOxygen(Tile tile, XmlReader.Element tileXML) {
        float oxygen = tileXML.getFloatAttribute("oxygen");

        tile.setOxygen(oxygen);
    }
}
