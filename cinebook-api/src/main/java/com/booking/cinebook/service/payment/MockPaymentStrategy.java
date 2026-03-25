package com.booking.cinebook.service.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Mock Payment Strategy Implementation
 *
 * Implements Strategy Pattern - Concrete strategy for mock payment processing
 * Used for testing and demo purposes
 *
 * In production, replace with real payment gateway implementations:
 * - StripePaymentStrategy
 * - PayPalPaymentStrategy
 * - RazorpayPaymentStrategy
 *
 * @author XYZ Booking Platform
 */
@Component
@Slf4j
public class MockPaymentStrategy implements PaymentStrategy {

    /**
     * Process mock payment
     * Always succeeds for demo purposes
     *
     * @param amount Amount to charge
     * @param customerEmail Customer email
     * @param paymentMethod Payment method
     * @return Generated transaction ID
     */
    @Override
    public String processPayment(BigDecimal amount, String customerEmail, String paymentMethod) {
        log.info("Processing mock payment: amount={}, customer={}, method={}",
                amount, customerEmail, paymentMethod);

        // Simulate payment processing
        try {
            Thread.sleep(1000); // Simulate gateway latency
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Generate mock transaction ID
        String transactionId = "TXN-" + UUID.randomUUID().toString();

        log.info("Mock payment successful: transactionId={}", transactionId);

        return transactionId;
    }

    /**
     * Get strategy name
     *
     * @return Strategy identifier
     */
    @Override
    public String getStrategyName() {
        return "MOCK_PAYMENT";
    }

    /**
     * Supports all payment methods for demo
     *
     * @param paymentMethod Payment method
     * @return true always
     */
    @Override
    public boolean supports(String paymentMethod) {
        return true;
    }
}
