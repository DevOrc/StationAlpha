package com.noahcharlton.stationalpha.engine.input.assets;

import com.noahcharlton.stationalpha.engine.assets.AssetManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssetManagerTests {

    @Test
    void startWithZeroCompletedTest() {
        Assertions.assertEquals(0, AssetManager.getInstance().getNumberOfCompletedTasks());
    }
}
