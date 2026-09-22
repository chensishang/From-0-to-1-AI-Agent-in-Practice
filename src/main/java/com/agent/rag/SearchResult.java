package com.agent.rag;

public class SearchResult {

    private final TextSegment segment;

    private final double score;

    public SearchResult(
            TextSegment segment,
            double score) {

        this.segment = segment;
        this.score = score;
    }

    public TextSegment getSegment() {
        return segment;
    }

    public double getScore() {
        return score;
    }
}