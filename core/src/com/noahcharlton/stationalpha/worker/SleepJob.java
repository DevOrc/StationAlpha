package com.noahcharlton.stationalpha.worker;

import com.noahcharlton.stationalpha.worker.job.JobRenderer;
import com.noahcharlton.stationalpha.worker.job.TickBasedJob;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

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

    @Override
    public Optional<JobRenderer> createRenderer() {
        return Optional.of((batch, job) -> {
            if(job.getStage() != JobStage.IN_PROGRESS || !hasAccessibleBedroom()){
                WorkerRenderer.defaultRender(batch, worker);
            }
        });
    }

    @Override
    public String toString() {
        return "Sleeping: " + super.toString();
    }

    boolean hasAccessibleBedroom() {
        return worker.getBedroom().filter(bedContainer -> bedContainer.getTile().getOpenAdjecent().isPresent()).isPresent();
    }
}
