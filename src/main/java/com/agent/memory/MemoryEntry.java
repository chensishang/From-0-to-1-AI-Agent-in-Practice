package com.agent.memory;

public class MemoryEntry {

    private final String key;

    private final String value;

    private final String content;


    public MemoryEntry(
            String key,
            String value
    ) {

        this.key = key;
        this.value = value;

        this.content =
                key + "：" + value;
    }


    public MemoryEntry(String content) {

        this.key = null;
        this.value = null;
        this.content = content;
    }


    public String getKey() {
        return key;
    }


    public String getValue() {
        return value;
    }


    public String getContent() {
        return content;
    }


    @Override
    public String toString() {

        return "MemoryEntry{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}