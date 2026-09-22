package com.agent.rag;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRetriever implements Retriever {

    private final EmbeddingModel embeddingModel;

    private final EmbeddingStore embeddingStore;

    public InMemoryRetriever(
            EmbeddingModel embeddingModel,
            EmbeddingStore embeddingStore) {

        this.embeddingModel = embeddingModel;
        this.embeddingStore = embeddingStore;
    }


    @Override
    public List<SearchResult> retrieve(
            String query,
            int topK,
            double threshold) {

        if (topK <= 0) {
            return List.of();
        }

        Embedding queryEmbedding =
                embeddingModel.embed(query);

        List<SearchResult> results =
                new ArrayList<>();

        for (EmbeddingRecord record :
                embeddingStore.getAll()) {

            double similarity =
                    CosineSimilarity.calculate(
                            queryEmbedding.getVector(),
                            record.getEmbedding().getVector()
                    );

            results.add(
                    new SearchResult(
                            record.getSegment(),
                            similarity
                    )
            );
        }

        results.sort(
                (a, b) ->
                        Double.compare(
                                b.getScore(),
                                a.getScore()
                        )
        );
        results.removeIf(
                result -> result.getScore() < threshold
        );

        return results.subList(
                0,
                Math.min(topK, results.size())
        );
    }
}