package com.booking.partner.repository;

import com.booking.partner.model.Show;
import com.booking.partner.model.ShowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

/**
 * Show Repository Interface
 *
 * Implements Repository Pattern for show data access
 *
 * @author XYZ Booking Platform
 */
@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    /**
     * Find shows by screen and date
     *
     * @param screenId Screen identifier
     * @param showDate Show date
     * @return List of shows
     */
    List<Show> findByScreenIdAndShowDate(Long screenId, LocalDate showDate);

    /**
     * Find shows by movie and date
     *
     * @param movieId Movie identifier
     * @param showDate Show date
     * @return List of shows
     */
    List<Show> findByMovieIdAndShowDate(Long movieId, LocalDate showDate);

    /**
     * Find shows by movie, date and status
     *
     * @param movieId Movie identifier
     * @param showDate Show date
     * @param status Show status
     * @return List of shows
     */
    List<Show> findByMovieIdAndShowDateAndStatus(Long movieId, LocalDate showDate, ShowStatus status);

    /**
     * Find shows for a theater on a specific date
     * Joins screen and theater for efficient querying
     *
     * @param theaterId Theater identifier
     * @param showDate Show date
     * @return List of shows with movie and screen details
     */
    @Query("SELECT s FROM Show s " +
           "JOIN FETCH s.movie " +
           "JOIN FETCH s.screen sc " +
           "WHERE sc.theater.id = :theaterId " +
           "AND s.showDate = :showDate " +
           "ORDER BY s.showTime")
    List<Show> findShowsByTheaterAndDate(@Param("theaterId") Long theaterId,
                                         @Param("showDate") LocalDate showDate);

    /**
     * Find bookable shows for a movie in a city
     * Complex query supporting browse functionality
     *
     * @param movieId Movie identifier
     * @param city City name
     * @param showDate Show date
     * @return List of bookable shows
     */
    @Query("SELECT s FROM Show s " +
           "JOIN FETCH s.screen sc " +
           "JOIN FETCH sc.theater t " +
           "WHERE s.movie.id = :movieId " +
           "AND t.city = :city " +
           "AND s.showDate = :showDate " +
           "AND s.status = 'SCHEDULED' " +
           "ORDER BY t.theaterName, s.showTime")
    List<Show> findBookableShowsByMovieAndCityAndDate(@Param("movieId") Long movieId,
                                                        @Param("city") String city,
                                                        @Param("showDate") LocalDate showDate);
}
