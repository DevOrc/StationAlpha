package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.InGameIcon;
import com.noahcharlton.stationalpha.world.Tile;

public interface BuildAction {

    void onClick(Tile tile, int button);

    default void onKeyPressed(int keycode){}

    default void render(SpriteBatch b){}

    default void onSelected(){}

    default void onDeselected(){}

    default InGameIcon getIcon(){
        return InGameIcon.NO_ICON;
    }

    String getName();
}
