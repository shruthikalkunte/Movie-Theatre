package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservationTests {
    final Customer testCustomer = new Customer("John Doe", "unused-id");
    final Movie testMovie = new Movie("Spider-Man: No Way Home", "2021 American superhero film based on the Marvel Comics character Spider-Man", Duration.ofMinutes(90), 12.5, 1);
    final Showing testShowing = new Showing(testMovie, 1, LocalDateTime.now());

    @Test
    void nullCustomer_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Reservation(null, testShowing, 3));
    }

    @Test
    void nullShowing_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Reservation(testCustomer, null, 3));
    }

    @Test
    void totalFeeAccountingForAllDiscount() {
        /*
         * Expected totalFee = (12.5 - $3) x 3 = 28.5
         * (First show discount > special code discount for ticket value of $12.5)
         */
        assertEquals(28.5, new Reservation(testCustomer, testShowing, 3).totalFee());
    }
}
