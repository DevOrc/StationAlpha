package com.noahcharlton.stationalpha.worker.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.noahcharlton.stationalpha.block.Block;
import com.noahcharlton.stationalpha.block.door.DoorBlock;
import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;

import java.util.Optional;

public class WorldGraph implements IndexedGraph<Tile> {

    private final World world;

    public WorldGraph(World world) {
        this.world = world;
    }

    public Path generatePath(Tile tileOn, Tile target) {
        return new Path(this, tileOn, target);
    }

    @Override
    public int getIndex(Tile node) {
        return node.getY() * 150 + node.getX();
    }

    @Override
    public Array<Connection<Tile>> getConnections(Tile src) {
        Array<Connection<Tile>> output = new Array<>();
        int x = src.getX();
        int y = src.getY();

        checkConnection(src, world.getTileAt(x + 1, y)).ifPresent(output::add);
        checkConnection(src, world.getTileAt(x - 1, y)).ifPresent(output::add);
        checkConnection(src, world.getTileAt(x, y + 1)).ifPresent(output::add);
        checkConnection(src, world.getTileAt(x, y - 1)).ifPresent(output::add);

        return output;
    }

    private Optional<Connection<Tile>> checkConnection(Tile src, Optional<Tile> dest) {
        if(dest.map(this::doesTileBlockPaths).orElse(false))
            return Optional.empty();

        return dest.map(tile -> new DefaultConnection<>(src, tile));
    }

    boolean doesTileBlockPaths(Tile tile) {
        if(tile.getBlock().isPresent()){
            Block block = tile.getBlock().get();

            if(block instanceof DoorBlock)
                return false;

            return !block.isPassable();
        }

        return false;
    }

    @Override
    public int getNodeCount() {
        return World.WORLD_TILE_SIZE * World.WORLD_TILE_SIZE;
    }
}