package com.noahcharlton.stationalpha.engine.assets;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class LoadAssetTask extends FutureTask<LoadAssetTask> {

    private final Asset asset;

    public LoadAssetTask(Asset asset) {
        super(createRunnableFromAsset(asset));

        this.asset = asset;
    }

    private static Callable createRunnableFromAsset(Asset asset) {
        return () -> {
            try{
                asset.load();
            }catch (RuntimeException e){
                e.printStackTrace();
            }

            return asset;
        };
    }

    public Asset getAsset() {
        return asset;
    }
}
