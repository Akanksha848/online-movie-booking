package com.booking.partner.repository;

import com.booking.partner.model.Theater;
import com.booking.partner.model.TheaterStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Theater Repository Interface
 *
 * Implements Repository Pattern for theater data access
 *
 * @author XYZ Booking Platform
 */
@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {

    /**
     * Find theaters by partner ID
     *
     * @param partnerId Partner identifier
     * @return List of theaters owned by partner
     */
    List<Theater> findByPartnerId(Long partnerId);

    /**
     * Find theaters by city
     * Supports location-based search requirement
     *
     * @param city City name
     * @return List of theaters in city
     */
    List<Theater> findByCity(String city);

    /**
     * Find active theaters by city and country
     * Supports internationalization
     *
     * @param city City name
     * @param country Country code
     * @param status Theater status
     * @return List of matching theaters
     */
    List<Theater> findByCityAndCountryAndStatus(String city, String country, TheaterStatus status);

    /**
     * Find theaters with legacy systems
     * Useful for integration planning
     *
     * @param hasLegacySystem Legacy system flag
     * @return List of theaters
     */
    List<Theater> findByHasLegacySystem(Boolean hasLegacySystem);

    /**
     * Search theaters by name (case-insensitive)
     *
     * @param theaterName Theater name pattern
     * @return List of matching theaters
     */
    @Query("SELECT t FROM Theater t WHERE LOWER(t.theaterName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Theater> searchByName(@Param("name") String theaterName);
}
