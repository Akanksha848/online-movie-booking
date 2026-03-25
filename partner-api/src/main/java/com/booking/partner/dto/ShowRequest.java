package com.booking.partner.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Show Request DTO
 *
 * Used for creating/scheduling shows
 * Implements DTO Pattern with validation
 *
 * @author XYZ Booking Platform
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowRequest {

    /**
     * Movie ID to show
     */
    @NotNull(message = "Movie ID is required")
    private Long movieId;

    /**
     * Screen ID where show will be scheduled
     */
    @NotNull(message = "Screen ID is required")
    private Long screenId;

    /**
     * Date of the show
     */
    @NotNull(message = "Show date is required")
    @FutureOrPresent(message = "Show date must be today or in future")
    private LocalDate showDate;

    /**
     * Start time of the show
     */
    @NotNull(message = "Show time is required")
    private LocalTime showTime;

    /**
     * Base price for the show
     */
    @NotNull(message = "Base price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Base price must be positive")
    private BigDecimal basePrice;
}
