package com.noahcharlton.stationalpha.gui.components;

public abstract class ButtonBase extends Pane {

    private final Runnable runnable;

    public ButtonBase(Runnable onClick) {
        this.runnable = onClick;
    }

    @Override
    protected void onClick() {
        runnable.run();
    }
}
