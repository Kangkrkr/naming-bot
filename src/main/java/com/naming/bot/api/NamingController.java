package com.naming.bot.api;

import com.naming.bot.application.NamingService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/naming")
public class NamingController {

    private final NamingService namingService;

    public NamingController(NamingService namingService) {
        this.namingService = namingService;
    }

    public record Request(String type, String description) {}

    @PostMapping
    public Mono<String> recommend(@RequestBody Request request) {

        return namingService.getSuggestion(request.type(), request.description());
    }
}