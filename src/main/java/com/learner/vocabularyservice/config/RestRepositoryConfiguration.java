package com.learner.vocabularyservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

/**
 * Extends {@link RepositoryRestConfigurerAdapter} to configure non-default REST repository settings.
 */
@Configuration
public class RestRepositoryConfiguration extends RepositoryRestConfigurerAdapter
{
    /**
     * Overrides {@link RepositoryRestConfigurerAdapter#configureRepositoryRestConfiguration(RepositoryRestConfiguration)}
     * to disable exposure of REST repository methods by default, so that only methods explicitly annotated
     * @RestResource will be exposed.
     * @see https://jira.spring.io/browse/DATAREST-1176.
     */
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.disableDefaultExposure();
        super.configureRepositoryRestConfiguration(config);
    }
}
