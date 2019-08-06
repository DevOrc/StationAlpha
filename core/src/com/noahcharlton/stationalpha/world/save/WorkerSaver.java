package com.noahcharlton.stationalpha.world.save;

import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.world.World;

public class WorkerSaver {

    private final World world;

    public WorkerSaver(World world) {
        this.world = world;
    }

    public void save(QuietXmlWriter writer){
        QuietXmlWriter workerGroup = writer.element("Workers");

        for(Worker worker: world.getWorkers()){
            saveWorker(workerGroup, worker);
        }

        workerGroup.pop();
    }

    void saveWorker(QuietXmlWriter writer, Worker worker) {
        QuietXmlWriter workerElement = writer.element("Worker")
                .attribute("name", worker.getName())
                .attribute("pixelX", worker.getPixelX())
                .attribute("pixelY", worker.getPixelY());

        saveWorkerNeeds(worker, workerElement);
        saveWorkerRoles(worker, workerElement);

        workerElement.pop();
    }

    void saveWorkerRoles(Worker worker, QuietXmlWriter workerElement) {
        for(WorkerRole role: worker.getRoles()){
            workerElement.element("WorkerRole", role.name());
        }
    }

    void saveWorkerNeeds(Worker worker, QuietXmlWriter workerElement) {
        workerElement.element("Needs")
                .attribute("sleep", worker.getAi().getNeedsManager().getSleepTick())
                .attribute("food", worker.getAi().getNeedsManager().getFoodTick())
                .pop();
    }
}
