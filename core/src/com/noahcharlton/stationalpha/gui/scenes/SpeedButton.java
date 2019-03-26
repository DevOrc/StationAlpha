package com.noahcharlton.stationalpha.gui.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.StationAlpha;
import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;
import com.noahcharlton.stationalpha.gui.GuiComponent;

public class SpeedButton extends GuiComponent {

    static final String texturePath = "ui/speed.png";
    private static final int SPACING = 16;
    private static final int WIDTH = 48;
    public static final int HEIGHT = 32;
    static final int STAGES = 4;

    private final ManagedTexture texture = new ManagedTexture(texturePath);
    private int currentStage;

    public SpeedButton() {
        setWidth(WIDTH);
        setHeight(HEIGHT);
    }

    @Override
    protected void onClick() {
        currentStage++;

        if(currentStage >= STAGES)
            currentStage = 0;

        StationAlpha.getInstance().setTicksPerUpdate(currentStage);
    }

    @Override
    protected void drawBackground(SpriteBatch batch) {
        syncStage();
        updatePosition();
    }

    void syncStage() {
        currentStage = StationAlpha.getInstance().getTicksPerUpdate();
    }

    @Override
    protected void drawForeground(SpriteBatch batch) {
        batch.draw(texture.get(), getX(), getY(), 0, 32 * currentStage, 48, 32);
    }

    private void updatePosition() {
        setX(Gdx.graphics.getWidth() - WIDTH - SPACING);
        setY(BuildBar.HEIGHT + SPACING);
    }

    int getCurrentStage() {
        return currentStage;
    }
}
