package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.item.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum GoalTab {

    TECH("Basics"), BOTANY("Botany");

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
        initTechTab();
        initBotanyTab();
    }

    private static void initTechTab() {
        Goal steelGoal = new ItemGoal(Item.STEEL, 80);
        steelGoal.setPosition(25, 175);

        Goal copperGoal = new ItemGoal(Item.COPPER, 5);
        copperGoal.setPosition(25, 75);

        Goal spaceDustGoal = new ItemGoal(Item.SPACE_DUST, 25);
        spaceDustGoal.setPosition(325, 125);
        spaceDustGoal.addRequirement(copperGoal, steelGoal);

        Goal synthesizeUnobtainiumGoal = new ItemGoal(Item.UNOBTAINIUM, 5);
        synthesizeUnobtainiumGoal.setPosition(625, 175);
        synthesizeUnobtainiumGoal.addRequirement(spaceDustGoal);

        Goal synthesizePowerIngotGoal = new ItemGoal(Item.POWER_INGOT, 5);
        synthesizePowerIngotGoal.setPosition(625, 75);
        synthesizePowerIngotGoal.addRequirement(spaceDustGoal);

        Goal arcReactorGoal = new ItemGoal(Item.TEST_ITEM, 25);
        arcReactorGoal.setPosition(925, 125);
        arcReactorGoal.addRequirement(synthesizeUnobtainiumGoal, synthesizePowerIngotGoal);

        TECH.addGoals(steelGoal, copperGoal, spaceDustGoal, synthesizeUnobtainiumGoal,
                synthesizePowerIngotGoal, arcReactorGoal);
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
