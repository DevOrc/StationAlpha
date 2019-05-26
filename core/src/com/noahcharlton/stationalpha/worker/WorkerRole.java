package com.noahcharlton.stationalpha.worker;

import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;

public enum  WorkerRole {

    GENERAL("general"), ENGINEER("engineer"), GARDENER("gardener");

    private final ManagedTexture workerTexture;
    private final ManagedTexture iconTexture;

    WorkerRole(String workerTexturePath) {
        workerTexture = new ManagedTexture("worker/" + workerTexturePath + ".png");
        iconTexture = new ManagedTexture("worker/" + workerTexturePath + "_icon.png");
    }

    /**
     * Forces the enum to initialize
     */
    public static void loadTextures(){}

    public ManagedTexture getIconTexture() {
        return iconTexture;
    }

    public ManagedTexture getWorkerTexture() {
        return workerTexture;
    }
}
