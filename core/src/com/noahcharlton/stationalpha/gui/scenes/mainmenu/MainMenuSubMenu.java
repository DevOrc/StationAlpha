package com.noahcharlton.stationalpha.gui.scenes.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Version;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.GLVersion;
import com.noahcharlton.stationalpha.StationAlpha;
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

        private final MenuButton fullScreenToggle;
        private final MenuButton vSyncToggle;

        private boolean vSync = true;//Default to vSync on

        public SettingsMenu(MainMenuButtonPane buttonPane) {
            super(buttonPane);

            this.fullScreenToggle = new MenuButton("Toggle Fullscreen", this::updateFullscreen);
            this.fullScreenToggle.setWidth(250);
            this.vSyncToggle = new MenuButton("VSync", this::toggleVSync);
            this.vSyncToggle.setWidth(250);

            if(Gdx.graphics != null)
                Gdx.graphics.setVSync(vSync);

            addAllGui(fullScreenToggle, vSyncToggle);
        }

        @Override
        protected void update() {
            this.vSyncToggle.setText("VSync: " + (vSync ? "On":"Off"));
        }

        @Override
        protected void updatePosition() {
            super.updatePosition();

            fullScreenToggle.setX(getX() + 10);
            fullScreenToggle.setY(getY() + getHeight() - MenuButton.HEIGHT - 10);
            vSyncToggle.setX(getX() + 10);
            vSyncToggle.setY(getY() + getHeight() - MenuButton.HEIGHT - 70);
        }

        private void updateFullscreen() {
            if(Gdx.graphics.isFullscreen()){
                Gdx.graphics.setWindowedMode(StationAlpha.DEFAULT_WIDTH, StationAlpha.DEFAULT_HEIGHT);
            }else{
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        }

        private void toggleVSync() {
            vSync = !vSync;
            Gdx.graphics.setVSync(vSync);
        }
    }

    static class AboutMenu extends MainMenuSubMenu{

        public AboutMenu(MainMenuButtonPane buttonPane) {
            super(buttonPane);
        }

        @Override
        public void drawForeground(SpriteBatch b) {
            setFontData(.5f, GuiComponent.ACCENT_COLOR);

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
                    "Source Code: github.com/DevOrc/StationAlpha",
                    "Game Version: " + StationAlpha.VERSION,
                    "\n\n",
                    "LibGDX Version: " + Version.VERSION,
                    "GL Version: " + glInfo.getMajorVersion() + "." + glInfo.getMinorVersion(),
                    "Vendor: " + glInfo.getVendorString(),
                    "Java Version: " + System.getProperty("java.version"),
                    "OS: " + System.getProperty("os.name")
            };
        }
    }
}
