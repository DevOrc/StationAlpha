package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.world.World;

import java.util.Optional;

public abstract class Goal {

    private boolean completed;

    final void update(World world){
        if(completed)
            return;

        completed = checkCompleted(world);
    }

    protected abstract boolean checkCompleted(World world);

    public abstract Optional<Goal> getNextGoal(World world);

    public void onComplete(World world){
        world.getWorkers().add(Worker.create(world));
    }

    public abstract String getName();

    public abstract String getDescription();

    public boolean isCompleted() {
        return completed;
    }
}
