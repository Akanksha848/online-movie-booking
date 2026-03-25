package com.booking.cinebook.dto;

import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Booking Request DTO
 *
 * Used for creating a new booking
 * Implements WRITE scenario requirement from PDF
 *
 * @author XYZ Booking Platform
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {

    /**
     * Customer details
     */
    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Customer email is required")
    @Email(message = "Email must be valid")
    private String customerEmail;

    @NotBlank(message = "Customer phone is required")
    private String customerPhone;

    /**
     * Show ID for booking
     */
    @NotNull(message = "Show ID is required")
    private Long showId;

    /**
     * List of seat IDs to book
     */
    @NotEmpty(message = "At least one seat must be selected")
    private List<Long> seatIds;

    /**
     * Payment method
     */
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
}
