package com.jpmc.theater;

public class Reservation {
    private Customer customer;
    private Showing showing;
    private int ticketCount;

    public Reservation(Customer customer, Showing showing, int ticketCount) {
        if (customer == null || showing == null) {
            throw new IllegalArgumentException("Customer or showing cannot be null.");
        }
        this.customer = customer;
        this.showing = showing;
        this.ticketCount = ticketCount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Showing getShowing() {
        return showing;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public double totalFee() {
        return showing.calculateFee(ticketCount);
    }
}