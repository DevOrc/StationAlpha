package com.noahcharlton.stationalpha.science;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;

public class ScienceManager {

    private final World world;
    private int sciencePoints;

    public ScienceManager(World world) {
        this.world = world;
    }

    public void update() {
    }

    public void earnSciencePoints(int amount){
        sciencePoints += amount;

        sciencePoints = Math.max(0, sciencePoints);
    }

    public void removeSciencePoints(int amount){
        sciencePoints -= amount;

        sciencePoints = Math.max(0, sciencePoints);
    }

    public void save(QuietXmlWriter writer){
        saveScience(writer);
    }

    public void saveScience(QuietXmlWriter writer) {
        writer.element("Science").attribute("value", sciencePoints).pop();
    }

    public void loadScience(XmlReader.Element element) {
        if(element.hasChild("Science"))
            sciencePoints = element.getChildByName("Science").getIntAttribute("value");

    }

    public int getSciencePoints() {
        return sciencePoints;
    }
}
