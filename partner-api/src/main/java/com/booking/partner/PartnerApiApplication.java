package com.booking.partner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Partner API Application - B2B Service for Theater Partner Onboarding
 *
 * This application provides APIs for theater partners to:
 * - Register and onboard their theaters
 * - Manage theater details (screens, seats, amenities)
 * - Configure shows and schedules
 * - Set pricing strategies
 *
 * Runs on port 8081
 *
 * @author XYZ Booking Platform
 * @version 1.0.0
 */
@SpringBootApplication
public class PartnerApiApplication {

    /**
     * Main entry point for the Partner API application
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(PartnerApiApplication.class, args);
    }
}
