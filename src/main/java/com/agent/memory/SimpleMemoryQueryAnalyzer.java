package com.agent.memory;

public class SimpleMemoryQueryAnalyzer
        implements MemoryQueryAnalyzer {

    @Override
    public String analyze(String query) {

        if (query == null ||
                query.isBlank()) {

            return null;
        }

        // 姓名
        if (query.contains("我叫")
                || query.contains("名字")
                || query.contains("姓名")) {

            return "name";
        }

        // 职业
        if (query.contains("职业")
                || query.contains("工作")
                || query.contains("做什么")) {

            return "job";
        }

        // 爱好
        if (query.contains("喜欢")
                || query.contains("爱好")) {

            return "hobby";
        }

        return null;
    }
}