package com.booking.cinebook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * Show Search Request DTO
 *
 * Used for browsing shows by movie, city, and date
 * Implements READ scenario requirement from PDF
 *
 * @author XYZ Booking Platform
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowSearchRequest {

    /**
     * Movie ID to search for
     */
    private Long movieId;

    /**
     * City to search in
     */
    private String city;

    /**
     * Show date
     */
    private LocalDate showDate;

    /**
     * Language filter (optional)
     */
    private String language;

    /**
     * Genre filter (optional)
     */
    private String genre;
}
