package com.agent;

public class AgentDecision {

    private final DecisionType type;

    public AgentDecision(DecisionType type) {
        this.type = type;
    }

    public DecisionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "AgentDecision{" +
                "type=" + type +
                '}';
    }
}