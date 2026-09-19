package com.agent;

import java.util.List;

public interface Retriever {

    List<SearchResult> retrieve(
            String query,
            int topK,
            double threshold
    );
}