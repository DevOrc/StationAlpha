package com.noahcharlton.stationalpha.gui.components;

import com.badlogic.gdx.Gdx;

import java.util.Optional;

public abstract class ButtonBase extends Pane {

    private final Runnable runnable;
    private Optional<Integer> hotKey = Optional.empty();

    public ButtonBase(Runnable onClick) {
        this.runnable = onClick;
    }

    @Override
    protected void update() {
        if(hotKey.filter(key -> Gdx.input.isKeyJustPressed(key)).isPresent()){
            onClick();
        }
    }

    @Override
    protected void onClick() {
        runnable.run();
    }

    public void setHotKey(int key) {
        this.hotKey = Optional.of(key);
    }
}
