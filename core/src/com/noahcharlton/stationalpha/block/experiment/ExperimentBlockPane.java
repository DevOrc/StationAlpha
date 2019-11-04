package com.noahcharlton.stationalpha.block.experiment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.ShapeUtil;
import com.noahcharlton.stationalpha.gui.components.BasicPane;
import com.noahcharlton.stationalpha.gui.components.CenteredPane;
import com.noahcharlton.stationalpha.gui.components.MenuButton;
import com.noahcharlton.stationalpha.gui.components.Pane;

public class ExperimentBlockPane extends CenteredPane {

    private static final int WIDTH = 450;
    private static final int HEIGHT = 450;

    private final ExperimentContainer container;
    private final Pane trackingPane;
    private final Pane startPane;

    public ExperimentBlockPane(ExperimentContainer experimentContainer) {
        setWidth(WIDTH);
        setHeight(HEIGHT);

        setDrawBorder(true, true, true, true);

        this.container = experimentContainer;
        this.startPane = new ExperimentStartPane(this);
        this.trackingPane = new ExperimentTrackingPane(this);

        this.addAllGui(trackingPane, startPane);
    }

    @Override
    protected void update() {
        if(hasExperiment()){
            trackingPane.setVisible(true);
            startPane.setVisible(false);
        }else{
            trackingPane.setVisible(false);
            startPane.setVisible(true);
        }
    }

    boolean hasExperiment() {
        return container.getExperiment().isPresent();
    }

    ExperimentContainer getContainer() {
        return container;
    }
}
class ExperimentTrackingPane extends BasicPane{

    private static final int BAR_HEIGHT = 40;
    private static final int BAR_SPACING = 20;
    private final ExperimentBlockPane pane;

    public ExperimentTrackingPane(ExperimentBlockPane pane) {
        this.pane = pane;

        setDrawBorder(true, true, true, true);
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        if(!pane.getContainer().getExperiment().isPresent())
            return;

        Experiment experiment = pane.getContainer().getExperiment().get();
        setFontData(1f, Color.WHITE);
        drawCenteredText(b, experiment.getName(), getHeight() - 20);

        drawPercentBar(b, experiment);
    }

    private void drawPercentBar(SpriteBatch batch, Experiment e) {
        int barBoxWidth = getWidth() - (BAR_SPACING * 2);
        int padding = 2;

        double percent = (double) e.getProgress() / e.getLength();
        int barLength = (int) ((barBoxWidth - (padding * 2)) * percent);

        int barX = getX() + BAR_SPACING;
        int barY = getY() + BAR_SPACING;

        ShapeUtil.drawRect(barX, barY, barBoxWidth, BAR_HEIGHT, Color.WHITE, batch);
        ShapeUtil.drawRect(barX + padding, barY + padding, barLength, BAR_HEIGHT - (padding * 2),
                Color.RED, batch);
    }

    @Override
    protected void update() {
        setX(pane.getX());
        setY(pane.getY());
        setWidth(pane.getWidth());
        setHeight(pane.getHeight());
    }
}
class ExperimentStartPane extends BasicPane{

    private static final int PADDING = 10;
    private final ExperimentBlockPane parent;
    private final MenuButton startButton;

    public ExperimentStartPane(ExperimentBlockPane pane) {
        this.parent = pane;
        this.startButton = new MenuButton("Start Experiment", () -> {
            pane.getContainer().createExperiment();
            pane.getContainer().startExperiment();
        });

        addGui(startButton);
        setDrawBorder(true, true, true, true);
    }

    @Override
    protected void update() {

    }

    @Override
    protected void updateSize() {
        setWidth(parent.getWidth());
        setHeight(parent.getHeight());

        startButton.setWidth(getWidth() - (PADDING * 2));
    }

    @Override
    protected void updatePosition() {
        setX(parent.getX());
        setY(parent.getY());

        startButton.setX(getX() + PADDING);
        startButton.setY(getY() + PADDING);
    }
}