package com.booking.cinebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Cinebook API Application - B2C Service for End Customers
 *
 * This application provides APIs for end customers to:
 * - Browse movies across cities, languages, and genres
 * - Search theaters showing specific movies
 * - View show timings and seat availability
 * - Book tickets with seat selection
 * - Process payments
 *
 * Runs on port 8082
 *
 * @author XYZ Booking Platform
 * @version 1.0.0
 */
@SpringBootApplication
public class CinebookApiApplication {

    /**
     * Main entry point for the Cinebook API application
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(CinebookApiApplication.class, args);
    }
}
