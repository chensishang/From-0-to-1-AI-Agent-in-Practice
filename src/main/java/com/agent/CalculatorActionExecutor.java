
package com.agent;

import java.util.UUID;

public class CalculatorActionExecutor implements ActionExecutor {

    private final ToolRuntime toolRuntime;

    public CalculatorActionExecutor(ToolRuntime toolRuntime) {
        this.toolRuntime = toolRuntime;
    }

    @Override
    public Observation execute(Action action) {

        // 1. 检查动作类型
        if (!"TOOL".equals(action.getType())) {
            throw new IllegalArgumentException(
                    "不支持的动作类型：" + action.getType()
            );
        }

        // 2. 检查目标工具
        if (!"calculator".equals(action.getTarget())) {
            throw new IllegalArgumentException(
                    "不支持的工具：" + action.getTarget()
            );
        }

        // 3. 将 Action 转换为现有的 ToolCall
        ToolCall toolCall = new ToolCall();

        toolCall.setId(UUID.randomUUID().toString());
        toolCall.setType("function");

        FunctionCall functionCall = new FunctionCall();
        functionCall.setName(action.getTarget());
        functionCall.setArguments(action.getInput());

        toolCall.setFunction(functionCall);

        // 4. 复用现有 ToolRuntime 执行工具
        ToolResult result = toolRuntime.execute(toolCall);

        // 5. 将 ToolResult 转换为统一的 Observation
        return new Observation(
                action.getType(),
                result.getContent(),
                true
        );
    }
}