package com.naming.bot.domain;

import reactor.core.publisher.Mono;

public interface AiNamingPort {
    Mono<String> suggest(NamingType type, String description);
}