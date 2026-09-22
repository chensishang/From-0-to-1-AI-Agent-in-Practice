package com.agent.memory;

import java.util.List;

public interface Memory {

    void save(MemoryEntry entry);

    List<MemoryEntry> retrieve(String query);

    List<MemoryEntry> retrieveByKey(String key);
}