package com.noahcharlton.stationalpha.engine.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AssetManager {

    private static final AssetManager instance = new AssetManager();

    private final ExecutorService executor;
    private final Array<Asset> assets = new Array<>();
    private final Array<Future> tasks = new Array<>();

    public AssetManager() {
        this.executor = Executors.newFixedThreadPool(4);
    }

    public synchronized void addAsset(Asset asset){
        if(Gdx.app == null)
            return;

        Objects.requireNonNull(asset, "Assets cannot be null!");
        LoadAssetTask task = new LoadAssetTask(asset);

        assets.add(asset);
        tasks.add(executor.submit(task));
    }

    public void dispose(){
        executor.shutdown();

        for(Asset asset : assets){
            asset.dispose();
        }
    }

    public boolean isDone(){
        for(Future future : tasks){
            if(future.isDone() == false)
                return false;
        }

        return true;
    }

    public int getNumberOfCompletedTasks() {
        int completedTasks = 0;

        for(Future future : tasks){
            if(future.isDone() == false)
                completedTasks++;
        }

        return completedTasks;
    }

    public int getNumberOfAssets(){
        return  assets.size;
    }

    public static AssetManager getInstance() {
        return instance;
    }
}
