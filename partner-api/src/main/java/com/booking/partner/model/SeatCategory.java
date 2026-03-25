package com.booking.partner.model;

/**
 * Seat Category Enumeration
 *
 * Defines pricing tiers for seats
 * Used in Strategy Pattern for pricing calculation
 *
 * @author XYZ Booking Platform
 */
public enum SeatCategory {
    /**
     * Standard economy seats
     */
    SILVER,

    /**
     * Mid-tier seats with better view
     */
    GOLD,

    /**
     * Premium seats with best view/comfort
     */
    PLATINUM,

    /**
     * VIP recliners or special seating
     */
    VIP
}
