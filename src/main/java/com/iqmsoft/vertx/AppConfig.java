package com.iqmsoft.vertx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import static java.util.Objects.requireNonNull;

@Configuration
@EnableConfigurationProperties(AppProp.class)
class AppConfig {

    private final Environment environment;
    private final AppProp appProperties;

    @Autowired
    public AppConfig(Environment environment, AppProp appProperties) {
        this.environment = requireNonNull(environment);
        this.appProperties = requireNonNull(appProperties);
    }

    int httpPort() {
        return environment.getProperty("http.port", Integer.class, 8080);
    }

    String webroot() {
        final String pathWhenInsideJarFile = "BOOT-INF/classes/" + appProperties.getWebroot();
        boolean insideJarFile = new ClassPathResource(pathWhenInsideJarFile).exists();
        return insideJarFile ? pathWhenInsideJarFile : appProperties.getWebroot();
    }
}
