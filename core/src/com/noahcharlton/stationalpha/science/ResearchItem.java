package com.noahcharlton.stationalpha.science;

import com.noahcharlton.stationalpha.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

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
    TEST("Test Item", "Desc. Here", 20, 25, world -> {}),
    TEST_2("Complex Plants", "Number 2 Here", 20, 350, world -> {}, TEST);

    private final String displayName;
    private final String desc;
    private final Consumer<World> onCompleted;
    private final List<ResearchItem> requirements;
    private final int posX;
    private final int posY;

    private boolean unlocked;

    ResearchItem() {
        this("No Name", "Desc", 0, 0, world -> {});
    }

    ResearchItem(String displayName, String desc, int y, int x, Consumer<World> onCompleted, ResearchItem... items) {
        this.displayName = displayName;
        this.desc = desc;
        this.onCompleted = onCompleted;
        this.requirements = Arrays.asList(items);
        this.posX = x;
        this.posY = y;

        unlocked = false;
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

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Consumer<World> getOnCompleted() {
        return onCompleted;
    }
}
