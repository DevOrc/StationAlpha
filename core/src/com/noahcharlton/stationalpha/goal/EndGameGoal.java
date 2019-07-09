package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.world.World;

public class EndGameGoal extends Goal{

    @Override
    protected boolean checkCompleted(World world) {
        return false;
    }

    @Override
    public String getName() {
        return "YOU WIN!";
    }

    @Override
    public String getDescription() {
        return "Congrats, your space station is officially completed!";
    }
}
