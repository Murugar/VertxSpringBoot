package com.iqmsoft.vertx;

import io.vertx.rxjava.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import static java.util.Objects.requireNonNull;

@Configuration
class BeanConfig {

    private AppConfig appConfiguration;

    @Autowired
    public BeanConfig(AppConfig appConfiguration) {
        this.appConfiguration = requireNonNull(appConfiguration);
    }

    @Bean
    public Vertx vertx() {
        return Vertx.vertx();
    }

    @Bean
    @Order(1)
    public StServer staticServer() {
        return new StServer(appConfiguration);
    }
}
