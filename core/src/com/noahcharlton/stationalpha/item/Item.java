package com.noahcharlton.stationalpha.item;

import com.badlogic.gdx.graphics.Texture;
import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;
import org.apache.logging.log4j.LogManager;

public enum Item {

    DIRT("dirt", "Dirt"),
    POTATO("potato", "Potato"),
    WOODROOT("woodroot", "Woodroot"),
    WOOD("wood", "Wood"),
    LEAVES("leaves", "Leaves"),
    SPACE_ROCK("space_rock", "Space Rock"),
    SPACE_DUST("dust", "Space Dust"),
    STEEL("steel", "Steel"),
    COPPER("copper", "Copper"),
    POWER_INGOT("power_ingot", "Power Ingot"),
    UNOBTAINIUM("unobtainium", "Unobtainium"),
    TEST_ITEM("test_item", "Test Item");

    private final String displayName;
    private final String id;
    private final ManagedTexture texture;

    Item(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
        texture = new ManagedTexture("items/" + id + ".png");
    }

    public ItemStack stack(int amount){
        return ItemStack.of(this, amount);
    }

    public Texture getTexture() {
        return texture.get();
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * @deprecated Should use Item.name() and Item.valueOf()
     */
    @Deprecated
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
