package com.noahcharlton.stationalpha.gui.scenes.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;
import com.noahcharlton.stationalpha.gui.components.Pane;

public class MainMenu extends Pane {

    static final String[] fontPaths = new String[]{"ui/title.fnt", "ui/title.png"};
    static final BitmapFont titleFont;

    static final ManagedTexture planetTexture = new ManagedTexture("ui/planet.png");

    static {
        if(Gdx.graphics != null)
            titleFont = new BitmapFont(Gdx.files.internal(fontPaths[0]),
                    Gdx.files.internal(fontPaths[1]), false);
        else
            titleFont = null;
    }

    private final MainMenuButtonPane buttonPane = new MainMenuButtonPane();

    public MainMenu() {
        setBackgroundColor(Color.BLACK);

        this.addGui(buttonPane);
        this.addGui(new TestScrollPane());
    }

    @Override
    public void drawBackground(SpriteBatch b) {
        super.drawBackground(b);

        drawPlanet(b);
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        drawTitle(b);
    }

    private void drawPlanet(SpriteBatch b) {
        int y = Gdx.graphics.getHeight() - planetTexture.get().getHeight() - 75;
        int x = Gdx.graphics.getWidth() - planetTexture.get().getWidth() - 100;

        b.draw(planetTexture.get(), x, y);
    }

    private void drawTitle(SpriteBatch b) {
        int y = Gdx.graphics.getHeight() - 75;

        titleFont.setColor(ACCENT_COLOR);
        titleFont.getData().setScale(.85f);
        titleFont.draw(b, "STATION ALPHA", 75, y);
    }

    @Override
    protected void updateSize() {
        setWidth(Gdx.graphics.getWidth());
        setHeight(Gdx.graphics.getHeight());
    }

    @Override
    protected void updatePosition() {
        setX(0);
        setY(0);
    }
}