package com.noahcharlton.stationalpha.gui.scenes.selectable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.engine.input.Selectable;
import com.noahcharlton.stationalpha.gui.components.BasicPane;
import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.gui.components.Pane;
import com.noahcharlton.stationalpha.gui.scenes.SpeedButton;

public class SelectableBox extends Pane {

    private static final int WIDTH = 350;
    private static final int Y_POS = SpeedButton.HEIGHT + 32;

    private final SelectableHelpButton helpButton = new SelectableHelpButton(this, this::onHelpButtonClicked);
    private final HelpMenu helpMenu = new HelpMenu();
    private final CloseSelectableMenuButton closeButton = new CloseSelectableMenuButton(this);
    private final BasicPane selectableGuiContainer = new BasicPane();
    private final MenuButton selectableGuiToggleButton = new MenuButton("Open Gui", this::onSubGuiTogglePressed);

    private int height = 250;

    public SelectableBox() {
        setDrawBorder(true, true, true, true);

        selectableGuiToggleButton.setWidth(200);
        selectableGuiToggleButton.setHeight(35);

        addAllGui(helpButton, helpMenu, closeButton, selectableGuiContainer, selectableGuiToggleButton);
    }

    void onSubGuiTogglePressed(){
        selectableGuiContainer.setVisible(!selectableGuiContainer.isVisible());
    }

    @Override
    protected void update() {
        if(hasSelectableGui()){
            selectableGuiToggleButton.setVisible(true);
        }else{
            selectableGuiToggleButton.setVisible(false);
            selectableGuiContainer.setVisible(false);
            selectableGuiContainer.getSubGuis().clear();
        }
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        InputHandler.getInstance().getCurrentlySelected().ifPresent(selectable -> {
            renderSelectable(b, selectable);

            if(selectable instanceof Selectable.GuiSelectable)
                handleSelectableGui((Selectable.GuiSelectable) selectable);

            helpButton.setVisible(selectable.getHelpInfo().isPresent());
        });
    }

    private void handleSelectableGui(Selectable.GuiSelectable selectable) {
        if(selectableGuiContainer.getSubGuis().size() == 0){
            selectableGuiContainer.getSubGuis().add(selectable.createGui());
        }
    }


    private void renderSelectable(SpriteBatch b, Selectable selectable) {
        int y = getHeight() - 25;
        int spacing = 20;

        setFontData(.85f, Color.WHITE);
        y -= drawCenteredText(b, selectable.getTitle(), y).height;
        y -= spacing * 2;

        if(!selectable.getDesc().isEmpty()){
            setFontData(.65f, Color.WHITE);
            y -= drawCenteredText(b, selectable.getDesc(), y).height + spacing;
        }

        setFontData(.55f, Color.WHITE);

        for(String info : selectable.getDebugInfo()) {
            y -= drawCenteredText(b, info, y).height + (spacing * .75);
        }

        if(hasSelectableGui())
            y -= selectableGuiToggleButton.getHeight() + 30;

        height = getHeight() - y;
    }

    public boolean hasSelectableGui(){
        return InputHandler.getInstance().getCurrentlySelected()
                .filter(s -> s instanceof Selectable.GuiSelectable).isPresent();
    }

    @Override
    protected void updatePosition() {
        setX(Gdx.graphics.getWidth() - WIDTH);
        setY(Y_POS);

        selectableGuiToggleButton.setX(getX() + (getWidth() / 2) - (MenuButton.WIDTH / 2));
        selectableGuiToggleButton.setY(getY() + 10);
    }

    @Override
    protected void updateSize() {
        setWidth(WIDTH);
        setHeight(height);
    }

    private void onHelpButtonClicked() {
        helpMenu.setVisible(!helpMenu.isVisible());
    }
}

class CloseSelectableMenuButton extends Pane {

    private static final int SIZE = 24;
    private final SelectableBox box;

    public CloseSelectableMenuButton(SelectableBox box) {
        this.box = box;

        setDrawBorder(true, true, true, true);
    }

    @Override
    protected void onClick() {
        InputHandler.getInstance().setCurrentlySelected(null);
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        setFontData(.75f, isHovering() ? Color.RED : Color.FIREBRICK);
        drawCenteredText(b, "X", SIZE * 9 / 10);
    }

    @Override
    protected void updateSize() {
        setWidth(SIZE);
        setHeight(SIZE);
    }

    @Override
    protected void updatePosition() {
        setX(box.getX() + box.getWidth() - SIZE);
        setY(box.getY() + box.getHeight() - SIZE);
    }
}
