package com.noahcharlton.stationalpha;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelpInfoTests extends LibGdxTest{

    @Test
    void basicEntryTest() {
        String actual = HelpInfo.get("testEntry");

        Assertions.assertEquals("FooBar", actual);
    }
}
