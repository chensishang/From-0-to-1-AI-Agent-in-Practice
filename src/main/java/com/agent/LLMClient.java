package com.agent;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;



public class LLMClient {
    private static final String API_URL =
            "https://api.deepseek.com/chat/completions";

    private static final String MODEL =
            "deepseek-v4-flash";
    private final HttpClient httpClient=HttpClient.newHttpClient();
    public String chat(String message){

        String apiKey = System.getenv("DEEPSEEK_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "请配置环境变量 DEEPSEEK_API_KEY"
            );
        }


        String requestBody = """
                {
                    "model": "%s",
                    "messages": [
                        {
                            "role": "user",
                            "content": "%s"
                        }
                    ],
                    "thinking": {
                        "type": "disabled"
                    },
                    "stream": false
                }
                """.formatted(MODEL, message);
        HttpRequest request= HttpRequest.newBuilder()
                .uri(java.net.URI.create((API_URL)))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (response.statusCode() != 200) {
                throw new RuntimeException(
                        "DeepSeek API 请求失败，HTTP Status: "
                        + response.statusCode()
                        + "\n"
                        + response.body()
                );
            }

            return response.body();

        } catch (Exception e) {

            throw new RuntimeException(
                    "调用 DeepSeek API 失败",
                    e
            );
        }


    }
}
