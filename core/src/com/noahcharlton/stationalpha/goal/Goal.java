package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.world.World;

import java.util.ArrayList;
import java.util.List;

public class Goal {

    private String name;
    private String desc;

    private List<Goal> requirements = new ArrayList<>();
    private boolean completed;

    private int x;
    private int y;

    public Goal() {
        this("", "");
    }

    public Goal(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.completed = false;

        this.x = 0;
        this.y = 0;
    }

    public void update(World world) {}

    public boolean allRequirementsCompleted(){
        for(Goal goal: requirements){
            if(!goal.isCompleted())
                return false;
        }

        return true;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void addRequirement(Goal goal){
        requirements.add(goal);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
