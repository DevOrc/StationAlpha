package com.noahcharlton.stationalpha.engine.input;

import com.noahcharlton.stationalpha.world.Tile;

public interface BuildAction {

    void onClick(Tile tile, int button);

    String getName();
}
