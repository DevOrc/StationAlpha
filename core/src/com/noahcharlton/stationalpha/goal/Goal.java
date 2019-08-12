package com.noahcharlton.stationalpha.goal;

import java.util.ArrayList;
import java.util.List;

public class Goal {

    private String name;
    private String desc;

    private List<Goal> requirements = new ArrayList<>();
    private boolean completed;

    public boolean allRequirementsCompleted(){
        for(Goal goal: requirements){
            if(!goal.isCompleted())
                return false;
        }

        return true;
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
}
