package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.HelpInfo;
import com.noahcharlton.stationalpha.block.Blocks;
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
        Goal steelGoal = new ItemGoal(Item.STEEL, 80, "Mine for Steel", "steel_goal_desc");
        steelGoal.setPosition(25, 175);
        steelGoal.setGoalReward(new ItemReward(Item.DIRT.stack(8)));

        Goal copperGoal = new ItemGoal(Item.COPPER, 5, "Mine for Copper", "copper_goal_desc");
        copperGoal.setPosition(25, 75);
        copperGoal.setGoalReward(new ItemReward(Item.DIRT.stack(8)));

        Goal spaceDustGoal = new ItemGoal(Item.SPACE_DUST, 25, "Collect Space Dust", "space_dust_goal");
        spaceDustGoal.setPosition(325, 125);
        spaceDustGoal.addRequirement(copperGoal, steelGoal);
        spaceDustGoal.setGoalReward(new ItemReward(Item.UNOBTAINIUM.stack(1)));

        Goal synthesizeUnobtainiumGoal = new ItemGoal(Item.UNOBTAINIUM, 5, "Synthesize Unobtainium",
                "unobtainium_goal_desc");
        synthesizeUnobtainiumGoal.setPosition(625, 175);
        synthesizeUnobtainiumGoal.addRequirement(spaceDustGoal);

        Goal synthesizePowerIngotGoal = new ItemGoal(Item.POWER_INGOT, 5, "Synthesize Power Ingot",
                "power_ingot_goal_desc");
        synthesizePowerIngotGoal.setPosition(625, 75);
        synthesizePowerIngotGoal.addRequirement(spaceDustGoal);

        Goal arcReactorGoal = new BuildBlockGoal(Blocks.getArcReactor());
        arcReactorGoal.setName("Build an Arc Reactor");
        arcReactorGoal.setDesc(HelpInfo.get("arc_reactor_goal_desc"));
        arcReactorGoal.setPosition(925, 125);
        arcReactorGoal.addRequirement(synthesizeUnobtainiumGoal, synthesizePowerIngotGoal);

        TECH.addGoals(steelGoal, copperGoal, spaceDustGoal, synthesizeUnobtainiumGoal,
                synthesizePowerIngotGoal, arcReactorGoal);
    }

    private static void initBotanyTab() {
        Goal potatoGoal = new ItemGoal(Item.POTATO, 125, "Grow Potatoes", "potato_goal_1_desc");
        potatoGoal.setPosition(20, 125);
        potatoGoal.setGoalReward(new ItemReward(Item.DIRT.stack(8)));

        Goal collectWoodGoal = new ItemGoal(Item.WOOD, 100, "Grow/Chop Trees", "chop_trees_goal_desc");
        collectWoodGoal.setPosition(350, 175);
        collectWoodGoal.addRequirement(potatoGoal);

        Goal compostDirtGoal = new ItemGoal(Item.DIRT, 8, "Compost Leaves", "dirt_goal_desc");
        compostDirtGoal.setPosition(350, 75);
        compostDirtGoal.addRequirement(potatoGoal);

        Goal growWoodrootGoal = new ItemGoal(Item.WOODROOT, 25, "Grow woodroot", "woodroot_goal_desc");
        growWoodrootGoal.setPosition(675, 175);
        growWoodrootGoal.addRequirement(collectWoodGoal);

        BOTANY.addGoals(potatoGoal, collectWoodGoal, compostDirtGoal, growWoodrootGoal);
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
