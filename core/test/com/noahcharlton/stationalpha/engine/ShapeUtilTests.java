package com.noahcharlton.stationalpha.engine;

import com.badlogic.gdx.graphics.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShapeUtilTests {

    @Test
    void drawLineX2LowerThanX1Test() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ShapeUtil.drawLine(20, 0, 15, 10, Color.WHITE, null);
        });
    }

    @Test
    void drawLineY2LowerThanY1Test() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ShapeUtil.drawLine(0, 50, 15, 45, Color.WHITE, null);
        });
    }
}
