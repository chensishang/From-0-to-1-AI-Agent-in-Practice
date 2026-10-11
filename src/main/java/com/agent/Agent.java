package com.agent;

import com.agent.memory.Memory;
import com.agent.memory.MemoryEntry;
import com.agent.memory.MemoryInjector;
import com.agent.memory.MemoryWriter;
import com.agent.rag.KnowledgeInjector;
import com.agent.rag.Retriever;
import com.agent.rag.SearchResult;

import java.util.List;
import com.agent.memory.MemoryRetriever;

public class Agent {

    private static final int MAX_ITERATIONS = 10;

    private final LLMClient llmClient;

    private final ToolRegistry toolRegistry;

    private final List<ToolDefinition> toolDefinitions;
    private final Retriever retriever;

    private final KnowledgeInjector knowledgeInjector;

    private final MemoryInjector memoryInjector;
    private final MemoryWriter memoryWriter;
    private final MemoryRetriever memoryRetriever;
    private final SimpleDecisionMaker decisionMaker;
    private final Memory memory;
    private final ActionExecutor actionExecutor;


    public Agent(
            LLMClient llmClient,
            ToolRegistry toolRegistry,
            List<ToolDefinition> toolDefinitions,
            Retriever retriever,
            KnowledgeInjector knowledgeInjector,
            Memory memory,
            MemoryInjector memoryInjector,
            MemoryWriter memoryWriter,
            MemoryRetriever memoryRetriever,
            SimpleDecisionMaker decisionMaker,
            ActionExecutor actionExecutor

    ) {
        this.llmClient = llmClient;
        this.toolRegistry = toolRegistry;
        this.toolDefinitions = toolDefinitions;
        this.retriever = retriever;
        this.knowledgeInjector = knowledgeInjector;
        this.memory = memory;
        this.memoryInjector = memoryInjector;
        this.memoryWriter=memoryWriter;
        this.memoryRetriever = memoryRetriever;
        this.decisionMaker = decisionMaker;
        this.actionExecutor = actionExecutor;

    }

    public String run(String userInput) {
        AgentDecision decision =
                decisionMaker.decide(userInput);

        System.out.println(
                "\n===== Agent Decision ====="
        );

        System.out.println(
                "Decision：" + decision.getType()
        );

        Context context = new Context();

        context.addMessage(
                Message.user(userInput)
        );
        //根据决策来判断要不要记忆机制
        // =========================
        if (decision.getType() == DecisionType.MEMORY) {

            System.out.println("\n===== Memory 检索 =====");

            List<MemoryEntry> memories =
                    memoryRetriever.retrieve(userInput);

            for (MemoryEntry memoryEntry : memories) {
                System.out.println(
                        memoryEntry.getKey()
                                + " → "
                                + memoryEntry.getValue()
                );
            }

            memoryInjector.inject(
                    context,
                    memories
            );
        }

        // 根据判断来决定要不要进行RAG
        if (decision.getType() == DecisionType.RAG) {

            List<SearchResult> results =
                    retriever.retrieve(
                            userInput,
                            2,
                            0.5
                    );

            System.out.println("\n===== RAG 检索 =====");

            for (SearchResult result : results) {

                System.out.println(
                        "Score：" + result.getScore()
                );

                System.out.println(
                        "Text：" +
                                result.getSegment().getText()
                );
            }

            if (results == null || results.isEmpty()) {

                System.out.println(
                        "RAG：没有找到满足 threshold 的知识"
                );

            } else {

                System.out.println(
                        "RAG：检索到 "
                                + results.size()
                                + " 条相关知识"
                );

                knowledgeInjector.inject(
                        context,
                        results
                );
            }
        }

        // 4. Agent Loop
        for (int i = 0; i < MAX_ITERATIONS; i++) {

            System.out.println(
                    "\n===== Agent Loop 第 "
                            + (i + 1)
                            + " 轮 ====="
            );

            System.out.println("调用 LLM...");
            //调试

            System.out.println("本轮调用 LLM 前的 Context：");

            List<Message> messages = context.getMessages();

            for (int j = 0; j < messages.size(); j++) {
                Message message = messages.get(j);

                System.out.println(
                        "[" + j + "] role=" + message.getRole()
                );

                if (message.getContent() != null) {
                    System.out.println("    content=" + message.getContent());
                }

                if (message.getToolCalls() != null
                        && !message.getToolCalls().isEmpty()) {
                    for (ToolCall toolCall : message.getToolCalls()) {
                        System.out.println(
                                "    toolCall="
                                        + toolCall.getFunction().getName()
                                        + ", arguments="
                                        + toolCall.getFunction().getArguments()
                        );
                    }
                }

                if (message.getToolCallId() != null) {
                    System.out.println(
                            "    toolCallId=" + message.getToolCallId()
                    );
                }
            }
            //调试

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


                System.out.println(
                        "准备写入Memory..."
                );


                memoryWriter.write(
                        userInput
                );


                return assistantMessage.getContent();
            }


            for (ToolCall toolCall : assistantMessage.getToolCalls()) {

                System.out.println(
                        "执行 Action："
                                + toolCall.getFunction().getName()
                );

                // 1. 将 LLM 返回的 ToolCall 转换为 Action
                Action action = new Action(
                        "TOOL",
                        toolCall.getFunction().getName(),
                        toolCall.getFunction().getArguments()
                );

                // 2. 交给 ActionExecutor 执行动作
                Observation observation = actionExecutor.execute(action);

                System.out.println(
                        "Action 是否成功：" + observation.isSuccess()
                );

                System.out.println(
                        "Observation：" + observation.getContent()
                );

                // 3. 将执行结果作为 Tool 消息放回 Context
                context.addMessage(
                        Message.tool(
                                toolCall.getId(),
                                observation.getContent()
                        )
                );
            }

        }

        throw new IllegalStateException(
                "Agent 执行超过最大循环次数"
        );
    }
}