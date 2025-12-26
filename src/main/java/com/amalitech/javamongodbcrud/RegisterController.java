package com.amalitech.javamongodbcrud;

import com.amalitech.javamongodbcrud.controllers.UserController;
import com.amalitech.javamongodbcrud.models.UserModel;
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

public class RegisterController {
    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    protected void onRegisterButtonClick() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        MongoClient client = MongoDBConnection.connect();
        try {
            MongoDatabase db = client.getDatabase("java-demo");
            UserController userController = new UserController(db);
            UserModel newUser = new UserModel(null, username, email, password, null, null);
            var user = userController.createUser(newUser);
            if (user != null) {
                try {
                    messageLabel.setVisible(false);
                    Stage stage = (Stage) usernameField.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setTitle("Welcome " + username);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    messageLabel.setText("OPPS!!! Navigation broken. Try again.");
                    messageLabel.setVisible(true);
                }
            } else {
                messageLabel.setText("Registration failed. Please try again.");
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
    protected void onLoginButtonClick() throws IOException {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
