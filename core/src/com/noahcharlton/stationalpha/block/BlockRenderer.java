package com.noahcharlton.stationalpha.block;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.world.Tile;

public interface BlockRenderer {

    void render(SpriteBatch batch, Tile tile);
}
