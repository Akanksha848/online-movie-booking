package com.booking.cinebook.service.payment;

/**
 * Payment Exception
 *
 * Custom exception for payment processing errors
 *
 * @author XYZ Booking Platform
 */
public class PaymentException extends Exception {

    /**
     * Constructor with message
     *
     * @param message Error message
     */
    public PaymentException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     *
     * @param message Error message
     * @param cause Root cause
     */
    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }
}
