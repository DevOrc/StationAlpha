package com.noahcharlton.stationalpha.gui.scenes;

import com.badlogic.gdx.Gdx;
import com.noahcharlton.stationalpha.LibGdxTest;
import com.noahcharlton.stationalpha.StationAlpha;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SpeedButtonTests extends LibGdxTest {

    private SpeedButton speedButton = new SpeedButton();

    @Test
    void verifyTexturePathTest() {
        Assertions.assertTrue(Gdx.files.internal(SpeedButton.texturePath).exists());
    }

    @Test
    void onClickIncreaseStageTest() {
        speedButton.onClick();

        Assertions.assertEquals(1, speedButton.getCurrentStage());
    }

    @Test
    void onClickWrapToZeroStageTest() {
        for(int i =0; i < SpeedButton.STAGES; i++)
            speedButton.onClick();


        Assertions.assertEquals(0, speedButton.getCurrentStage());
    }

    @Test
    void onClickUpdatesStationAlphaUpdateSpeedTest() {
        speedButton.onClick();

        Assertions.assertEquals(1, StationAlpha.getInstance().getTicksPerUpdate());
    }

    @Test
    void syncStageWithStationAlphaInstanceTest() {
        StationAlpha.getInstance().setTicksPerUpdate(3);

        speedButton.syncStage();

        Assertions.assertEquals(3, speedButton.getCurrentStage());
    }
}
