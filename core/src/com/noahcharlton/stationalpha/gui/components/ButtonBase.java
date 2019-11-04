package com.noahcharlton.stationalpha.gui.components;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.engine.audio.Sounds;

import java.util.Optional;

public abstract class ButtonBase extends Pane {

    private final Runnable runnable;
    private Optional<Integer> hotKey = Optional.empty();

    private boolean playSound = true;

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
        if(playSound)
            Sounds.CLICK.play(.3f);

        runnable.run();
    }

    public void setPlaySound(boolean playSound) {
        this.playSound = playSound;
    }

    public void setHotKey(int key) {
        this.hotKey = Optional.of(key);
    }
}
