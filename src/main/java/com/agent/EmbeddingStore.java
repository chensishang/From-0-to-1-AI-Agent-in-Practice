package com.agent;

import java.util.List;

public interface EmbeddingStore {

    void add(Embedding embedding, TextSegment segment);

    List<EmbeddingRecord> getAll();
}