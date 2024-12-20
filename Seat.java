
package com.example.projectapp;

import javafx.beans.property.*;

public  class Seat {
    private final IntegerProperty seatNumber;
    private boolean isWindowSeat;
    private boolean isAisleSeat;
    private final BooleanProperty available;

    public Seat(String seatNumber,boolean isWindowSeat,boolean isAisleSeat) {
        this.seatNumber = new SimpleIntegerProperty();
        this.isWindowSeat = isWindowSeat();
        this.isAisleSeat = isAisleSeat();
        this.available =new SimpleBooleanProperty(true);
    }

    public int getSeatNumber() {
        return seatNumber.get();
    }

    public boolean isAvailable() {
        return available.get();
    }

    public void setAvailable(boolean available) {
        this.available.set(available);
    }

    public BooleanProperty availableProperty() {
        return available;
    }

    public void bookSeat() {
    }
    public boolean isWindowSeat() {
        return isWindowSeat;
    }
    public boolean isAisleSeat() {
        return isAisleSeat;
    }
    @Override
    public String toString() {
        return "Seat Number: " + seatNumber.get()
                + ", Window Seat: " + (isWindowSeat ? "Yes" : "No")
                + ", Aisle Seat: " + (isAisleSeat ? "Yes" : "No");
    }


}
