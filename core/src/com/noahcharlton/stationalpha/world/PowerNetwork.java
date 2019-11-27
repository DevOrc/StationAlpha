package com.noahcharlton.stationalpha.world;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;

public class PowerNetwork {

    private static final int BASE_CAPACITY = 1000;

    private int power = 0;
    private int capacity = BASE_CAPACITY;

    public void changePower(int amount){
        this.power = Math.max(0, Math.min(capacity, power + amount));
    }

    public boolean usePowerIfAvailable(int amount){
        if(getPower() >= amount){
            changePower(-amount);

            return true;
        }

        return false;
    }

    public void changeCapacity(int amount){
        this.capacity = Math.max(BASE_CAPACITY, capacity + amount);

        this.power = Math.max(0, Math.min(capacity, power));
    }

    public void save(QuietXmlWriter writer){
        writer.element("PowerNetwork")
                .attribute("power", power)
                .attribute("capacity", capacity)
                .pop();
    }

    public void load(XmlReader.Element element){
        element = element.getChildByName("PowerNetwork");

        power = 0;
        capacity = 0;

        changeCapacity(element.getIntAttribute("capacity"));
        changePower(element.getIntAttribute("power"));
    }

    public int getPower() {
        return power;
    }

    public int getCapacity() {
        return capacity;
    }
}
