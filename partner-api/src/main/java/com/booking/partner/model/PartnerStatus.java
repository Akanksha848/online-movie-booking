package com.booking.partner.model;

/**
 * Partner Status Enumeration
 *
 * Defines the lifecycle states of a partner in the system
 *
 * @author XYZ Booking Platform
 */
public enum PartnerStatus {
    /**
     * Partner registration is pending approval
     */
    PENDING,

    /**
     * Partner is active and can manage theaters
     */
    ACTIVE,

    /**
     * Partner is temporarily suspended
     */
    SUSPENDED,

    /**
     * Partnership is terminated
     */
    TERMINATED
}
