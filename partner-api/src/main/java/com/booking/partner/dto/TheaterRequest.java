package com.booking.partner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Theater Request DTO
 *
 * Used for theater creation/update
 * Implements DTO Pattern
 *
 * @author XYZ Booking Platform
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheaterRequest {

    /**
     * Theater name
     */
    @NotBlank(message = "Theater name is required")
    private String theaterName;

    /**
     * City
     */
    @NotBlank(message = "City is required")
    private String city;

    /**
     * State
     */
    @NotBlank(message = "State is required")
    private String state;

    /**
     * Country code (ISO 3166-1 alpha-2)
     */
    @NotBlank(message = "Country is required")
    private String country;

    /**
     * Complete address
     */
    @NotBlank(message = "Address is required")
    private String address;

    /**
     * Postal code
     */
    private String postalCode;

    /**
     * Latitude for map integration
     */
    private Double latitude;

    /**
     * Longitude for map integration
     */
    private Double longitude;

    /**
     * Theater contact phone
     */
    private String phone;

    /**
     * Whether theater has existing IT system
     */
    @NotNull(message = "Legacy system flag is required")
    private Boolean hasLegacySystem;
}
