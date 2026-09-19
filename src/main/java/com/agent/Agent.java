package com.agent;

import java.util.List;

public class Agent {

    private static final int MAX_ITERATIONS = 10;

    private final LLMClient llmClient;

    private final ToolRegistry toolRegistry;

    private final List<ToolDefinition> toolDefinitions;
    private final Retriever retriever;

    private final KnowledgeInjector knowledgeInjector;

    public Agent(
            LLMClient llmClient,
            ToolRegistry toolRegistry,
            List<ToolDefinition> toolDefinitions,
            Retriever retriever,
            KnowledgeInjector knowledgeInjector
    ) {
        this.llmClient = llmClient;
        this.toolRegistry = toolRegistry;
        this.toolDefinitions = toolDefinitions;
        this.retriever = retriever;
        this.knowledgeInjector = knowledgeInjector;
    }

    public String run(String userInput) {

        Context context = new Context();

        context.addMessage(
                Message.user(userInput)
        );

        // 1. 检索知识库
        List<SearchResult> results =
                retriever.retrieve(
                        userInput,
                        2,
                        0.5
                );
        System.out.println(
                "\n===== RAG 检索 ====="
        );

        for (SearchResult result : results) {

            System.out.println(
                    "Score：" + result.getScore()
            );

            System.out.println(
                    "Text：" +
                            result.getSegment().getText()
            );
        }

        // 2. 没有检索到足够相关的知识
        if (results == null || results.isEmpty()) {
            System.out.println(
                    "RAG：没有找到满足 threshold 的知识"
            );

            return "知识库中没有找到与该问题相关的信息。";
        }
        //有结果时
        System.out.println(
                "RAG：检索到 "
                        + results.size()
                        + " 条相关知识"
        );

        // 3. 将知识注入 Context
        knowledgeInjector.inject(
                context,
                results
        );

        // 4. Agent Loop
        for (int i = 0; i < MAX_ITERATIONS; i++) {

            System.out.println(
                    "\n===== Agent Loop 第 "
                            + (i + 1)
                            + " 轮 ====="
            );

            System.out.println("调用 LLM...");

            ChatResponse response =
                    llmClient.chat(
                            context,
                            toolDefinitions
                    );

            Message assistantMessage =
                    response
                            .getChoices()
                            .get(0)
                            .getMessage();

            context.addMessage(
                    assistantMessage
            );

            if (assistantMessage.getToolCalls() == null
                    || assistantMessage.getToolCalls().isEmpty()) {

                return assistantMessage.getContent();
            }

            ToolRuntime runtime =
                    new ToolRuntime(toolRegistry);

            for (ToolCall toolCall :
                    assistantMessage.getToolCalls()) {

                System.out.println(
                        "执行 Tool："
                                + toolCall.getFunction().getName()
                );

                ToolResult result =
                        runtime.execute(toolCall);

                System.out.println(
                        "ToolResult："
                                + result.getContent()
                );

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