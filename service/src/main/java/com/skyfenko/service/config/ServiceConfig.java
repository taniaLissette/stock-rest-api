package com.skyfenko.service.config;

import com.skyfenko.persistence.config.PersistenceConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * ServiceConfig
 *
 * @author Stanislav Kyfenko
 */
@EnableAutoConfiguration
@ComponentScan("com.skyfenko.service")
@Import(PersistenceConfig.class)
public class ServiceConfig {
}
