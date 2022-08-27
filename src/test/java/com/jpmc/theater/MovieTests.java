package com.jpmc.theater;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MovieTests {

    @Test
    void nullOrEmptyTitle_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Movie(null, "2021 American superhero film based on the Marvel Comics character Spider-Man", Duration.ofMinutes(90),12.5, 1));
        assertThrows(IllegalArgumentException.class, () -> new Movie(StringUtils.EMPTY, "2021 American superhero film based on the Marvel Comics character Spider-Man", Duration.ofMinutes(90),12.5, 1));
    }

    @Test
    void nullRunningTime_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Movie("Spider-Man: No Way Home", "2021 American superhero film based on the Marvel Comics character Spider-Man", null,12.5, 1));
    }

    @Test
    void specialMovieCode_expected20PercentDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", "2021 American superhero film based on the Marvel Comics character Spider-Man", Duration.ofMinutes(90),12.5, 1);
        Showing showing = new Showing(spiderMan, 5, LocalDate.now().atTime(10,0));

        assertEquals(10, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    void movieStartingAt4PM_expected25PercentDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", "2021 American superhero film based on the Marvel Comics character Spider-Man", Duration.ofMinutes(90),12.5, 1);
        Showing showing = new Showing(spiderMan, 5, LocalDate.now().atTime(16,0));

        assertEquals(9.375, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    void movieStartingAt11AM_expected25PercentDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", "2021 American superhero film based on the Marvel Comics character Spider-Man", Duration.ofMinutes(90),12.5, 0);
        Showing showing = new Showing(spiderMan, 5, LocalDate.now().atTime(11,0));

        assertEquals(9.375, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    void firstShowOfTheDay_expected3DollarDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", "2021 American superhero film based on the Marvel Comics character Spider-Man", Duration.ofMinutes(90),12.5, 0);
        Showing showing = new Showing(spiderMan, 1, LocalDate.now().atTime(10,0));

        assertEquals(9.5, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    void secondShowOfTheDay_expected2DollarDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", "2021 American superhero film based on the Marvel Comics character Spider-Man", Duration.ofMinutes(90),12.5, 0);
        Showing showing = new Showing(spiderMan, 2, LocalDate.now().atTime(10,0));

        assertEquals(10.5, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    void seventhOfTheMonth_expected1DollarDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", "2021 American superhero film based on the Marvel Comics character Spider-Man", Duration.ofMinutes(90),12.5, 0);
        Showing showing = new Showing(spiderMan, 6, LocalDate.now().withDayOfMonth(7).atTime(10,0));

        assertEquals(11.5, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    void maxDiscountApplied_whenMultipleDiscountsAreValid() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", "2021 American superhero film based on the Marvel Comics character Spider-Man", Duration.ofMinutes(90),12.5, 1);
        Showing showing = new Showing(spiderMan, 1, LocalDate.now().withDayOfMonth(7).atTime(11,0));

        assertEquals(9.375, spiderMan.calculateTicketPrice(showing));
    }
}
