package com.drsf.api.web;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages ={"com.drsf.api"})
@EntityScan(basePackages = {"com.drsf.api"})
@ComponentScan(basePackages = {"com.drsf.api"})
public class ConfigWeb {
}
