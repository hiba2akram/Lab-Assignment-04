
package com.example.projectapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class AirlineApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("backgroundimage.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        root.setBackground(new Background(backgroundImage));


        Label heading = new Label("Welcome to AirJet Booking System");
        heading.setFont(Font.font("Arial", 36));
        heading.setStyle("-fx-textFill: #34495e;-fx-font-weight: bold;");
        heading.setAlignment(Pos.CENTER);


        Label quote = new Label("\" Air travel is nature's way of making you look like your passport photo.\"");
        quote.setFont(Font.font("Arial", 18));
        quote.setStyle("-fx-textFill: #34495e; -fx-font-weight: bold;");
        quote.setTextAlignment(TextAlignment.CENTER);
        quote.setWrapText(true);

        VBox centerVBox = new VBox(10, heading, quote);
        centerVBox.setAlignment(Pos.CENTER);
        root.setCenter(centerVBox);

        HBox navbar = new HBox(30);
        navbar.setPadding(new Insets(10));
        navbar.setAlignment(Pos.TOP_RIGHT);
        navbar.setStyle("-fx-background-color: #34495e;");


        Label navbarText = new Label("AirJet Booking");
        navbarText.setFont(Font.font("Arial", 18));
        navbarText.setTextFill(Color.WHITE);
        navbarText.setAlignment(Pos.TOP_LEFT);


        Button loginButton = new Button("LOGIN");
        Button signUpButton = new Button("Sign Up");

        Button flightSearchButton = new Button("Flight Search");
        Button flightBookingButton = new Button("Book Flights");
        Button paymentFormButton = new Button("Payment Info");



        String buttonStyle = "-fx-font-weight: bold; -fx-background-color: #87cefa; -fx-font-size: 14px;";
        loginButton.setStyle(buttonStyle);
        signUpButton.setStyle(buttonStyle);

        flightSearchButton.setStyle(buttonStyle);
        flightBookingButton.setStyle(buttonStyle);
        paymentFormButton.setStyle(buttonStyle);


        loginButton.setOnAction(e -> NavigationHelper.openLoginWindow(primaryStage)
        );
        signUpButton.setOnAction(e -> NavigationHelper.openSignUpWindow(primaryStage));

        flightSearchButton.setOnAction(e -> {
            NavigationHelper.checkLoginBeforeAction(() -> {
                FlightSearch.openFlightSearchForm(primaryStage);
            });
        });

        flightBookingButton.setOnAction(e -> {
            NavigationHelper.checkLoginBeforeAction(() -> {
                Flight selectedFlight = FlightSearch.getSelectedFlight();
                if (selectedFlight == null) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a flight from the table before booking.");
                    alert.showAndWait();
                } else {
                    FlightBooking.openBookingForm(primaryStage, selectedFlight);
                }
            });
        });



        paymentFormButton.setOnAction(e -> {
            NavigationHelper.checkLoginBeforeAction(() -> {
                NavigationHelper.openPaymentInfoForm(primaryStage);
            });
        });





        navbar.getChildren().addAll( navbarText,loginButton, signUpButton,
                flightSearchButton, flightBookingButton, paymentFormButton);


        root.setTop(navbar);


        primaryStage.setMaximized(true);
        primaryStage.setResizable(true);


        Scene scene = new Scene(root);
        primaryStage.setTitle("Airline Booking System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch();
}
}