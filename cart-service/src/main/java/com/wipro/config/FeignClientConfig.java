package com.wipro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public AuthorizationRequestInterceptor authorizationRequestInterceptor() {
        return new AuthorizationRequestInterceptor();
    }
}