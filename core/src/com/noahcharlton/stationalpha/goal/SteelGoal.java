package com.noahcharlton.stationalpha.goal;

import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.World;

import java.util.Optional;

public class SteelGoal extends Goal{

    private static final int AMOUNT = 200;

    @Override
    protected boolean checkCompleted(World world) {
        return world.getInventory().getAmountForItem(Item.STEEL) >= AMOUNT;
    }

    @Override
    public void onComplete(World world) {
        world.getInventory().changeAmountForItem(Item.DIRT, 10);
    }

    @Override
    public Optional<Goal> getNextGoal(World world) {
        return Optional.of(new PotatoGoal(100));
    }

    @Override
    public String getName() {
        return "Gather Steel";
    }

    @Override
    public String getDescription() {
        return "Gather "+ AMOUNT + " steel so that you can start your base."
                + " You can collect steel by mining space rock. To mine rock, use the mining " +
                "tool in the actions menu. \n\nOnce you have space rock, you can use the workbench, to craft the steel." +
                " \n\nOnce this goal has been achieved, you will receive 10 more dirt to help you grow your farm.";
    }
}
