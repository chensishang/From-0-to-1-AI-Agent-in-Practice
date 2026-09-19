package com.agent;

public class EmbeddingRecord {

    private final Embedding embedding;

    private final TextSegment segment;

    public EmbeddingRecord(
            Embedding embedding,
            TextSegment segment) {

        this.embedding = embedding;
        this.segment = segment;
    }

    public Embedding getEmbedding() {
        return embedding;
    }

    public TextSegment getSegment() {
        return segment;
    }
}