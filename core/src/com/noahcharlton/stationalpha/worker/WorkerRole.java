package com.noahcharlton.stationalpha.worker;

import com.noahcharlton.stationalpha.engine.assets.ManagedTexture;

public enum  WorkerRole {

    GENERAL("general.png"), ENGINEER("engineer.png"), GARDENER("gardener.png");

    private final ManagedTexture workerTexture;

    WorkerRole(String workerTexturePath) {
        workerTexture = new ManagedTexture("worker/" + workerTexturePath);
    }

    /**
     * Forces the enum to initialize
     */
    public static void loadTextures(){}

    public ManagedTexture getWorkerTexture() {
        return workerTexture;
    }
}
