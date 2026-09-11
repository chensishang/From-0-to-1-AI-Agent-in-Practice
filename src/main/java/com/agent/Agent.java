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

            // 5. 执行 ToolCall
            for (ToolCall toolCall :
                    assistantMessage.getToolCalls()) {

                String toolName =
                        toolCall
                                .getFunction()
                                .getName();

                String arguments =
                        toolCall
                                .getFunction()
                                .getArguments();

                // 6. 根据工具名称找到真正的 Java Tool
                Tool tool =
                        toolRegistry.getTool(
                                toolName
                        );

                if (tool == null) {

                    throw new IllegalStateException(
                            "未找到工具: " + toolName
                    );
                }

                // 7. 执行工具
                Object result =
                        tool.execute(arguments);

                // 8. 将工具结果放回 Context
                context.addMessage(
                        Message.tool(
                                toolCall.getId(),
                                String.valueOf(result)
                        )
                );
            }
        }

        throw new IllegalStateException(
                "Agent 执行超过最大循环次数"
        );
    }
}