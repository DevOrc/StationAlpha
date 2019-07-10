package com.noahcharlton.stationalpha.gui;

import com.noahcharlton.stationalpha.engine.assets.AssetManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoadingScreenTests {

    private final LoadingScreen loadingScreen = new LoadingScreen();

    @Test
    void zeroPercentBarWidthZeroTest() {
        TestAssetManager assets = new TestAssetManager(0, 10);

        Assertions.assertEquals(0, loadingScreen.calcInnerBar(assets));
    }

    @Test
    void hundredPercentBarWidthFullTest() {
        TestAssetManager assets = new TestAssetManager(7, 7);

        Assertions.assertEquals(LoadingScreen.BAR_WIDTH, loadingScreen.calcInnerBar(assets));
    }

    @Test
    void fiftyPercentBarWidthHalfFullTest() {
        TestAssetManager assets = new TestAssetManager(11, 22);

        Assertions.assertEquals(LoadingScreen.BAR_WIDTH / 2, loadingScreen.calcInnerBar(assets));
    }


    static class TestAssetManager extends AssetManager {

        private final int numberOfAssets;
        private final int numberOfAssetsCompleted;

        public TestAssetManager(int numberOfAssetsCompleted, int numberOfAssets) {
            this.numberOfAssets = numberOfAssets;
            this.numberOfAssetsCompleted = numberOfAssetsCompleted;
        }

        @Override
        public int getNumberOfAssets() {
            return numberOfAssets;
        }

        @Override
        public int getNumberOfCompletedTasks() {
            return numberOfAssetsCompleted;
        }
    }
}
