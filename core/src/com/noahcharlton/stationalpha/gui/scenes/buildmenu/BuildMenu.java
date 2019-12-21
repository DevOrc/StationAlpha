package com.noahcharlton.stationalpha.gui.scenes.buildmenu;

import com.noahcharlton.stationalpha.engine.InGameIcon;
import com.noahcharlton.stationalpha.engine.settings.KeySetting;
import com.noahcharlton.stationalpha.gui.GuiComponent;

import java.util.Optional;

public interface BuildMenu {

    InGameIcon getIcon();

    String getName();

    GuiComponent getComponent();

    default Optional<KeySetting> getHotKey(){
        return Optional.empty();
    }
}
