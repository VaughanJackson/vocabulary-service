package com.learner.vocabularyservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.apache.logging.log4j.util.Strings.isEmpty;

/**
 * Encapsulates the management of the CORS policy governing access to all REST API methods
 * exposed by this application.
 */
@Component
@Slf4j
public class CorsConfig {

    /**
     * Configuration properties used by this.
     */
    private final ConfigProperties properties;

    @Autowired
    public CorsConfig(ConfigProperties properties) {
        this.properties = properties;
    }

    /**
     * Examines the configuration property {@link ConfigProperties#corsAllowedOrigins} to determine whether
     * to allow cross-origin requests from any origin, or only from those listed.
     * @param config the {@link RepositoryRestConfiguration} used to control spring data repository
     *               behaviour.
     */
    void configureCors(final RepositoryRestConfiguration config) {
        final CorsRegistry registry = config.getCorsRegistry();
        configureCors(registry);
    }

    /**
     * Examines the configuration property {@link ConfigProperties#corsAllowedOrigins} to determine whether
     * to allow cross-origin requests from any origin, or only from those listed.
     * @param registry the {@link CorsRegistry} used to control CORS-related behaviour.
     */
    public void configureCors(final CorsRegistry registry) {
        final String allowedOrigins = properties.getCorsAllowedOrigins();
        LOGGER.debug("allowedOrigins = {}", allowedOrigins);
        if (!isEmpty(allowedOrigins)) {
            // Restrict cross origin requests to those from the origins listed in allowedOrigins only
            registry.addMapping("/**").allowedOrigins(splitAllowedOrigins(allowedOrigins));
        } else {
            // Open up for all cross origin requests
            registry.addMapping("/**");
        }
    }

    /**
     * Splits the allowed origins into an array of separate, trimmed origin strings.
     * @param allowedOrigins the allowed origins list as a single string
     * @return an array of trimmed allowed origins
     */
    private String[] splitAllowedOrigins(final String allowedOrigins) {
        return Stream.of(allowedOrigins.split(","))
                .map(String::trim)
                .collect(toList())
                .toArray(new String[]{});
    }


}
