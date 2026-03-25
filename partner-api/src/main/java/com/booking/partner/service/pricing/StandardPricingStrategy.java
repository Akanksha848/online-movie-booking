package com.booking.partner.service.pricing;

import com.booking.partner.model.SeatCategory;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Standard Pricing Strategy Implementation
 *
 * Implements Strategy Pattern - Concrete strategy for standard pricing
 * Applies category-based multipliers to base price
 *
 * Pricing Multipliers:
 * - SILVER: 1.0x (base price)
 * - GOLD: 1.25x
 * - PLATINUM: 1.5x
 * - VIP: 2.0x
 *
 * @author XYZ Booking Platform
 */
@Component
public class StandardPricingStrategy implements PricingStrategy {

    /**
     * Calculate price using standard category multipliers
     *
     * @param basePrice Base show price
     * @param seatCategory Seat category
     * @return Calculated price with category multiplier applied
     */
    @Override
    public BigDecimal calculatePrice(BigDecimal basePrice, SeatCategory seatCategory) {
        // Determine multiplier based on seat category
        BigDecimal multiplier = switch (seatCategory) {
            case SILVER -> BigDecimal.valueOf(1.0);    // No premium
            case GOLD -> BigDecimal.valueOf(1.25);      // 25% premium
            case PLATINUM -> BigDecimal.valueOf(1.5);   // 50% premium
            case VIP -> BigDecimal.valueOf(2.0);        // 100% premium
        };

        // Calculate final price: basePrice * multiplier
        return basePrice.multiply(multiplier)
                       .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Get strategy name
     *
     * @return Strategy identifier
     */
    @Override
    public String getStrategyName() {
        return "STANDARD_PRICING";
    }
}
