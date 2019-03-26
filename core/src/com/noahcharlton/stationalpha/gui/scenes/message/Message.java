package com.noahcharlton.stationalpha.gui.scenes.message;

public class Message {

    private final String name;
    private final String description;

    public Message(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
