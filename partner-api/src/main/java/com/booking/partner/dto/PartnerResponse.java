package com.booking.partner.dto;

import com.booking.partner.model.PartnerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Partner Response DTO
 *
 * Returns partner details without exposing internal structure
 * Implements DTO Pattern
 *
 * @author XYZ Booking Platform
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartnerResponse {

    /**
     * Partner identifier
     */
    private Long id;

    /**
     * Partner name
     */
    private String partnerName;

    /**
     * Contact email
     */
    private String email;

    /**
     * Contact phone
     */
    private String phone;

    /**
     * Registration number
     */
    private String registrationNumber;

    /**
     * Current status
     */
    private PartnerStatus status;

    /**
     * Onboarding timestamp
     */
    private LocalDateTime onboardedAt;

    /**
     * Number of theaters owned
     */
    private Integer theaterCount;
}
