package com.agent.memory;

import java.util.List;

public interface MemoryExtractor {

    List<MemoryEntry> extract(String text);

}