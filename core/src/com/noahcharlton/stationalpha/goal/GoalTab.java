package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.item.Item;

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
        Goal steelGoal = new ItemGoal(Item.STEEL, 20);
        steelGoal.setPosition(20, 20);
        Goal secondSteelGoal = new ItemGoal(Item.STEEL, 40);
        secondSteelGoal.setPosition(350, 20);
        secondSteelGoal.addRequirement(steelGoal);

        BASICS.addGoal(steelGoal);
        BASICS.addGoal(secondSteelGoal);
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
