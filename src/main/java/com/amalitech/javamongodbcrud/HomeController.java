package com.amalitech.javamongodbcrud;

import com.amalitech.javamongodbcrud.models.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class HomeController {
    @FXML
    private Label username;

    private UserModel loggedInUser;

    public void setLoggedInUser(UserModel user) {
         this.loggedInUser = user;
         username.setText(loggedInUser.getName());
    }
}
