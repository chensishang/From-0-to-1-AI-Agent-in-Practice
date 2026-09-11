package com.agent;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class LLMClient {

    private static final String API_URL =
            "https://api.deepseek.com/chat/completions";

    private static final String MODEL =
            "deepseek-v4-flash";

    private final HttpClient httpClient =
            HttpClient.newHttpClient();

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    public ChatResponse chat(
            Context context,
            List<ToolDefinition> tools
    ) {

        // 1. Context → ChatRequest
        ChatRequest chatRequest = new ChatRequest(
                MODEL,
                context.getMessages(),
                false,
                tools
        );

        // 2. ChatRequest → JSON
        String requestBody;

        try {

            requestBody =
                    objectMapper.writeValueAsString(chatRequest);

        } catch (Exception e) {

            throw new RuntimeException(
                    "构造请求 JSON 失败",
                    e
            );
        }

        // 3. 获取 API Key
        String apiKey = System.getenv("DEEPSEEK_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "请配置环境变量 DEEPSEEK_API_KEY"
            );
        }

        // 4. 构造 HTTP 请求
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(
                        HttpRequest.BodyPublishers.ofString(requestBody)
                )
                .build();

        // 5. 发送 HTTP 请求
        try {

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            // 6. 检查 HTTP 状态码
            if (response.statusCode() != 200) {

                throw new RuntimeException(
                        "DeepSeek API 请求失败，HTTP Status: "
                                + response.statusCode()
                                + "\n"
                                + response.body()
                );
            }

            // 7. JSON → ChatResponse
            ChatResponse chatResponse =
                    objectMapper.readValue(
                            response.body(),
                            ChatResponse.class
                    );

            // 8. 提取 AI 回复
            return chatResponse;
        } catch (Exception e) {

            throw new RuntimeException(
                    "调用 DeepSeek API 失败",
                    e
            );
        }
    }
}