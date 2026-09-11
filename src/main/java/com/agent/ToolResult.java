package com.agent;

public class ToolResult {

    private String toolCallId;

    private String content;

    public ToolResult() {
    }

    public ToolResult(
            String toolCallId,
            String content
    ) {
        this.toolCallId = toolCallId;
        this.content = content;
    }

    public String getToolCallId() {
        return toolCallId;
    }

    public void setToolCallId(String toolCallId) {
        this.toolCallId = toolCallId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}