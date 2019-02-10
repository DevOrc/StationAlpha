package com.noahcharlton.stationalpha.worker.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.noahcharlton.stationalpha.world.Tile;

public class SimpleHeuristic implements Heuristic<Tile> {

    @Override
    public float estimate(Tile src, Tile dest) {
        int diffX = Math.abs(src.getX() - dest.getX());
        int diffY = Math.abs(src.getY() - dest.getY());

        double hypoteneusSquared = Math.pow(diffX, 2) + Math.pow(diffY, 2);

        return (float) Math.sqrt(hypoteneusSquared);
    }
}
