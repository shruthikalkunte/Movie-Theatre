package com.jpmc.theater;

import java.time.LocalDateTime;

public class Showing {
    private final Movie movie;
    private final int sequenceOfTheDay;
    private final LocalDateTime showStartTime;

    public Showing(final Movie movie, final int sequenceOfTheDay, final LocalDateTime showStartTime) {
        if (movie == null || showStartTime == null) {
            throw new IllegalArgumentException("Movie or showStartTime cannot be null.");
        }

        this.movie = movie;
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartTime = showStartTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getStartTime() {
        return showStartTime;
    }

    public boolean isSequence(final int sequence) {
        return this.sequenceOfTheDay == sequence;
    }

    public double getMovieFee() {
        return movie.getTicketPrice();
    }

    public int getSequenceOfTheDay() {
        return sequenceOfTheDay;
    }

    public double calculateFee(final int audienceCount) {
        return movie.calculateTicketPrice(this) * audienceCount;
    }
}
