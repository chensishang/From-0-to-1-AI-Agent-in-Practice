package com.agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {

        // =========================
        // 1. 创建 Tool
        // =========================

        CalculatorTool calculatorTool =
                new CalculatorTool();

        ToolRegistry registry =
                new ToolRegistry();

        registry.register(calculatorTool);

        ToolDefinitionFactory factory =
                new ToolDefinitionFactory();

        List<ToolDefinition> tools =
                factory.createAll(registry);


        // =========================
        // 2. 创建 RAG 基础组件
        // =========================


        // =========================
        // 3. 构建 Document
        // =========================

        Map<String, Object> metadata =
                new HashMap<>();

        metadata.put(
                "source",
                "agent-introduction.txt"
        );

        Document document =
                new Document(
                        "Redis 是一个高性能的内存数据库。" +
                                "Redis 使用内存存储数据，因此访问速度很快。" +
                                "Redis 支持 String、Hash、List、Set、ZSet 等多种数据结构。" +
                                "Redis 常用于缓存、分布式锁、排行榜等场景。",
                        metadata
                );

        // =========================
        // 4. Document → TextSegment
        // =========================

        DocumentSplitter splitter =
              new  FixedSizeDocumentSplitter(30);

        List<TextSegment> segments =
                splitter.split(document);


        EmbeddingModel embeddingModel =
                new KeywordEmbeddingModel();

        EmbeddingStore embeddingStore =
                new InMemoryEmbeddingStore();



        for (TextSegment segment : segments) {

            Embedding embedding =
                    embeddingModel.embed(
                            segment.getText()
                    );

            embeddingStore.add(
                    embedding,
                    segment
            );
        }


        // =========================
        // 5. 构建知识库
        // =========================





        // =========================
        // 6. 创建 Retriever
        // =========================

        Retriever retriever =
                new InMemoryRetriever(
                        embeddingModel,
                        embeddingStore
                );


        // =========================
        // 7. 创建 KnowledgeInjector
        // =========================

        KnowledgeInjector injector =
                new KnowledgeInjector();


        // =========================
        // 8. 创建 LLMClient
        // =========================

        LLMClient llmClient =
                new LLMClient();


        // =========================
        // 9. 创建 Agent
        // =========================

        Agent agent =
                new Agent(
                        llmClient,
                        registry,
                        tools,
                        retriever,
                        injector
                );


        // =========================
        // 10. Agent 执行任务
        // =========================

        String answer =
                agent.run(
                        "Redis 为什么这么快？"

                );


        // =========================
        // 11. 输出最终答案
        // =========================

        System.out.println(
                "\n===== AI最终回答 ====="
        );

        System.out.println(answer);
    }
}