package com.noahcharlton.stationalpha.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class DebugKeys {

    public static final int DEBUG_KEY = Input.Keys.CONTROL_RIGHT;

    public static final int PATHFIND = Input.Keys.P;

    public static final int INVENTORY = Input.Keys.I;

    public static final int DEBUG_BOX = Input.Keys.U;

    public static boolean isDebugPressed(int key){
        return Gdx.input.isKeyPressed(DEBUG_KEY) && Gdx.input.isKeyPressed(key);
    }

}
