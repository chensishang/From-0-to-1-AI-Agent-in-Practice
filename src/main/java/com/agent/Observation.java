
package com.agent;

public class Observation {

    private final String actionType;

    private final String content;

    private final boolean success;

    public Observation(
            String actionType,
            String content,
            boolean success
    ) {
        this.actionType = actionType;
        this.content = content;
        this.success = success;
    }

    public String getActionType() {
        return actionType;
    }

    public String getContent() {
        return content;
    }

    public boolean isSuccess() {
        return success;
    }
}