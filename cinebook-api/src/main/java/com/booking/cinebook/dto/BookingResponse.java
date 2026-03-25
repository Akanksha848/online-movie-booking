package com.booking.cinebook.dto;

import com.booking.cinebook.model.BookingStatus;
import com.booking.cinebook.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Booking Response DTO
 *
 * Returns booking confirmation details
 * Implements DTO Pattern
 *
 * @author XYZ Booking Platform
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    /**
     * Booking ID
     */
    private Long bookingId;

    /**
     * Booking reference code for retrieval
     */
    private String bookingReference;

    /**
     * Customer name
     */
    private String customerName;

    /**
     * Show ID
     */
    private Long showId;

    /**
     * Seat identifiers (e.g., ["A-15", "A-16"])
     */
    private List<String> seatIdentifiers;

    /**
     * Total amount paid
     */
    private BigDecimal totalAmount;

    /**
     * Booking status
     */
    private BookingStatus status;

    /**
     * Payment status
     */
    private PaymentStatus paymentStatus;

    /**
     * Booking timestamp
     */
    private LocalDateTime bookedAt;

    /**
     * Expiry timestamp (for PENDING bookings)
     */
    private LocalDateTime expiresAt;
}
