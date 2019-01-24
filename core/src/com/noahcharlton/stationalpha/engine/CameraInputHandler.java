package com.noahcharlton.stationalpha.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraInputHandler {

    private static final float moveSpeed = 10f;

    public static void update(OrthographicCamera cam) {
        updateCameraPosition(cam);
    }

    private static void updateCameraPosition(OrthographicCamera cam) {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            cam.translate(moveSpeed, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.translate(-moveSpeed, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            cam.translate(0, -moveSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.translate(0, moveSpeed);
        }
    }

}
