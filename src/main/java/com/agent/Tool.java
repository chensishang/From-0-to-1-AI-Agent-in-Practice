package com.agent;

import java.util.Map;

public interface Tool {

    String getName();

    String getDescription();

    Map<String, Object> getParameters();

    Object execute(
            String arguments
    );
}