package com.noahcharlton.stationalpha.engine.input;

import com.noahcharlton.stationalpha.gui.GuiComponent;

import java.util.Optional;

public interface Selectable {

    interface GuiSelectable extends Selectable{

        GuiComponent createGui();

    }

    String getTitle();

    String getDesc();

    String[] getDebugInfo();

    default Optional<String> getHelpInfo(){
        return Optional.empty();
    }
}
