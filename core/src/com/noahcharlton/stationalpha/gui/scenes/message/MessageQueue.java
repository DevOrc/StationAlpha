package com.noahcharlton.stationalpha.gui.scenes.message;

import java.util.ArrayDeque;
import java.util.Optional;

public class MessageQueue {

    private static final MessageQueue instance = new MessageQueue();
    private final ArrayDeque<Message> messages = new ArrayDeque<>();

    public void add(Message message){
        messages.add(message);
    }

    public void add(String name, String description){
        add(new Message(name, description));
    }

    Optional<Message> poll(){
        return Optional.ofNullable(messages.poll());
    }

    public ArrayDeque<Message> getMessages() {
        return messages;
    }

    public static MessageQueue getInstance() {
        return instance;
    }
}
