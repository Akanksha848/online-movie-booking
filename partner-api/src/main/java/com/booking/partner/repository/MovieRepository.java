package com.booking.partner.repository;

import com.booking.partner.model.Movie;
import com.booking.partner.model.MovieStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Movie Repository Interface
 *
 * Implements Repository Pattern for movie data access
 *
 * @author XYZ Booking Platform
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    /**
     * Find movies by status
     *
     * @param status Movie status
     * @return List of movies with given status
     */
    List<Movie> findByStatus(MovieStatus status);

    /**
     * Find movies by language
     * Supports localization requirement
     *
     * @param language Movie language
     * @return List of movies in given language
     */
    List<Movie> findByLanguage(String language);

    /**
     * Find movies by genre
     *
     * @param genre Movie genre
     * @return List of movies in genre
     */
    @Query("SELECT m FROM Movie m WHERE LOWER(m.genre) LIKE LOWER(CONCAT('%', :genre, '%'))")
    List<Movie> findByGenreContaining(@Param("genre") String genre);

    /**
     * Search movies by title (case-insensitive)
     *
     * @param title Movie title pattern
     * @return List of matching movies
     */
    @Query("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%')) AND m.status = 'ACTIVE'")
    List<Movie> searchActiveMoviesByTitle(@Param("title") String title);

    /**
     * Find currently showing movies
     *
     * @return List of active movies that have been released
     */
    @Query("SELECT m FROM Movie m WHERE m.status = 'ACTIVE' AND m.releaseDate <= CURRENT_DATE")
    List<Movie> findNowShowingMovies();
}
