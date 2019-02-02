package com.noahcharlton.stationalpha.gui.components.layout;

import com.noahcharlton.stationalpha.gui.GuiComponent;

import java.util.ArrayList;

public interface LayoutManager {

    void layout(GuiComponent parent, ArrayList<GuiComponent> children);
}
