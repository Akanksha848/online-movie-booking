package com.booking.partner.controller;

import com.booking.partner.dto.TheaterRequest;
import com.booking.partner.model.Theater;
import com.booking.partner.service.TheaterService;
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
 * Theater Controller - REST API endpoints for theater management
 *
 * Handles theater CRUD operations for partners
 *
 * @author XYZ Booking Platform
 */
@RestController
@RequestMapping("/api/partners/{partnerId}/theaters")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Theater Management", description = "APIs for managing theaters")
public class TheaterController {

    private final TheaterService theaterService;

    /**
     * Create a new theater for a partner
     *
     * POST /api/partners/{partnerId}/theaters
     *
     * @param partnerId Partner identifier
     * @param request Theater details
     * @return Created theater with HTTP 201 status
     */
    @PostMapping
    @Operation(summary = "Create theater", description = "Add a new theater for a partner")
    public ResponseEntity<Theater> createTheater(@PathVariable Long partnerId,
                                                  @Valid @RequestBody TheaterRequest request) {
        log.info("POST /api/partners/{}/theaters - Creating theater: {}", partnerId, request.getTheaterName());

        try {
            Theater theater = theaterService.createTheater(partnerId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(theater);
        } catch (IllegalArgumentException | IllegalStateException e) {
            log.error("Theater creation failed: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get all theaters for a partner
     *
     * GET /api/partners/{partnerId}/theaters
     *
     * @param partnerId Partner identifier
     * @return List of theaters
     */
    @GetMapping
    @Operation(summary = "Get partner theaters", description = "Retrieve all theaters owned by a partner")
    public ResponseEntity<List<Theater>> getPartnerTheaters(@PathVariable Long partnerId) {
        log.info("GET /api/partners/{}/theaters - Fetching theaters", partnerId);

        List<Theater> theaters = theaterService.getPartnerTheaters(partnerId);
        return ResponseEntity.ok(theaters);
    }

    /**
     * Get theater by ID
     *
     * GET /api/partners/{partnerId}/theaters/{theaterId}
     *
     * @param partnerId Partner identifier (path context)
     * @param theaterId Theater identifier
     * @return Theater details
     */
    @GetMapping("/{theaterId}")
    @Operation(summary = "Get theater by ID", description = "Retrieve details of a specific theater")
    public ResponseEntity<Theater> getTheater(@PathVariable Long partnerId,
                                              @PathVariable Long theaterId) {
        log.info("GET /api/partners/{}/theaters/{} - Fetching theater", partnerId, theaterId);

        try {
            Theater theater = theaterService.getTheater(theaterId);
            return ResponseEntity.ok(theater);
        } catch (IllegalArgumentException e) {
            log.error("Theater not found: {}", theaterId);
            return ResponseEntity.notFound().build();
        }
    }
}
