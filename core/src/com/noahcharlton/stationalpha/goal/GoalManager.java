package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.gui.scenes.message.MessageQueue;
import com.noahcharlton.stationalpha.worker.Worker;
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
            showGoalCompletedMessage();
            currentGoal = currentGoal.get().getNextGoal(world);

            addWorker();
        }
    }

    private void showGoalCompletedMessage() {
        currentGoal.ifPresent(goal -> {
            String desc = "Congrats! You have completed the goal: " + goal.getName() ;
            MessageQueue.getInstance().add("Goal Completed", desc);
        });
    }

    private void addWorker() {
        world.getWorkers().add(Worker.create(world));
    }

    public Optional<Goal> getCurrentGoal() {
        return currentGoal;
    }
}
