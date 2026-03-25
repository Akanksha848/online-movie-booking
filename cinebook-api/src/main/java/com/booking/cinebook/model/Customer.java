package com.booking.cinebook.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Customer Entity - Represents an end user/customer
 *
 * Contains customer profile information
 * Implements Domain Model Pattern
 *
 * @author XYZ Booking Platform
 */
@Entity
@Table(name = "CUSTOMERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    /**
     * Unique identifier for the customer
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name = "customer_seq", sequenceName = "CUSTOMER_SEQ", allocationSize = 1)
    @Column(name = "CUSTOMER_ID")
    private Long id;

    /**
     * Customer name
     */
    @Column(name = "NAME", nullable = false, length = 200)
    private String name;

    /**
     * Customer email
     * Used for booking confirmations
     */
    @Column(name = "EMAIL", nullable = false, unique = true, length = 100)
    private String email;

    /**
     * Customer phone number
     */
    @Column(name = "PHONE", nullable = false, length = 20)
    private String phone;

    /**
     * Creation timestamp
     */
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Pre-persist callback
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
