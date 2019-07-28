package com.noahcharlton.stationalpha.engine;

import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;

public enum InGameIcon {
    PICK_AXE("pick_axe.png"), AXE("axe.png");

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
