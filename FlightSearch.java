
package com.example.projectapp;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.projectapp.NavigationHelper.showAlert;

public class FlightSearch {
    private static TableView<Flight> flightTable = new TableView<>();
    private static ObservableList<Flight> allFlights = FXCollections.observableArrayList(
            new Flight("FL123", "New York", "London", "Economy", "2024-12-15", 500, "JFK", "Heathrow"),
            new Flight("FL124", "Los Angeles", "Paris", "Business", "2024-12-16", 1200, "LAX", "CDG"),
            new Flight("FL125", "Chicago", "Tokyo", "Economy", "2024-12-20", 800, "ORD", "Narita"),
            new Flight("FL126", "San Francisco", "Rome", "Business", "2024-12-22", 1500, "SFO", "FCO")
    );

    public static void openFlightSearchForm(Stage primaryStage) {
        Stage newStage = new Stage();
        newStage.setTitle("Flight Search");

        GridPane layout = createFlightSearchLayout(newStage);
        layout.setStyle("-fx-background-color: #2c3e50; -fx-font-weight: bold;");

        StackPane root = new StackPane();
        root.getChildren().add(layout);

        Scene scene = new Scene(root, 900, 700);
        newStage.setScene(scene);
        newStage.show();
    }

    private static GridPane createFlightSearchLayout(Stage newStage) {
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(15);
        layout.setHgap(15);
        layout.setPadding(new Insets(20));

        Label flightSearchLabel = new Label("Flight Search");
        flightSearchLabel.setFont(new Font("Arial", 22));
        flightSearchLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

        Label departureCityLabel = new Label("Departure City:");
        departureCityLabel.setStyle("-fx-font-size: 16px; -fx-font-family: Arial; -fx-text-fill: white;");
        TextField departureCityField = new TextField();
        departureCityField.setPromptText("Enter Departure City");

        Label destinationCityLabel = new Label("Destination City:");
        destinationCityLabel.setStyle("-fx-font-size: 16px; -fx-font-family: Arial; -fx-text-fill: white;");
        TextField destinationCityField = new TextField();
        destinationCityField.setPromptText("Enter Destination City");

        Label departureairportlabel = new Label("Departure Airport");
        departureairportlabel.setStyle("-fx-font-size: 16px; -fx-font-family: Arial; -fx-text-fill: white;");
        TextField departureAirportField = new TextField();
        departureAirportField.setPromptText("Enter Departure Airport");

        Label destinationairportlabel = new Label("Destination Airport");
        destinationairportlabel.setStyle("-fx-font-size: 16px; -fx-font-family: Arial; -fx-text-fill: white;");
        TextField destinationAirportField = new TextField();
        destinationAirportField.setPromptText("Enter Destination Airport");
        destinationAirportField.setStyle("-fx-text-fill:black;");

        Label departuredatelabel =new Label("Departure Date");
        departuredatelabel.setStyle("-fx-font-size: 16px; -fx-font-family: Arial; -fx-text-fill: white;");
        DatePicker departureDatePicker = new DatePicker();
        DatePicker returnDatePicker = new DatePicker();

        Label returndatelabel = new Label("Return Date:");
        returndatelabel.setStyle("-fx-font-size: 16px; -fx-font-family: Arial; -fx-text-fill: white;");

        Label classboxlabel = new Label("Class box");
        classboxlabel.setStyle("-fx-font-size: 16px; -fx-font-family: Arial; -fx-text-fill: white;");
        ComboBox<String> classComboBox = new ComboBox<>();
        classComboBox.getItems().addAll("Economy", "Business");


        ToggleGroup tripTypeGroup = new ToggleGroup();
        RadioButton oneWayButton = new RadioButton("One-Way");
        oneWayButton.setStyle("-fx-font-size: 16px; -fx-font-family: Arial; -fx-text-fill: white;");
        oneWayButton.setToggleGroup(tripTypeGroup);
        oneWayButton.setSelected(true);
        RadioButton roundTripButton = new RadioButton("Round Trip");
        roundTripButton.setStyle("-fx-font-size: 16px; -fx-font-family: Arial; -fx-text-fill: white;");
        roundTripButton.setToggleGroup(tripTypeGroup);

        flightTable = createFlightTable();

        Button searchButton = new Button("Search Flights");
        searchButton.setStyle("-fx-background-color: #4A90E2; -fx-text-fill: white;");
        searchButton.setOnAction(e -> handleSearchButtonClick(
                departureCityField, destinationCityField, departureAirportField, destinationAirportField, classComboBox,
                departureDatePicker, returnDatePicker, tripTypeGroup, roundTripButton
        ));
        Button saveButton = new Button("Select a Seat");
        saveButton.setStyle("-fx-background-color: #FFC107; -fx-text-fill: black;");
        saveButton.setOnAction(e -> {
            Flight selectedFlight = flightTable.getSelectionModel().getSelectedItem();
            if (selectedFlight == null) {
                showAlert("Please select a flight.", Alert.AlertType.WARNING);
                return;
            }
            openSeatSelectionForm(selectedFlight);
        });


        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #FF3D00; -fx-text-fill: white;");
        cancelButton.setOnAction(e -> newStage.close());




        layout.add(flightSearchLabel, 0, 0, 2, 1);
        layout.add(oneWayButton, 0, 1);
        layout.add(roundTripButton, 1, 1);
        layout.add(departureCityLabel, 0, 2);
        layout.add(departureCityField, 1, 2);
        layout.add(destinationCityLabel, 0, 3);
        layout.add(destinationCityField, 1, 3);
        layout.add(departureairportlabel, 0, 4);
        layout.add(departureAirportField, 1, 4);
        layout.add(destinationairportlabel, 0, 5);
        layout.add(destinationAirportField, 1, 5);
        layout.add(departuredatelabel, 0, 6);
        layout.add(departureDatePicker, 1, 6);
        layout.add(returndatelabel, 0, 7);
        layout.add(returnDatePicker, 1, 7);
        layout.add(classboxlabel, 0, 8);
        layout.add(classComboBox, 1, 8);
        layout.add(flightTable, 0, 9, 2, 1);
        layout.add(searchButton, 0, 10);
        layout.add(saveButton, 1, 10);
        layout.add(cancelButton, 1, 11);

        returnDatePicker.setDisable(true);
        roundTripButton.setOnAction(e -> returnDatePicker.setDisable(false));
        oneWayButton.setOnAction(e -> returnDatePicker.setDisable(true));

        return layout;
    }

