package com.noahcharlton.stationalpha.science;

import com.noahcharlton.stationalpha.world.World;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public enum ResearchItem {

    //Plants
    BASIC_GARDENING, //Plants
    BOTANY, // Composter, Plant Experiment?
    COMPLEX_PLANTS, // Add

    //Technology
    BASIC_POWER,
    DUST_COLLECTOR,
    SYNTHESIZER,
    NEXT_GEN_POWER;

    private final String displayName;
    private final String desc;
    private final Consumer<World> onCompleted;
    private final List<ResearchItem> requirements;

    private boolean unlocked;

    ResearchItem() {
        this("No Name", "Desc", world -> {}, Collections.emptyList());
    }

    ResearchItem(String displayName, String desc, Consumer<World> onCompleted, List<ResearchItem> requirements) {
        this.displayName = displayName;
        this.desc = desc;
        this.onCompleted = onCompleted;
        this.requirements = requirements;
    }

    public boolean isUnlocked() {
        return unlocked;
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

    public Consumer<World> getOnCompleted() {
        return onCompleted;
    }
}
