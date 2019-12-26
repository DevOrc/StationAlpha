package com.noahcharlton.stationalpha.science;

import com.noahcharlton.stationalpha.HelpInfo;

import java.util.Arrays;
import java.util.List;

public enum ResearchItem {

    TEST("Test", "Desc. Here", 0, -5000, -5000),
    BASIC_GARDENING("Basic Botany", HelpInfo.get("basic_botany_desc"),
            20, 50, 25),
    COMPOSTING("Composting", HelpInfo.get("composting_desc"),
            5, 50, 300, BASIC_GARDENING),
    COMPLEX_PLANTS("Complex Plants", HelpInfo.get("woodroot_research_desc"),
            20, 130, 300, BASIC_GARDENING),
    BASIC_MATERIALS("Space Materials", HelpInfo.get("basic_material_desc"),
            20, 220, 25),

    SOLAR_PANELS("Solar Panels", HelpInfo.get("solar_panel_research_desc"),
            10, 220, 300, BASIC_MATERIALS),
    BATTERIES("Batteries", HelpInfo.get("battery_research_desc"),
            20, 220, 550, SOLAR_PANELS),
    DUST_COLLECTION("Space Dust Collector", HelpInfo.get("dust_collector_desc"),
            20, 110, 600, SOLAR_PANELS),
    SYNTHESIZER("Atomic Synthesizer", HelpInfo.get("synthesizer_research_desc"),
            25, 110, 950, DUST_COLLECTION),
    ARC_REACTOR("Arc Reactor", HelpInfo.get("arc_reactor_research_desc"),
            50, 110, 1320, SYNTHESIZER);


    public static final List<ResearchItem> unlockedItems = Arrays.asList(BASIC_GARDENING, BASIC_MATERIALS);

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

    public boolean isRequirementCompleted() {
        for(ResearchItem requirement : requirements) {
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
