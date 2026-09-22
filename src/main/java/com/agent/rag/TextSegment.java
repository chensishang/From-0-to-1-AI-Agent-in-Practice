package com.agent.rag;

import java.util.Map;

public class TextSegment {

    private final String text;

    private final Map<String, Object> metadata;

    public TextSegment(String text, Map<String, Object> metadata) {
        this.text = text;
        this.metadata = metadata;
    }

    public String getText() {
        return text;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }
}