package com.agent.rag;

import java.util.List;

public class Embedding {
    private final List<Double> vector;

    public Embedding(List<Double> vector) {
        this.vector = vector;
    }

    public List<Double> getVector() {
        return vector;
    }
}
