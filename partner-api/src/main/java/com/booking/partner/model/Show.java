package com.booking.partner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Show Entity - Represents a specific movie showing/screening
 *
 * Contains show timing, pricing, and availability
 * Used for booking flow
 *
 * Design Pattern: Domain Model Pattern
 *
 * @author XYZ Booking Platform
 */
@Entity
@Table(name = "SHOWS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Show {

    /**
     * Unique identifier for the show
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "show_seq")
    @SequenceGenerator(name = "show_seq", sequenceName = "SHOW_SEQ", allocationSize = 1)
    @Column(name = "SHOW_ID")
    private Long id;

    /**
     * Date of the show
     */
    @Column(name = "SHOW_DATE", nullable = false)
    private LocalDate showDate;

    /**
     * Start time of the show
     */
    @Column(name = "SHOW_TIME", nullable = false)
    private LocalTime showTime;

    /**
     * End time of the show (calculated based on movie duration)
     */
    @Column(name = "END_TIME", nullable = false)
    private LocalTime endTime;

    /**
     * Base price for the show
     * Actual seat price = base price + category multiplier
     */
    @Column(name = "BASE_PRICE", nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    /**
     * Total available seats for this show
     */
    @Column(name = "TOTAL_SEATS", nullable = false)
    private Integer totalSeats;

    /**
     * Number of seats already booked
     */
    @Column(name = "BOOKED_SEATS", nullable = false)
    private Integer bookedSeats;

    /**
     * Show status
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    private ShowStatus status;

    /**
     * Creation timestamp
     */
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Last update timestamp
     */
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    /**
     * Movie being shown
     * Many-to-One relationship
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MOVIE_ID", nullable = false)
    private Movie movie;

    /**
     * Screen where show is scheduled
     * Many-to-One relationship
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCREEN_ID", nullable = false)
    private Screen screen;

    /**
     * Pre-persist callback
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = ShowStatus.SCHEDULED;
        }
        if (bookedSeats == null) {
            bookedSeats = 0;
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
     * Get available seats count
     *
     * @return Number of available seats
     */
    public Integer getAvailableSeats() {
        return totalSeats - bookedSeats;
    }

    /**
     * Check if show is bookable
     *
     * @return true if show has available seats and is in SCHEDULED status
     */
    public boolean isBookable() {
        return status == ShowStatus.SCHEDULED && getAvailableSeats() > 0;
    }

    /**
     * Increment booked seats count
     *
     * @param count Number of seats to book
     * @throws IllegalStateException if not enough seats available
     */
    public void bookSeats(int count) {
        if (getAvailableSeats() < count) {
            throw new IllegalStateException("Not enough seats available");
        }
        this.bookedSeats += count;

        // Auto-mark as HOUSEFULL when all seats are booked
        if (getAvailableSeats() == 0) {
            this.status = ShowStatus.HOUSEFULL;
        }
    }
}
