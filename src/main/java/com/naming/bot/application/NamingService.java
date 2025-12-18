package com.naming.bot.application;

import com.naming.bot.domain.AiNamingPort;
import com.naming.bot.domain.NamingType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class NamingService {

    private final AiNamingPort aiNamingPort;

    public NamingService(AiNamingPort aiNamingPort) {
        this.aiNamingPort = aiNamingPort;
    }

    public Mono<String> getSuggestion(String typeStr, String description) {
        NamingType type;
        try {
            type = NamingType.valueOf(typeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Mono.error(new IllegalArgumentException("지원하지 않는 타입입니다: " + typeStr));
        }

        return aiNamingPort.suggest(type, description);
    }
}