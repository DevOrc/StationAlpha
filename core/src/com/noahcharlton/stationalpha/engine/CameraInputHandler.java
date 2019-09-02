package com.noahcharlton.stationalpha.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraInputHandler {

    private static final float scrollZoomSpeed = .25f;
    private static final float zoomSpeed = 3f;
    private static final float moveSpeed = 600f;

    public static void update(OrthographicCamera cam) {
        updateCameraPosition(cam);
        updateCameraZoom(cam);
    }

    private static void updateCameraPosition(OrthographicCamera cam) {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            cam.translate(moveSpeed * Gdx.graphics.getDeltaTime(), 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.translate(-moveSpeed * Gdx.graphics.getDeltaTime(), 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            cam.translate(0, -moveSpeed * Gdx.graphics.getDeltaTime());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.translate(0, moveSpeed * Gdx.graphics.getDeltaTime());
        }
    }

    private static void updateCameraZoom(OrthographicCamera cam) {
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            cam.zoom += zoomSpeed * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            cam.zoom -= zoomSpeed * Gdx.graphics.getDeltaTime();
        }

        trimZoom(cam);
    }

    static void trimZoom(OrthographicCamera cam) {
        if (cam.zoom < .5f) {
            cam.zoom = .5f;
        } else if (cam.zoom > 5.75) {
            cam.zoom = 5.75f;
        }
    }

    public static void onScroll(OrthographicCamera camera, int amount) {
        camera.zoom += scrollZoomSpeed * amount;
        trimZoom(camera);
    }
}
