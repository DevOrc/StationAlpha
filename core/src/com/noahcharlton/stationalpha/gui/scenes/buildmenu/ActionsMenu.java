package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.engine.input.mine.MineAction;
import com.noahcharlton.stationalpha.engine.input.mine.MineActions;

public class ActionsMenu extends BuildBarMenu<MineAction>{

    public ActionsMenu() {
        super(MineActions.getActions());
    }

    @Override
    protected Runnable createRunnable(MineAction item) {
        return () -> {
            InputHandler.getInstance().setBuildAction(item);
        };
    }

    @Override
    public String getName() {
        return "Actions";
    }
}
