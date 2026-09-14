package com.agent;

import java.util.List;

public class Agent {

    private static final int MAX_ITERATIONS = 10;

    private final LLMClient llmClient;

    private final ToolRegistry toolRegistry;

    private final List<ToolDefinition> toolDefinitions;

    public Agent(
            LLMClient llmClient,
            ToolRegistry toolRegistry,
            List<ToolDefinition> toolDefinitions
    ) {
        this.llmClient = llmClient;
        this.toolRegistry = toolRegistry;
        this.toolDefinitions = toolDefinitions;
    }

    public String run(String userInput) {

        Context context = new Context();

        context.addMessage(
                Message.user(userInput)
        );

        for (int i = 0; i < MAX_ITERATIONS; i++) {

            // 1. 调用 LLM
            ChatResponse response =
                    llmClient.chat(
                            context,
                            toolDefinitions
                    );

            // 2. 获取 Assistant Message
            Message assistantMessage =
                    response
                            .getChoices()
                            .get(0)
                            .getMessage();

            // 3. 保存 Assistant Message
            context.addMessage(
                    assistantMessage
            );

            // 4. 如果没有 ToolCall
            //    说明 LLM 已经可以直接回答
            if (assistantMessage.getToolCalls() == null
                    || assistantMessage.getToolCalls().isEmpty()) {

                return assistantMessage.getContent();
            }

            // 5. 创建 ToolRuntime
            ToolRuntime runtime =
                    new ToolRuntime(toolRegistry);

            // 6. 执行所有 ToolCall
            for (ToolCall toolCall :
                    assistantMessage.getToolCalls()) {

                ToolResult result =
                        runtime.execute(toolCall);

                // 7. 将工具结果放回 Context
                context.addMessage(
                        Message.tool(
                                result.getToolCallId(),
                                result.getContent()
                        )
                );
            }
        }

        throw new IllegalStateException(
                "Agent 执行超过最大循环次数"
        );
    }
}