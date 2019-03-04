package com.noahcharlton.stationalpha.item;

import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;
import org.apache.logging.log4j.LogManager;

public enum Item {

    SPACE_ROCK("space_rock", "Space Rock");

    private final String displayName;
    private final String id;
    private final ManagedTexture texture;

    Item(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
        texture = new ManagedTexture("item/" + id + ".png");
    }

    public ManagedTexture getTexture() {
        return texture;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static void init(){
        LogManager.getLogger(Item.class).info("Items loaded: " + values().length);
    }
}
