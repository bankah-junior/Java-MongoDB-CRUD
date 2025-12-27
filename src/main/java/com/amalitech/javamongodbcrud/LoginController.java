package com.amalitech.javamongodbcrud;

import com.amalitech.javamongodbcrud.controllers.UserController;
import com.amalitech.javamongodbcrud.utils.MongoDBConnection;
import com.amalitech.javamongodbcrud.utils.exceptions.UserInputsException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    protected void onLoginButtonClick() {
        String email = emailField.getText();
        String password = passwordField.getText();
        MongoClient client = MongoDBConnection.connect();
        try {
            MongoDatabase db = client.getDatabase("java-demo");
            UserController userController = new UserController(db);
            var user = userController.loginUser(email, password);
            if (user != null) {
                try {
                    messageLabel.setVisible(false);
                    Stage stage = (Stage) emailField.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    HomeController homeController = fxmlLoader.getController();
                    homeController.setLoggedInUser(user);
                    stage.setTitle("Home Screen");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    messageLabel.setText("OPPS!!! Navigation broken. Try again.");
                    System.out.println(e.getMessage());
                    messageLabel.setVisible(true);
                }
            } else {
                messageLabel.setText("No user found with the provided email.");
                messageLabel.setVisible(true);
            }
        } catch (UserInputsException e) {
            messageLabel.setText(e.getMessage());
            messageLabel.setVisible(true);
        } finally {
            client.close(); // close only after all DB work is done
        }
    }

    @FXML
    protected void onRegisterButtonClick() throws IOException {
        Stage stage = (Stage) emailField.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
