package com.noahcharlton.stationalpha.gui.scenes.mainmenu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;
import com.noahcharlton.stationalpha.gui.GuiComponent;

class MainMenuButton extends GuiComponent {

    static final int WIDTH = 200;
    static final int HEIGHT = 50;

    static final float HOVER_SCALE = 1.1f;
    static final int SCALE_WIDTH = (int) (WIDTH * HOVER_SCALE);
    static final int SCALE_HEIGHT = (int) (HEIGHT * HOVER_SCALE);

    static final ManagedTexture texture = new ManagedTexture("ui/title_button.png");

    private final String text;
    private final Runnable onClick;

    public MainMenuButton(String text, Runnable onClick) {
        this.text = text;
        this.onClick = onClick;

        setWidth(WIDTH);
        setHeight(HEIGHT);
    }

    @Override
    protected void drawBackground(SpriteBatch batch) {
        if(isHovering()){
            int x = getX() - ((SCALE_WIDTH - WIDTH) / 2);
            int y = getY() - ((SCALE_HEIGHT - HEIGHT) / 2);
            batch.draw(texture.get(), x, y, SCALE_WIDTH, SCALE_HEIGHT);
        }else{
            batch.draw(texture.get(), getX(), getY());
        }
    }

    @Override
    protected void onClick() {
        onClick.run();
    }

    @Override
    protected void drawForeground(SpriteBatch batch) {
        setFontData(1f, ACCENT_COLOR);
        font.draw(batch, text, getX(), getY() + HEIGHT - 10, WIDTH, Align.center, false);
    }
}