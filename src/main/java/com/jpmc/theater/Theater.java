package com.jpmc.theater;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Theater {

    final LocalDateProvider provider;
    private final List<Showing> schedule;

    public Theater(final LocalDateProvider provider) {
        if (provider == null) {
            throw new IllegalArgumentException("LocalDateProvider cannot be null");
        }

        this.provider = provider;

        Movie spiderMan = new Movie("Spider-Man: No Way Home", "2021 American superhero film based on the Marvel Comics character Spider-Man", Duration.ofMinutes(90), 12.5, 1);
        Movie turningRed = new Movie("Turning Red", "2022 American computer-animated fantasy comedy film", Duration.ofMinutes(85), 11, 0);
        Movie theBatMan = new Movie("The Batman", "2022 American superhero film based on the DC Comics character Batman", Duration.ofMinutes(95), 9, 0);
        schedule = ImmutableList.of(
            new Showing(turningRed, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))),
            new Showing(spiderMan, 2, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0))),
            new Showing(theBatMan, 3, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50))),
            new Showing(turningRed, 4, LocalDateTime.of(provider.currentDate(), LocalTime.of(14, 30))),
            new Showing(spiderMan, 5, LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10))),
            new Showing(theBatMan, 6, LocalDateTime.of(provider.currentDate(), LocalTime.of(17, 50))),
            new Showing(turningRed, 7, LocalDateTime.of(provider.currentDate(), LocalTime.of(19, 30))),
            new Showing(spiderMan, 8, LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10))),
            new Showing(theBatMan, 9, LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 0)))
        );
    }

    public Reservation reserve(final Customer customer, final int sequence, final int howManyTickets) {
        final Showing showing;
        try {
            showing = schedule.get(sequence - 1);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalStateException("Unable to find any showing for the given sequence " + sequence);
        }
        return new Reservation(customer, showing, howManyTickets);
    }

    public void printSchedule() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("===================================================");
        System.out.println("============    MOVIE SCHEDULE   ==================");
        System.out.println("===================================================");
        schedule.forEach(s -> {
                System.out.println("##### Showing #" + s.getSequenceOfTheDay() + " #####");
                System.out.println(s.getSequenceOfTheDay() + ": " + DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(s.getStartTime()) + " " + s.getMovie().getTitle() + " " + humanReadableFormat(s.getMovie().getRunningTime()) + " $" + s.getMovieFee());
                System.out.println("--------------------------------------");
                System.out.println(gson.toJson(s));
                System.out.println("--------------------------------------");
            }
        );
        System.out.println("===================================================");
    }

    public String humanReadableFormat(final Duration duration) {
        if (duration == null) {
            throw new IllegalArgumentException("Duration cannot be null.");
        }

        final long hour = duration.toHours();
        final long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    // (s) postfix should be added to handle plural correctly
    private String handlePlural(final long value) {
        if (value == 1) {
            return "";
        }
        return "s";
    }

    public static void main(final String[] args) {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printSchedule();
    }
}
