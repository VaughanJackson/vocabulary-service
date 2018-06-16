package com.learner.vocabularyservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties
public class ConfigProperties {

    /**
     * Comma-separated list of origins from which cross-origin requests are allowed.
     * If this is <code>null</code> or empty, cross-origin requests are allowed from anywhere.
     */
    private String corsAllowedOrigins;

}

