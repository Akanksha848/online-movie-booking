package com.booking.partner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Seat Entity - Represents a physical seat in a screen
 *
 * Contains seat configuration and category for pricing
 *
 * Design Pattern: Domain Model Pattern
 *
 * @author XYZ Booking Platform
 */
@Entity
@Table(name = "SEATS", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"SCREEN_ID", "ROW_NAME", "SEAT_NUMBER"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {

    /**
     * Unique identifier for the seat
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seat_seq")
    @SequenceGenerator(name = "seat_seq", sequenceName = "SEAT_SEQ", allocationSize = 1)
    @Column(name = "SEAT_ID")
    private Long id;

    /**
     * Row identifier (e.g., "A", "B", "C")
     */
    @Column(name = "ROW_NAME", nullable = false, length = 10)
    private String rowName;

    /**
     * Seat number within the row (e.g., 1, 2, 3)
     */
    @Column(name = "SEAT_NUMBER", nullable = false)
    private Integer seatNumber;

    /**
     * Seat category determines pricing tier
     * GOLD, SILVER, PLATINUM, etc.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "SEAT_CATEGORY", nullable = false, length = 20)
    private SeatCategory seatCategory;

    /**
     * Whether seat is available for booking
     * Can be disabled for maintenance or blocked seats
     */
    @Column(name = "IS_AVAILABLE", nullable = false)
    private Boolean isAvailable;

    /**
     * Screen that contains this seat
     * Many-to-One relationship
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCREEN_ID", nullable = false)
    private Screen screen;

    /**
     * Get full seat identifier (e.g., "A-15")
     *
     * @return Formatted seat identifier
     */
    public String getSeatIdentifier() {
        return rowName + "-" + seatNumber;
    }
}
