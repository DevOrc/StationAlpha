package com.noahcharlton.stationalpha.world.load;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.worker.Worker;
import com.noahcharlton.stationalpha.worker.WorkerRole;
import com.noahcharlton.stationalpha.world.World;

public class WorkerLoader {

    static void load(XmlReader.Element elements, World world) {
        for(XmlReader.Element element : elements.getChildrenByName("Worker")){
            Worker worker = createWorker(element, world);

            world.getWorkers().add(worker);
        }
    }

     static Worker createWorker(XmlReader.Element element, World world) {
        String name = element.getAttribute("name");
        int pixelX = element.getIntAttribute("pixelX");
        int pixelY = element.getIntAttribute("pixelY");

        Worker worker = new Worker(name, world);
        worker.setPixelX(pixelX);
        worker.setPixelY(pixelY);

        parseRoles(worker, element);
        parseNeeds(worker, element);

        return worker;
    }

     static void parseNeeds(Worker worker, XmlReader.Element element) {
        XmlReader.Element needsElement = element.getChildByName("Needs");
        int sleepTick = needsElement.getIntAttribute("sleep");
        int foodTick = needsElement.getIntAttribute("food");

        worker.getAi().getNeedsManager().setFoodTick(foodTick);
        worker.getAi().getNeedsManager().setSleepTick(sleepTick);
    }

     static void parseRoles(Worker worker, XmlReader.Element element) {
        worker.getRoles().clear();

        for(XmlReader.Element roleElement : element.getChildrenByName("WorkerRole")){
            worker.addRole(WorkerRole.valueOf(roleElement.getText()));
        }
    }

}
