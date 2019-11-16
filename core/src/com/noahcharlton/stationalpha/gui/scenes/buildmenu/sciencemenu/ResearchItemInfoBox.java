package com.noahcharlton.stationalpha.gui.scenes.buildmenu.sciencemenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.audio.Sounds;
import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.gui.components.Pane;
import com.noahcharlton.stationalpha.science.ResearchItem;
import com.noahcharlton.stationalpha.world.World;

import java.util.Optional;

public class ResearchItemInfoBox extends Pane {

    static final int HEIGHT = 200;

    private final ScienceMenu scienceMenu;
    private final MenuButton unlockButton;
    private Optional<ResearchItem> selectedItem;

    public ResearchItemInfoBox(ScienceMenu scienceMenu) {
        this.scienceMenu = scienceMenu;
        this.unlockButton = new MenuButton("Unlock", this::onUnlockButtonPressed);

        setSelectedItem(null);
        setDrawBorder(true, true, true, true);
        addGui(unlockButton);
    }

    private void onUnlockButtonPressed() {
        if(!World.getInstance().isPresent())
            return;

        World world = World.getInstance().get();

        selectedItem.ifPresent(item -> {
            if(hasEnoughPoints(item) && item.isRequirementCompleted()){
                item.completeItem(world);
                world.getScienceManager().removeSciencePoints(item.getScienceCost());
            }else{
                Sounds.ERROR.play();
            }
        });
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        selectedItem.ifPresent(item -> {
            setFontData(.75f, Color.WHITE);
            drawCenteredText(b, item.getDisplayName(), getHeight() - 10);

            setFontData(.55f, hasEnoughPoints(item) ? Color.FOREST : Color.FIREBRICK);
            drawCenteredText(b, "Cost: " + item.getScienceCost() + " science", getHeight() - 38);

            setFontData(.55f, Color.WHITE);
            drawCenteredText(b, item.getDesc(), getHeight() - 70);

            unlockButton.setVisible(!item.isCompleted());
        });
    }

    private boolean hasEnoughPoints(ResearchItem item){
        return World.getInstance().map(world -> {
            int points = world.getScienceManager().getSciencePoints();

            return points >= item.getScienceCost();
        }).orElse(false);
    }

    @Override
    protected void updateSize() {
        setWidth(scienceMenu.getWidth());
        setHeight(HEIGHT);
    }

    @Override
    protected void updatePosition() {
        setX(scienceMenu.getX());
        setY(scienceMenu.getY());

        unlockButton.setX(getX() + (getWidth() / 2) - (unlockButton.getWidth() / 2));
        unlockButton.setY(getY() + 15);
    }

    public void setSelectedItem(ResearchItem item) {
        this.selectedItem = Optional.ofNullable(item);
        this.setVisible(item != null);
    }

    public Optional<ResearchItem> getSelectedItem() {
        return selectedItem;
    }
}