    private static void openSeatSelectionForm(Flight selectedFlight) {
        Stage seatStage = new Stage();
        seatStage.setTitle("Seat Selection");

        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(15);
        layout.setHgap(15);
        layout.setPadding(new Insets(20));

        Label seatSelectionLabel = new Label("Select a Seat");
        seatSelectionLabel.setFont(new Font("Arial", 22));
        seatSelectionLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black;");

        ListView<Seat> seatListView = new ListView<>();
        seatListView.getItems().addAll(selectedFlight.getSeats());

        Button confirmButton = new Button("Confirm Seat");
        confirmButton.setStyle("-fx-background-color: #4A90E2; -fx-text-fill: white;");
        confirmButton.setOnAction(e -> {
            Seat selectedSeat = seatListView.getSelectionModel().getSelectedItem();
            if (selectedSeat != null && selectedSeat.isAvailable()) {
                selectedSeat.setAvailable(false);
                showAlert("Seat booked successfully.", Alert.AlertType.INFORMATION);
                seatStage.close();
            } else {
                showAlert("Please select an available seat.", Alert.AlertType.WARNING);
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #FF3D00; -fx-text-fill: white;");
        cancelButton.setOnAction(e -> seatStage.close());

        layout.add(seatSelectionLabel, 0, 0, 2, 1);
        layout.add(seatListView, 0, 1, 2, 1);
        layout.add(confirmButton, 0, 2);
        layout.add(cancelButton, 1, 2);

        Scene scene = new Scene(layout, 400, 400);
        seatStage.setScene(scene);
        seatStage.show();
    }

   private static void showAlert1(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType == Alert.AlertType.WARNING ? "Warning" : alertType == Alert.AlertType.INFORMATION ? "Information" : "Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static boolean isAirportAvailable(String airport) {
        return !airport.equalsIgnoreCase("Heathrow") && !airport.equalsIgnoreCase("CDG");
    }

    public static Flight getSelectedFlight() {
        if (flightTable == null) {
            throw new IllegalStateException("Flight table is not initialized.");
        }
        return flightTable.getSelectionModel().getSelectedItem();
    }

    private static TableView<Flight> createFlightTable() {
        TableView<Flight> table = new TableView<>();
        table.setPrefWidth(800);

        TableColumn<Flight, String> flightIDColumn = new TableColumn<>("Flight ID");
        flightIDColumn.setCellValueFactory(cellData -> cellData.getValue().flightIDProperty());

        TableColumn<Flight, String> departureCityColumn = new TableColumn<>("Departure City");
        departureCityColumn.setCellValueFactory(cellData -> cellData.getValue().departureCityProperty());

        TableColumn<Flight, String> destinationCityColumn = new TableColumn<>("Destination City");
        destinationCityColumn.setCellValueFactory(cellData -> cellData.getValue().destinationCityProperty());

        TableColumn<Flight, String> departureAirportColumn = new TableColumn<>("Departure Airport");
        departureAirportColumn.setCellValueFactory(cellData -> cellData.getValue().departureAirportProperty());

        TableColumn<Flight, String> destinationAirportColumn = new TableColumn<>("Destination Airport");
        destinationAirportColumn.setCellValueFactory(cellData -> cellData.getValue().destinationAirportProperty());

        TableColumn<Flight, String> classColumn = new TableColumn<>("Class");
        classColumn.setCellValueFactory(cellData -> cellData.getValue().flightClassProperty());

        TableColumn<Flight, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        TableColumn<Flight, Double> priceColumn = new TableColumn<>("Price (PKR)");
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());


        TableColumn<Flight, Boolean> bookedColumn = new TableColumn<>("Booked");
        bookedColumn.setCellValueFactory(cellData -> cellData.getValue().bookedProperty().asObject());
        bookedColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean booked, boolean empty) {
                super.updateItem(booked, empty);
                if (empty || booked == null) {
                    setText(null);
                } else {
                    setText(booked ? "Yes" : "No");
                }
            }
        });

