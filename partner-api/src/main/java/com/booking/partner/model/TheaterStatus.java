package com.booking.partner.model;

/**
 * Theater Status Enumeration
 *
 * Defines operational status of a theater
 *
 * @author XYZ Booking Platform
 */
public enum TheaterStatus {
    /**
     * Theater is operational and accepting bookings
     */
    ACTIVE,

    /**
     * Theater is temporarily closed
     */
    INACTIVE,

    /**
     * Theater is under maintenance
     */
    UNDER_MAINTENANCE
}
