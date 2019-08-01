package com.noahcharlton.stationalpha.goal;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.World;

public class DefaultGoals implements GoalSupplier {

    private int currentGoalID = -1;

    public DefaultGoals() {}

    public Goal getNext() {
        currentGoalID++;

        switch(currentGoalID) {
            case -1:
                throw new GdxRuntimeException("Goal ID cannot be -1");
            case 0:
                return new SteelGoal(80);
            case 1:
                return new PotatoGoal(50);
            case 2:
                return new DirtGoal(8);
            case 3:
                return new SpaceDustGoal(1);
            case 4:
                return new UnobtainiumGoal(5);
            default:
                return new EndGameGoal();
        }
    }

    public int getId() {
        return currentGoalID;
    }

    static class SteelGoal extends ItemGoal {

        public SteelGoal(int goalCount) {
            super(Item.STEEL, goalCount);
        }

        @Override
        public void onComplete(World world) {
            world.getInventory().changeAmountForItem(Item.DIRT, 12);
        }

        @Override
        public String getName() {
            return "Gather Steel";
        }

        public String getTextDesc() {
            return "Gather " + getGoalCount() + " steel so that you can start your base." +
                    " You can collect steel by mining space rock. To mine rock, use the mining " +
                    "tool in the actions menu. \n\nOnce you have space rock, you can use the workbench, " +
                    "to craft steel.\n\nOnce this goal has been achieved, you will receive 10 dirt" +
                    " to help you grow your farm.";
        }
    }

    static class PotatoGoal extends ItemGoal {

        public PotatoGoal(int goalCount) {
            super(Item.POTATO, goalCount);
        }

        @Override
        public void onComplete(World world) {
            world.getInventory().changeAmountForItem(Item.DIRT, 6);
        }

        @Override
        public String getName() {
            return "Grow Potatoes";
        }

        public String getTextDesc() {
            return "Grow " + getGoalCount() + " potatoes so that your worker can eat. To grow potatoes, " +
                    "you need to place potatoes plants (in the block menu) on a dirt floor. \n\n In order for the " +
                    "plants to grow, they must have oxygen. To get oxygen, use the compressor block. Note: The room " +
                    "must be enclosed (with walls/airlocks and floors) or else the oxygen will escape!";
        }
    }

    static class DirtGoal extends ItemGoal {

        public DirtGoal(int goalCount) {
            super(Item.DIRT, goalCount);
        }

        @Override
        public String getName() {
            return "Compost Dirt";
        }

        public String getTextDesc() {
            return "Compost leaves to create " + getGoalCount() + " bags of dirt. You can use the composter to compost" +
                    " leaves. To collect leaves, you can grow trees, and then use the cut tree tool in the actions " +
                    "menu. \n\nTrees need a 3x3 area (centered on the sapling) to grow.";
        }
    }

    static class SpaceDustGoal extends ItemGoal {

        public SpaceDustGoal(int goalCount) {
            super(Item.SPACE_DUST, goalCount);
        }

        @Override
        public void onComplete(World world) {
            world.getInventory().changeAmountForItem(Item.UNOBTAINIUM, 1);
        }

        @Override
        public String getName() {
            return "Collect Space Dust";
        }

        public String getTextDesc() {
            return "Using dust collectors, collect one piece of space dust. Eventually, you will be able to use the " +
                    "space dust to synthesize unobtainium";
        }
    }

    static class UnobtainiumGoal extends ItemGoal {

        public UnobtainiumGoal(int goalCount) {
            super(Item.UNOBTAINIUM, goalCount);
        }

        @Override
        public String getName() {
            return "Obtain Unobtainium";
        }

        public String getTextDesc() {
            return "Now that you have space dust, you can use the synthesizer to create unobtainium. Because " +
                    "synthesizing takes so long, it will take your workers multiple sessions to synthesize one piece. ";
        }
    }
}
