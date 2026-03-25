package com.booking.partner.service.pricing;

import com.booking.partner.model.SeatCategory;
import java.math.BigDecimal;

/**
 * Pricing Strategy Interface
 *
 * Implements Strategy Pattern for flexible pricing calculation
 * Allows different pricing strategies without modifying client code
 *
 * Benefits:
 * - Open/Closed Principle: Open for extension, closed for modification
 * - Easy to add new pricing strategies (weekend, holiday, dynamic pricing)
 * - Strategy can be changed at runtime
 *
 * @author XYZ Booking Platform
 */
public interface PricingStrategy {

    /**
     * Calculate price for a seat based on base price and category
     *
     * @param basePrice Base show price
     * @param seatCategory Seat category (SILVER, GOLD, PLATINUM, VIP)
     * @return Calculated price
     */
    BigDecimal calculatePrice(BigDecimal basePrice, SeatCategory seatCategory);

    /**
     * Get strategy name for logging/debugging
     *
     * @return Strategy name
     */
    String getStrategyName();
}
