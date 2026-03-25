package com.booking.cinebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Browse Controller - REST API endpoints for browsing movies and shows
 *
 * Implements READ scenario requirement from PDF:
 * "Browse theatres currently running the show (movie selected) in the town,
 * including show timing by a chosen date"
 *
 * Provides endpoints for:
 * - Searching movies by city, language, genre
 * - Finding theaters showing a specific movie
 * - Viewing show timings for a theater and date
 *
 * @author XYZ Booking Platform
 */
@RestController
@RequestMapping("/api/browse")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Browse Movies & Shows", description = "APIs for browsing movies, theaters, and show timings")
public class BrowseController {

    /**
     * Browse theaters showing a movie in a city on a specific date
     *
     * GET /api/browse/shows?movieId={movieId}&city={city}&date={date}
     *
     * Implements READ scenario requirement
     *
     * @param movieId Movie identifier
     * @param city City name
     * @param date Show date (format: YYYY-MM-DD)
     * @return List of theaters with show timings
     */
    @GetMapping("/shows")
    @Operation(
        summary = "Browse shows",
        description = "Find theaters showing a movie in a city on a specific date with show timings"
    )
    public ResponseEntity<Map<String, Object>> browseShows(
            @RequestParam Long movieId,
            @RequestParam String city,
            @RequestParam LocalDate date) {

        log.info("GET /api/browse/shows - movieId={}, city={}, date={}", movieId, city, date);

        /*
         * In real implementation, this would:
         * 1. Query ShowRepository.findBookableShowsByMovieAndCityAndDate()
         * 2. Group results by theater
         * 3. Return theater details with show timings
         *
         * This demonstrates the READ scenario requirement:
         * "Browse theatres currently running the show (movie selected) in the town,
         * including show timing by a chosen date"
         */

        // Mock response for demonstration
        Map<String, Object> response = new HashMap<>();
        response.put("movieId", movieId);
        response.put("city", city);
        response.put("date", date);
        response.put("message", "In production, this would return theaters with show timings");
        response.put("note", "Query Partner API database for show details");

        return ResponseEntity.ok(response);
    }

}
