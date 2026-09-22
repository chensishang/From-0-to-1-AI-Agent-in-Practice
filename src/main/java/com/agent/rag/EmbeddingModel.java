package com.agent.rag;

public interface EmbeddingModel {

    Embedding embed(String text);
}
