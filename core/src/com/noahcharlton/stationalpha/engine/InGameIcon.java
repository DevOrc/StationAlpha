package com.noahcharlton.stationalpha.engine;

import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;

public enum InGameIcon {
    PICK_AXE("pick_axe.png"), AXE("axe.png"), NO_POWER("no_power.png"), MENU_UI("menu_ui.png"),
    GEAR("gear.png"), BLOCK("block.png"), WORKER("worker.png"), GOAL("goal.png"),
    BLACK_WHITE_PICK_AXE("black_white_pick_axe.png"), FLOOR("floor.png"), NO_ICON("no_icon.png"),
    INVENTORY("inventory.png");

    private final String path;
    private final ManagedTexture managedTexture;

    InGameIcon(String path) {
        this.path = "icons/" + path;
        this.managedTexture = new ManagedTexture(this.path);
    }

    public static void loadTextures(){
        //Forces JVM to load the class, so the texture objects are created
    }

    public String getPath() {
        return path;
    }

    public ManagedTexture getTexture() {
        return managedTexture;
    }
}
