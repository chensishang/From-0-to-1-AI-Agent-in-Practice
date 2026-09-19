package com.agent;

import java.util.List;

public class KnowledgeInjector {

    public void inject(
            Context context,
            List<SearchResult> results) {

        if (results == null || results.isEmpty()) {
            return;
        }

        StringBuilder knowledge =
                new StringBuilder();

        knowledge.append(
                "以下是知识库检索到的相关知识：\n"
        );

        for (SearchResult result : results) {

            knowledge.append("- ")
                    .append(result.getSegment().getText())
                    .append("\n");
        }

        context.addMessage(
                new Message(
                        MessageRole.SYSTEM,
                        knowledge.toString()
                )
        );
    }
}