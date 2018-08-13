package com.learner.vocabularyservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

/**
 * Extends {@link RepositoryRestConfigurerAdapter} to configure non-default REST repository settings.
 */
@Configuration
@Slf4j // TODO Why doesn't @XSlf4j have LOGGER.debug() etc? - would like to use it for entry(), exit()...
public class RestRepositoryConfiguration extends RepositoryRestConfigurerAdapter
{
    /** CorsConfig used by this. */
    private final CorsConfig corsConfig;

    @Autowired
    public RestRepositoryConfiguration(CorsConfig corsConfig) {
        this.corsConfig = corsConfig;
    }

    /**
     * Overrides {@link RepositoryRestConfigurerAdapter#configureRepositoryRestConfiguration(RepositoryRestConfiguration)}
     * to disable exposure of REST repository methods by default, so that only methods explicitly annotated
     * 	&#64;RestResource will be exposed. Also invokes code used to configure allowed origins for cross-origin requests.
     * @see <a href="https://jira.spring.io/browse/DATAREST-1176">DATAREST-1176</a>.
     */
    @Override
    public void configureRepositoryRestConfiguration(final RepositoryRestConfiguration config) {
        config.disableDefaultExposure();
        corsConfig.configureCors(config);
        super.configureRepositoryRestConfiguration(config);
    }
}
