package com.booking.partner.repository;

import com.booking.partner.model.Partner;
import com.booking.partner.model.PartnerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Partner Repository Interface
 *
 * Implements Repository Pattern for data access abstraction
 * Extends JpaRepository for CRUD operations
 *
 * Benefits:
 * - Separates data access logic from business logic (SRP)
 * - Makes code more testable with mock repositories
 * - Centralizes database queries
 *
 * @author XYZ Booking Platform
 */
@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

    /**
     * Find partner by email
     *
     * @param email Partner email
     * @return Optional containing partner if found
     */
    Optional<Partner> findByEmail(String email);

    /**
     * Find partner by registration number
     *
     * @param registrationNumber Business registration number
     * @return Optional containing partner if found
     */
    Optional<Partner> findByRegistrationNumber(String registrationNumber);

    /**
     * Find partners by status
     *
     * @param status Partner status
     * @return List of partners with given status
     */
    List<Partner> findByStatus(PartnerStatus status);

    /**
     * Check if email already exists
     *
     * @param email Email to check
     * @return true if email exists
     */
    boolean existsByEmail(String email);

    /**
     * Get partners with their theaters (eagerly loaded)
     * Uses JPQL to avoid N+1 query problem
     *
     * @return List of partners with theaters
     */
    @Query("SELECT DISTINCT p FROM Partner p LEFT JOIN FETCH p.theaters WHERE p.status = 'ACTIVE'")
    List<Partner> findActivePartnersWithTheaters();
}
