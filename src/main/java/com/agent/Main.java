package com.agent;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        // 1. 创建 Tool
        CalculatorTool calculatorTool =
                new CalculatorTool();

        // 2. 创建 ToolRegistry
        ToolRegistry registry =
                new ToolRegistry();

        // 3. 注册 Tool
        registry.register(calculatorTool);

        // 4. 创建 ToolDefinitionFactory
        ToolDefinitionFactory factory =
                new ToolDefinitionFactory();

        // 5. 创建所有 ToolDefinition
        List<ToolDefinition> tools =
                factory.createAll(registry);

        // 6. 创建 LLMClient
        LLMClient llmClient =
                new LLMClient();

        // 7. 创建 Agent
        Agent agent =
                new Agent(
                        llmClient,
                        registry,
                        tools
                );

        // 8. 让 Agent 执行任务
        String answer =
                agent.run(
                        "请分别计算以下两个问题：123乘以456，以及100加200。"
                );

        // 9. 输出最终答案
        System.out.println(
                "AI最终回答: "
                        + answer
        );
    }
}