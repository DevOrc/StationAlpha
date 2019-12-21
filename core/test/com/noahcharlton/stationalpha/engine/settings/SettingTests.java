package com.noahcharlton.stationalpha.engine.settings;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SettingTests {

    private boolean testState;

    @Test
    void booleanSettingOnApplyTest() {
        BooleanSetting setting = new BooleanSetting("", false, this::setStateConsumer);

        setting.setState(true);
        setting.apply();

        Assertions.assertTrue(testState);
    }

    @Test
    void setApplyRunsConsumerTest() {
        BooleanSetting setting = new BooleanSetting("", false, state -> {});
        setting.setState(true);

        setting.setApply(this::setStateConsumer);

        Assertions.assertTrue(testState);
    }

    public void setStateConsumer(boolean state){
        this.testState = state;
    }
}
