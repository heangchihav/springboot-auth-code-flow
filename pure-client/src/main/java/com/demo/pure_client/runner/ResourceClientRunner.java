package com.demo.pure_client.runner;

import com.demo.pure_client.client.ResourceClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Command-line Runner for OAuth2 Client Demo
 * This class implements CommandLineRunner to execute code when the Spring Boot
 * application starts.
 * It demonstrates how to use OAuth2 client credentials flow in a command-line
 * context.
 * 
 * CommandLineRunner is perfect for:
 * - Command-line applications and scripts
 * - Batch processing jobs
 * - Data migration tasks
 * - Automated testing
 * - CI/CD pipeline operations
 */
@Component
public class ResourceClientRunner implements CommandLineRunner {

    private final ResourceClient resourceClient;

    /**
     * Constructor injection of ResourceClient.
     * Spring automatically injects the ResourceClient bean configured with OAuth2.
     * 
     * @param resourceClient HTTP client for making authenticated requests
     */
    public ResourceClientRunner(ResourceClient resourceClient) {
        this.resourceClient = resourceClient;
    }

    /**
     * Executes the OAuth2 client demo when the application starts.
     * This method is called automatically after Spring context is initialized.
     * 
     * Execution flow:
     * 1. Spring Boot starts and loads all beans
     * 2. OAuth2 configuration is applied (client credentials from properties)
     * 3. This run() method is executed
     * 4. ResourceClient obtains access token and makes authenticated request
     * 5. Response is printed to console
     * 6. Application exits automatically
     * 
     * This demonstrates the complete OAuth2 client credentials flow
     * in a stateless, command-line environment.
     * 
     * @param args command-line arguments (not used in this demo)
     * @throws Exception if OAuth2 flow or HTTP request fails
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting OAuth2 Client Credentials Flow Demo...");

        // Execute the OAuth2 client credentials flow
        String response = resourceClient.fetchData();

        // Display the result
        System.out.println("Response from Resource Service: " + response);

        System.out.println("Demo completed successfully!");
    }
}
