package com.booking.partner.model;

/**
 * Show Status Enumeration
 *
 * Defines status of a show/screening
 *
 * @author XYZ Booking Platform
 */
public enum ShowStatus {
    /**
     * Show is scheduled and accepting bookings
     */
    SCHEDULED,

    /**
     * Show is fully booked (no seats available)
     */
    HOUSEFULL,

    /**
     * Show has been cancelled
     */
    CANCELLED,

    /**
     * Show has already completed
     */
    COMPLETED
}
