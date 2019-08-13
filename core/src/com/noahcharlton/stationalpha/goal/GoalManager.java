package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.world.World;

import java.util.*;

public class GoalManager {

    private final EnumSet<GoalTab> tabs = EnumSet.allOf(GoalTab.class);
    private final World world;

    public GoalManager(World world) {
        this.world = world;
    }

    public void update() {
        tabs.forEach(this::updateTab);
    }

    private void updateTab(GoalTab tab) {
        tab.getGoals().forEach(this::updateGoal);
    }

    void updateGoal(Goal goal){
        if(goal.allRequirementsCompleted() && !goal.isCompleted()){
            goal.update(world);
        }
    }

    public Set<GoalTab> getTabs() {
        return tabs;
    }
}
