package com.agent;

import com.agent.memory.*;
import com.agent.rag.Document;
import com.agent.rag.DocumentSplitter;
import com.agent.rag.Embedding;
import com.agent.rag.EmbeddingModel;
import com.agent.rag.EmbeddingStore;
import com.agent.rag.FixedSizeDocumentSplitter;
import com.agent.rag.InMemoryEmbeddingStore;
import com.agent.rag.InMemoryRetriever;
import com.agent.rag.KnowledgeInjector;
import com.agent.rag.KeywordEmbeddingModel;
import com.agent.rag.Retriever;
import com.agent.rag.TextSegment;

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



        // =========================
        // 7.5 创建 Memory
        // =========================

        MemoryPolicy policy =
                new SimpleMemoryPolicy();

        Memory memory =
                new InMemoryMemory(policy);
        //放入一点记忆



        MemoryInjector memoryInjector =
                new MemoryInjector();

        MemoryExtractor extractor =
                new SimpleMemoryExtractor();


        MemoryWriter memoryWriter =
                new MemoryWriter(
                        memory,
                        extractor
                );

        MemoryQueryAnalyzer analyzer =
                new SimpleMemoryQueryAnalyzer();

        MemoryRetriever memoryRetriever =
                new MemoryRetriever(
                        memory,
                        analyzer
                );

        // =========================
        // 8. 创建 LLMClient
        // =========================

        LLMClient llmClient =
                new LLMClient();



        SimpleDecisionMaker decisionMaker =
                new SimpleDecisionMaker();

        //
        ToolRuntime toolRuntime =
                new ToolRuntime(registry);

        ActionExecutor  actionExecutor =
                new CalculatorActionExecutor(toolRuntime);
        // =========================
        // 9. 创建 Agent
        // =========================

        Agent agent =
                new Agent(
                        llmClient,
                        registry,
                        tools,
                        retriever,
                        injector,
                        memory,
                        memoryInjector,
                        memoryWriter,
                        memoryRetriever,
                        decisionMaker,
                        actionExecutor


                );


// 10. Agent 执行任务
// =========================
        System.out.println(
                "\n========== 第一次对话 =========="
        );

        String answer1 =
                agent.run("我叫小明");

        System.out.println(
                "AI：" + answer1
        );


        System.out.println(
                "\n========== 第二次对话 =========="
        );

        String answer2 =
                agent.run("我喜欢摄影");

        System.out.println(
                "AI：" + answer2
        );


        System.out.println(
                "\n========== 第三次对话 =========="
        );

        String answer3 =
                agent.run("我喜欢打羽毛球");

        System.out.println(
                "AI：" + answer3
        );


        System.out.println(
                "\n========== 第四次对话：查询姓名 =========="
        );

        String answer4 =
                agent.run("我叫什么？");

        System.out.println(
                "AI：" + answer4
        );


        System.out.println(
                "\n========== 第五次对话：查询爱好 =========="
        );

        String answer5 =
                agent.run("我喜欢什么？");

        System.out.println(
                "AI：" + answer5
        );

        System.out.println(
                "\n========== 第七次对话：RAG =========="
        );

        String answer7 =
                agent.run("Redis 是什么？");

        System.out.println(
                "AI：" + answer7
        );



        // =========================
        // 阶段 09：Action → Observation 测试
        // =========================

        System.out.println(
                "\n========== 阶段 09：Action 执行测试 =========="
        );



        Action action = new Action(
                "TOOL",
                "calculator",
                "{\"a\":123,\"b\":456,\"operation\":\"multiply\"}"
        );

        Observation observation =
                actionExecutor.execute(action);

        System.out.println(
                "执行是否成功：" + observation.isSuccess()
        );

        System.out.println(
                "动作类型：" + observation.getActionType()
        );

        System.out.println(
                "执行结果：" + observation.getContent()
        );

        System.out.println(
                "\n========== 阶段 10：连续工具调用测试 =========="
        );

        String answer8 = agent.run(
                "请先计算123乘以456，然后把第一次计算的结果再加上200，最后告诉我两次计算的过程和最终结果。"
        );

        System.out.println("AI：" + answer8);

        System.out.println(
                "\n========== 阶段 10：停止条件测试 =========="
        );

        String answer9 = agent.run("你好，请简单介绍一下你自己。");

        System.out.println("AI：" + answer9);

    }
}