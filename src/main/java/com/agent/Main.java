package com.agent;

public class Main {

    public static void main(String[] args) {

        Context context = new Context();

        context.addMessage(
                Message.system("你是一个 Java 后端专家")
        );

        context.addMessage(
                Message.user("什么是 Redis？")
        );

        context.addMessage(
                Message.assistant("Redis 是一个高性能的内存数据存储系统。")
        );

        context.addMessage(
                Message.user("那它为什么这么快？")
        );

        for (Message message : context.getMessages()) {

            System.out.println(
                    message.getRole()
                            + ": "
                            + message.getContent()
            );
        }
    }
}