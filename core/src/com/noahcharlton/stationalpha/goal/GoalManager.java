package com.noahcharlton.stationalpha.goal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.noahcharlton.stationalpha.gui.scenes.message.MessageQueue;
import com.noahcharlton.stationalpha.world.World;

import java.util.Objects;

public class GoalManager {

    private final World world;
    private final GoalSupplier goals;
    private Goal currentGoal;

    public GoalManager(World world, GoalSupplier goals) {
        this.world = world;
        this.goals = goals;
        this.currentGoal = goals.getNext();
    }

    public void update() {
        currentGoal.update(world);

        boolean debugKey = Gdx.input != null ? Gdx.input.isKeyJustPressed(Input.Keys.G) : false;

        if(currentGoal.isCompleted() || debugKey) {
            currentGoal.onComplete(world);
            showGoalCompletedMessage();

            currentGoal = Objects.requireNonNull(goals.getNext());
        }
    }

    private void showGoalCompletedMessage() {
        String desc = "Congrats! You have completed the goal: " + currentGoal.getName();
        MessageQueue.getInstance().add("Goal Completed", desc);
    }

    public Goal getCurrentGoal() {
        return currentGoal;
    }
}
