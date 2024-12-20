package com.example.projectapp;


import javafx.scene.control.Alert;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserFileHandler {

    private static final String FILE_PATH = "users.txt";


    public static void saveUserInfo(String name, String email, String password) {
        String encryptedPassword = encryptPassword(password);

        String userInfo = name + "," + email + "," + encryptedPassword;


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(userInfo);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean emailExists(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length >= 2 && userData[1].equals(email)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean validateCredentials(String email, String password) {
        String encryptedPassword = encryptPassword(password);
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String storedEmail = parts[1];
                    String storedPassword = parts[2];
                    if (storedEmail.equals(email) && storedPassword.equals(encryptedPassword)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return password;
        }
    }
    public static boolean resetPassword(String email, String newPassword) {
        File file = new File(FILE_PATH);
        File tempFile = new File("temp_users.txt");
        boolean emailFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length >= 3 && userData[1].equals(email)) {
                    emailFound = true;
                    String encryptedPassword = encryptPassword(newPassword);
                    writer.write(userData[0] + "," + userData[1] + "," + encryptedPassword);
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


        if (emailFound) {
            if (!file.delete() || !tempFile.renameTo(file)) {
                System.out.println("Error updating the password file.");
                return false;
            }
        } else {
            tempFile.delete();
        }

        return emailFound;
    }

    static void savePaymentInfoToFile(String cardNumber, String expiryDate, String cvv) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("paymentHistory.txt", true))) {

            writer.write("Card Number: " + cardNumber);
            writer.newLine();
            writer.write("Expiry Date: " + expiryDate);
            writer.newLine();
            writer.write("CVV: " + cvv);
            writer.newLine();
            writer.write("---------");
            writer.newLine();


            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Payment info saved successfully.");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error saving payment info.");
            alert.showAndWait();
        }

    }
}