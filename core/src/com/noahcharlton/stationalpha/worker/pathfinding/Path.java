package com.noahcharlton.stationalpha.worker.pathfinding;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class Path {

    private final WorldGraph worldGraph;
    private final Tile src;
    private final Tile dest;

    public Path(WorldGraph worldGraph, Tile src, Tile dest) {
        if(!src.getWorld().equals(dest.getWorld())){
            throw new IllegalArgumentException("Worlds must match!");
        }

        this.worldGraph = worldGraph;
        this.src = src;
        this.dest = dest;
    }

    public Optional<GraphPath<Tile>> calcPathSync(){
        GraphPath<Tile> path = new SimpleGraphPath();

        boolean pathFound = new IndexedAStarPathFinder<>(worldGraph)
                .searchNodePath(src, dest, new SimpleHeuristic(), path);

        if(pathFound)
            return Optional.of(path);

        return Optional.empty();
    }
}
