package com.agent.rag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryEmbeddingStore
        implements EmbeddingStore {

    private final List<EmbeddingRecord> records =
            new ArrayList<>();

    @Override
    public void add(
            Embedding embedding,
            TextSegment segment) {

        records.add(
                new EmbeddingRecord(
                        embedding,
                        segment
                )
        );
    }

    @Override
    public List<EmbeddingRecord> getAll() {

        return Collections.unmodifiableList(records);
    }
}