package com.noahcharlton.stationalpha.gui.scenes.mainmenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.StationAlpha;
import com.noahcharlton.stationalpha.engine.audio.Sounds;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.gui.components.Pane;
import com.noahcharlton.stationalpha.world.load.LoadGameException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class LoadMenu extends MainMenuSubMenu{

    private static final Logger logger = LogManager.getLogger(LoadMenu.class);
    private final List<MenuButton> buttons = new ArrayList<>();
    private final LoadErrorMenu errorMenu = new LoadErrorMenu(this);

    public LoadMenu(MainMenuButtonPane buttonPane) {
        super(buttonPane);

        initButtons();
        buttons.forEach(this::addGui);
        this.addGui(errorMenu);
    }

    private void initButtons() {
        for(int i = 0; i < 10; i++){
            int finalI = i;
            MenuButton button = new MenuButton("Load " + i, createLoadRunnable(finalI));
            button.setPlaySound(false);

            this.buttons.add(button);
        }
    }

    private Runnable createLoadRunnable(int saveNum) {
        return () -> {
            errorMenu.setVisible(false);

            try{
                StationAlpha.getInstance().loadGame(saveNum);
                Sounds.CLICK.play(.3f);
            }catch(LoadGameException e){
                Sounds.ERROR.play();
                logger.info("Failed to load save {}!", saveNum, e);

                errorMenu.setVisible(true);
                errorMenu.setReason(e.getMessage());
            }
        };
    }

    @Override
    protected void updatePosition() {
        super.updatePosition();

        int width = getWidth() / 2;
        int height = getHeight() / 5;

        for(int x = 0; x < 2; x++){
            for(int y = 0; y < 5; y++){
                MenuButton button = buttons.get((x * 5) + y);
                button.setX(x == 0 ? getX() : getX() + width);
                button.setY(getY() + getHeight() - ((y + 1) * height));
                button.setWidth(width);
                button.setHeight(height);
            }
        }
    }
}
class LoadErrorMenu extends Pane {

    private static final int WIDTH = 350;
    private final LoadMenu loadMenu;

    private String reason = "";

    public LoadErrorMenu(LoadMenu loadMenu) {
        this.loadMenu = loadMenu;

        setBackgroundColor(Color.BLACK);
        setDrawBorder(true, true, true, false);
        setVisible(false);
    }

    public void setReason(String reason) {
        if(reason == null)
            reason = "Unknown cause. See log for more details!";
        this.reason = reason;
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        setFontData(1.25f, GuiComponent.ACCENT_COLOR);
        drawCenteredText(b, "Error!", getHeight() - 25);


        setFontData(.65f, GuiComponent.ACCENT_COLOR);
        drawCenteredText(b, reason, getHeight() - 100);
    }

    @Override
    protected void updatePosition() {
        if(loadMenu == null)
            return;

        setX(loadMenu.getX() + loadMenu.getWidth());
        setY(loadMenu.getY());
    }

    @Override
    protected void updateSize() {
        if(loadMenu != null)
            setHeight(loadMenu.getHeight());

        setWidth(WIDTH);
    }

    public String getReason() {
        return reason;
    }
}