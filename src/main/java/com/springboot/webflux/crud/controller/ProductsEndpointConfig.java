package com.springboot.webflux.crud.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.springboot.webflux.crud.handler.ProductHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProductsEndpointConfig {

    private static final String PRODUCT = "/product";

    @Bean
    RouterFunction<ServerResponse> routes(ProductHandler handler) {
        return route(GET(PRODUCT), handler::all)
                .andRoute(POST(PRODUCT), handler::create)
                .andRoute(DELETE(PRODUCT + "/{id}"), handler::deleteById);
    }
}
