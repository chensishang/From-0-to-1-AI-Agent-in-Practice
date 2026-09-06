package com.agent;

import java.util.List;

public class ChatRequest {

    private String model;

    private List<Message> messages;

    private boolean stream;

    public ChatRequest(
            String model,
            List<Message> messages,
            boolean stream
    ) {
        this.model = model;
        this.messages = messages;
        this.stream = stream;
    }

    public String getModel() {
        return model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public boolean isStream() {
        return stream;
    }
}