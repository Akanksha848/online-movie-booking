package com.booking.cinebook.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * Booking Seat Entity - Represents a seat in a booking
 *
 * Links booking to specific seats
 * Stores price at time of booking (historical data)
 *
 * @author XYZ Booking Platform
 */
@Entity
@Table(name = "BOOKING_SEATS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingSeat {

    /**
     * Unique identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_seat_seq")
    @SequenceGenerator(name = "booking_seat_seq", sequenceName = "BOOKING_SEAT_SEQ", allocationSize = 1)
    @Column(name = "BOOKING_SEAT_ID")
    private Long id;

    /**
     * Booking this seat belongs to
     * Many-to-One relationship
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOKING_ID", nullable = false)
    private Booking booking;

    /**
     * Seat ID from Partner API schema
     */
    @Column(name = "SEAT_ID", nullable = false)
    private Long seatId;

    /**
     * Seat identifier for display (e.g., "A-15")
     * Denormalized for performance and historical data
     */
    @Column(name = "SEAT_IDENTIFIER", nullable = false, length = 20)
    private String seatIdentifier;

    /**
     * Price for this seat at booking time
     * Historical data - even if show price changes later
     */
    @Column(name = "PRICE", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}
