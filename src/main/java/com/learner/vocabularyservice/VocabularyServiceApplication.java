package com.learner.vocabularyservice;

import com.learner.vocabularyservice.config.ConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@SpringBootApplication
@Slf4j // TODO Why doesn't @XSlf4j have LOGGER.debug() etc? - would like to use it for entry(), exit()...
public class VocabularyServiceApplication {

    /**
     * Configuration properties used by this.
     */
    private ConfigProperties properties;

	public static void main(String[] args) {
		SpringApplication.run(VocabularyServiceApplication.class, args);
	}

    /**
     * Returns a {@link WebMvcConfigurer} bean to configure the application.
     * @return a {@link WebMvcConfigurer} configuration bean
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                configureCors(registry);
            }
        };
    }

    /**
     * Examines the configuration property {@link ConfigProperties#corsAllowedOrigins} to determine whether
     * to allow cross-origin requests from any origin, or only from those listed.
     * @param registry the {@link CorsRegistry} used to control spring data repository
     *               behaviour.
     */
    private void configureCors(final CorsRegistry registry) {
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

    /**
     * Sets the properties used by this.
     * @param properties {@link ConfigProperties}
     */
    @Autowired
    public void setProperties(ConfigProperties properties) {
        this.properties = properties;
    }


}
