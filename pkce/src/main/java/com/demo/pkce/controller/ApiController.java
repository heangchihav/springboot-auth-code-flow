package com.demo.pkce.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for PKCE OAuth2 Demo
 * This controller provides endpoints that demonstrate OAuth2 PKCE (Proof Key for Code Exchange)
 * flow with user authentication through a browser-based login process.
 * 
 * PKCE Flow Characteristics:
 * - Enhanced security with code verifier/challenge mechanism
 * - Perfect for single-page applications (SPAs) and mobile apps
 * - Prevents authorization code interception attacks
 * - No client secret required (suitable for public clients)
 * 
 * This controller shows how to:
 * - Protect endpoints with OAuth2 PKCE authentication
 * - Access authenticated user information from JWT tokens
 * - Create personalized user experiences for SPA users
 * - Handle CORS-enabled frontend integration
 */
@RestController
public class ApiController {

    /**
     * Home endpoint that returns a personalized greeting for authenticated users.
     * This endpoint demonstrates OAuth2 PKCE flow in action:
     * 
     * PKCE Access Flow:
     * 1. SPA frontend initiates OAuth2 PKCE flow
     * 2. User is redirected to authorization server (Keycloak) with code challenge
     * 3. User logs in and grants consent
     * 4. Authorization server redirects back with authorization code
     * 5. SPA exchanges code + code verifier for access token
     * 6. SPA makes authenticated request to this endpoint with JWT token
     * 7. Spring Security validates JWT token and allows access
     * 8. JWT token is injected as method parameter
     * 9. Personalized greeting is returned to frontend
     * 
     * JWT Token Processing:
     * - Spring Security automatically validates JWT signature
     * - Token claims are extracted and available in the Jwt object
     * - User information can be accessed via token claims
     * - Token expiration and issuer are validated automatically
     * 
     * @param jwt JWT token containing authenticated user details
     * @return String personalized greeting message for the user
     * @throws Exception if token processing fails
     */
    @GetMapping("/api/home")
    public String home(@AuthenticationPrincipal Jwt jwt) {
        // Extract username from JWT token claims
        // 'preferred_username' is a standard OIDC claim that contains the user's preferred username
        String username = jwt.getClaim("preferred_username");
        
        // Return personalized greeting
        return "Hello, " + username + "!";
    }

    /**
     * Additional endpoint to demonstrate accessing various JWT claims.
     * This shows how to extract different user information from the JWT token
     * that was obtained through the PKCE flow.
     * 
     * Available JWT Claims (depends on authorization server configuration):
     * - sub: Subject identifier (unique user ID)
     * - preferred_username: User's preferred username
     * - email: User's email address
     * - name: User's full name
     * - given_name: User's given name
     * - family_name: User's family name
     * - roles: User's roles/authorities
     * - iss: Issuer (authorization server)
     * - exp: Expiration time
     * - iat: Issued at time
     * 
     * @param jwt JWT token containing authenticated user details
     * @return String containing user information extracted from JWT claims
     */
    @GetMapping("/api/user-info")
    public String userInfo(@AuthenticationPrincipal Jwt jwt) {
        // Extract various user claims from JWT token
        String subject = jwt.getSubject();
        String username = jwt.getClaim("preferred_username");
        String email = jwt.getClaim("email");
        String name = jwt.getClaim("name");
        
        // Build user information response
        StringBuilder userInfo = new StringBuilder();
        userInfo.append("User Information:\n");
        userInfo.append("Subject: ").append(subject).append("\n");
        userInfo.append("Username: ").append(username).append("\n");
        userInfo.append("Email: ").append(email).append("\n");
        userInfo.append("Full Name: ").append(name).append("\n");
        userInfo.append("Token Issuer: ").append(jwt.getIssuer()).append("\n");
        userInfo.append("Token Expires: ").append(jwt.getExpiresAt()).append("\n");
        
        return userInfo.toString();
    }
}
