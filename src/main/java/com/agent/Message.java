package com.agent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    private MessageRole role;

    private String content;

    @JsonProperty("tool_calls")
    private List<ToolCall> toolCalls;
    @JsonProperty("tool_call_id")
    private String toolCallId;

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

    public List<ToolCall> getToolCalls() {
        return toolCalls;
    }

    public void setToolCalls(List<ToolCall> toolCalls) {
        this.toolCalls = toolCalls;
    }
    public String getToolCallId() {
        return toolCallId;
    }

    public void setToolCallId(String toolCallId) {
        this.toolCallId = toolCallId;
    }

    public static Message system(String content) {
        return new Message(
                MessageRole.SYSTEM,
                content
        );
    }

    public static Message user(String content) {
        return new Message(
                MessageRole.USER,
                content
        );
    }
    public static Message tool(
            String toolCallId,
            String content
    ) {

        Message message =
                new Message(
                        MessageRole.TOOL,
                        content
                );

        message.setToolCallId(toolCallId);

        return message;
    }

    public static Message assistant(String content) {
        return new Message(
                MessageRole.ASSISTANT,
                content
        );
    }

    public static Message tool(String content) {
        return new Message(
                MessageRole.TOOL,
                content
        );
    }
}