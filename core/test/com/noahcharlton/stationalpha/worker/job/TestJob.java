package com.noahcharlton.stationalpha.worker.job;

import com.noahcharlton.stationalpha.world.Tile;
import com.noahcharlton.stationalpha.world.World;

public class TestJob extends Job {

    private int updateCount;

    public TestJob() {
        super(new Tile(0, 0, new World()));
    }

    @Override
    public void update() {
        updateCount++;
    }

    public int getUpdateCount() {
        return updateCount;
    }
}
