package com.booking.partner.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Movie Entity - Represents a movie/film
 *
 * Contains movie metadata for display and search
 * Supports localization through language field
 *
 * Design Pattern: Domain Model Pattern
 *
 * @author XYZ Booking Platform
 */
@Entity
@Table(name = "MOVIES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    /**
     * Unique identifier for the movie
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_seq")
    @SequenceGenerator(name = "movie_seq", sequenceName = "MOVIE_SEQ", allocationSize = 1)
    @Column(name = "MOVIE_ID")
    private Long id;

    /**
     * Movie title
     */
    @Column(name = "TITLE", nullable = false, length = 200)
    private String title;

    /**
     * Movie description/synopsis
     */
    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    /**
     * Primary language of the movie
     * Supports localization requirements
     */
    @Column(name = "LANGUAGE", nullable = false, length = 50)
    private String language;

    /**
     * Movie genre (Action, Drama, Comedy, etc.)
     * Comma-separated for multiple genres
     */
    @Column(name = "GENRE", nullable = false, length = 100)
    private String genre;

    /**
     * Movie duration in minutes
     */
    @Column(name = "DURATION_MINUTES", nullable = false)
    private Integer durationMinutes;

    /**
     * Movie release date
     */
    @Column(name = "RELEASE_DATE", nullable = false)
    private LocalDate releaseDate;

    /**
     * Movie rating (U, U/A, A, etc.)
     */
    @Column(name = "RATING", length = 10)
    private String rating;

    /**
     * URL to movie poster image
     */
    @Column(name = "POSTER_URL", length = 500)
    private String posterUrl;

    /**
     * URL to movie trailer
     */
    @Column(name = "TRAILER_URL", length = 500)
    private String trailerUrl;

    /**
     * Movie status
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    private MovieStatus status;

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
     * Pre-persist callback
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = MovieStatus.ACTIVE;
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
     * Check if movie is currently showing
     *
     * @return true if movie is released and active
     */
    public boolean isNowShowing() {
        return status == MovieStatus.ACTIVE &&
               releaseDate != null &&
               !releaseDate.isAfter(LocalDate.now());
    }
}
