package com.agent;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        // 1. 创建 CalculatorTool
        CalculatorTool calculatorTool =
                new CalculatorTool();

        // 2. 创建 ToolRegistry
        ToolRegistry toolRegistry =
                new ToolRegistry();

        toolRegistry.register(
                calculatorTool
        );

        // 3. 定义 calculator 的参数 Schema
        Map<String, Object> parameters = Map.of(

                "type", "object",

                "properties", Map.of(

                        "a", Map.of(
                                "type", "number",
                                "description", "第一个数字"
                        ),

                        "b", Map.of(
                                "type", "number",
                                "description", "第二个数字"
                        ),

                        "operation", Map.of(
                                "type", "string",
                                "description", "数学运算类型",
                                "enum", new String[]{
                                        "add",
                                        "subtract",
                                        "multiply",
                                        "divide"
                                }
                        )
                ),

                "required", new String[]{
                        "a",
                        "b",
                        "operation"
                }
        );

        // 4. 创建 FunctionDefinition
        ToolDefinition.FunctionDefinition function =
                new ToolDefinition.FunctionDefinition(
                        "calculator",
                        "执行数学计算",
                        parameters
                );

        // 5. 创建 ToolDefinition
        ToolDefinition calculatorDefinition =
                new ToolDefinition(
                        "function",
                        function
                );

        // 6. 创建 LLMClient
        LLMClient llmClient =
                new LLMClient();

        // 7. 创建 Agent
        Agent agent =
                new Agent(
                        llmClient,
                        toolRegistry,
                        List.of(calculatorDefinition)
                );

        // 8. 让 Agent 执行任务
        String answer =
                agent.run(
                        "123 × 456 等于多少？"
                );

        // 9. 输出结果
        System.out.println("Agent:");
        System.out.println(answer);
    }
}