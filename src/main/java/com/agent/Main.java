package com.agent;

public class Main {

    public static void main(String[] args) {

        Context context = new Context();

        // 第一轮
        context.addMessage(
                Message.system("你是一个 Java 后端专家")
        );

        context.addMessage(
                Message.user("什么是 Redis？")
        );

        LLMClient client = new LLMClient();

        String answer = client.chat(context);

        System.out.println("AI:");
        System.out.println(answer);

        // 把 AI 的回答放回 Context
        context.addMessage(
                Message.assistant(answer)
        );

        // 第二轮
        context.addMessage(
                Message.user("那它为什么这么快？")
        );

        answer = client.chat(context);

        System.out.println("\nAI:");
        System.out.println(answer);
    }
}