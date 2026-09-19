package com.agent;

import java.util.Map;

public class Document {

    private final String content;

    private final Map<String, Object> metadata;

    public Document(String content, Map<String, Object> metadata) {
        this.content = content;
        this.metadata = metadata;
    }

    public String getContent() {
        return content;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }
}