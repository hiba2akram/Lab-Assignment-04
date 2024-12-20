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

public class createaccount {
    public void createaccount(){
        Stage newstage = new Stage();
        newstage.setTitle("Create new Password");

        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE,null,null);
        VBox newlayout1 = new VBox(20);
        newlayout1.setAlignment(Pos.CENTER);
        newlayout1.setMaxHeight(400);
        newlayout1.setMaxWidth(400);
        newlayout1.setPadding(new Insets(20));

        Label createaccountlabel  = new Label("Create Account");
        createaccountlabel.setStyle("-fx-font-weight:bold;");
        createaccountlabel.setTextFill(Color.GRAY);
        createaccountlabel.setFont(new Font("Times New Roman",20));
        createaccountlabel.setAlignment(Pos.CENTER);

        Label name1label = new Label("First Name");
        name1label.setStyle("-fx-font-weight:bold; -fx-text-fill: black;");
        name1label.setFont(new Font(16));
        TextField name1field= new TextField();
        name1field.setPromptText("First name");

        Label lastnamelabel = new Label("Last Name");
        lastnamelabel.setStyle("-fx-font-weight:bold; -fx-text-fill: black;");
        lastnamelabel.setFont(new Font(16));
        TextField lastnamefield = new TextField();
        lastnamefield.setPromptText("Last name");

        Label emaillabel = new Label("Email Address");
        emaillabel.setStyle("-fx-font-weight:bold; -fx-text-fill: black;");
        emaillabel.setFont(new Font(16));
        TextField emailtextfield = new TextField();
        emailtextfield.setPromptText("Email address");

        Label passwordlabel = new Label("Password");
        passwordlabel.setStyle("-fx-font-weight:bold; -fx-text-fill: black;");
        passwordlabel.setFont(new Font(16));
        PasswordField passwordField= new PasswordField();
        passwordField.setPromptText("Password");
        Label passwordtextlabel = new Label("Use at least 8 characters, including both letters and symbols");

        Button createbutton = new Button("Create");
        createbutton.setAlignment(Pos.CENTER);
        createbutton.setPrefWidth(250);
        createbutton.setStyle(
                "-fx-background-color: skyblue;"+
                        "-fx-text-fill: white;"+
                        "-fx-border-radius: 5px;"+
                        "-fx-text-weight: bold;"
        );
        createbutton.setOnAction(e->{
            String name = name1field.getText();
            String email = emailtextfield.getText();


            if (name.isEmpty() || email.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill all the text fields");
                alert.showAndWait();
            } else {
                System.out.println("Login successful! Name: " + name + ", Email: " + email);

            }

        });

        Button cancelbutton = new Button("Cancel");
        cancelbutton.setAlignment(Pos.CENTER);
        cancelbutton.setPrefWidth(250);
        cancelbutton.setOnAction(e-> System.exit(0));
        cancelbutton.setStyle(
                "-fx-background-color: skyblue;"+
                        "-fx-text-fill: white;"+
                        "-fx-border-radius: 5px;"+
                        "-fx-text-weight: bold;"
        );
        CheckBox acceptTerms = new CheckBox("I accept the Privacy Policy and Terms of Use.");

        newlayout1.setStyle(
                "-fx-background-color: white;"+
                        "-fx-border-radius: 15;"+
                        "-fx-background-radius:20;"+
                        "-fx-padding: 20;"

        );

        newlayout1.getChildren().addAll(createaccountlabel,name1label,
                name1field,lastnamelabel,lastnamefield,
                emaillabel,emailtextfield,passwordlabel,passwordField,passwordtextlabel,
                acceptTerms,createbutton,cancelbutton);

        StackPane root = new StackPane();
        root.setBackground(new Background(backgroundFill));
        root.getChildren().addAll(newlayout1);

        Scene scene = new Scene(root,550,550);
        newstage.setScene(scene);
        newstage.show();

    }

}
