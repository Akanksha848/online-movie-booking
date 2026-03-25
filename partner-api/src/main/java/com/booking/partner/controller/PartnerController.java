package com.booking.partner.controller;

import com.booking.partner.dto.PartnerRegistrationRequest;
import com.booking.partner.dto.PartnerResponse;
import com.booking.partner.service.PartnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Partner Controller - REST API endpoints for partner management
 *
 * Implements REST API best practices:
 * - Proper HTTP status codes
 * - Request/Response validation
 * - Error handling
 * - API documentation with Swagger
 *
 * Follows SOLID Principles:
 * - Single Responsibility: Only handles HTTP concerns
 * - Dependency Inversion: Depends on service abstraction
 *
 * @author XYZ Booking Platform
 */
@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
@Slf4j
public class PartnerController {

    // Injected via constructor (Dependency Inversion Principle)
    private final PartnerService partnerService;

    /**
     * Register a new partner
     *
     * POST /api/partners
     *
     * @param request Partner registration details
     * @return Created partner with HTTP 201 status
     */
    @PostMapping
    @Operation(summary = "Register a new partner", description = "Onboard a new theater partner to the platform")
    public ResponseEntity<PartnerResponse> registerPartner(@Valid @RequestBody PartnerRegistrationRequest request) {
        log.info("POST /api/partners - Registering partner: {}", request.getPartnerName());

        try {
            PartnerResponse response = partnerService.registerPartner(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            log.error("Partner registration failed: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get all partners
     *
     * GET /api/partners
     *
     * @return List of all partners
     */
    @GetMapping
    @Operation(summary = "Get all partners", description = "Retrieve list of all registered partners")
    public ResponseEntity<List<PartnerResponse>> getAllPartners() {
        log.info("GET /api/partners - Fetching all partners");

        List<PartnerResponse> partners = partnerService.getAllPartners();
        return ResponseEntity.ok(partners);
    }

    /**
     * Get partner by ID
     *
     * GET /api/partners/{partnerId}
     *
     * @param partnerId Partner identifier
     * @return Partner details
     */
    @GetMapping("/{partnerId}")
    @Operation(summary = "Get partner by ID", description = "Retrieve details of a specific partner")
    public ResponseEntity<PartnerResponse> getPartner(@PathVariable Long partnerId) {
        log.info("GET /api/partners/{} - Fetching partner", partnerId);

        try {
            PartnerResponse response = partnerService.getPartner(partnerId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Partner not found: {}", partnerId);
            return ResponseEntity.notFound().build();
        }
    }
}
