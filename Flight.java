package com.example.projectapp;


import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;

public class Flight {
    private final StringProperty flightID;
    private final StringProperty departureCity;
    private final StringProperty destinationCity;
    private final StringProperty flightClass;
    private final StringProperty date;
    private final DoubleProperty price;
    private final StringProperty departureAirport;
    private final StringProperty destinationAirport;
    private final BooleanProperty booked;
    private final List<Seat> seats;

    public Flight(String flightID, String departureCity, String destinationCity, String flightClass,
                  String date, double price, String departureAirport, String destinationAirport) {
        this.flightID = new SimpleStringProperty(flightID);
        this.departureCity = new SimpleStringProperty(departureCity);
        this.destinationCity = new SimpleStringProperty(destinationCity);
        this.flightClass = new SimpleStringProperty(flightClass);
        this.date = new SimpleStringProperty(date);
        this.price = new SimpleDoubleProperty(price);
        this.departureAirport = new SimpleStringProperty(departureAirport);
        this.destinationAirport = new SimpleStringProperty(destinationAirport);
        this.booked = new SimpleBooleanProperty(false);
        this.seats = createsampleseats();


        for (int i = 0; i < 10; i++) {
            seats.add(new Seat("i" + 1,true,true)); // Seat numbering starts from 1
        }
    }

    private List<Seat> createsampleseats() {
        List<Seat> seats = new ArrayList<>();
        seats.add(new Seat("1A",true,false));
        seats.add(new Seat("1B",false,true));
        seats.add(new Seat("2A",true,false));
        seats.add(new Seat("2B",false,true));
        seats.add(new Seat("3A",false,true));
        seats.add(new Seat("3B",true,false));
        return seats;
    }
    public StringProperty flightIDProperty() {
        return flightID;
    }
    public StringProperty departureCityProperty() {
        return departureCity;
    }

    public StringProperty destinationCityProperty() {
        return destinationCity;
    }

    public StringProperty flightClassProperty() {
        return flightClass;
    }

    public StringProperty dateProperty() {
        return date;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public StringProperty departureAirportProperty() {
        return departureAirport;
    }

    public StringProperty destinationAirportProperty() {
        return destinationAirport;
    }

    public BooleanProperty bookedProperty() {
        return booked;
    }

    public String getFlightClass() {
        return flightClass.get();
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public String getDepartureAirport() {
        return departureAirport.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getDepartureCity() {
        return departureCity.get();
    }

    public String getDestinationAirport() {
        return destinationAirport.get();
    }

    public String getDestinationCity() {
        return destinationCity.get();
    }

    public String getFlightID() {
        return flightID.get();
    }

    public boolean isBooked() {
        return booked.get();
    }

    public double getPrice() {
        return price.get();
    }

    public boolean isSeatAvailable(int seatNumber) {
        if (seatNumber < 1 || seatNumber > seats.size()) return false;
        return seats.get(seatNumber - 1).isAvailable();
    }

    public void bookSeat(int seatNumber) {
        if (isSeatAvailable(seatNumber)) {
            seats.get(seatNumber - 1).setAvailable(false);
        }
    }

    @Override
    public String toString() {
        return "Flight " + flightID.get() + " from " + departureCity.get() + " to " + destinationCity.get() +
                " on " + date.get() + " in " + flightClass.get() + " class, " + "Price: " + price.get() + " PKR.";
    }

    public void setBooked(boolean b) {
    }


}



