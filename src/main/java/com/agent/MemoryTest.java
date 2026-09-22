package com.agent;

import com.agent.memory.*;

import java.util.List;

public class MemoryTest {

    public static void main(String[] args) {

        MemoryPolicy policy =
                new SimpleMemoryPolicy();

        Memory memory =
                new InMemoryMemory(policy);

        memory.save(
                new MemoryEntry(
                        "name",
                        "小明"
                )
        );

     memory.save(new MemoryEntry(
             "name",
             "小李"
     ));

        memory.save(
                new MemoryEntry(
                        "hobby",
                        "摄影"
                )
        );

        memory.save(
                new MemoryEntry(
                        "hobby",
                        "羽毛球"
                )
        );


        System.out.println(
                "===== name ====="
        );

        for (MemoryEntry entry :
                memory.retrieveByKey("name")) {

            System.out.println(
                    entry.getKey()
                            + " → "
                            + entry.getValue()
            );
        }


        System.out.println(
                "\n===== hobby ====="
        );

        for (MemoryEntry entry :
                memory.retrieveByKey("hobby")) {

            System.out.println(
                    entry.getKey()
                            + " → "
                            + entry.getValue()
            );
        }
    }
}