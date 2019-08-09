package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.noahcharlton.stationalpha.engine.input.BuildAction;
import com.noahcharlton.stationalpha.engine.input.InputHandler;
import com.noahcharlton.stationalpha.engine.input.PlayerActions;

public class ActionsMenu extends BuildBarMenu<BuildAction>{

    public ActionsMenu() {
        super(PlayerActions.getActions());
    }

    @Override
    protected Runnable createRunnable(BuildAction item) {
        return () -> InputHandler.getInstance().setBuildAction(item);
    }

    @Override
    public String getName() {
        return "Actions";
    }
}
