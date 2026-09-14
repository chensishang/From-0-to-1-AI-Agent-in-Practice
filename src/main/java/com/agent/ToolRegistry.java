package com.agent;

import java.util.HashMap;
import java.util.Map;

public class ToolRegistry {

    private final Map<String, Tool> tools = new HashMap<>();

    public void register(Tool tool) {
        tools.put(tool.getName(), tool);
    }

    public Tool getTool(String name) {
        return tools.get(name);
    }

    public Map<String, Tool> getTools() {
        return tools;
    }
}