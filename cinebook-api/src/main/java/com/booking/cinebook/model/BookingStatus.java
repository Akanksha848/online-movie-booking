package com.booking.cinebook.model;

/**
 * Booking Status Enumeration
 *
 * Defines lifecycle states of a booking
 * Implements state machine pattern for booking flow
 *
 * @author XYZ Booking Platform
 */
public enum BookingStatus {
    /**
     * Booking created, awaiting payment
     * Seats are locked but not yet confirmed
     * Auto-cancels after timeout
     */
    PENDING,

    /**
     * Payment successful, booking confirmed
     */
    CONFIRMED,

    /**
     * Booking cancelled (by user or timeout)
     * Seats are released back to inventory
     */
    CANCELLED,

    /**
     * Show completed, booking archived
     */
    COMPLETED
}
