package com.noahcharlton.stationalpha.goal;

import java.util.ArrayList;
import java.util.List;

public enum GoalTab {

    BASICS("Basics"), BOTANY("Botany"), TECH("Tech");

    static{
        init();
    }

    private final String name;
    private List<Goal> goals = new ArrayList<>();

    GoalTab(String name) {
        this.name = name;
    }

    private static void init() {
        Goal testGoal = new Goal("Test Goal", "Description");
        testGoal.setPosition(20, 20);

        BASICS.addGoal(testGoal);
    }

    private void addGoal(Goal goal) {
        goals.add(goal);
    }

    public String getDisplayName() {
        return name;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
