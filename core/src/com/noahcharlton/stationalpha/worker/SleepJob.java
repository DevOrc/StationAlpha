package com.noahcharlton.stationalpha.worker;

import com.noahcharlton.stationalpha.worker.job.TickBasedJob;
import com.noahcharlton.stationalpha.world.Tile;

public class SleepJob extends TickBasedJob {

    private static final int SLEEP_TIME = 360;

    private final Worker worker;

    public SleepJob(Tile target, Worker worker) {
        super(target, SLEEP_TIME);

        this.worker = worker;
        this.setWorker(worker);
    }

    @Override
    public void finish() {
        super.finish();

        worker.getAi().getNeedsManager().finishSleep();
    }
}
