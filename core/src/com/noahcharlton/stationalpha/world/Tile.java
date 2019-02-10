package com.noahcharlton.stationalpha.world;

import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.BlockContainer;

import java.util.Objects;
import java.util.Optional;

public final class Tile {

    public static final int TILE_SIZE = 32;

    private final World world;
    private final int x;
    private final int y;

    private Optional<Block> block;
    private Optional<BlockContainer> container;
    private Optional<Floor> floor;

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

    public void setBlock(Block block){
        this.block = Optional.ofNullable(block);

        this.container = this.block.flatMap(b -> b.createContainer(this));

        world.triggerWorldUpdate(x, y);
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
}
