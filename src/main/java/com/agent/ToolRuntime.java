package com.agent;

public class ToolRuntime {

    private final ToolRegistry registry;

    public ToolRuntime(ToolRegistry registry) {
        this.registry = registry;
    }

    public ToolResult execute(ToolCall toolCall) {

        FunctionCall functionCall = toolCall.getFunction();

        String toolName = functionCall.getName();
        String arguments = functionCall.getArguments();

        Tool tool = registry.getTool(toolName);

        if (tool == null) {
            throw new IllegalArgumentException(
                    "Tool not found: " + toolName
            );
        }

        Object result = tool.execute(arguments);

        return new ToolResult(
                toolCall.getId(),
                String.valueOf(result)
        );
    }
}