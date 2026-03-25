package com.booking.cinebook.service;

import com.booking.cinebook.dto.BookingRequest;
import com.booking.cinebook.dto.BookingResponse;
import com.booking.cinebook.model.Booking;
import com.booking.cinebook.model.BookingSeat;
import com.booking.cinebook.model.BookingStatus;
import com.booking.cinebook.model.Customer;
import com.booking.cinebook.model.PaymentStatus;
import com.booking.cinebook.repository.BookingRepository;
import com.booking.cinebook.repository.CustomerRepository;
import com.booking.cinebook.service.payment.PaymentException;
import com.booking.cinebook.service.payment.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Booking Service - Business logic for ticket booking
 *
 * Implements Service Layer Pattern with transaction management
 * Handles complete booking flow:
 * 1. Validate show and seats
 * 2. Create/fetch customer
 * 3. Calculate pricing
 * 4. Process payment
 * 5. Confirm booking
 *
 * Implements WRITE scenario requirement from PDF
 *
 * @author XYZ Booking Platform
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final PaymentStrategy paymentApi;

    /**
     * Create a new booking
     *
     * Transactional to ensure atomicity:
     * - Either complete booking succeeds, or everything rolls back
     * - Prevents partial bookings and orphaned seat locks
     *
     * @param request Booking details
     * @return Booking confirmation
     * @throws IllegalArgumentException if validation fails
     * @throws IllegalStateException if booking cannot be completed
     */
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        log.info("Creating booking: showId={}, seats={}", request.getShowId(), request.getSeatIds().size());

        // Step 1: Validate request
        if (request.getSeatIds().isEmpty()) {
            throw new IllegalArgumentException("No seats selected");
        }

        // Step 2: Get or create customer
        Customer customer = getOrCreateCustomer(request);

        // Step 3: Calculate total amount
        // In real implementation, fetch seat prices from Partner API
        BigDecimal totalAmount = calculateTotalAmount(request.getSeatIds());

        // Step 4: Create booking
        String bookingReference = generateBookingReference();

        Booking booking = Booking.builder()
                .bookingReference(bookingReference)
                .customer(customer)
                .showId(request.getShowId())
                .totalAmount(totalAmount)
                .seatCount(request.getSeatIds().size())
                .status(BookingStatus.PENDING)
                .paymentStatus(PaymentStatus.PENDING)
                .paymentMethod(request.getPaymentMethod())
                .build();

        // Step 5: Add seats to booking
        for (Long seatId : request.getSeatIds()) {
            BookingSeat bookingSeat = BookingSeat.builder()
                    .seatId(seatId)
                    .seatIdentifier("SEAT-" + seatId) // In real impl, fetch from Partner API
                    .price(BigDecimal.valueOf(200.00)) // Mock price
                    .build();
            booking.addBookingSeat(bookingSeat);
        }

        // Step 6: Save booking
        Booking savedBooking = bookingRepository.save(booking);

        // Step 7: Process payment
        try {
            String transactionId = paymentApi.processPayment(
                    totalAmount,
                    customer.getEmail(),
                    request.getPaymentMethod()
            );

            // Step 8: Confirm booking on successful payment
            savedBooking.setPaymentTransactionId(transactionId);
            savedBooking.confirm();
            bookingRepository.save(savedBooking);

            log.info("Booking confirmed: bookingId={}, reference={}", savedBooking.getId(), bookingReference);

        } catch (PaymentException e) {
            log.error("Payment failed for booking: reference={}, error={}", bookingReference, e.getMessage());

            // Cancel booking on payment failure
            savedBooking.cancel();
            savedBooking.setPaymentStatus(PaymentStatus.FAILED);
            bookingRepository.save(savedBooking);

            throw new IllegalStateException("Payment failed: " + e.getMessage());
        }

        // Step 9: Return response
        return convertToResponse(savedBooking);
    }

    /**
     * Get booking by reference
     *
     * @param bookingReference Booking reference code
     * @return Booking response
     * @throws IllegalArgumentException if booking not found
     */
    @Transactional(readOnly = true)
    public BookingResponse getBooking(String bookingReference) {
        log.debug("Fetching booking: reference={}", bookingReference);

        Booking booking = bookingRepository.findByBookingReference(bookingReference)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingReference));

        return convertToResponse(booking);
    }

    /**
     * Cancel booking
     *
     * @param bookingReference Booking reference code
     * @return Updated booking response
     */
    @Transactional
    public BookingResponse cancelBooking(String bookingReference) {
        log.info("Cancelling booking: reference={}", bookingReference);

        Booking booking = bookingRepository.findByBookingReference(bookingReference)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingReference));

        // Business rule: Can only cancel PENDING or CONFIRMED bookings
        if (booking.getStatus() == BookingStatus.CANCELLED ||
            booking.getStatus() == BookingStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel booking in status: " + booking.getStatus());
        }

        booking.cancel();
        Booking updatedBooking = bookingRepository.save(booking);

        log.info("Booking cancelled: bookingId={}", booking.getId());

        return convertToResponse(updatedBooking);
    }

    /**
     * Get or create customer
     * Reuses existing customer if email matches
     *
     * @param request Booking request with customer details
     * @return Customer entity
     */
    private Customer getOrCreateCustomer(BookingRequest request) {
        return customerRepository.findByEmail(request.getCustomerEmail())
                .orElseGet(() -> {
                    Customer newCustomer = Customer.builder()
                            .name(request.getCustomerName())
                            .email(request.getCustomerEmail())
                            .phone(request.getCustomerPhone())
                            .build();
                    return customerRepository.save(newCustomer);
                });
    }

    /**
     * Calculate total booking amount
     * In real implementation, would fetch seat prices from Partner API
     *
     * @param seatIds List of seat IDs
     * @return Total amount
     */
    private BigDecimal calculateTotalAmount(List<Long> seatIds) {
        // Mock calculation: 200 per seat
        return BigDecimal.valueOf(200.00).multiply(BigDecimal.valueOf(seatIds.size()));
    }

    /**
     * Generate unique booking reference
     *
     * @return Booking reference code
     */
    private String generateBookingReference() {
        return "BKG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    /**
     * Convert Booking entity to BookingResponse DTO
     *
     * @param booking Booking entity
     * @return Booking response DTO
     */
    private BookingResponse convertToResponse(Booking booking) {
        List<String> seatIdentifiers = booking.getBookingSeats().stream()
                .map(BookingSeat::getSeatIdentifier)
                .collect(Collectors.toList());

        return BookingResponse.builder()
                .bookingId(booking.getId())
                .bookingReference(booking.getBookingReference())
                .customerName(booking.getCustomer().getName())
                .showId(booking.getShowId())
                .seatIdentifiers(seatIdentifiers)
                .totalAmount(booking.getTotalAmount())
                .status(booking.getStatus())
                .paymentStatus(booking.getPaymentStatus())
                .bookedAt(booking.getBookedAt())
                .expiresAt(booking.getExpiresAt())
                .build();
    }
}
