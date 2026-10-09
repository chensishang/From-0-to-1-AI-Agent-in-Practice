
package com.agent;

public class Action {

    private final String type;
    private final String target;
    private final String input;

    public Action(String type, String target, String input) {
        this.type = type;
        this.target = target;
        this.input = input;
    }

    public String getType() {
        return type;
    }

    public String getTarget() {
        return target;
    }

    public String getInput() {
        return input;
    }
}