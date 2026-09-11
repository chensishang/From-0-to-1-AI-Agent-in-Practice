package com.agent;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class CalculatorTool implements Tool {

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    @Override
    public String getName() {
        return "calculator";
    }

    @Override
    public String getDescription() {
        return "执行数学计算";
    }

    @Override
    public Object execute(String arguments) {

        try {

            Map<String, Object> params =
                    objectMapper.readValue(
                            arguments,
                            new TypeReference<Map<String, Object>>() {}
                    );

            double a =
                    ((Number) params.get("a")).doubleValue();

            double b =
                    ((Number) params.get("b")).doubleValue();

            String operation =
                    (String) params.get("operation");

            return calculate(
                    a,
                    b,
                    operation
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "计算器工具执行失败",
                    e
            );
        }
    }

    public double calculate(
            double a,
            double b,
            String operation
    ) {

        return switch (operation) {

            case "add" ->
                    a + b;

            case "subtract" ->
                    a - b;

            case "multiply" ->
                    a * b;

            case "divide" -> {

                if (b == 0) {
                    throw new IllegalArgumentException(
                            "除数不能为 0"
                    );
                }

                yield a / b;
            }

            default ->
                    throw new IllegalArgumentException(
                            "不支持的操作: " + operation
                    );
        };
    }
}