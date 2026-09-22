package com.agent.memory;

public class SimpleMemoryPolicy
        implements MemoryPolicy {

    @Override
    public MemoryType getType(String key) {

        if ("hobby".equals(key)) {
            return MemoryType.MULTI_VALUE;
        }

        return MemoryType.SINGLE_VALUE;
    }
}