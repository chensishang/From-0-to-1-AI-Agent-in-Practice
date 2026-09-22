package com.agent.memory;

import java.util.ArrayList;
import java.util.List;

public class InMemoryMemory implements Memory {

    private final List<MemoryEntry> memories = new ArrayList<>();
    private final MemoryPolicy policy;

    public InMemoryMemory(
            MemoryPolicy policy
    ) {

        this.policy = policy;
    }

    @Override
    public void save(MemoryEntry entry) {

        // 普通文本 Memory
        if (entry.getKey() == null) {

            memories.add(entry);

            return;
        }


        MemoryType type =
                policy.getType(entry.getKey());


        // =========================
        // 单值 Memory
        // =========================

        if (type == MemoryType.SINGLE_VALUE) {

            for (int i = 0;
                 i < memories.size();
                 i++) {

                MemoryEntry oldEntry =
                        memories.get(i);

                if (entry.getKey().equals(
                        oldEntry.getKey()
                )) {

                    memories.set(i, entry);

                    return;
                }
            }

            memories.add(entry);

            return;
        }


        // =========================
        // 多值 Memory
        // =========================

        memories.add(entry);
    }

    @Override
    public List<MemoryEntry> retrieve(String query) {

        if (query == null || query.isBlank()) {
            return List.of();
        }

        List<MemoryEntry> results = new ArrayList<>();

        for (MemoryEntry memory : memories) {

            if (memory.getContent().contains(query)) {
                results.add(memory);
            }
        }

        return List.copyOf(results);
    }

    @Override
    public List<MemoryEntry> retrieveByKey(String key) {

        return memories.stream()
                .filter(entry ->
                        key.equals(entry.getKey()))
                .toList();
    }
}