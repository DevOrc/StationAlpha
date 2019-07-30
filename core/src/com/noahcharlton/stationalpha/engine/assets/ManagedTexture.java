package com.noahcharlton.stationalpha.engine.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.Optional;

public class ManagedTexture implements Asset {

    private final String path;
    private Optional<Texture> texture = Optional.empty();

    private volatile boolean loaded = false;

    public ManagedTexture(String path) {
        this.path = path;

        AssetManager.getInstance().addAsset(this);
    }

    public Texture get() {
        return texture.orElseThrow(() -> new GdxRuntimeException("Cannot get texture before its been loaded!"));
    }

    @Override
    public void load() {
        FileHandle file = Gdx.files.internal(path);
        byte[] data = loadData(file);

        Gdx.app.postRunnable(() -> {
            Pixmap pixmap = new Pixmap(data, 0, data.length);
            texture = Optional.of(new Texture(pixmap));
            loaded = true;
        });

        while(!loaded){
            Thread.yield();
        }
    }

    private byte[] loadData(FileHandle file) {
        try {
            return file.readBytes();
        } catch (GdxRuntimeException ioException) {
            GdxRuntimeException e = new GdxRuntimeException("Failed to load texture at path: " + file, ioException);
            Gdx.app.postRunnable(() -> {
                throw e;
            });

            throw e;
        }
    }

    @Override
    public void dispose() {
        texture.ifPresent(Texture::dispose);
    }

    @Override
    public String getPath() {
        return path;
    }
}
