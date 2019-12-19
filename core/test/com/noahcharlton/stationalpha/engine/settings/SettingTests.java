package com.noahcharlton.stationalpha.engine.settings;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SettingTests {

    private boolean testState;

    @Test
    void booleanSettingOnApplyTest() {
        Setting setting = new BooleanSetting("", false, this::setStateConsumer);

        setting.state = true;
        setting.apply();

        Assertions.assertTrue(testState);
    }

    @Test
    void setApplyRunsConsumerTest() {
        BooleanSetting setting = new BooleanSetting("", false, state -> {});
        setting.state = true;

        setting.setApply(this::setStateConsumer);

        Assertions.assertTrue(testState);
    }

    public void setStateConsumer(boolean state){
        this.testState = state;
    }
}
