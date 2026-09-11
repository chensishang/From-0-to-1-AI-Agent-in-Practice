package com.agent;

import java.util.Map;

public class ToolDefinition {

    private String type;

    private FunctionDefinition function;

    public ToolDefinition(
            String type,
            FunctionDefinition function
    ) {
        this.type = type;
        this.function = function;
    }

    public String getType() {
        return type;
    }

    public FunctionDefinition getFunction() {
        return function;
    }

    public static class FunctionDefinition {

        private String name;

        private String description;

        private Map<String, Object> parameters;

        public FunctionDefinition(
                String name,
                String description,
                Map<String, Object> parameters
        ) {
            this.name = name;
            this.description = description;
            this.parameters = parameters;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public Map<String, Object> getParameters() {
            return parameters;
        }
    }
}