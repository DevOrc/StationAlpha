package com.noahcharlton.stationalpha.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;

import java.util.Optional;

public enum GameCursor {

    ARROW("ui/cursors/arrow.png", 0, 0),
    PICK_AXE(InGameIcon.PICK_AXE, 0, 0),
    AXE(InGameIcon.AXE, 0, 0);

    private final InGameIcon icon;
    private final ManagedTexture texture;
    private final int hitPointX;
    private final int hitPointY;

    private Optional<Cursor> cursor = Optional.empty();

    GameCursor(String path, int hitPointX, int hitPointY) {
        this.texture = new ManagedTexture(path);
        this.texture.setOnLoad(this::onLoad);
        this.icon = InGameIcon.NO_ICON;

        this.hitPointX = hitPointX;
        this.hitPointY = hitPointY;
    }

    GameCursor(InGameIcon icon, int hitPointY, int hitPointX) {
        this.texture = icon.getTexture();
        this.texture.setOnLoad(this::onLoad);
        this.icon = icon;

        this.hitPointX = hitPointX;
        this.hitPointY = hitPointY;
    }

    public static void init(){
        ARROW.texture.setOnLoad(() -> {
            ARROW.onLoad();

            Gdx.graphics.setCursor(ARROW.getCursor());
        });
    }

    public ManagedTexture getTexture() {
        return texture;
    }

    private void onLoad() {
        Pixmap pixelMap = texture.get().getTextureData().consumePixmap();
        cursor = Optional.of(Gdx.graphics.newCursor(pixelMap, hitPointX, hitPointY));
    }

    public Cursor getCursor() {
        return cursor.orElseThrow(() -> new GdxRuntimeException("Cursor not loaded yet!"));
    }

    public InGameIcon getIcon() {
        return icon;
    }
}
