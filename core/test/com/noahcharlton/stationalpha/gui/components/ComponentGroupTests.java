package com.noahcharlton.stationalpha.gui.components;

import org.junit.jupiter.api.Test;

public class ComponentGroupTests {

    private final ComponentGroup group = new ComponentGroup() {
        @Override
        protected void updatePosition() {

        }

        @Override
        protected void updateSize() {

        }
    };

    @Test
    void nullLayoutManagerHasNoProblemTest() {
        group.setLayoutManager(null);

        group.layout();
    }
}
