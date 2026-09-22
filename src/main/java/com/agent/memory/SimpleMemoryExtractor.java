package com.agent.memory;

import java.util.ArrayList;
import java.util.List;

public class SimpleMemoryExtractor
        implements MemoryExtractor {

    @Override
    public List<MemoryEntry> extract(String content) {

        List<MemoryEntry> memories =
                new ArrayList<>();

        if (content == null ||
                content.isBlank()) {

            return memories;
        }


        // =========================
        // 1. 用户姓名
        // =========================

        if (content.startsWith("我叫")
                && !content.startsWith("我叫什么")) {

            String name =
                    content.substring(2).trim();

            if (!name.isEmpty()) {

                memories.add(
                        new MemoryEntry(
                                "name",
                                name
                        )
                );
            }
        }


        // =========================
        // 2. 用户爱好
        // =========================

        if (content.startsWith("我喜欢")
                && !content.startsWith("我喜欢什么")) {

            String hobby =
                    content.substring(3).trim();

            if (!hobby.isEmpty()) {

                memories.add(
                        new MemoryEntry(
                                "hobby",
                                hobby
                        )
                );
            }
        }


        return memories;
    }
}