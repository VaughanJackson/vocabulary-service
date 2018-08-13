package com.learner.vocabularyservice;

import com.learner.vocabularyservice.config.CorsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Slf4j // TODO Why doesn't @XSlf4j have LOGGER.debug() etc? - would like to use it for entry(), exit()...
public class VocabularyServiceApplication {

    /** CorsConfig used by this. */
    private final CorsConfig corsConfig;

    @Autowired
    public VocabularyServiceApplication(CorsConfig corsConfig) {
        this.corsConfig = corsConfig;
    }

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
                corsConfig.configureCors(registry);
            }
        };
    }

}
