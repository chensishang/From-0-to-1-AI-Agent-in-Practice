package com.agent.memory;


import java.util.List;


public class MemoryWriter {


    private final Memory memory;

    private final MemoryExtractor extractor;


    public MemoryWriter(
            Memory memory,
            MemoryExtractor extractor
    ){

        this.memory = memory;
        this.extractor = extractor;

    }



    public void write(String content){


        List<MemoryEntry> entries =
                extractor.extract(content);



        for(MemoryEntry entry : entries){


            System.out.println(
                    "保存Memory:"
                            + entry.getContent()
            );


            memory.save(entry);

        }

    }
}