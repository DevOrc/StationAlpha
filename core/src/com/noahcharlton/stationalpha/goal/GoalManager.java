package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.world.World;

import java.util.Optional;

public class GoalManager {

    private final World world;
    private Optional<Goal> currentGoal;

    public GoalManager(World world, Goal firstGoal) {
        this.world = world;
        this.currentGoal = Optional.of(firstGoal);
    }

    public void update(){
        currentGoal.ifPresent(Goal::update);

        if(currentGoal.map(Goal::isCompleted).orElse(false)){
            currentGoal = currentGoal.get().getNextGoal(world);
        }
    }

    public Optional<Goal> getCurrentGoal() {
        return currentGoal;
    }
}
