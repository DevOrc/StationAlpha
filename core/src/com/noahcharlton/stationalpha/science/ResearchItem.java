package com.noahcharlton.stationalpha.science;

import java.util.Arrays;
import java.util.List;

public enum ResearchItem {

//    //Plants
//    BASIC_GARDENING, //Plants
//    BOTANY, // Composter, Plant Experiment?
//    COMPLEX_PLANTS, // Add
//
//    //Technology
//    BASIC_POWER,
//    DUST_COLLECTOR,
//    SYNTHESIZER,
//    NEXT_GEN_POWER;

    //Used To For Unit Testing
    TEST("Test", "Desc. Here", 0,  -500, -500),
    TEST_3("Test", "Desc. Here", 0,  20, 100),
    TEST_2("Complex Plants", "Number 2 Here", 35, 20, 350, TEST_3);

    private final String displayName;
    private final String desc;
    private final List<ResearchItem> requirements;
    private final int scienceCost;
    private final int posX;
    private final int posY;

    private boolean completed;

    ResearchItem() {
        this("No Name", "Desc", 0, 0, 0);
    }

    ResearchItem(String displayName, String desc, int scienceCost,
                 int y, int x, ResearchItem... items) {
        this.displayName = displayName;
        this.desc = desc;
        this.requirements = Arrays.asList(items);
        this.scienceCost = scienceCost;
        this.posX = x;
        this.posY = y;

        completed = false;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed && isRequirementCompleted();
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDesc() {
        return desc;
    }

    public List<ResearchItem> getRequirements() {
        return requirements;
    }

    public boolean isRequirementCompleted(){
        for(ResearchItem requirement: requirements){
            if(!requirement.isCompleted())
                return false;
        }

        return true;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getScienceCost() {
        return scienceCost;
    }
}
