package com.noahcharlton.stationalpha.worker.job;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.engine.ShapeUtil;
import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.worker.WorkerRenderer;
import com.noahcharlton.stationalpha.world.Tile;

import java.util.Optional;

public class TickBasedJob extends Job {

    private static final int BAR_HEIGHT = 12;

    private final int tickDuration;
    protected int tick = 0;


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

    @Override
    public Optional<JobRenderer> createRenderer() {
        return Optional.of((batch, job) -> {
            if(!getAssignedWorker().isPresent())
                return;

            Worker worker = getAssignedWorker().get();

            WorkerRenderer.defaultRender(batch, worker);

            if(job.getStage() == JobStage.IN_PROGRESS)
                drawBar(batch, worker);
        });
    }

    private void drawBar(SpriteBatch batch, Worker worker) {
        int x = worker.getPixelX();
        int y = worker.getPixelY() + 48 + 6;
        float barPercent = ((float) tick / tickDuration);
        int barWidth = (int) (barPercent * 28);

        ShapeUtil.drawRect(x, y, 32, BAR_HEIGHT, Color.WHITE, batch);
        ShapeUtil.drawRect(x + 2, y + 2, barWidth, BAR_HEIGHT - 4, Color.RED, batch);
    }

    public int getTick() {
        return tick;
    }

    public int getJobDuration() {
        return tickDuration;
    }

    @Override
    public String toString() {
        return "" + tick + " / " + tickDuration + "";
    }
}
