package com.booking.cinebook.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Booking Entity - Represents a ticket booking
 *
 * Contains booking details, seats, and payment information
 * Implements Domain Model Pattern with aggregate root pattern
 *
 * Transaction handling:
 * - Booking status tracks the transaction lifecycle
 * - Seats are locked during PENDING status
 * - Auto-cancellation after timeout to prevent seat blocking
 *
 * @author XYZ Booking Platform
 */
@Entity
@Table(name = "BOOKINGS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    /**
     * Unique identifier for the booking
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_seq")
    @SequenceGenerator(name = "booking_seq", sequenceName = "BOOKING_SEQ", allocationSize = 1)
    @Column(name = "BOOKING_ID")
    private Long id;

    /**
     * Unique booking reference code
     * Used for ticket retrieval and confirmation
     */
    @Column(name = "BOOKING_REFERENCE", nullable = false, unique = true, length = 20)
    private String bookingReference;

    /**
     * Customer who made the booking
     * Many-to-One relationship
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    /**
     * Show for which booking is made
     * Many-to-One relationship
     * Note: Show entity is in Partner API schema, use ID reference
     */
    @Column(name = "SHOW_ID", nullable = false)
    private Long showId;

    /**
     * Total amount for the booking
     */
    @Column(name = "TOTAL_AMOUNT", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    /**
     * Number of seats booked
     */
    @Column(name = "SEAT_COUNT", nullable = false)
    private Integer seatCount;

    /**
     * Booking status
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    private BookingStatus status;

    /**
     * Payment status
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "PAYMENT_STATUS", nullable = false, length = 20)
    private PaymentStatus paymentStatus;

    /**
     * Payment method used
     */
    @Column(name = "PAYMENT_METHOD", length = 50)
    private String paymentMethod;

    /**
     * Payment transaction ID
     */
    @Column(name = "PAYMENT_TRANSACTION_ID", length = 100)
    private String paymentTransactionId;

    /**
     * Booking creation timestamp
     */
    @Column(name = "BOOKED_AT", nullable = false)
    private LocalDateTime bookedAt;

    /**
     * Booking expiry time for PENDING status
     * After this time, booking auto-cancels to free up seats
     */
    @Column(name = "EXPIRES_AT")
    private LocalDateTime expiresAt;

    /**
     * Last update timestamp
     */
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    /**
     * Seats included in this booking
     * One-to-Many relationship with cascade operations
     */
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<BookingSeat> bookingSeats = new ArrayList<>();

    /**
     * Pre-persist callback
     * Sets timestamps and default statuses
     */
    @PrePersist
    protected void onCreate() {
        bookedAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (status == null) {
            status = BookingStatus.PENDING;
        }

        if (paymentStatus == null) {
            paymentStatus = PaymentStatus.PENDING;
        }

        // Set expiry time: 15 minutes from creation
        if (expiresAt == null && status == BookingStatus.PENDING) {
            expiresAt = LocalDateTime.now().plusMinutes(15);
        }
    }

    /**
     * Pre-update callback
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Helper method to add seat to booking
     *
     * @param bookingSeat Seat to add
     */
    public void addBookingSeat(BookingSeat bookingSeat) {
        bookingSeats.add(bookingSeat);
        bookingSeat.setBooking(this);
    }

    /**
     * Confirm booking after successful payment
     * Updates status to CONFIRMED
     */
    public void confirm() {
        this.status = BookingStatus.CONFIRMED;
        this.paymentStatus = PaymentStatus.COMPLETED;
        this.expiresAt = null; // No longer needs expiry
    }

    /**
     * Cancel booking
     * Can be customer-initiated or auto-cancelled on expiry
     */
    public void cancel() {
        this.status = BookingStatus.CANCELLED;
    }

    /**
     * Check if booking has expired
     *
     * @return true if booking is pending and past expiry time
     */
    public boolean isExpired() {
        return status == BookingStatus.PENDING &&
               expiresAt != null &&
               LocalDateTime.now().isAfter(expiresAt);
    }
}
