package com.booking.partner.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Partner Registration Request DTO
 *
 * Used for partner onboarding API
 * Implements DTO Pattern to decouple API contract from domain model
 *
 * Validation annotations ensure data integrity (Defense in depth)
 *
 * @author XYZ Booking Platform
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartnerRegistrationRequest {

    /**
     * Partner's business name
     * Required field
     */
    @NotBlank(message = "Partner name is required")
    private String partnerName;

    /**
     * Contact email
     * Must be valid email format
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    /**
     * Contact phone number
     * Format validation using regex
     */
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone must be 10-15 digits")
    private String phone;

    /**
     * Business registration number
     * Optional but recommended
     */
    private String registrationNumber;
}
