package com.noahcharlton.stationalpha.worker;

import com.noahcharlton.stationalpha.gui.scenes.message.MessageQueue;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;
import java.util.Random;

public class WorkerNeedsManager {

    private static final Random random = new Random();
    public static final int FOOD_RESET = 1200;
    public static final int SLEEP_RESET = 6000;

    private final Worker worker;

    private int foodTick;
    private int sleepTick;

    public WorkerNeedsManager(Worker worker) {
        this.worker = worker;

        resetEatTime();
        resetSleepTime();
    }

    public void resetSleepTime(){
        sleepTick = (int) (SLEEP_RESET * (random.nextFloat() + 1f));
    }

    public void resetEatTime(){
        foodTick = (int) (FOOD_RESET * (random.nextFloat() + 1f));
    }

    public void update(){
        updateSleep();

        if(!isSleeping())
            updateFood();
    }

    void updateSleep() {
        if(isSleeping())
            return;

        sleepTick--;

        if(sleepTick < 1) {
            SleepJob job = createSleepJob();

            worker.getAi().getJobManager().setCurrentJob(job);
        }
    }

    SleepJob createSleepJob() {
        if(!worker.getBedroom().isPresent()){
            return new SleepJob(worker.getTileOn(), worker);
        }

        Optional<Tile> tileNextToBed = worker.getBedroom().get().getTile().getOpenAdjecent();

        if(tileNextToBed.isPresent()){
            return new SleepJob(tileNextToBed.get(), worker);
        }else{
            MessageQueue.getInstance().add("NO BED", worker.getName() + "'s bed is not accessible!");

            return new SleepJob(worker.getTileOn(), worker);
        }
    }

    public boolean isSleeping(){
        Optional<Job> current =  worker.getAi().getJobManager().getCurrentJob();

        if(!current.isPresent())
            return false;

        if(current.get() instanceof SleepJob){
            return current.get().getStage() != Job.JobStage.FINISHED;
        }

        return false;
    }

    private void updateFood() {
        foodTick--;

        if(foodTick < 0){
            eat();
        }
    }

    void eat() {
        resetEatTime();

        Inventory inventory = worker.getWorld().getInventory();
        int potatoAmount = inventory.getAmountForItem(Item.POTATO);
        int woodrootAmount = inventory.getAmountForItem(Item.WOODROOT);

        if(potatoAmount + woodrootAmount < 10){
            MessageQueue.getInstance().add("LOW FOOD!", "WARNING: YOU ARE LOW ON FOOD");
        }

        if(woodrootAmount > 0){
            inventory.changeAmountForItem(Item.WOODROOT, -1);
        }else if(potatoAmount > 0){
            inventory.changeAmountForItem(Item.POTATO, -1);
        }else{
            worker.die("Lack of food!");
        }
    }

    public void setSleepTick(int sleepTick) {
        this.sleepTick = sleepTick;
    }

    public WorkerNeedsManager setFoodTick(int foodTick) {
        this.foodTick = foodTick;
        return this;
    }

    public int getFoodTick() {
        return foodTick;
    }

    public int getSleepTick() {
        return sleepTick;
    }
}
