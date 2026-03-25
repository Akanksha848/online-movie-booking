package com.booking.cinebook.repository;

import com.booking.cinebook.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Customer Repository Interface
 *
 * Implements Repository Pattern for customer data access
 *
 * @author XYZ Booking Platform
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Find customer by email
     *
     * @param email Customer email
     * @return Optional containing customer if found
     */
    Optional<Customer> findByEmail(String email);

    /**
     * Find customer by phone
     *
     * @param phone Customer phone
     * @return Optional containing customer if found
     */
    Optional<Customer> findByPhone(String phone);

    /**
     * Check if email exists
     *
     * @param email Email to check
     * @return true if exists
     */
    boolean existsByEmail(String email);
}
