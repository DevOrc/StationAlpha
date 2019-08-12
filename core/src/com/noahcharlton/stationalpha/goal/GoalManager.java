package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.world.World;

import java.util.*;

public class GoalManager {

    private final EnumSet<GoalTab> tabs = EnumSet.allOf(GoalTab.class);

    public GoalManager(World world) {

    }

    public void update() {

    }

    public Set<GoalTab> getTabs() {
        return tabs;
    }
}
