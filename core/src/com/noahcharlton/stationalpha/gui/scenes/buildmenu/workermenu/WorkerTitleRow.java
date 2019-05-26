package com.noahcharlton.stationalpha.gui.scenes.buildmenu.workermenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.worker.WorkerRole;

public class WorkerTitleRow extends WorkerInfoRow {

    public WorkerTitleRow() {
        super("Worker Name");

        this.setBorderColor(Color.WHITE);
        setDrawBorder(false, false, true, false);
    }

    @Override
    public void drawForeground(SpriteBatch batch) {
        super.drawForeground(batch);

        int x = TEXT_WIDTH;
        int y = getY() + ((getHeight() / 2 ) - 16);

        for(int i = 0; i < roles.length; i++){
            WorkerRole role = roles[i];

            batch.draw(role.getIconTexture().get(), x, y);

            x += BOX_SPACING;
        }
    }
}
