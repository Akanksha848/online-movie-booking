package com.booking.cinebook.service.payment;

import java.math.BigDecimal;

/**
 * Payment Strategy Interface
 *
 * Implements Strategy Pattern for payment gateway integration
 * Allows supporting multiple payment methods without modifying client code
 *
 * Benefits:
 * - Open/Closed Principle: Can add new payment gateways without changing existing code
 * - Dependency Inversion: High-level modules depend on abstraction
 * - Easy testing with mock strategies
 *
 * Supports requirement: Integration with payment gateways
 *
 * @author XYZ Booking Platform
 */
public interface PaymentStrategy {

    /**
     * Process payment for booking
     *
     * @param amount Amount to charge
     * @param customerEmail Customer email
     * @param paymentMethod Payment method details
     * @return Payment transaction ID if successful
     * @throws PaymentException if payment fails
     */
    String processPayment(BigDecimal amount, String customerEmail, String paymentMethod)
            throws PaymentException;

    /**
     * Get strategy name
     *
     * @return Payment gateway name
     */
    String getStrategyName();

    /**
     * Check if payment method is supported
     *
     * @param paymentMethod Payment method
     * @return true if supported
     */
    boolean supports(String paymentMethod);
}
