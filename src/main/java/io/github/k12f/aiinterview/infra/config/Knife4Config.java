package io.github.k12f.aiinterview.infra.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableKnife4j
public class Knife4Config {

    @Bean
    public GroupedOpenApi webApi() {
        return GroupedOpenApi.builder()
                .group("api")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI restfulOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("ai interviewer")
                        .version("1.0")
                        .description("ai 面试官")
                        .contact(new Contact().name("https://github.com/K12f")));
    }
}
