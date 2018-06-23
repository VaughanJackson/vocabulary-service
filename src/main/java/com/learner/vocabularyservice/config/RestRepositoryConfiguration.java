package com.learner.vocabularyservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryCorsRegistry;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.apache.logging.log4j.util.Strings.isEmpty;

/**
 * Extends {@link RepositoryRestConfigurerAdapter} to configure non-default REST repository settings.
 */
@Configuration
public class RestRepositoryConfiguration extends RepositoryRestConfigurerAdapter
{
    /**
     * Logger used by this.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RestRepositoryConfiguration.class);

    /**
     * Configuration properties used by this.
     */
    private ConfigProperties properties;

    /**
     * Overrides {@link RepositoryRestConfigurerAdapter#configureRepositoryRestConfiguration(RepositoryRestConfiguration)}
     * to disable exposure of REST repository methods by default, so that only methods explicitly annotated
     * 	&#64;RestResource will be exposed. Also invokes code used to configure allowed origins for cross-origin requests.
     * @see <a href="https://jira.spring.io/browse/DATAREST-1176">DATAREST-1176</a>.
     */
    @Override
    public void configureRepositoryRestConfiguration(final RepositoryRestConfiguration config) {
        config.disableDefaultExposure();
        configureCors(config);
        super.configureRepositoryRestConfiguration(config);
    }

    /**
     * Examines the configuration property {@link ConfigProperties#corsAllowedOrigins} to determine whether
     * to allow cross-origin requests from any origin, or only from those listed.
     * @param config the {@link RepositoryRestConfiguration} used to control spring data repository
     *               behaviour.
     */
    private void configureCors(final RepositoryRestConfiguration config) {
        final String allowedOrigins = properties.getCorsAllowedOrigins();
        LOGGER.debug("allowedOrigins = {}", allowedOrigins);
        final RepositoryCorsRegistry registry = config.getCorsRegistry();
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
