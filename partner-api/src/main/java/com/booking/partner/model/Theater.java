package com.booking.partner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Theater Entity - Represents a physical theater/cinema
 *
 * Contains theater details, location, and associated screens
 * Implements Single Responsibility: Manages theater-specific data only
 *
 * Design Pattern: Domain Model Pattern
 *
 * @author XYZ Booking Platform
 */
@Entity
@Table(name = "THEATERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Theater {

    /**
     * Unique identifier for the theater
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "theater_seq")
    @SequenceGenerator(name = "theater_seq", sequenceName = "THEATER_SEQ", allocationSize = 1)
    @Column(name = "THEATER_ID")
    private Long id;

    /**
     * Theater name (e.g., "PVR Phoenix Mall")
     */
    @Column(name = "THEATER_NAME", nullable = false, length = 200)
    private String theaterName;

    /**
     * City where theater is located
     * Used for location-based searches
     */
    @Column(name = "CITY", nullable = false, length = 100)
    private String city;

    /**
     * State/Province
     */
    @Column(name = "STATE", nullable = false, length = 100)
    private String state;

    /**
     * Country code (ISO 3166-1 alpha-2)
     * Supports internationalization
     */
    @Column(name = "COUNTRY", nullable = false, length = 2)
    private String country;

    /**
     * Complete address
     */
    @Column(name = "ADDRESS", nullable = false, length = 500)
    private String address;

    /**
     * Postal/ZIP code
     */
    @Column(name = "POSTAL_CODE", length = 20)
    private String postalCode;

    /**
     * Latitude for map integration
     */
    @Column(name = "LATITUDE")
    private Double latitude;

    /**
     * Longitude for map integration
     */
    @Column(name = "LONGITUDE")
    private Double longitude;

    /**
     * Theater contact phone
     */
    @Column(name = "PHONE", length = 20)
    private String phone;

    /**
     * Theater status: ACTIVE, INACTIVE, UNDER_MAINTENANCE
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    private TheaterStatus status;

    /**
     * Whether theater has existing IT system integration
     * Important for integration strategy
     */
    @Column(name = "HAS_LEGACY_SYSTEM")
    private Boolean hasLegacySystem;

    /**
     * Creation timestamp
     */
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Last update timestamp
     */
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    /**
     * Partner who owns this theater
     * Many-to-One relationship
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARTNER_ID", nullable = false)
    private Partner partner;

    /**
     * List of screens in this theater
     * One-to-Many relationship with cascade operations
     */
    @JsonIgnore
    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Screen> screens = new ArrayList<>();

    /**
     * Pre-persist callback
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = TheaterStatus.ACTIVE;
        }
        if (hasLegacySystem == null) {
            hasLegacySystem = false;
        }
    }

    /**
     * Pre-update callback
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Helper method to add screen
     *
     * @param screen Screen to add
     */
    public void addScreen(Screen screen) {
        screens.add(screen);
        screen.setTheater(this);
    }

    /**
     * Helper method to remove screen
     *
     * @param screen Screen to remove
     */
    public void removeScreen(Screen screen) {
        screens.remove(screen);
        screen.setTheater(null);
    }
}
