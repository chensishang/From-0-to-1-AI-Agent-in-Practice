package com.agent;

public class SimpleDecisionMaker {

    public AgentDecision decide(String userInput) {

        if (userInput == null || userInput.isBlank()) {
            return new AgentDecision(DecisionType.DIRECT);
        }

        // Memory
        if (userInput.contains("我叫什么")
                || userInput.contains("我喜欢什么")) {

            return new AgentDecision(
                    DecisionType.MEMORY
            );
        }

        // Tool
        if (userInput.contains("计算")
                || userInput.contains("乘")
                || userInput.contains("加")
                || userInput.contains("减")
                || userInput.contains("除")
                || userInput.contains("*")
                || userInput.contains("+")
                || userInput.contains("-")
                || userInput.contains("/")) {

            return new AgentDecision(
                    DecisionType.TOOL
            );
        }

        // RAG
        if (userInput.contains("Redis")
                || userInput.contains("MySQL")
                || userInput.contains("Java")
                || userInput.contains("是什么")
                || userInput.contains("怎么回事")) {

            return new AgentDecision(
                    DecisionType.RAG
            );
        }

        // Direct
        return new AgentDecision(
                DecisionType.DIRECT
        );
    }
}