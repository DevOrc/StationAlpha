package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.item.Item;

import java.util.ArrayList;
import java.util.Collections;
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

    private void addGoals(Goal... goal) {
        Collections.addAll(goals, goal);
    }

    private static void init() {
        initBasicTab();
        initBotanyTab();
        initTechTab();
    }

    private static void initBasicTab() {

    }

    private static void initBotanyTab() {
        Goal firstPotatoGoal = new ItemGoal(Item.POTATO, 100);
        firstPotatoGoal.setPosition(20, 125);

        Goal collectWoodGoal = new ItemGoal(Item.WOOD, 100);
        collectWoodGoal.setPosition(350, 175);
        collectWoodGoal.addRequirement(firstPotatoGoal);

        Goal compostDirtGoal = new ItemGoal(Item.DIRT, 100);
        compostDirtGoal.setPosition(350, 75);
        compostDirtGoal.addRequirement(firstPotatoGoal);

        Goal secondPotatoGoal = new ItemGoal(Item.POTATO, 150);
        secondPotatoGoal.setPosition(675, 75);
        secondPotatoGoal.addRequirement(compostDirtGoal);

        Goal growWoodrootGoal = new ItemGoal(Item.WOODROOT, 25);
        growWoodrootGoal.setPosition(675, 175);
        growWoodrootGoal.addRequirement(collectWoodGoal);

        BOTANY.addGoals(firstPotatoGoal, collectWoodGoal, compostDirtGoal, secondPotatoGoal, growWoodrootGoal);
    }

    private static void initTechTab() {


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
