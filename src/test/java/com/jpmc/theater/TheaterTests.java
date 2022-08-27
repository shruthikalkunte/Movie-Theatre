package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TheaterTests {

    @Test
    void nullLocalDateTimeProvider_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Theater(null));
    }

    @Test
    void testReserve_nullCustomer_throwsIllegalArgumentException() {
        Theater theater = new Theater(LocalDateProvider.singleton());

        assertThrows(IllegalArgumentException.class, () -> theater.reserve(null, 2, 4));
    }

    @Test
    void testReserve_success() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer john = new Customer("John Doe", "id-12345");
        Reservation reservation = theater.reserve(john, 2, 4);

        assertEquals(john, reservation.getCustomer());
        assertEquals(2, reservation.getShowing().getSequenceOfTheDay());
        assertEquals(4, reservation.getTicketCount());
        /*
         * Sequence = 2 which refers to Spider-man movie starting at 11AM
         * Total Fee = ($12.5 - 25%) x 4
         */
        assertEquals(37.5, reservation.totalFee());
    }

    @Test
    void testPrintMovieSchedule_success() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printSchedule();
    }

    @Test
    void testHumanReadableFormat_nullDuration_throwsIllegalArgumentException() {
        Theater theater = new Theater(LocalDateProvider.singleton());

        assertThrows(IllegalArgumentException.class, () -> theater.humanReadableFormat(null));
    }

    @Test
    void testHumanReadableFormat_validDuration_success() {
        Theater theater = new Theater(LocalDateProvider.singleton());

        assertEquals("(1 hour 30 minutes)", theater.humanReadableFormat(Duration.ofMinutes(90)));
    }
}
