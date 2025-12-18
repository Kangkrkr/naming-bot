package com.naming.bot.infrastructure;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.naming.bot.domain.AiNamingPort;
import com.naming.bot.domain.NamingType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class GeminiNamingAdapter implements AiNamingPort {

    private final Client client;

    public GeminiNamingAdapter(@Value("${gemini.api-key}") String apiKey) {
        this.client = Client.builder().apiKey(apiKey).build();
    }

    @Override
    public Mono<String> suggest(NamingType type, String description) {
        return Mono.fromCallable(() -> {
            String prompt = String.format("""
                Role: Java Expert and Answer with Korean.
                Task: Suggest a %s name for "%s".
                Format: JSON { "name": "...", "reason": "..." } only.
                """, type.name(), description);

            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.5-flash-lite", prompt, null
            );
            
            return cleanJson(response.text());
        });
    }

    private String cleanJson(String text) {
        return text.replace("```json", "").replace("```", "").trim();
    }
}