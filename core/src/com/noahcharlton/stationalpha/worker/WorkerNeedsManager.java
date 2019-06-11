package com.noahcharlton.stationalpha.worker;

import com.noahcharlton.stationalpha.gui.scenes.message.MessageQueue;
import com.noahcharlton.stationalpha.item.Item;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.world.Inventory;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class WorkerNeedsManager {

    public static final int FOOD_TIME = 1200;
    public static final int SLEEP_RESET = 30;

    private final Worker worker;

    private int foodTick;
    private int sleepTick = SLEEP_RESET;

    public WorkerNeedsManager(Worker worker) {
        this.worker = worker;
    }

    public void update(){
        updateSleep();
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
            MessageQueue.getInstance().add("NO BED", worker.getName() + " has no bed to sleep in!");

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

    public void finishSleep() {
        sleepTick = SLEEP_RESET;
    }

    private void updateFood() {
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

    void setSleepTick(int sleepTick) {
        this.sleepTick = sleepTick;
    }

    public int getFoodTick() {
        return foodTick;
    }

    public int getSleepTick() {
        return sleepTick;
    }
}
