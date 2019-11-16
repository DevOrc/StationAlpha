package com.noahcharlton.stationalpha.science;

import com.badlogic.gdx.utils.XmlReader;
import com.noahcharlton.stationalpha.engine.input.DebugKeys;
import com.noahcharlton.stationalpha.world.World;
import com.noahcharlton.stationalpha.world.save.QuietXmlWriter;

public class ScienceManager {

    private final World world;
    private int sciencePoints;

    public ScienceManager(World world) {
        this.world = world;
    }

    public void update() {
        if(DebugKeys.isDebugPressed(DebugKeys.SCIENCE_POINTS))
            sciencePoints++;
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
        saveResearchItems(writer);
    }

    private void saveResearchItems(QuietXmlWriter writer) {
        writer = writer.element("ResearchItems");

        for(ResearchItem item: ResearchItem.values()){
            writer.element("ResearchItem")
                    .attribute("Name", item.name())
                    .attribute("Completed", item.isCompleted())
                    .pop();
        }

        writer.pop();
    }

    public void saveScience(QuietXmlWriter writer) {
        writer.element("Science").attribute("value", sciencePoints).pop();
    }

    public void loadResearchItems(XmlReader.Element items){
        for(XmlReader.Element element : items.getChildrenByName("ResearchItem")){
            ResearchItem item = ResearchItem.valueOf(element.getAttribute("Name"));

            if(element.getBooleanAttribute("Completed")){
                item.setCompleted(true);
            }else{
                item.setCompleted(false);
            }
        }
    }

    public void loadScience(XmlReader.Element element) {
        if(element.hasChild("Science"))
            sciencePoints = element.getChildByName("Science").getIntAttribute("value");

    }

    public int getSciencePoints() {
        return sciencePoints;
    }
}
