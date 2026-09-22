package com.agent.rag;

import java.util.List;

public interface DocumentSplitter {

    List<TextSegment> split(Document document);
}