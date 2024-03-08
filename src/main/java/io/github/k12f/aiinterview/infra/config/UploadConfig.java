package io.github.k12f.aiinterview.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadConfig {
    @Value("${upload.pdf.path}")
    public String path;
}
