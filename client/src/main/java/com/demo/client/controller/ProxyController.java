package com.demo.client.controller;

import com.demo.client.service.ResourceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for Proxy Endpoints
 * This controller provides HTTP endpoints that proxy requests to the resource
 * server.
 * It demonstrates how a client application can authenticate and fetch data from
 * a protected service.
 */
@RestController
public class ProxyController {

    private final ResourceService resourceService;

    /**
     * Constructor injection of ResourceService.
     * Spring automatically injects the ResourceService bean.
     * 
     * @param resourceService service for handling resource operations
     */
    public ProxyController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /**
     * Proxy endpoint that fetches data from the resource server.
     * This endpoint demonstrates the OAuth2 client credentials flow:
     * 1. Client authenticates using client credentials
     * 2. Obtains access token from authorization server
     * 3. Makes authenticated request to resource server
     * 4. Returns the response to the caller
     * 
     * @return String response from the resource server
     */
    @GetMapping("/proxy")
    public String proxy() {
        return resourceService.fetchDataFromResource();
    }
}
