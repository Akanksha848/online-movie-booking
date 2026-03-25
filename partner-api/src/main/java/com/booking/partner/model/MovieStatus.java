package com.booking.partner.model;

/**
 * Movie Status Enumeration
 *
 * Defines lifecycle status of a movie
 *
 * @author XYZ Booking Platform
 */
public enum MovieStatus {
    /**
     * Movie is available for scheduling
     */
    ACTIVE,

    /**
     * Movie is coming soon (pre-release)
     */
    UPCOMING,

    /**
     * Movie is no longer available
     */
    ARCHIVED
}
