package io.github.k12f.aiinterview.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfig {
    @Value("${ai.openai.api-key}")
    public String apiKey;

    @Value("${ai.openai.base-url}")
    public String baseURL;

    @Value("${ai.openai.model-name}")
    public String modelName;

    @Value("${ai.openai.debug}")
    public Boolean debug;
}
