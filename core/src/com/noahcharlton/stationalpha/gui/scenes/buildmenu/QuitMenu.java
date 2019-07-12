package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.StationAlpha;

import java.util.Arrays;
import java.util.List;

public class QuitMenu extends BuildBarMenu<QuitMenuOption> {

    private static final int WIDTH = 250;
    private static final int HEIGHT = 125;

    public QuitMenu() {
        super(createOptions());
    }

    @Override
    protected Runnable createRunnable(QuitMenuOption item) {
        return item.getAction();
    }

    private static List<QuitMenuOption> createOptions() {
        return Arrays.asList(
                new QuitMenuOption("Quit to Desktop", () -> Gdx.app.exit()),
                new QuitMenuOption("Quit to Main Menu", QuitMenu::quitToMainMenu)
        );
    }

    private static void quitToMainMenu() {
        StationAlpha.getInstance().gotoMainMenu();
    }

    @Override
    protected void updateSize() {
        setWidth(WIDTH);
        setHeight(HEIGHT);
    }

    @Override
    public String getName() {
        return "Save/Quit";
    }
}

class QuitMenuOption {

    private final String name;
    private final Runnable action;

    public QuitMenuOption(String name, Runnable action) {
        this.name = name;
        this.action = action;
    }

    public Runnable getAction() {
        return action;
    }

    @Override
    public String toString() {
        return name;
    }
}
