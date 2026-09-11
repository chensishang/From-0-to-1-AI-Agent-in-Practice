package com.agent;

import java.util.List;

public class ChatRequest {

    private String model;

    private List<Message> messages;

    private boolean stream;

    private List<ToolDefinition> tools;

    public ChatRequest(
            String model,
            List<Message> messages,
            boolean stream,
            List<ToolDefinition> tools
    ) {
        this.model = model;
        this.messages = messages;
        this.stream = stream;
        this.tools = tools;
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

    public List<ToolDefinition> getTools() {
        return tools;
    }
}