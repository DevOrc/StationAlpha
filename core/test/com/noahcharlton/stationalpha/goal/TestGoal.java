package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.world.World;

public class TestGoal extends Goal{

    private int id;
    private boolean goalAchieved;
    private boolean onCompletedRan = false;

    public TestGoal(int id) {
        this.id = id;
    }

    @Override
    protected boolean checkCompleted(World world) {
        return goalAchieved;
    }

    @Override
    public void onComplete(World world) {
        onCompletedRan = true;
    }

    @Override
    public String getDescription() {
        return "Used for tests";
    }

    @Override
    public String getName() {
        return "Test Goal";
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof TestGoal)) return false;
        TestGoal testGoal = (TestGoal) o;
        return id == testGoal.id;
    }

    @Override
    public String toString() {
        return "TestGoal(" + id + ")";
    }

    public void setGoalAchieved(boolean goalAchieved) {
        this.goalAchieved = goalAchieved;
    }

    public boolean isOnCompletedRan() {
        return onCompletedRan;
    }

    public int getId() {
        return id;
    }
}
