package com.demo.resource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for Protected Data Endpoints
 * This controller provides endpoints that are protected by OAuth2 JWT
 * validation.
 * Only requests with valid JWT access tokens can access these endpoints.
 * 
 * This demonstrates how to create a resource server that exposes protected APIs
 * which can be called by OAuth2 clients using access tokens.
 */
@RestController
public class DataController {

    /**
     * Protected data endpoint that returns a simple greeting.
     * This endpoint is protected by OAuth2 JWT validation configured in
     * SecurityConfig.
     * 
     * Access requirements:
     * - HTTP Authorization header with Bearer token (e.g., "Authorization: Bearer
     * <jwt_token>")
     * - Valid JWT token signed by the authorization server
     * - Non-expired token
     * - Token must have proper issuer and audience claims
     * 
     * Example request flow:
     * 1. Client obtains access token from authorization server using client
     * credentials
     * 2. Client makes request to this endpoint with Bearer token
     * 3. Spring Security validates the JWT token
     * 4. If valid, the request reaches this controller method
     * 5. If invalid, returns 401 Unauthorized or 403 Forbidden
     * 
     * @return String greeting message from the resource server
     */
    @GetMapping("/data")
    public String getData() {
        return "Hello from Resource Service";
    }
}
