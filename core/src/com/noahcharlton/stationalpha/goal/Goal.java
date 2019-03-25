package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.world.World;

import java.util.Optional;

public abstract class Goal {

    private boolean completed;

    final void update(){
        if(completed)
            return;

        completed = checkCompleted();
    }

    protected abstract boolean checkCompleted();

    public abstract Optional<Goal> getNextGoal(World world);

    public abstract String getName();

    public abstract String getDescription();

    public boolean isCompleted() {
        return completed;
    }
}
