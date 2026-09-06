package com.agent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    private MessageRole role;
    private String content;

    public Message() {
    }

    public Message(MessageRole role, String content) {
        this.role = role;
        this.content = content;
    }

    public MessageRole getRole() {
        return role;
    }

    public void setRole(MessageRole role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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