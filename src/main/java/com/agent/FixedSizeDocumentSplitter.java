package com.agent;

import java.util.ArrayList;
import java.util.List;

public class FixedSizeDocumentSplitter implements DocumentSplitter {

    private final int chunkSize;

    public FixedSizeDocumentSplitter(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    @Override
    public List<TextSegment> split(Document document) {

        List<TextSegment> segments = new ArrayList<>();

        String content = document.getContent();

        int start = 0;

        while (start < content.length()) {

            int end = Math.min(start + chunkSize, content.length());

            String text = content.substring(start, end);

            TextSegment segment = new TextSegment(
                    text,
                    document.getMetadata()
            );

            segments.add(segment);

            start = end;
        }

        return segments;
    }
}