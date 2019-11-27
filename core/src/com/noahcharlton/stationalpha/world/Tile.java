package com.noahcharlton.stationalpha.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;
import com.noahcharlton.stationalpha.block.BlockRotation;
import com.noahcharlton.stationalpha.block.power.PoweredContainer;
import com.noahcharlton.stationalpha.engine.input.Selectable;
import com.noahcharlton.stationalpha.gui.GuiComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class Tile implements Selectable {

    public static final int TILE_SIZE = 32;
    public static final int MAX_POWER = 25;

    private final World world;
    private final int x;
    private final int y;

    private Optional<Block> block;
    private Optional<BlockContainer> container;
    private Optional<Floor> floor;
    private float oxygenLevel;

    private boolean hasPlacedConduit;

    public Tile(int x, int y, World world) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.block = Optional.empty();
        this.container = Optional.empty();
        this.floor = Optional.empty();

        checkWithinWorld(x, "X coordinate not inside world!");
        checkWithinWorld(y, "Y coordinate not inside world!");

        Objects.requireNonNull(world, "World cannot be null!");
    }

    private void checkWithinWorld(int coordinate, String s) {
        if(coordinate < 0 || coordinate >= World.WORLD_TILE_SIZE)
            throw new IllegalArgumentException(s);
    }

    public ArrayList<Tile> getAdjacent(){
        ArrayList<Tile> tiles = new ArrayList<>();

        world.getTileAt(x - 1, y).ifPresent(tiles::add);
        world.getTileAt(x + 1, y).ifPresent(tiles::add);
        world.getTileAt(x, y - 1).ifPresent(tiles::add);
        world.getTileAt(x, y + 1).ifPresent(tiles::add);

        return tiles;
    }

    public Optional<Tile> getOpenAdjecent() {
        ArrayList<Tile> adjacentTiles = this.getAdjacent();

        for(Tile tile : adjacentTiles){
            if(!tile.hasNonPassableBlock())
                return Optional.of(tile);
        }

        return Optional.empty();
    }

    public boolean hasNonPassableBlock() {
        return getBlock().filter(block -> !block.isPassable()).isPresent();
    }

    public void updateOxygen(){
        getAdjacent().forEach(tile -> {
            boolean needsOxygen = tile.oxygenLevel < this.oxygenLevel;
            boolean acceptsOxygen = !tile.getBlock().filter(Block::isOpaque).isPresent();

            if(acceptsOxygen && needsOxygen){
                transferOxygen(tile, this);
            }
        });
    }

    static void transferOxygen(Tile dest, Tile src) {
        if(dest.getOxygenLevel() > src.getOxygenLevel())
            throw new IllegalArgumentException("Destination oxygen must be lower than source tile!");

        float diff = (src.oxygenLevel - dest.oxygenLevel) / 2;

        if(diff > 15){
            diff = 15;
        }

        dest.changeOxygenLevel(diff);
        src.changeOxygenLevel(-diff);
    }

    public void setOxygen(float oxygen) {
        oxygenLevel = oxygen;

        if(oxygenLevel > 100)
            oxygenLevel = 100;

        if(oxygenLevel < 0)
            oxygenLevel = 0;

        applyOxygenRules();
    }

    public void changeOxygenLevel(float amount){
       setOxygen(oxygenLevel + amount);
    }

    private void applyOxygenRules() {
        if(!getFloor().isPresent())
            oxygenLevel = 0;

        if(getBlock().filter(Block::isOpaque).isPresent())
            oxygenLevel = 0;
    }

    public void drawOxygen(SpriteBatch spriteBatch) {
        BitmapFont font = GuiComponent.getFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(.33f);

        int pixelX = x * Tile.TILE_SIZE;
        int pixelY = y * Tile.TILE_SIZE + (Tile.TILE_SIZE / 2);
        String text = String.format("%3.0f", oxygenLevel) + "%";

        font.draw(spriteBatch, text, pixelX, pixelY, Tile.TILE_SIZE, Align.center, false);
    }

    public void setConduit(boolean hasConduit) {
        this.hasPlacedConduit = hasConduit;
    }

    public boolean hasConduit() {
        boolean poweredContainer = container.filter(container -> container instanceof PoweredContainer).isPresent();

        return hasPlacedConduit || poweredContainer;
    }

    @Deprecated
    /**
     * @deprecated
     * This should not be used! Instead use hasConduit()
     *  This function returns if the user placed a conduit, not if one is present!
     */
    public boolean hasPlacedConduit() {
        return hasPlacedConduit;
    }

    @Override
    public String getTitle() {
        return toString();
    }

    @Override
    public String getDesc() {
        return "This is a tile.";
    }

    @Override
    public String[] getDebugInfo() {
        List<String> info = new ArrayList<>();
        info.add("Floor: " + floor.map(Floor::toString).orElse("None"));
        info.add("Oxygen: " + oxygenLevel);

        return info.toArray(new String[0]);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Tile)) return false;
        Tile tile = (Tile) o;
        return x == tile.x &&
                y == tile.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Tile(" + x + ", " + y + ")";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setBlock(Block block, BlockContainer container){
        if(block == null && container != null)
            throw new IllegalArgumentException("Container must be null if block is null");

        if(block != null && container == null)
            throw new IllegalArgumentException("Container cannot be null if block is not null!");

        this.container.ifPresent(BlockContainer::onDestroy);
        this.block = Optional.ofNullable(block);
        this.container = Optional.ofNullable(container);

        world.triggerWorldUpdate(x, y);
    }

    public void setBlock(Block block){
        if(block != null)
            setBlock(block, block.createContainer(this, BlockRotation.NORTH));
        else
            setBlock(null, null);
    }

    public void setFloor(Floor floor){
        this.floor = Optional.ofNullable(floor);

        world.triggerWorldUpdate(x, y);
    }

    public Optional<Block> getBlock() {
        return block;
    }

    public Optional<BlockContainer> getContainer() {
        return container;
    }

    public World getWorld() {
        return world;
    }

    public void onWorldUpdate() {
        container.ifPresent(BlockContainer::onBlockUpdate);
    }

    public Optional<Floor> getFloor() {
        return floor;
    }

    public float getOxygenLevel() {
        return oxygenLevel;
    }
}
