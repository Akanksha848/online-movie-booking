package com.booking.partner.service.pricing;

import com.booking.partner.model.SeatCategory;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Weekend Pricing Strategy Implementation
 *
 * Implements Strategy Pattern - Concrete strategy for weekend pricing
 * Applies higher multipliers for weekend shows
 *
 * Weekend Pricing Multipliers:
 * - SILVER: 1.2x (20% premium over weekday)
 * - GOLD: 1.5x
 * - PLATINUM: 1.8x
 * - VIP: 2.5x
 *
 * This demonstrates how different pricing strategies can be easily swapped
 * without changing the client code (Strategy Pattern benefit)
 *
 * @author XYZ Booking Platform
 */
@Component
public class WeekendPricingStrategy implements PricingStrategy {

    /**
     * Calculate price using weekend category multipliers
     *
     * @param basePrice Base show price
     * @param seatCategory Seat category
     * @return Calculated price with weekend multiplier applied
     */
    @Override
    public BigDecimal calculatePrice(BigDecimal basePrice, SeatCategory seatCategory) {
        // Higher multipliers for weekend shows
        BigDecimal multiplier = switch (seatCategory) {
            case SILVER -> BigDecimal.valueOf(1.2);    // 20% premium
            case GOLD -> BigDecimal.valueOf(1.5);      // 50% premium
            case PLATINUM -> BigDecimal.valueOf(1.8);   // 80% premium
            case VIP -> BigDecimal.valueOf(2.5);        // 150% premium
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
        return "WEEKEND_PRICING";
    }
}
