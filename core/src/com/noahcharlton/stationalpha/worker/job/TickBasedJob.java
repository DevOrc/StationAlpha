package com.noahcharlton.stationalpha.worker.job;

import com.noahcharlton.stationalpha.world.Tile;

public class TickBasedJob extends Job {

    private final int tickDuration;
    private int tick = 0;


    public TickBasedJob(Tile target, int tickDuration) {
        super(target);

        this.tickDuration = tickDuration;
    }

    @Override
    public void start() {
        super.start();

        tick = 0;
    }

    @Override
    public void update() {
        tick++;

        if(tick >= tickDuration){
            finish();
        }
    }

    public int getTick() {
        return tick;
    }
}
