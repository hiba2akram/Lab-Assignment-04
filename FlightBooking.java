package com.example.projectapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FlightBooking {

    public static void openBookingForm(Stage primaryStage, Flight selectedFlight) {
        if (selectedFlight == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Booking Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a flight before proceeding with the booking.");
            alert.showAndWait();
            return;
        }


        Stage bookingStage = new Stage();

        bookingStage.setTitle("Flight Booking");



        VBox layout = new VBox(20);
        layout.setStyle("-fx-background-color: #2c3e50;-fx-font-weight: bold;");
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label("Booking for Flight: " + selectedFlight.getFlightID());
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label detailsLabel = new Label(selectedFlight.toString());
        detailsLabel.setTextFill(Color.WHITE);
        detailsLabel.setStyle("-fx-font-size: 16px;");

        Label passengerNameLabel = new Label("Passenger Name:");
        passengerNameLabel.setAlignment(Pos.CENTER);
        passengerNameLabel.setTextFill(Color.WHITE);
        TextField passengerNameField = new TextField();
        passengerNameLabel.setPrefWidth(100);

        Label contactLabel = new Label("Contact Information:");
        contactLabel.setTextFill(Color.WHITE);
        TextField contactField = new TextField();
        contactField.setPrefWidth(300);

        Label adultLabel = new Label("Number of Adults:");
        adultLabel.setTextFill(Color.WHITE);
        Spinner<Integer> adultSpinner = new Spinner<>(1, 10, 1);
        adultSpinner.setPrefWidth(150);

        Label childLabel = new Label("Number of Children:");
        childLabel.setTextFill(Color.WHITE);

        Spinner<Integer> childSpinner = new Spinner<>(0, 10, 0);
        childSpinner.setPrefWidth(150);

        Label infantLabel = new Label("Number of Infants:");
        infantLabel.setTextFill(Color.WHITE);
        Spinner<Integer> infantSpinner = new Spinner<>(0, 10, 0);
        infantSpinner.setPrefWidth(150);

        Label priceLabel = new Label("Total Price: PKR: 0");
        priceLabel.setTextFill(Color.WHITE);
        priceLabel.setStyle("-fx-font-size: 18px;");
        Button calculateButton = new Button("Calculate Price");
        calculateButton.setOnAction(e -> {
            int numAdults = adultSpinner.getValue();
            int numChildren = childSpinner.getValue();
            int numInfants = infantSpinner.getValue();

            double totalPrice = calculateTotalPrice(selectedFlight, numAdults, numChildren, numInfants);
            priceLabel.setText("Total Price: PKR" + totalPrice);
        });

        Button confirmBookingButton = new Button("Confirm Booking");
        confirmBookingButton.setOnAction(e -> {
            String passengerName = passengerNameField.getText();
            String contactInfo = contactField.getText();

            if (passengerName.isEmpty() || contactInfo.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill in all fields.");
                alert.showAndWait();
            } else {
                saveBookingDetails(selectedFlight, passengerName, contactInfo, adultSpinner.getValue(), childSpinner.getValue(), infantSpinner.getValue());
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Booking confirmed!");
                successAlert.showAndWait();
                bookingStage.close();
            }
        });

        layout.getChildren().addAll(titleLabel, detailsLabel, passengerNameLabel, passengerNameField, contactLabel, contactField,
                adultLabel, adultSpinner, childLabel, childSpinner, infantLabel, infantSpinner, calculateButton, priceLabel, confirmBookingButton);

        Scene scene = new Scene(layout, 800, 700);
        primaryStage.setFullScreen(true);
        bookingStage.setScene(scene);
        bookingStage.show();
    }

    private static double calculateTotalPrice(Flight flight, int numAdults, int numChildren, int numInfants) {
        double adultPrice = flight.getPrice();
        double childDiscount = 0.5;
        double infantDiscount = 0.1;

        double totalPrice = (numAdults * adultPrice) + (numChildren * adultPrice * childDiscount) + (numInfants * adultPrice * infantDiscount);
        return totalPrice;
    }

    private static void saveBookingDetails(Flight flight, String passengerName, String contactInfo, int numAdults, int numChildren, int numInfants) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bookings.txt", true))) {
            writer.write("Flight ID: " + flight.getFlightID());
            writer.newLine();
            writer.write("Passenger Name: " + passengerName);
            writer.newLine();
            writer.write("Contact Info: " + contactInfo);
            writer.newLine();
            writer.write("Number of Adults: " + numAdults);
            writer.newLine();
            writer.write("Number of Children: " + numChildren);
            writer.newLine();
            writer.write("Number of Infants: " + numInfants);
            writer.newLine();
            writer.write("Total Price: " + calculateTotalPrice(flight, numAdults, numChildren, numInfants));
            writer.newLine();
            writer.write("---------------------------------------");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
 }
}
}