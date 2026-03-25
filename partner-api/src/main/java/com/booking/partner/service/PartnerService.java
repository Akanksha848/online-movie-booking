package com.booking.partner.service;

import com.booking.partner.dto.PartnerRegistrationRequest;
import com.booking.partner.dto.PartnerResponse;
import com.booking.partner.model.Partner;
import com.booking.partner.model.PartnerStatus;
import com.booking.partner.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Partner Service - Business logic for partner management
 *
 * Implements Service Layer Pattern:
 * - Separates business logic from controllers
 * - Handles transactions
 * - Performs validations
 *
 * Follows SOLID Principles:
 * - Single Responsibility: Only handles partner operations
 * - Dependency Inversion: Depends on repository abstraction, not concrete implementation
 *
 * @author XYZ Booking Platform
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PartnerService {

    // Dependency injection via constructor (Dependency Inversion Principle)
    private final PartnerRepository partnerRepository;

    /**
     * Register a new partner
     *
     * Validates uniqueness of email and registration number
     * Creates partner with PENDING status
     *
     * @param request Partner registration details
     * @return Created partner response
     * @throws IllegalArgumentException if email or registration number already exists
     */
    @Transactional
    public PartnerResponse registerPartner(PartnerRegistrationRequest request) {
        log.info("Registering new partner: {}", request.getPartnerName());

        // Business validation: Check email uniqueness
        if (partnerRepository.existsByEmail(request.getEmail())) {
            log.error("Partner registration failed: Email already exists - {}", request.getEmail());
            throw new IllegalArgumentException("Email already registered");
        }

        // Business validation: Check registration number uniqueness if provided
        if (request.getRegistrationNumber() != null &&
            partnerRepository.findByRegistrationNumber(request.getRegistrationNumber()).isPresent()) {
            log.error("Partner registration failed: Registration number already exists - {}",
                     request.getRegistrationNumber());
            throw new IllegalArgumentException("Registration number already exists");
        }

        // Create partner entity
        Partner partner = Partner.builder()
                .partnerName(request.getPartnerName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .registrationNumber(request.getRegistrationNumber())
                .status(PartnerStatus.PENDING)
                .build();

        // Save to database
        Partner savedPartner = partnerRepository.save(partner);

        log.info("Partner registered successfully: ID={}, Name={}", savedPartner.getId(),
                savedPartner.getPartnerName());

        // Convert to DTO for response
        return convertToResponse(savedPartner);
    }

    /**
     * Get partner by ID
     *
     * @param partnerId Partner identifier
     * @return Partner response
     * @throws IllegalArgumentException if partner not found
     */
    @Transactional(readOnly = true)
    public PartnerResponse getPartner(Long partnerId) {
        log.debug("Fetching partner: ID={}", partnerId);

        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new IllegalArgumentException("Partner not found: " + partnerId));

        return convertToResponse(partner);
    }

    /**
     * Get all partners
     *
     * @return List of all partners
     */
    @Transactional(readOnly = true)
    public List<PartnerResponse> getAllPartners() {
        log.debug("Fetching all partners");

        return partnerRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


	private PartnerResponse convertToResponse(Partner partner) {
		return PartnerResponse.builder()
				.id(partner.getId())
				.partnerName(partner.getPartnerName())
				.email(partner.getEmail())
				.phone(partner.getPhone())
				.registrationNumber(partner.getRegistrationNumber())
				.status(partner.getStatus())
				.onboardedAt(partner.getOnboardedAt())
				.theaterCount(partner.getTheaters() != null ? partner.getTheaters().size() : 0)
				.build();
	}
}
