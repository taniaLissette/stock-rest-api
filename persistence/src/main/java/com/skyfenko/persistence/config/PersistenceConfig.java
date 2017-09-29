package com.skyfenko.persistence.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.skyfenko.persistence")
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.skyfenko.core.domain"})
@EnableJpaRepositories(basePackages = {"com.skyfenko.persistence"})
@EnableTransactionManagement
public class PersistenceConfig {
}
