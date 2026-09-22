package com.agent.memory;

import java.util.List;

public class MemoryRetriever {

    private final Memory memory;

    private final MemoryQueryAnalyzer analyzer;


    public MemoryRetriever(
            Memory memory,
            MemoryQueryAnalyzer analyzer
    ) {

        this.memory = memory;
        this.analyzer = analyzer;
    }


    public List<MemoryEntry> retrieve(
            String query
    ) {

        String key =
                analyzer.analyze(query);


        if (key == null) {

            return List.of();
        }


        return memory.retrieveByKey(key);
    }
}