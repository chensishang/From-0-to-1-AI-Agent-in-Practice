package com.agent.rag;

import java.util.List;

public interface EmbeddingStore {

    void add(Embedding embedding, TextSegment segment);

    List<EmbeddingRecord> getAll();
}