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
 * Screen Entity - Represents a screen/auditorium within a theater
 *
 * Contains screen configuration, seating capacity, and show schedules
 *
 * Design Pattern: Domain Model Pattern
 *
 * @author XYZ Booking Platform
 */
@Entity
@Table(name = "SCREENS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Screen {

    /**
     * Unique identifier for the screen
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "screen_seq")
    @SequenceGenerator(name = "screen_seq", sequenceName = "SCREEN_SEQ", allocationSize = 1)
    @Column(name = "SCREEN_ID")
    private Long id;

    /**
     * Screen name/number (e.g., "Screen 1", "IMAX Hall")
     */
    @Column(name = "SCREEN_NAME", nullable = false, length = 100)
    private String screenName;

    /**
     * Screen type: REGULAR, IMAX, 3D, 4DX, etc.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "SCREEN_TYPE", nullable = false, length = 20)
    private ScreenType screenType;

    /**
     * Total seating capacity
     */
    @Column(name = "SEATING_CAPACITY", nullable = false)
    private Integer seatingCapacity;

    /**
     * Screen status
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    private ScreenStatus status;

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
     * Theater that owns this screen
     * Many-to-One relationship
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "THEATER_ID", nullable = false)
    private Theater theater;

    /**
     * List of seats in this screen
     * One-to-Many relationship with cascade operations
     */
    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Seat> seats = new ArrayList<>();

    /**
     * List of shows scheduled for this screen
     * One-to-Many relationship
     */
    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Show> shows = new ArrayList<>();

    /**
     * Pre-persist callback
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = ScreenStatus.ACTIVE;
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
     * Helper method to add seat
     *
     * @param seat Seat to add
     */
    public void addSeat(Seat seat) {
        seats.add(seat);
        seat.setScreen(this);
    }

    /**
     * Helper method to add show
     *
     * @param show Show to add
     */
    public void addShow(Show show) {
        shows.add(show);
        show.setScreen(this);
    }
}
