package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.World;

import java.util.Optional;

public class PotatoGoal extends Goal {

    private final int goalCount;

    public PotatoGoal(int goalCount) {
        this.goalCount = goalCount;
    }

    @Override
    public Optional<Goal> getNextGoal(World world) {
        return Optional.of(new PotatoGoal(goalCount * 2));
    }

    @Override
    protected boolean checkCompleted(World world) {
        return world.getInventory().getAmountForItem(Item.POTATO) >= goalCount;
    }

    @Override
    public String getName() {
        return "Grow Potatoes";
    }

    @Override
    public String getDescription() {
        return String.format("Grow %d potatoes!", goalCount);
    }
}
