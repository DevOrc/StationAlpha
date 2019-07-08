package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.world.World;

import java.util.Optional;

public class TestGoal extends Goal{

    private Optional<Goal> nextGoal = Optional.empty();
    private boolean goalAchieved;

    @Override
    protected boolean checkCompleted(World world) {
        return goalAchieved;
    }

    @Override
    public String getDescription() {
        return "Used for tests";
    }

    @Override
    public String getName() {
        return "Test Goal";
    }

    public void setGoalAchieved(boolean goalAchieved) {
        this.goalAchieved = goalAchieved;
    }

    public void setNextGoal(Goal nextGoal) {
        this.nextGoal = Optional.of(nextGoal);
    }

    @Override
    public Optional<Goal> getNextGoal(World world) {
        return nextGoal;
    }
}
