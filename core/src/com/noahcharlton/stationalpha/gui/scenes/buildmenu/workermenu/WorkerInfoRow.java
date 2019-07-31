package com.noahcharlton.stationalpha.gui.scenes.buildmenu.workermenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.engine.input.Selectable;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.gui.components.Pane;
import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.worker.WorkerSelectable;

import java.util.Optional;

public class WorkerInfoRow extends Pane {

    protected static final WorkerRole[] roles = WorkerRole.values();
    protected static final int TEXT_WIDTH = 300;
    protected static final int BOX_SPACING = 13 + WorkerRoleBox.SIZE;

    static final int WIDTH = TEXT_WIDTH + (WorkerRole.values().length * BOX_SPACING);
    static final int PREFERRED_HEIGHT = 45;

    private final String name;
    private Optional<Worker> worker = Optional.empty();

    protected WorkerInfoRow(String name){
        this.name = name;

        setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void onClick() {
        if(worker.isPresent()){
            Optional<Selectable> selectable = Optional.of(new WorkerSelectable(worker.get()));
            InputHandler.getInstance().setCurrentlySelected(selectable);
        }
    }

    public WorkerInfoRow(Worker worker) {
        this(worker.getName());

        addItems(worker);
        this.worker = Optional.of(worker);
    }

    private void addItems(Worker worker) {
        int x = TEXT_WIDTH;

        for(int i = 0; i < roles.length; i++){
            WorkerRole role = roles[i];

            WorkerRoleBox box = new WorkerRoleBox(worker, role);
            box.setX(x);

            addGui(box);

            x += BOX_SPACING;
        }
    }

    @Override
    public void drawForeground(SpriteBatch batch) {
        setFontData(.75f, Color.WHITE);
        font.draw(batch, name, getX() + 20, getY() + getHeight() - 10);
    }

    @Override
    protected void updateSize() {
        setWidth(WIDTH);
    }

    @Override
    protected void updatePosition() {
        int y = getY() + ((getHeight() / 2 ) - 16);

        for(GuiComponent subGui : getSubGuis()){
            subGui.setY(y);
        }
    }
}
