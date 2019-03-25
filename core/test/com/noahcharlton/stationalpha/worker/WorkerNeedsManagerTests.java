package com.noahcharlton.stationalpha.worker;

import com.noahcharlton.stationalpha.item.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WorkerNeedsManagerTests {

    private final Worker worker = new TestWorker();
    private final WorkerNeedsManager needsManager = worker.getAi().getNeedsManager();

    @Test
    void diesWithoutFoodTest() {
        for(int i = 0; i < 1200; i++){
            needsManager.update();
        }

        Assertions.assertTrue(worker.isDead());
    }

    @Test
    void foodTickResetsTest() {
        for(int i = 0; i < 1200; i++){
            needsManager.update();
        }

        Assertions.assertEquals(0, needsManager.getFoodTick());
    }

    @Test
    void potatoDecreasesOnEatTest() {
        worker.getWorld().getInventory().setAmountForItem(Item.POTATO, 1);

        for(int i = 0; i < 1200; i++){
            needsManager.update();
        }

        Assertions.assertEquals(0, worker.getWorld().getInventory().getAmountForItem(Item.POTATO));
    }
}
