package com.noahcharlton.stationalpha.gui.scenes.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.noahcharlton.stationalpha.StationAlpha;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.gui.components.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainMenuButtonPane extends Pane {

    private final Logger logger = LogManager.getLogger(MainMenuButtonPane.class);
    static final int BUTTON_COUNT = 4;
    static final int PADDING = 15;

    private static final int WIDTH = PADDING * 2 + MainMenuButton.WIDTH;
    private static final int HEIGHT = PADDING + ((PADDING + MainMenuButton.HEIGHT) * BUTTON_COUNT);

    public MainMenuButtonPane() {
        setBackgroundColor(Color.BLACK);

        addButtons();
    }

    private void addButtons() {
        addGui(new MainMenuButton("Quit", () -> {
            logger.info("Quit button clicked! Exiting game...");
            Gdx.app.exit();
        }));

        addGui(new MainMenuButton("Settings", () -> {

        }));

        addGui(new MainMenuButton("About", () -> {

        }));

        addGui(new MainMenuButton("Start", () -> {
            StationAlpha.getInstance().startGame();
        }));
    }

    @Override
    protected void updateSize() {
        setX(100);
        setY(100);
    }

    @Override
    protected void updatePosition() {
        setWidth(WIDTH);
        setHeight(HEIGHT);

        layoutButtons();
    }

    private void layoutButtons() {
        int x = getX() + PADDING;
        int y = getY() + PADDING;

        for(int i = 0; i < this.getSubGuis().size(); i++) {
            GuiComponent comp = this.getSubGuis().get(i);

            comp.setX(x);
            comp.setY(y);

            y += PADDING + MainMenuButton.HEIGHT;
        }
    }
}


