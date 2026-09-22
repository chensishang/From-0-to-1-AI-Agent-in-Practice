package com.agent.memory;

import com.agent.Context;
import com.agent.Message;
import com.agent.MessageRole;

import java.util.List;

public class MemoryInjector {

    public void inject(
            Context context,
            List<MemoryEntry> memories) {

        if (memories == null || memories.isEmpty()) {
            return;
        }

        StringBuilder memoryText =
                new StringBuilder();

        memoryText.append(
                "以下是用户过去的相关记忆：\n"
        );

        for (MemoryEntry memory : memories) {
            memoryText.append("- ")
                    .append(memory.getContent())
                    .append("\n");
        }

        context.addMessage(
                new Message(
                        MessageRole.SYSTEM,
                        memoryText.toString()
                )
        );
    }
}