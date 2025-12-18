package com.naming.bot;

import com.naming.bot.application.NamingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
@Tag("integration")
class GeminiRealCallTest {

    @Autowired
    private NamingService namingService;

    @Test
    @DisplayName("진짜 Gemini API를 호출해서 응답을 받아온다")
    @EnabledIfEnvironmentVariable(named = "GEMINI_API_KEY", matches = ".*")
    void realCallToGoogle() {
        // given
        String type = "variable";
        String description = "사용자가 성인인지 확인하는 여부";

        // when & then
        namingService.getSuggestion(type, description)
                .as(StepVerifier::create)
                .assertNext(jsonResponse -> {
                    System.out.println(">>> 🤖 Gemini의 응답: " + jsonResponse);

                    if (!jsonResponse.contains("{") || !jsonResponse.contains("name")) {
                        throw new AssertionError("응답이 JSON 형식이 아닙니다: " + jsonResponse);
                    }
                })
                .verifyComplete();
    }
}