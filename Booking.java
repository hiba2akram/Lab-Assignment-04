package com.example.projectapp;

import com.example.projectapp.Flight;

public class Booking {
    private String passengerName;
    private String contactInfo;
    private int numAdults;
    private int numChildren;
    private int numInfants;


    public Booking(String passengerName, String contactInfo, int numAdults, int numChildren, int numInfants, double totalPrice, Flight selectedFlight) {
        this.passengerName = passengerName;
        this.contactInfo = contactInfo;
        this.numAdults = numAdults;
        this.numChildren = numChildren;
        this.numInfants = numInfants;
        this.totalPrice = totalPrice;
        this.selectedFlight = selectedFlight;
    }


    public String getPassengerName() {
        return passengerName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public int getNumAdults() {
        return numAdults;
    }

    public int getNumChildren() {
        return numChildren;
    }

    public int getNumInfants() {
        return numInfants;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Flight getSelectedFlight() {
        return selectedFlight;
    }
    public void setNumInfants(int numInfants) {
        this.numInfants = numInfants;
    }

    public void setNumChildren(int numChildren) {
        this.numChildren = numChildren;
    }

    public void setNumAdults(int numAdults) {
        this.numAdults = numAdults;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setSelectedFlight(Flight selectedFlight) {
        this.selectedFlight = selectedFlight;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    private double totalPrice;
    private Flight selectedFlight;

    @Override
    public String toString() {
        return "Flight ID: " + selectedFlight.getFlightID() + "\n" +
                "Passenger Name: " + passengerName + "\n" +
                "Contact Info: " + contactInfo + "\n" +
                "Adults: " + numAdults + ", Children: " + numChildren + ", Infants: " + numInfants + "\n" +
                "Total Price: $" + totalPrice;
}
}