package com.noahcharlton.stationalpha.worker.pathfinding;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;

public class SimpleGraphPath extends DefaultGraphPath {

    @Override
    public String toString() {
        return this.nodes.toString(" -> ");
    }
}
