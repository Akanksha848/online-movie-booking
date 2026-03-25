package com.booking.partner.service;

import com.booking.partner.model.PartnerStatus;
import com.booking.partner.dto.TheaterRequest;
import com.booking.partner.model.Partner;
import com.booking.partner.model.Theater;
import com.booking.partner.model.TheaterStatus;
import com.booking.partner.repository.PartnerRepository;
import com.booking.partner.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Theater Service - Business logic for theater management
 *
 * Implements Service Layer Pattern
 * Follows Single Responsibility Principle
 *
 * @author XYZ Booking Platform
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TheaterService {

    private final TheaterRepository theaterRepository;
    private final PartnerRepository partnerRepository;

    /**
     * Create a new theater for a partner
     *
     * @param partnerId Partner identifier
     * @param request Theater details
     * @return Created theater
     * @throws IllegalArgumentException if partner not found or not active
     */
    @Transactional
    public Theater createTheater(Long partnerId, TheaterRequest request) {
        log.info("Creating theater: {} for partner: {}", request.getTheaterName(), partnerId);

        // Fetch and validate partner
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new IllegalArgumentException("Partner not found: " + partnerId));

        // Business rule: Only active partners can create theaters
        if (partner.getStatus() != PartnerStatus.ACTIVE) {
            log.error("Theater creation failed: Partner not active - ID={}", partnerId);
            throw new IllegalStateException("Partner must be active to create theaters");
        }

        // Create theater entity
        Theater theater = Theater.builder()
                .theaterName(request.getTheaterName())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .address(request.getAddress())
                .postalCode(request.getPostalCode())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .phone(request.getPhone())
                .status(TheaterStatus.ACTIVE)
                .partner(partner)
                .build();

        // Save to database
        Theater savedTheater = theaterRepository.save(theater);

        log.info("Theater created successfully: ID={}, Name={}", savedTheater.getId(),
                savedTheater.getTheaterName());

        return savedTheater;
    }

    /**
     * Get all theaters for a partner
     *
     * @param partnerId Partner identifier
     * @return List of theaters
     */
    @Transactional(readOnly = true)
    public List<Theater> getPartnerTheaters(Long partnerId) {
        log.debug("Fetching theaters for partner: {}", partnerId);
        return theaterRepository.findByPartnerId(partnerId);
    }

    /**
     * Get theaters by city (for B2C search)
     *
     * @param city City name
     * @return List of active theaters in city
     */
    @Transactional(readOnly = true)
    public List<Theater> getTheatersByCity(String city) {
        log.debug("Fetching theaters in city: {}", city);
        return theaterRepository.findByCityAndCountryAndStatus(city, "IN", TheaterStatus.ACTIVE);
    }

    /**
     * Get theater by ID
     *
     * @param theaterId Theater identifier
     * @return Theater
     * @throws IllegalArgumentException if theater not found
     */
    @Transactional(readOnly = true)
    public Theater getTheater(Long theaterId) {
        return theaterRepository.findById(theaterId)
                .orElseThrow(() -> new IllegalArgumentException("Theater not found: " + theaterId));
    }
}
