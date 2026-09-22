package com.agent.rag;

import java.util.ArrayList;
import java.util.List;

public class KeywordEmbeddingModel implements EmbeddingModel {

    @Override
    public Embedding embed(String text) {

        List<Double> vector = new ArrayList<>();

        vector.add(text.contains("Redis") ? 1.0 : 0.0);
        vector.add(text.contains("MySQL") ? 1.0 : 0.0);
        vector.add(text.contains("Java") ? 1.0 : 0.0);
        vector.add(text.contains("Agent") ? 1.0 : 0.0);

        return new Embedding(vector);
    }
}