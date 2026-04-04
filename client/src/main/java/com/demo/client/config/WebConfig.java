package com.demo.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Web Configuration for HTTP Client Setup
 * This configuration provides the RestTemplate bean used for making HTTP
 * requests
 * to external services (in this case, the OAuth2 resource server).
 */
@Configuration
public class WebConfig {

    /**
     * Creates a RestTemplate bean for making HTTP requests.
     * RestTemplate is Spring's central class for synchronous HTTP client
     * operations.
     * It will be used to make authenticated requests to the resource server.
     * 
     * @return RestTemplate instance configured for HTTP operations
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
