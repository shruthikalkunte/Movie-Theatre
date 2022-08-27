package com.jpmc.theater;

import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Movie {
    private static final int MOVIE_CODE_SPECIAL = 1;
    private final String title;
    private final String description;
    private final Duration runningTime;
    private final double ticketPrice;
    private final int specialCode;

    public Movie(String title, String description, Duration runningTime, double ticketPrice, int specialCode) {

        if (StringUtils.isBlank(title)) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (runningTime == null) {
            throw new IllegalArgumentException("Running time cannot be null");
        }

        this.title = title;
        this.description = description;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public double calculateTicketPrice(Showing showing) {
        return ticketPrice - getDiscount(showing);
    }

    private double getDiscount(Showing showing) {

        double discountApplied = 0;
        final LocalDateTime showStartTime = showing.getStartTime();

        // 20% discount for special movie
        if (MOVIE_CODE_SPECIAL == specialCode) {
            double discountToApply = ticketPrice * 0.2;
            discountApplied = getMaxDiscount(discountApplied,discountToApply);
        }

        // 25% discount if show start time is between 11AM and 4PM
        LocalDate showDate = showStartTime.toLocalDate();
        LocalDateTime discountStartTime = showDate.atTime(11,0);
        LocalDateTime discountEndTime = showDate.atTime(16,0);

        if(showStartTime.compareTo(discountStartTime) != -1 && showStartTime.compareTo(discountEndTime) != 1 ){
            double discountToApply = ticketPrice * 0.25;
            discountApplied = getMaxDiscount(discountApplied,discountToApply);
        }

        // $3 discount for 1st show
        if (showing.isSequence(1)) {
            double discountToApply = 3;
            discountApplied = getMaxDiscount(discountApplied,discountToApply);
        }

        // $2 discount for 2nd show
        if (showing.isSequence(2)) {
            double discountToApply = 2;
            discountApplied = getMaxDiscount(discountApplied,discountToApply);
        }

        // $1 discount for 7th of every month
        if(showStartTime.getDayOfMonth() == 7){
            double discountToApply = 1;
            discountApplied = getMaxDiscount(discountApplied,discountToApply);
        }
        return discountApplied;
    }

    private double getMaxDiscount(double discount, double discountToApply) {
        return Math.max(discount, discountToApply);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.ticketPrice, ticketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(description, movie.description)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, runningTime, ticketPrice, specialCode);
    }
}