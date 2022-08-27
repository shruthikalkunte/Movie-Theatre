package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShowingTests {

    final Movie testMovie = new Movie("Spider-Man: No Way Home", "2021 American superhero film based on the Marvel Comics character Spider-Man", Duration.ofMinutes(90), 12.5, 1);

    @Test
    void nullMovie_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Showing(null, 1, LocalDateTime.now()));
    }

    @Test
    void nullShowStartTime_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Showing(testMovie, 1, null));
    }

    @Test
    void checkIsSequence() {
        final Showing testShowing = new Showing(testMovie, 1, LocalDateTime.now());

        assertTrue(testShowing.isSequence(1));
    }

    @Test
    void checkCalculateFee() {
        final Showing testShowing = new Showing(testMovie, 1, LocalDateTime.now());

        /*
         * Expected calculateFee = (12.5 - $3) x 10 = 95
         * (First show discount > special code discount for ticket value of $12.5)
         */
        assertEquals(95, testShowing.calculateFee(10));
    }
}
