package com.agent;

public interface Tool {

    String getName();

    String getDescription();

    Object execute(
            String arguments
    );
}