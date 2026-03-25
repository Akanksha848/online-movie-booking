package com.booking.cinebook.repository;

import com.booking.cinebook.model.Booking;
import com.booking.cinebook.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Booking Repository Interface
 *
 * Implements Repository Pattern for booking data access
 *
 * @author XYZ Booking Platform
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Find booking by reference code
     *
     * @param bookingReference Booking reference
     * @return Optional containing booking if found
     */
    Optional<Booking> findByBookingReference(String bookingReference);

    /**
     * Find bookings by customer
     *
     * @param customerId Customer identifier
     * @return List of customer bookings
     */
    List<Booking> findByCustomerId(Long customerId);

    /**
     * Find bookings by show
     *
     * @param showId Show identifier
     * @return List of show bookings
     */
    List<Booking> findByShowId(Long showId);

    /**
     * Find bookings by show and status
     * Useful for checking confirmed bookings
     *
     * @param showId Show identifier
     * @param status Booking status
     * @return List of bookings
     */
    List<Booking> findByShowIdAndStatus(Long showId, BookingStatus status);

    /**
     * Find expired pending bookings for cleanup
     * These bookings need to be cancelled to free up seats
     *
     * @param now Current timestamp
     * @return List of expired bookings
     */
    @Query("SELECT b FROM Booking b WHERE b.status = 'PENDING' AND b.expiresAt < :now")
    List<Booking> findExpiredPendingBookings(LocalDateTime now);
}
