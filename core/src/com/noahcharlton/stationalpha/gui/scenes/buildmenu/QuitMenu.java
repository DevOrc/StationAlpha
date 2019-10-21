package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.StationAlpha;
import com.noahcharlton.stationalpha.engine.InGameIcon;
import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.gui.components.Pane;
import com.noahcharlton.stationalpha.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuitMenu extends BuildBarListMenu<Void> {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 150;

    private final SaveMenu saveMenu;

    private final MenuButton quitToDesktop;
    private final MenuButton quitToMainMenu;
    private final MenuButton saveButton;

    public QuitMenu() {
        super(Collections.emptyList());

        this.setLayoutManager(null);
        this.saveMenu = new SaveMenu();
        this.quitToDesktop = new MenuButton("Quit To Desktop", () -> Gdx.app.exit());
        this.quitToMainMenu = new MenuButton("Quit to Main Menu", () -> StationAlpha.getInstance().gotoMainMenu());
        this.saveButton = new MenuButton("Save Game", () -> saveMenu.setVisible(!saveMenu.isVisible()));

        addAllGui(saveButton, quitToDesktop, quitToMainMenu, saveMenu);
    }

    @Override
    protected Runnable createRunnable(Void item) {
        return () -> {
        };
    }

    @Override
    protected void updateSize() {
        setWidth(WIDTH);
        setHeight(HEIGHT);

        if(quitToDesktop == null)
            return;

        int heightPerButton = (HEIGHT / 3) - 4;

        quitToDesktop.setWidth(WIDTH - 6);
        quitToDesktop.setHeight(heightPerButton);
        saveButton.setWidth(WIDTH - 6);
        quitToMainMenu.setHeight(heightPerButton);
        quitToMainMenu.setWidth(WIDTH - 6);
        saveButton.setHeight(heightPerButton);
    }

    @Override
    protected void updatePosition() {
        super.updatePosition();

        if(quitToDesktop == null)
            return;

        int heightPerButton = HEIGHT / 3;
        quitToDesktop.setY(getY());
        quitToDesktop.setX(3);
        quitToMainMenu.setY(getY() + heightPerButton);
        quitToMainMenu.setX(3);
        saveButton.setY(getY() + (heightPerButton * 2));
        saveButton.setX(3);
    }

    @Override
    public String getName() {
        return "Save/Quit";
    }

    @Override
    public InGameIcon getIcon() {
        return InGameIcon.MENU_UI;
    }
}

class SaveMenu extends Pane {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final List<MenuButton> buttons = new ArrayList<>();

    public SaveMenu() {
        for(int i = 0; i < 10; i++) {
            int finalI = i;
            MenuButton button = new MenuButton("Save " + i, () -> this.onButtonClicked(finalI));

            buttons.add(button);
            addGui(button);
        }

        setVisible(false);
    }

    private void onButtonClicked(int i) {
        World.getInstance().ifPresent(world -> world.save(i));
        this.setVisible(false);
    }

    @Override
    protected void updatePosition() {
        setX((Gdx.graphics.getWidth() / 2) - (WIDTH / 2));
        setY((Gdx.graphics.getHeight() / 2) - (HEIGHT / 2));

        layoutButtons();
    }

    @Override
    protected void updateSize() {
        setWidth(WIDTH);
        setHeight(HEIGHT);
    }

    private void layoutButtons() {
        int width = getWidth() / 2;
        int height = getHeight() / 5;

        for(int x = 0; x < 2; x++) {
            for(int y = 0; y < 5; y++) {
                MenuButton button = buttons.get((x * 5) + y);
                button.setX(x == 0 ? getX() : getX() + width);
                button.setY(getY() + getHeight() - ((y + 1) * height));
                button.setWidth(width);
                button.setHeight(height);
            }
        }
    }
}