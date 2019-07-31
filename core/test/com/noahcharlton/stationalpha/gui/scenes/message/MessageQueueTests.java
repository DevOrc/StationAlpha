package com.noahcharlton.stationalpha.gui.scenes.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MessageQueueTests {

    private final MessageQueue messageQueue = new MessageQueue();

    @Test
    void addBasicTest() {
        Message message = new Message("Test Message", "Foo");

        messageQueue.add(message);

        Assertions.assertSame(message, messageQueue.poll().get());
    }

    @Test
    void addNameTest() {
        messageQueue.add("1234", "");

        Message message = messageQueue.poll().get();

        Assertions.assertEquals("1234", message.getName());
    }

    @Test
    void addDescriptionTest() {
        messageQueue.add("", "Zebra");

        Message message = messageQueue.poll().get();

        Assertions.assertEquals("Zebra", message.getDescription());
    }

    @Test
    void emptyByDefaultTest() {
        Assertions.assertFalse(messageQueue.poll().isPresent());
    }

    @Test
    void doesNotAddSameMessageTwiceTest() {
        Message message = new Message("Foo", "Bar");

        messageQueue.add(message);
        messageQueue.add(message);

        Assertions.assertEquals(1, messageQueue.getMessages().size());
    }

    @Test
    void doesNotAddSameDetailsTwiceTest() {
        messageQueue.add("1234", "5678");
        messageQueue.add("1234", "5678");

        Assertions.assertEquals(1, messageQueue.getMessages().size());
    }

    @Test
    void messageQueueLimitsAt25Spots() {
        for(int i = 0; i < 50; i++){
            messageQueue.add("", "" + i);
        }

        Assertions.assertEquals(25, messageQueue.getMessages().size());
    }
}
