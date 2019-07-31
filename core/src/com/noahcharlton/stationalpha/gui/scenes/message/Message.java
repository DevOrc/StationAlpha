package com.noahcharlton.stationalpha.gui.scenes.message;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Message)) return false;
        Message message = (Message) o;
        return Objects.equals(name, message.name) &&
                Objects.equals(description, message.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
