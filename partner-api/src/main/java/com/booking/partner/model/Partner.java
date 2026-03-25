package com.booking.partner.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Partner Entity - Represents a theater partner/chain in the system
 *
 * This entity follows:
 * - Single Responsibility Principle: Only responsible for partner data
 * - Open/Closed Principle: Can be extended without modification
 *
 * Design Pattern: Domain Model Pattern
 *
 * @author XYZ Booking Platform
 */
@Entity
@Table(name = "PARTNERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Partner {

    /**
     * Unique identifier for the partner
     * Uses Oracle sequence for ID generation
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partner_seq")
    @SequenceGenerator(name = "partner_seq", sequenceName = "PARTNER_SEQ", allocationSize = 1)
    @Column(name = "PARTNER_ID")
    private Long id;

    /**
     * Partner's business name
     * Required field with unique constraint
     */
    @Column(name = "PARTNER_NAME", nullable = false, unique = true, length = 200)
    private String partnerName;

    /**
     * Contact email for the partner
     * Used for communications and notifications
     */
    @Column(name = "EMAIL", nullable = false, unique = true, length = 100)
    private String email;

    /**
     * Contact phone number
     */
    @Column(name = "PHONE", nullable = false, length = 20)
    private String phone;

    /**
     * Partner's business registration number
     */
    @Column(name = "REGISTRATION_NUMBER", unique = true, length = 50)
    private String registrationNumber;

    /**
     * Partnership status: PENDING, ACTIVE, SUSPENDED, TERMINATED
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    private PartnerStatus status;

    /**
     * Date and time when partner was onboarded
     */
    @Column(name = "ONBOARDED_AT", nullable = false)
    private LocalDateTime onboardedAt;

    /**
     * Last update timestamp
     */
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    /**
     * List of theaters owned by this partner
     * One-to-Many relationship with cascade operations
     * Implements aggregation - Partner owns theaters
     */
    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Theater> theaters = new ArrayList<>();

    /**
     * Pre-persist callback to set onboarding timestamp
     */
    @PrePersist
    protected void onCreate() {
        onboardedAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = PartnerStatus.PENDING;
        }
    }

    /**
     * Pre-update callback to update timestamp
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Helper method to add theater to partner
     * Maintains bidirectional relationship
     *
     * @param theater Theater to add
     */
    public void addTheater(Theater theater) {
        theaters.add(theater);
        theater.setPartner(this);
    }

    /**
     * Helper method to remove theater from partner
     *
     * @param theater Theater to remove
     */
    public void removeTheater(Theater theater) {
        theaters.remove(theater);
        theater.setPartner(null);
    }
}
