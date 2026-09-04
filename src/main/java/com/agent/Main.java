package com.agent;

public class Main {

    public static void main(String[] args) {

        LLMClient client = new LLMClient();

        String response = client.chat(
                "你好，请用简单的语言介绍一下什么是 AI Agent？"
        );

        System.out.println("DeepSeek:");
        System.out.println(response);
    }
}