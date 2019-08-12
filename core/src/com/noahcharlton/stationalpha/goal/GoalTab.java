package com.noahcharlton.stationalpha.goal;

import java.util.ArrayList;
import java.util.List;

public enum GoalTab {

    BASICS("Basics"), BOTANY("Botany"), TECH("Tech");

    private final String name;
    private List<Goal> goals = new ArrayList<>();

    GoalTab(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return name;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
