package com.noahcharlton.stationalpha.engine.input;

import java.util.Optional;

public interface Selectable {

    String getTitle();

    String getDesc();

    String[] getDebugInfo();

    default Optional<String> getHelpInfo(){
        return Optional.empty();
    }
}
