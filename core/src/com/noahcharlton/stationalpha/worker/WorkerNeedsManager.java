package com.noahcharlton.stationalpha.worker;

import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.world.Inventory;

public class WorkerNeedsManager {

    private final int FOOD_TIME = 1200;
    private final Worker worker;

    private int foodTick;

    public WorkerNeedsManager(Worker worker) {
        this.worker = worker;
    }

    public void update(){
        foodTick++;

        if(foodTick >= FOOD_TIME){
            eat();
        }
    }

    private void eat() {
        foodTick = 0;

        Inventory inventory = worker.getWorld().getInventory();

        if(inventory.getAmountForItem(Item.POTATO) > 0){
            inventory.changeAmountForItem(Item.POTATO, -1);
        }else{
            worker.die("Lack of food!");
        }
    }

    public int getFoodTick() {
        return foodTick;
    }
}
