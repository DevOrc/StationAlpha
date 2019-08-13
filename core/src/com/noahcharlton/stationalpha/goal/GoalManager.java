package com.noahcharlton.stationalpha.goal;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

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

    public void save(QuietXmlWriter writer){
        QuietXmlWriter element = writer.element("Goals");

        for(GoalTab tab: tabs){
            saveTab(tab, element);
        }

        element.pop();
    }

    void saveTab(GoalTab tab, QuietXmlWriter writer) {
        QuietXmlWriter element = writer.element("Tab");
        element.attribute("name", tab.name());

        for(Goal goal: tab.getGoals()){
            saveGoal(element, goal);
        }

        element.pop();
    }

    void saveGoal(QuietXmlWriter element, Goal goal) {
        element.element("Goal")
                .attribute("name", goal.getName())
                .attribute("completed", goal.isCompleted())
                .pop();
    }

    public void loadGoals(XmlReader.Element element){
        element.getChildrenByName("Tab").forEach(this::loadTab);
    }

    private void loadTab(XmlReader.Element tabElement) {
        String name = tabElement.getAttribute("name");

        GoalTab tab = GoalTab.valueOf(name);
        tabElement.getChildrenByName("Goal").forEach(element -> loadGoal(tab, element));
    }

    private void loadGoal(GoalTab tab, XmlReader.Element element) {
        String goalName = element.getAttribute("name");
        boolean completed = element.getBooleanAttribute("completed");

        Optional<Goal> goal = tab.getGoals().stream().filter(g -> g.getName().equals(goalName)).findFirst();
        goal.ifPresent(g -> g.setCompleted(completed));
    }

    public Set<GoalTab> getTabs() {
        return tabs;
    }
}
