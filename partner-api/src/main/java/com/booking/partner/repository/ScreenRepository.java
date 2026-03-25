package com.booking.partner.repository;

import com.booking.partner.model.Screen;
import com.booking.partner.model.ScreenStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Screen Repository Interface
 *
 * Implements Repository Pattern for screen data access
 *
 * @author XYZ Booking Platform
 */
@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {

    /**
     * Find screens by theater ID
     *
     * @param theaterId Theater identifier
     * @return List of screens in theater
     */
    List<Screen> findByTheaterId(Long theaterId);

    /**
     * Find active screens by theater ID
     *
     * @param theaterId Theater identifier
     * @param status Screen status
     * @return List of active screens
     */
    List<Screen> findByTheaterIdAndStatus(Long theaterId, ScreenStatus status);

    /**
     * Count screens in a theater
     *
     * @param theaterId Theater identifier
     * @return Count of screens
     */
    long countByTheaterId(Long theaterId);
}
