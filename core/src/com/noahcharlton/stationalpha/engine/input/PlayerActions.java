package com.noahcharlton.stationalpha.engine.input;

import com.noahcharlton.stationalpha.engine.input.mine.MineActions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerActions {

    private static List<BuildAction> actions = Collections.emptyList();

    public static void init(){
        MineActions.init();

        ArrayList<BuildAction> actionsList = new ArrayList<>(MineActions.getActions());

        PlayerActions.actions = Collections.unmodifiableList(actionsList);
    }

    public static List<BuildAction> getActions() {
        return actions;
    }
}