        table.getColumns().addAll(flightIDColumn, departureCityColumn, destinationCityColumn, departureAirportColumn,
                destinationAirportColumn, classColumn, dateColumn, priceColumn, bookedColumn);
        table.setItems(allFlights);

        return table;
    }

    private static void handleSearchButtonClick(
            TextField departureCityField, TextField destinationCityField, TextField departureAirportField,
            TextField destinationAirportField, ComboBox<String> classComboBox, DatePicker departureDatePicker,
            DatePicker returnDatePicker, ToggleGroup tripTypeGroup, RadioButton roundTripButton) {

        String departureCity = departureCityField.getText().trim();
        String destinationCity = destinationCityField.getText().trim();
        String departureAirport = departureAirportField.getText().trim();
        String destinationAirport = destinationAirportField.getText().trim();
        String flightClass = classComboBox.getSelectionModel().getSelectedItem();
        LocalDate departureDate = departureDatePicker.getValue();
        LocalDate returnDate = roundTripButton.isSelected() ? returnDatePicker.getValue() : null;

        if (departureCity.isEmpty() || destinationCity.isEmpty() || departureAirport.isEmpty() || destinationAirport.isEmpty() ||
                flightClass == null || departureDate == null || (roundTripButton.isSelected() && returnDate == null)) {
            showAlert("Please fill in all fields.", Alert.AlertType.WARNING);
            return;
        }

        ObservableList<Flight> filteredFlights = allFlights.stream()
                .filter(flight -> flight.getDepartureCity().equalsIgnoreCase(departureCity)
                        && flight.getDestinationCity().equalsIgnoreCase(destinationCity)
                        && flight.getDepartureAirport().equalsIgnoreCase(departureAirport)
                        && flight.getDestinationAirport().equalsIgnoreCase(destinationAirport)
                        && flight.getFlightClass().equalsIgnoreCase(flightClass)
                        && flight.getDate().equals(departureDate.toString()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        flightTable.setItems(filteredFlights);
    }

    private static void saveSelectedFlight(Flight selectedFlight) {
        if (selectedFlight == null) {
            showAlert("Please select a flight.", Alert.AlertType.WARNING);
            return;
        }

        Seat selectedSeat = getSelectedSeat(selectedFlight);

        if (selectedSeat == null) {
            showAlert("Please select a seat.", Alert.AlertType.WARNING);
            return;
        }


        if (!selectedSeat.isAvailable()) {
            showAlert("This seat is already booked. Please select another seat.", Alert.AlertType.ERROR);
            return;
        }


        selectedSeat.setAvailable(false);


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("favorites.txt"))) {
            writer.write("Selected Flight: " + selectedFlight.toString());

            selectedFlight.setBooked(true);
            showAlert("Flight booked successfully.", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            showAlert("Error saving flight.", Alert.AlertType.ERROR);
        }
    }

    private static Seat getSelectedSeat(Flight selectedFlight) {
        return null;
    }


    private static void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType == Alert.AlertType.WARNING ? "Warning" : alertType == Alert.AlertType.INFORMATION ? "Information" : "Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}




