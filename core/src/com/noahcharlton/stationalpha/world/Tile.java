package com.noahcharlton.stationalpha.world;

import com.noahcharlton.stationalpha.block.Block;

import java.util.Objects;
import java.util.Optional;

public final class Tile {

    public static final int TILE_SIZE = 32;

    private final int x;
    private final int y;

    private Optional<Block> block;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.block = Optional.empty();
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

    public void setBlock(Block block) {
        this.block = Optional.ofNullable(block);
    }

    public Optional<Block> getBlock() {
        return block;
    }
}
