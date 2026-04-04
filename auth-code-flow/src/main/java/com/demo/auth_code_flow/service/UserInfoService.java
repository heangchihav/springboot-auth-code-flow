package com.demo.auth_code_flow.service;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service for Extracting and Processing User Information from OAuth2
 * Authentication
 * This service extracts user details from the OAuth2AuthenticationToken that
 * Spring Security
 * creates after successful OAuth2 authorization code flow authentication.
 * 
 * The OAuth2AuthenticationToken contains:
 * - User attributes from the authorization server (name, email, etc.)
 * - User authorities/roles
 * - Access token information
 * - Client registration details
 * 
 * This service demonstrates how to access and process user information
 * from OAuth2 providers like Keycloak, Okta, Auth0, etc.
 */
@Service
public class UserInfoService {

    /**
     * Extracts user information from OAuth2 authentication token.
     * This method processes the OAuth2AuthenticationToken to extract user details
     * that were returned from the authorization server after successful
     * authentication.
     * 
     * Extracted information includes:
     * - Email: User's email address from the authorization server
     * - Name: User's display name or full name
     * - Roles: User's authorities/roles from the authorization server
     * - Authorities: Spring Security GrantedAuthority objects
     * 
     * The attributes available depend on:
     * - OAuth2 provider (Keycloak, Okta, etc.)
     * - Configured scopes in application.properties
     * - User profile configuration in authorization server
     * - Token claims configuration
     * 
     * @param token OAuth2AuthenticationToken containing user authentication details
     * @return Map containing structured user information
     */
    public Map<String, Object> extractUserInfo(OAuth2AuthenticationToken token) {
        Map<String, Object> userInfo = new HashMap<>();

        // Extract standard user attributes from OAuth2 token
        String email = token.getPrincipal().getAttribute("email");
        String name = token.getPrincipal().getAttribute("name");
        String roles = token.getAuthorities().toString();

        // Store extracted information in structured format
        userInfo.put("email", email);
        userInfo.put("name", name);
        userInfo.put("roles", roles);
        userInfo.put("authorities", token.getAuthorities());

        return userInfo;
    }

    /**
     * Creates a welcome message using user information from OAuth2 authentication.
     * This method demonstrates how to create personalized responses using
     * the authenticated user's information from the authorization server.
     * 
     * Use cases for this pattern:
     * - Personalized dashboard greetings
     * - User profile displays
     * - Audit logging with user context
     * - Conditional UI based on user roles
     * - User session management
     * 
     * The method first extracts user information, then formats it
     * into a human-readable welcome message.
     * 
     * @param token OAuth2AuthenticationToken containing authenticated user details
     * @return String formatted welcome message with user information
     */
    public String getWelcomeMessage(OAuth2AuthenticationToken token) {
        Map<String, Object> userInfo = extractUserInfo(token);

        // Format welcome message with user details
        return String.format("Welcome %s, %s, %s",
                userInfo.get("email"),
                userInfo.get("name"),
                userInfo.get("roles"));
    }
}
