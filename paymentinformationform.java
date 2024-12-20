package com.example.projectapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class paymentinformationform {
    bookingconfirmationform bookingconfirmation = new bookingconfirmationform();
    public void paymentinformationform1(){
        Stage newstage4 = new Stage();
        newstage4.setTitle("Payment information");
        BackgroundImage image1 = new BackgroundImage(
                new Image("image1.jpg",true ),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(
                        100, 100, true, true, true, true
                )
        );
        VBox newlayout4 = new VBox(10);
        newlayout4.setAlignment(Pos.CENTER);
        newlayout4.setMaxWidth(400);
        newlayout4.setPadding(new Insets(20));

        Label titlelabel = new Label("Payment Information");
        titlelabel.setTextFill(Color.GRAY);
        titlelabel.setStyle(" -fx-text-weight:bold");
        titlelabel.setFont(new Font("Times New Roman",16));

        Label paymentmethod = new Label("Payment method");
        paymentmethod.setStyle("-fx-font-weight:bold;");
        ToggleGroup paymenttoggle = new ToggleGroup();

        RadioButton cardpayment = new RadioButton("Card payment");
        cardpayment.setStyle("-fx-font-weight:bold;");
        cardpayment.setToggleGroup(paymenttoggle);
        cardpayment.setSelected(true);

        RadioButton banktransfer = new RadioButton("Bank transfer");
        banktransfer.setStyle("-fx-font-weight:bold;");
        banktransfer.setToggleGroup(paymenttoggle);

        VBox paymentmethodbox = new VBox(10,cardpayment,banktransfer);
        Label carddetail = new Label("Card Details");
        TextField cardownerfield = new TextField();
        cardownerfield.setPromptText("Enter cardholder name");

        TextField cardnumberfield = new TextField();
        cardnumberfield.setPromptText("Enter card number");

        HBox cardexpirybox = new HBox(10);
        TextField expirymonthfield = new TextField("");
        expirymonthfield.setPromptText("MM");

        TextField expiryyearfield = new TextField();
        expiryyearfield.setPromptText("YY");

        cardexpirybox.getChildren().addAll(expirymonthfield,expiryyearfield);

        TextField cvvfield = new TextField();
        cvvfield.setPromptText("CVV");
        cvvfield.setPrefWidth(20);

        Label summaryLabel = new Label("Summary of Charges:");
        Label totalAmountLabel = new Label("Total Amount: PKR 10,000");
        VBox summaryBox = new VBox(5, summaryLabel, totalAmountLabel);
        newlayout4.getChildren().add(summaryBox);

        newlayout4.getChildren().addAll(titlelabel,paymentmethod,paymentmethodbox,
                cardownerfield,cardnumberfield,cardexpirybox,cvvfield
        );
        Button submitbutton = new Button("Submit");
        submitbutton.setTextFill(Color.WHITE);
        submitbutton.setStyle("-fx-background-color: green");
        submitbutton.setOnAction(e-> {
            if(cardownerfield.getText().isEmpty()||cardnumberfield.getText().isEmpty()||
                    paymentmethod.getText().isEmpty()||cvvfield.getText().isEmpty()||expiryyearfield.getText().isEmpty()
            ){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Please fill all the fields");
                alert.setTitle("'Missing information");
                alert.showAndWait();
            }else {
                bookingconfirmation.bookingconfirmationform();
            }

        });
        Button backbutton = new Button("Back");
        backbutton.setTextFill(Color.WHITE);
        backbutton.setStyle("-fx-background-color:blue;");
        backbutton.setOnAction(e->newstage4.close());

        newlayout4.getChildren().addAll(submitbutton,backbutton);

        newlayout4.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-radius: 15;" +
                        "-fx-background-radius:20;" +
                        "-fx-padding: 20;"

        );

        StackPane root = new StackPane();
        root.setBackground(new Background(image1));
        root.getChildren().add(newlayout4);

        Scene newscene = new Scene(root, 550, 550);
        newstage4.setScene(newscene);
        newstage4.show();
    }

}
