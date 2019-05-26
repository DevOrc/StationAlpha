package com.noahcharlton.stationalpha.gui.scenes.buildmenu.workermenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.ShapeUtil;
import com.noahcharlton.stationalpha.gui.components.Pane;
import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.worker.WorkerRole;

public class WorkerRoleBox extends Pane {

    static final int SIZE = 32;

    private final Worker worker;
    private final WorkerRole role;

    public WorkerRoleBox(Worker worker, WorkerRole role) {
        setBackgroundColor(Color.WHITE);
        setBorderColor(Color.BLACK);

        setDrawBorder(true, true, true, true);

        this.worker = worker;
        this.role = role;
    }

    @Override
    public void drawForeground(SpriteBatch b) {
        int offset = 4;
        int boxSize = SIZE - (offset * 2);

        if(worker.getRoles().contains(role)){
            ShapeUtil.drawRect(getX() + offset, getY() + offset, boxSize, boxSize, Color.RED, b);
        }
    }

    @Override
    protected void onClick() {
        if(worker.getRoles().contains(role)){
            worker.removeRole(role);
        }else{
            worker.addRole(role);
        }
    }

    @Override
    protected void updateSize() {
        setHeight(SIZE);
        setWidth(SIZE);
    }

    @Override
    protected void updatePosition() {}
}
