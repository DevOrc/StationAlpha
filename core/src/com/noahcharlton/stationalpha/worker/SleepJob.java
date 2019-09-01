package com.noahcharlton.stationalpha.worker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.noahcharlton.stationalpha.gui.GuiComponent;
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
            boolean isSleeping = job.getStage() == JobStage.IN_PROGRESS;
            boolean sleepsInBed = hasAccessibleBedroom();

            if(sleepsInBed && !isSleeping){
                WorkerRenderer.defaultRender(batch, worker);
            }

            if(!sleepsInBed){
                WorkerRenderer.defaultRender(batch, worker);

                if(!isSleeping)
                    drawZZZ(batch);
            }
        });
    }

    private void drawZZZ(SpriteBatch batch) {
        int x = worker.getPixelX() + 20;
        int y = worker.getPixelY() + 48;

        BitmapFont font = GuiComponent.getFont();
        font.setColor(Color.YELLOW);
        font.getData().setScale(.5f);

        font.draw(batch, "ZZZ", x, y);
    }

    @Override
    public String toString() {
        return "Sleeping: " + super.toString();
    }

    boolean hasAccessibleBedroom() {
        return worker.getBedroom().filter(bedContainer -> bedContainer.getTile().getOpenAdjecent().isPresent()).isPresent();
    }
}
