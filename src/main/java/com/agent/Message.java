package com.agent;

public class Message {

    private final MessageRole role;
    private final String content;

    public Message(MessageRole role, String content) {
        this.role = role;
        this.content = content;
    }

    public MessageRole getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

    public static Message system(String content) {
        return new Message(MessageRole.SYSTEM, content);
    }

    public static Message user(String content) {
        return new Message(MessageRole.USER, content);
    }

    public static Message assistant(String content) {
        return new Message(MessageRole.ASSISTANT, content);
    }

    public static Message tool(String content) {
        return new Message(MessageRole.TOOL, content);
    }
}