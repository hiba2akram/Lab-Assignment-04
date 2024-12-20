package com.example.projectapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class NavigationHelper {

    private static boolean isLoggedIn = false;
    private static String loggedInUser = "";
    private static Stage parentStage;


    public static void openSignUpWindow(Stage parentStage) {

        Stage signUpStage = new Stage();
        VBox signUpBox = new VBox(15);
        signUpBox.setAlignment(Pos.CENTER);
        signUpBox.setPadding(new Insets(20));
        signUpBox.setStyle("-fx-background-color: #2c3e50;-fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        nameField.setPrefHeight(40);
        nameField.setStyle("-fx-background-color: #3A3A3A; -fx-border-color: #1976D2; -fx-border-radius: 5px; -fx-text-fill: white; -fx-font-weight: bold;");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setPrefHeight(40);
        emailField.setStyle("-fx-background-color: #3A3A3A; -fx-border-color: #1976D2; -fx-border-radius: 5px; -fx-text-fill: white; -fx-font-weight: bold;");
        Label signupLabel=new Label("Sign up");
        signupLabel.setFont(Font.font("Arial", 36));
        signupLabel.setStyle("-fx-text-fill: white;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPrefHeight(40);
        passwordField.setStyle("-fx-background-color: #3A3A3A; -fx-border-color: #1976D2; -fx-border-radius: 5px; -fx-text-fill: white; -fx-font-weight: bold;");

        Button signUpButton = new Button("Sign Up");
        signUpButton.setPrefHeight(35);
        signUpButton.setStyle("-fx-background-color: #1976D2; -fx-text-fill: white; -fx-font-weight: bold;");
        signUpButton.setPrefWidth(100);
        signUpButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();


            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill in all fields.");
                return;
            }
            if (!email.matches("^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
                showAlert(Alert.AlertType.WARNING, "Invalid Email", "Please enter a valid email address.");
                return;
            }


            if (password.length() < 6) {
                showAlert(Alert.AlertType.WARNING, "Weak Password", "Password must be at least 6 characters long.");
                return;
            }


            if (UserFileHandler.emailExists(email)) {
                showAlert(Alert.AlertType.WARNING, "Email Already Registered", "This email is already registered.");
                return;
            }

            UserFileHandler.saveUserInfo(name, email, password);

            showAlert(Alert.AlertType.INFORMATION, "Sign-Up Successful", "You have successfully signed up!");
            signUpStage.close();
        });

        signUpBox.getChildren().addAll(signupLabel, nameField, emailField, passwordField, signUpButton);


        Scene signUpScene = new Scene(signUpBox, 400, 300);
        signUpStage.setTitle("Sign Up");
        signUpStage.setScene(signUpScene);
        signUpStage.show();
    }


    public static void openLoginWindow(Stage primaryStage) {
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;-fx-background-color: #2c3e50;");
        Label loginLabel=new Label("Login to your account");
        loginLabel.setStyle("-fx-text-fill: white;-fx-text-fill: white;");
        loginLabel.setFont(Font.font("Arial", 20));
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setPrefHeight(35);
        emailField.setStyle("-fx-background-color: #3A3A3A; -fx-border-color: #1976D2; -fx-border-radius: 5px; -fx-text-fill: white; -fx-font-weight: bold;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(35);
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle("-fx-background-color: #3A3A3A; -fx-border-color: #1976D2; -fx-border-radius: 5px; -fx-text-fill: white; -fx-font-weight: bold;");


        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(100);
        loginButton.setStyle("-fx-background-color: #1976D2; -fx-text-fill: white; -fx-font-weight: bold;");
        loginButton.setOnAction(e -> handleLogin(emailField.getText(), passwordField.getText(), loginStage));


        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(100);
        Button forgotPassword= new Button("Forgot Password");
        forgotPassword.setOnAction(e->{
            NavigationHelper.openForgotPasswordWindow();
        });
        cancelButton.setStyle("-fx-background-color: #1976D2; -fx-text-fill: white;-fx-font-weight: bold;");
        cancelButton.setOnAction(e -> loginStage.close());
        forgotPassword.setStyle("-fx-background-color: red; -fx-text-fill: white;-fx-font-weight: bold;");

        layout.getChildren().addAll(loginLabel,
                emailField,
                passwordField,
                loginButton,
                cancelButton, forgotPassword
        );

        Scene loginScene = new Scene(layout, 400, 250);
        loginStage.setScene(loginScene);
        loginStage.show();
    }

    private static void handleLogin(String email, String password, Stage loginStage) {
        UserFileHandler userFileHandler = new UserFileHandler();

        if (userFileHandler.validateCredentials(email, password)) {
            isLoggedIn = true;
            loggedInUser = email;
            showAlert(Alert.AlertType.INFORMATION, "Login Success", "Welcome, " + email + "!");
            loginStage.close();
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password. Please try again.");
        }
    }


    public static void checkLoginBeforeAction(Runnable action) {
        if (!isLoggedIn) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Authentication Required");
            alert.setHeaderText(null);
            alert.setContentText("You need to log in to perform this action.");
            alert.showAndWait();
        } else {
            action.run();
        }
    }


    static void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void openPaymentInfoForm(Stage primaryStage) {
        Stage paymentStage = new Stage();
        paymentStage.setTitle("Payment Information");
        GridPane layout = new GridPane();
        layout.setStyle("-fx-background-color: #2c3e50;-fx-font-weight: bold;");
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(15);
        layout.setHgap(15);
        layout.setPadding(new Insets(20));


        Label cardNumberLabel = new Label("Card Number:");
        cardNumberLabel.setTextFill(Color.WHITE);
        TextField cardNumberField = new TextField();
        cardNumberField.setPromptText("Enter your card number");
        cardNumberField.setStyle("-fx-background-color: #3A3A3A; -fx-border-color: #1976D2; -fx-border-radius: 5px; -fx-text-fill: white; -fx-font-weight: bold;");

        Label expiryDateLabel = new Label("Expiry Date (MM/YY):");
        expiryDateLabel.setTextFill(Color.WHITE);
        TextField expiryDateField = new TextField();
        expiryDateField.setPromptText("Enter expiry date");
        expiryDateField.setStyle("-fx-background-color: #3A3A3A; -fx-border-color: #1976D2; -fx-border-radius: 5px; -fx-text-fill: white; -fx-font-weight: bold;");

        Label cvvLabel = new Label("CVV:");
        cvvLabel.setTextFill(Color.WHITE);
        TextField cvvField = new TextField();
        cvvField.setPromptText("Enter CVV");
        cvvField.setStyle("-fx-background-color: #3A3A3A; -fx-border-color: #1976D2; -fx-border-radius: 5px; -fx-text-fill: white; -fx-font-weight: bold;");

        Button submitButton = new Button("Submit Payment Info");
        submitButton.setStyle("-fx-background-color: #1976D2; -fx-text-fill: white; -fx-font-weight: bold;");
        submitButton.setOnAction(e -> {
            String cardNumber = cardNumberField.getText();
            String expiryDate = expiryDateField.getText();
            String cvv = cvvField.getText();

            if (cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill in all payment details.");
                alert.showAndWait();
            } else {

                UserFileHandler.savePaymentInfoToFile(cardNumber, expiryDate, cvv);
                paymentStage.close();
            }
        });


        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #FF3D00; -fx-text-fill: white;");
        cancelButton.setOnAction(e -> paymentStage.close());


        layout.add(cardNumberLabel, 0, 0);
        layout.add(cardNumberField, 1, 0);
        layout.add(expiryDateLabel, 0, 1);
        layout.add(expiryDateField, 1, 1);
        layout.add(cvvLabel, 0, 2);
        layout.add(cvvField, 1, 2);
        layout.add(submitButton, 0, 3);
        layout.add(cancelButton, 1, 3);

        Scene scene = new Scene(layout, 400, 400);
        paymentStage.setScene(scene);
        paymentStage.show();

    }
    public static void openBookingConfirmationWindow(Stage primaryStage, Booking booking) {

        Stage confirmationStage = new Stage();
        confirmationStage.setTitle("Booking Confirmation");


        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #2c3e50;-fx-font-weight: bold;");

        Label titleLabel = new Label("Booking Confirmation");
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("Arial", 24));

        Label bookingDetailsLabel = new Label(booking.toString());
        bookingDetailsLabel.setTextFill(Color.WHITE);
        bookingDetailsLabel.setFont(Font.font("Arial", 16));
        bookingDetailsLabel.setWrapText(true);


        Button updateBookingButton = new Button("Update Booking");
        updateBookingButton.setStyle("-fx-font-weight: bold; -fx-background-color: #87cefa; -fx-font-size: 14px;");
        Button cancelBookingButton = new Button("Cancel Booking");
        cancelBookingButton.setStyle("-fx-font-weight: bold; -fx-background-color: #87cefa; -fx-font-size: 14px;");
        Button generateTicketButton = new Button("Generate Ticket");
        generateTicketButton.setStyle("-fx-font-weight: bold; -fx-background-color: #87cefa; -fx-font-size: 14px;");


        updateBookingButton.setOnAction(e -> showUpdateBookingDialog(booking));

        cancelBookingButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel this booking?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        cancelBooking(booking.getPassengerName());
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Booking canceled successfully.");
                        successAlert.showAndWait();
                        confirmationStage.close();
                    } catch (IOException ex) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Failed to cancel booking. Please try again.");
                        errorAlert.showAndWait();
                    }
                }
            });
        });


        generateTicketButton.setOnAction(e -> {
            try {
                generateTicket(booking);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Ticket generated successfully.");
                successAlert.showAndWait();
            } catch (IOException ex) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Failed to generate ticket.");
                errorAlert.showAndWait();
            }
        });


        layout.getChildren().addAll(titleLabel, bookingDetailsLabel, updateBookingButton, cancelBookingButton, generateTicketButton);


        Scene scene = new Scene(layout, 400, 300);
        confirmationStage.setScene(scene);
        confirmationStage.show();
    }


    private static void showUpdateBookingDialog(Booking booking) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Update Booking");
        dialog.setHeaderText("Update booking details");


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.setStyle("-fx-background-color: #2c3e50;-fx-font-weight: bold;");

        TextField passengerNameField = new TextField(booking.getPassengerName());
        TextField contactInfoField = new TextField(booking.getContactInfo());
        TextField numAdultsField = new TextField(String.valueOf(booking.getNumAdults()));
        TextField numChildrenField = new TextField(String.valueOf(booking.getNumChildren()));
        TextField numInfantsField = new TextField(String.valueOf(booking.getNumInfants()));

        grid.add(new Label("Passenger Name:"), 0, 0);
        grid.add(passengerNameField, 1, 0);
        grid.add(new Label("Contact Info:"), 0, 1);
        grid.add(contactInfoField, 1, 1);
        grid.add(new Label("Number of Adults:"), 0, 2);
        grid.add(numAdultsField, 1, 2);
        grid.add(new Label("Number of Children:"), 0, 3);
        grid.add(numChildrenField, 1, 3);
        grid.add(new Label("Number of Infants:"), 0, 4);
        grid.add(numInfantsField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);


        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                booking.setPassengerName(passengerNameField.getText());
                booking.setContactInfo(contactInfoField.getText());
                booking.setNumAdults(Integer.parseInt(numAdultsField.getText()));
                booking.setNumChildren(Integer.parseInt(numChildrenField.getText()));
                booking.setNumInfants(Integer.parseInt(numInfantsField.getText()));

                boolean updated = updateBookingInFile(booking);

                Alert alert;
                if (updated) {
                    alert = new Alert(Alert.AlertType.INFORMATION, "Booking updated successfully!");
                } else {
                    alert = new Alert(Alert.AlertType.ERROR, "update booking.");
                }
                alert.showAndWait();
            }
        });
    }


    private static boolean updateBookingInFile(Booking booking) {
        File file = new File("bookings.txt");
        List<String> lines;
        boolean bookingFound = false;

        try {
            lines = new ArrayList<>(Files.readAllLines(file.toPath()));

            for (int i = 0; i < lines.size(); i++) {
                String[] details = lines.get(i).split(",");
                if (details.length > 0 && details[0].equalsIgnoreCase(booking.getPassengerName())) {
                    lines.set(i, booking.toString());
                    bookingFound = true;
                    break;
                }
            }

            if (bookingFound) {
                Files.write(file.toPath(), lines);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return bookingFound;
    }


    private static void cancelBooking(String passengerName) throws IOException {
        File inputFile = new File("bookings.txt");
        File tempFile = new File("temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;
            boolean bookingFound = false;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.contains(passengerName)) {
                    bookingFound = true;
                    continue;
                }
                writer.write(currentLine + System.lineSeparator());
            }

            if (!bookingFound) {
                throw new IOException("Booking not found.");
            }
        }

        tempFile.renameTo(inputFile);
    }


    private static void generateTicket(Booking booking) throws IOException {
        File ticketFile = new File(booking.getPassengerName() + "_ticket.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ticketFile))) {
            writer.write("Ticket Details:\n");
            writer.write(booking.toString());
        }
    }





    public static void openForgotPasswordWindow() {
        Stage forgotStage = new Stage();
        forgotStage.setTitle("Forgot Password");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #2c3e50;-fx-font-weight: bold;");
        Label resetLabel=new Label("Reset your password");
        resetLabel.setStyle("-fx-text-fill: white;-fx-text-fill: white;");
        resetLabel.setFont(Font.font("Arial", 20));
        TextField emailField = new TextField();
        emailField.setPrefHeight(35);
        emailField.setStyle("-fx-border-radius: 5px;-fx-border-color: #1976D2;-fx-background-color: #3A3A3A;");
        emailField.setPromptText("Enter your registered email");

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPrefHeight(35);
        newPasswordField.setPromptText("Enter new password");
        newPasswordField.setStyle("-fx-border-radius: 5px;-fx-border-color: #1976D2;-fx-background-color: #3A3A3A;");

        Button resetButton = new Button("Reset Password");
        resetButton.setStyle("-fx-background-color: #1976D2; -fx-text-fill: white; -fx-font-weight: bold;-fx-border-radius: 5px;");
        resetButton.setOnAction(e -> {
            String email = emailField.getText();
            String newPassword = newPasswordField.getText();

            if (email.isEmpty() || newPassword.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill in all fields.");
                return;
            }

            if (!newPassword.matches(".{6,}")) {
                showAlert(Alert.AlertType.WARNING, "Weak Password", "Password must be at least 6 characters long.");
                return;
            }

            if (UserFileHandler.resetPassword(email, newPassword)) {
                showAlert(Alert.AlertType.INFORMATION, "Password Reset", "Your password has been reset successfully.");
                forgotStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Reset Failed", "Email not found. Please try again.");
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefHeight(20);
        cancelButton.setPrefWidth(100);
        cancelButton.setStyle("-fx-background-color: #1976D2; -fx-text-fill: white; -fx-font-weight: bold;-fx-border-radius: 5px;");
        cancelButton.setOnAction(e -> forgotStage.close());

        layout.getChildren().addAll(resetLabel,
                emailField,
                newPasswordField,
                resetButton,
                cancelButton
        );

        Scene forgotScene = new Scene(layout, 350, 300);
        forgotStage.setScene(forgotScene);
        forgotStage.show();
}
}