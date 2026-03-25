package com.booking.cinebook.model;

/**
 * Payment Status Enumeration
 *
 * Tracks payment transaction status
 * Separate from booking status for better transaction management
 *
 * @author XYZ Booking Platform
 */
public enum PaymentStatus {
    /**
     * Payment not yet initiated
     */
    PENDING,

    /**
     * Payment in progress (gateway processing)
     */
    PROCESSING,

    /**
     * Payment successful
     */
    COMPLETED,

    /**
     * Payment failed
     */
    FAILED,

    /**
     * Payment refunded
     */
    REFUNDED
}
