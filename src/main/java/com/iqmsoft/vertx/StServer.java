package com.iqmsoft.vertx;

import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.StaticHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Objects.requireNonNull;

@Slf4j
class StServer extends AbstractVerticle {

    private AppConfig configuration;

    @Autowired
    public StServer(AppConfig configuration) {
        this.configuration = requireNonNull(configuration);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);


        String webroot = configuration.webroot();
        log.info("Using '{}' as static webroot", webroot);

        router.route().handler(Route.loggerHandler());
        router.route().handler(Route.timeoutHandler());
        router.route().handler(Route.responseTimeHandler());
        router.route().failureHandler(Route.failureHandler());

        router.route().handler(StaticHandler.create(webroot));

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(configuration.httpPort());
    }
}
