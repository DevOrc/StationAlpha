package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.World;

import java.util.Optional;

public abstract class ItemGoal extends Goal {

    private final Item item;
    private final int goalCount;
    private Optional<World> world = Optional.empty();


    public ItemGoal(Item item, int goalCount) {
        this.item = item;
        this.goalCount = goalCount;
    }

    @Override
    protected boolean checkCompleted(World world) {
        this.world = Optional.of(world);
        return world.getInventory().getAmountForItem(item) >= goalCount;
    }

    protected abstract String getTextDesc();

    @Override
    public final String getDescription() {
        return getTextDesc() + getProgress();
    }

    private String getProgress(){
        return world.map(w -> {
            Inventory inventory = w.getInventory();

            return "\n\n\nProgress: " + inventory.getAmountForItem(item) + " / " + goalCount;
        }).orElse("");
    }

    public int getGoalCount() {
        return goalCount;
    }

    public Item getItem() {
        return item;
    }
}
