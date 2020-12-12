package com.drsf.api.postgres;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages ={"drsf.api.postgres.repositories"})
@EntityScan(basePackages = {"com.drsf.api"})
@ComponentScan(basePackages = {"drsf.api.postgres"})
public class ConfigPolygon {
}
