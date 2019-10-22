package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.noahcharlton.stationalpha.engine.InGameIcon;
import com.noahcharlton.stationalpha.gui.GuiComponent;

public interface BuildMenu {

    InGameIcon getIcon();

    String getName();

    GuiComponent getComponent();

}
