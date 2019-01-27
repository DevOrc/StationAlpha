package com.noahcharlton.stationalpha;

import com.badlogic.gdx.Gdx;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BasicTest extends LibGdxTest{

    @Test
    public void basicTest() {
        Assertions.assertNotNull(Gdx.files);
    }
}
