package com.demo.auth_code_flow.controller;

import com.demo.auth_code_flow.service.UserInfoService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST Controller for OAuth2 Authorization Code Flow Demo
 * This controller provides endpoints that demonstrate OAuth2 authorization code
 * flow
 * with user authentication through a browser-based login process.
 * 
 * Unlike the client credentials flow (machine-to-machine), this flow involves:
 * - User authentication through browser
 * - User consent for requested scopes
 * - Session management and user context
 * - Access to user profile information
 * 
 * This controller shows how to:
 * - Protect endpoints with OAuth2
 * - Access authenticated user information
 * - Create personalized user experiences
 * - Expose user data as APIs
 */
@RestController
public class HomeController {

    private final UserInfoService userInfoService;

    /**
     * Constructor injection of UserInfoService.
     * Spring automatically injects the UserInfoService bean.
     * 
     * @param userInfoService service for processing user information
     */
    public HomeController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    /**
     * Home endpoint that displays a personalized welcome message.
     * This endpoint demonstrates OAuth2 authorization code flow:
     * 
     * Access Flow:
     * 1. User accesses http://localhost:8080/
     * 2. Spring Security redirects to Keycloak login page
     * 3. User enters credentials and logs in
     * 4. User grants consent for requested scopes
     * 5. Keycloak redirects back with authorization code
     * 6. Spring exchanges code for access token
     * 7. User is authenticated and this method is called
     * 8. OAuth2AuthenticationToken contains user information
     * 9. Personalized welcome message is returned
     * 
     * The OAuth2AuthenticationToken parameter is automatically injected by Spring
     * Security
     * after successful authentication. It contains:
     * - User attributes (email, name, etc.)
     * - User authorities/roles
     * - Access token details
     * - Authentication information
     * 
     * @param token OAuth2AuthenticationToken with authenticated user details
     * @return String personalized welcome message
     */
    @GetMapping("/")
    public String home(OAuth2AuthenticationToken token) {
        return userInfoService.getWelcomeMessage(token);
    }

    /**
     * User information endpoint that returns structured user data as JSON.
     * This endpoint demonstrates how to expose user information from OAuth2
     * authentication
     * as a REST API. This is useful for:
     * - Single Page Applications (SPAs)
     * - Mobile applications
     * - Microservices that need user context
     * - User profile APIs
     * - Frontend frameworks that consume user data
     * 
     * The response includes:
     * - email: User's email address
     * - name: User's display name
     * - roles: User's roles/authorities as string
     * - authorities: Spring Security GrantedAuthority objects
     * 
     * Security considerations:
     * - Only authenticated users can access this endpoint
     * - User information is filtered based on scopes
     * - Sensitive data should be carefully exposed
     * - Consider data minimization principles
     * 
     * @param token OAuth2AuthenticationToken with authenticated user details
     * @return Map containing structured user information as JSON
     */
    @GetMapping("/user-info")
    public Map<String, Object> userInfo(OAuth2AuthenticationToken token) {
        return userInfoService.extractUserInfo(token);
    }
}
