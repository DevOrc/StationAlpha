package com.noahcharlton.stationalpha.worker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;
import com.noahcharlton.stationalpha.gui.GuiComponent;
import com.noahcharlton.stationalpha.worker.job.Job;
import com.noahcharlton.stationalpha.worker.job.JobRenderer;
import com.noahcharlton.stationalpha.worker.job.WorkerJobManager;

import java.util.Optional;

public class WorkerRenderer {

    private static final float HELMET_THRESHOLD = 15f;
    private static final ManagedTexture helmetTexture = new ManagedTexture("worker/worker_helmet.png");

    public static void init(){
        WorkerRole.loadTextures();
    }

    public static void render(SpriteBatch batch, Worker worker){
        WorkerJobManager jobManager = worker.getAi().getJobManager();
        Optional<JobRenderer> jobRenderer = jobManager.getCurrentJob().flatMap(Job::getRenderer);

        if(jobRenderer.isPresent()){
            jobRenderer.get().render(batch, jobManager.getCurrentJob().get());
        }else{
            defaultRender(batch, worker);
        }
    }

    public static void defaultRender(SpriteBatch batch, Worker worker){
        checkRendererInitialized();

        batch.draw(getRenderableRole(worker).getWorkerTexture().get(), worker.getPixelX(), worker.getPixelY());

        if(shouldRenderHelmet(worker))
            batch.draw(helmetTexture.get(), worker.getPixelX(), worker.getPixelY());

        renderName(batch, worker);
    }

    static boolean shouldRenderHelmet(Worker worker) {
        return worker.getTileOn().getOxygenLevel() <= HELMET_THRESHOLD;
    }

    static WorkerRole getRenderableRole(Worker worker) {
        if(worker.getRoles().size() > 0)
            return worker.getRoles().iterator().next();

        return WorkerRole.GENERAL;
    }

    private static void renderName(SpriteBatch batch, Worker worker) {
        BitmapFont font = GuiComponent.getFont();
        font.getData().setScale(.4f);
        font.setColor(Color.WHITE);

        font.draw(batch, worker.getName(), worker.getPixelX(), worker.getPixelY() - 10, 32, Align.center, false);
    }

    static void checkRendererInitialized() {
        if(WorkerRole.GENERAL.getWorkerTexture() == null) {
            throw new GdxRuntimeException("Renderer not initialized!");
        }
    }
}
