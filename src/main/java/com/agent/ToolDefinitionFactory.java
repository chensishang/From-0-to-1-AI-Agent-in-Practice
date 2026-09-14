package com.agent;

import java.util.List;
import java.util.Map;

public class ToolDefinitionFactory {

    public ToolDefinition create(Tool tool) {

        ToolDefinition.FunctionDefinition function =
                new ToolDefinition.FunctionDefinition(
                        tool.getName(),
                        tool.getDescription(),
                        createParameters(tool)
                );

        return new ToolDefinition(
                "function",
                function
        );
    }

    private Map<String, Object> createParameters(Tool tool) {
        return tool.getParameters();
    }

    public List<ToolDefinition> createAll(
            ToolRegistry registry
    ) {
        return registry.getTools()
                .values()
                .stream()
                .map(this::create)
                .toList();
    }
}