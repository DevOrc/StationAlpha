package com.noahcharlton.stationalpha.gui.scenes.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Version;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.GLVersion;
import com.noahcharlton.stationalpha.StationAlpha;
import com.noahcharlton.stationalpha.engine.settings.Settings;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.gui.components.Pane;

public abstract class MainMenuSubMenu extends Pane {

    private static final int SPACE = 25;
    private static final int WIDTH = 450;

    private final MainMenuButtonPane buttonPane;

    public MainMenuSubMenu(MainMenuButtonPane buttonPane) {
        this.buttonPane = buttonPane;

        setBackgroundColor(Color.BLACK);
        setDrawBorder(true, true, true, true);
        setVisible(false);
    }

    @Override
    protected void updatePosition() {
        setX(buttonPane.getX() + buttonPane.getWidth() + SPACE);
        setY(buttonPane.getY());
    }

    @Override
    protected void updateSize() {
        setWidth(WIDTH);
        setHeight(buttonPane.getHeight());
    }

    static class SettingsMenu extends MainMenuSubMenu{

        private static final MenuButton saveButton = new MenuButton("Save", () -> Settings.save());

        public SettingsMenu(MainMenuButtonPane buttonPane) {
            super(buttonPane);

            Settings.getSettings().forEach(setting -> addGui(setting.createMenuComponent()));

            this.addGui(saveButton);
        }

        @Override
        protected void updatePosition() {
            super.updatePosition();
            layoutSaveButton();

            int spacing = 10;
            int y = getY() + getHeight() - spacing;

            for(GuiComponent comp : getSubGuis()) {
                if(comp == saveButton)
                    continue;

                y -= comp.getHeight();

                comp.setX(getX() + spacing);
                comp.setY(y);
                comp.setWidth(getWidth() - (BORDER_WIDTH * 2) - (spacing * 2));

                y -= spacing;
            }
        }

        private void layoutSaveButton() {
            saveButton.setX(getX());
            saveButton.setY(getY() - saveButton.getHeight());
        }
    }

    static class AboutMenu extends MainMenuSubMenu{

        public AboutMenu(MainMenuButtonPane buttonPane) {
            super(buttonPane);
        }

        @Override
        public void drawForeground(SpriteBatch b) {
            setFontData(.65f, GuiComponent.ACCENT_COLOR);

            String[] info = getInfo();

            int space = 10;
            int x = getX() + space;
            int y = getY() + getHeight() - space;

            for(String str: info){
                y -= font.draw(b, str, x, y).height;
                y -= space;
            }
        }

        private String[] getInfo() {
            GLVersion glInfo = Gdx.graphics.getGLVersion();
            return new String[]{
                    "Author: Noah Charlton",
                    "Game Version: " + StationAlpha.VERSION,
                    "LibGDX Version: " + Version.VERSION,
                    "GL Version: " + glInfo.getMajorVersion() + "." + glInfo.getMinorVersion(),
                    "Vendor: " + glInfo.getVendorString(),
                    "Java Version: " + System.getProperty("java.version"),
                    "OS: " + System.getProperty("os.name")
            };
        }
    }
}
