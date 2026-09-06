package com.agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Context {

    private final List<Message> messages = new ArrayList<>();

    public void addMessage(Message message) {
        messages.add(message);
    }

    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    public void clear() {
        messages.clear();
    }
}