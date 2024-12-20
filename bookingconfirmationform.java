package com.example.projectapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class bookingconfirmationform {
    public void bookingconfirmationform(){
        Stage newstage5 = new Stage();
        newstage5.setTitle("Booking Confirmation Form");

        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE,null,null);
        VBox newlayout5 = new VBox(10);
        newlayout5.setAlignment(Pos.CENTER);
        newlayout5.setMaxWidth(400);
        newlayout5.setPadding(new Insets(20));

        Label bookedflight = new Label("Booking Confirmation");
        bookedflight.setStyle("-fx-font-weight:bold ; -fx-font-size:20px;");
        bookedflight.setTextFill(Color.GRAY);

        Label bookingref =  new Label("Booking Reference:ABC1234XYZ");
        bookingref.setStyle("-fx-font-weight:bold;-fx-font-size:20px;");

        Label passengerinformation = new Label("Passenger Information");
        passengerinformation.setStyle("-fx-font-size:14px;");
        Label passengerinfo = new Label("Name:Hiba AKram/n seat:12-A");

        Label paymentmethod = new Label("Payment Status");
        paymentmethod.setStyle("-fx-font-size:20px;-fx-font-weight:bold;");
        Label paymentstatus = new Label("Payment confirm: PKR 10,000");

        Label thankslabel = new Label("Thanks for travelling with us!");
        thankslabel.setStyle("-fx-font-weight:bold; -fx-font-size-:24px;");

        Button downloadbutton = new Button("Download Ticket");
        downloadbutton.setOnAction(e->{
            System.out.println("Ticket Downloaded");
        });
        Button homebutton = new Button("Go to login page");
        homebutton.setTextFill(Color.WHITE);
        homebutton.setStyle("-fx-background-color:green;");
        homebutton.setOnAction(e->{
            System.out.println("Go back to login page");
        });

        Button cancelbutton = new Button("Cancel Ticket");
        cancelbutton.setTextFill(Color.WHITE);
        cancelbutton.setStyle("-fx-background-color:red;");
        cancelbutton.setOnAction(e->newstage5.close());
        HBox buttonbox = new HBox(10);
        buttonbox.getChildren().addAll(downloadbutton,homebutton,cancelbutton);
        newlayout5.getChildren().addAll(bookedflight,bookingref,passengerinformation,passengerinfo,
                paymentmethod,paymentstatus,thankslabel,buttonbox

        );

        newlayout5.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-radius: 15;" +
                        "-fx-background-radius:20;" +
                        "-fx-padding: 20;"

        );

        StackPane root = new StackPane();
        root.setBackground(new Background(backgroundFill));
        root.getChildren().add(newlayout5);

        Scene newscene = new Scene(root, 550, 550);
        newstage5.setScene(newscene);
        newstage5.show();

    }



}


