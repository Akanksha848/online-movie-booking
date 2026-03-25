package com.booking.cinebook.controller;

import com.booking.cinebook.dto.BookingRequest;
import com.booking.cinebook.dto.BookingResponse;
import com.booking.cinebook.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Booking Controller - REST API endpoints for ticket booking
 *
 * Implements WRITE scenario requirement from PDF:
 * "Book movie tickets by selecting a theatre, timing, and preferred seats for the day"
 *
 * Follows REST API best practices:
 * - Proper HTTP methods (POST for create, GET for retrieve, DELETE for cancel)
 * - Appropriate status codes
 * - Request/Response validation
 *
 * @author XYZ Booking Platform
 */
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    /**
     * Create a new booking
     *
     * POST /api/bookings
     *
     * @param request Booking details including show, seats, and customer info
     * @return Booking confirmation with HTTP 201 status
     */
    @PostMapping
    @Operation(summary = "Book tickets", description = "Create a new ticket booking for a show")
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {
        log.info("POST /api/bookings - Creating booking for show: {}", request.getShowId());

        try {
            BookingResponse response = bookingService.createBooking(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            log.error("Booking validation failed: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            log.error("Booking failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get booking by reference
     *
     * GET /api/bookings/{bookingReference}
     *
     * @param bookingReference Booking reference code
     * @return Booking details
     */
    @GetMapping("/{bookingReference}")
    @Operation(summary = "Get booking", description = "Retrieve booking details by reference code")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable String bookingReference) {
        log.info("GET /api/bookings/{} - Fetching booking", bookingReference);

        try {
            BookingResponse response = bookingService.getBooking(bookingReference);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Booking not found: {}", bookingReference);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Cancel booking
     *
     * DELETE /api/bookings/{bookingReference}
     *
     * @param bookingReference Booking reference code
     * @return Updated booking details
     */
    @DeleteMapping("/{bookingReference}")
    @Operation(summary = "Cancel booking", description = "Cancel an existing booking")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable String bookingReference) {
        log.info("DELETE /api/bookings/{} - Cancelling booking", bookingReference);

        try {
            BookingResponse response = bookingService.cancelBooking(bookingReference);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Booking not found: {}", bookingReference);
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            log.error("Cannot cancel booking: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
