package com.amalitech.javamongodbcrud.controllers;

import com.amalitech.javamongodbcrud.models.UserModel;
import com.amalitech.javamongodbcrud.services.impl.UserServiceImpl;
import com.amalitech.javamongodbcrud.utils.exceptions.UserInputsException;
import com.mongodb.client.MongoDatabase;

import static com.amalitech.javamongodbcrud.utils.RegexValidatorUtils.*;

public class UserController {
    UserServiceImpl userService;
    public UserController(MongoDatabase database) {
        userService = new UserServiceImpl(database);
    }

    /**
     * Create a new user.
     *
     * @param userModel The user model containing user details.
     * @return The created user model or null if the user already exists.
     */
    public UserModel createUser(UserModel userModel) {
        if (userModel.getEmail() == null || userModel.getPassword() == null || userModel.getName() == null) {
            throw new UserInputsException("Email, password, and username cannot be null.");
        }
        if (!isValidEmail(userModel.getEmail())) {
            throw new UserInputsException("Invalid email format.");
        }
        if (!isValidPassword(userModel.getPassword())) {
            throw new UserInputsException("Password must be at least 8 characters long, " +
                    "contain at least one uppercase and lowercase letter, " +
                    "one digit, and one special character");
        }
        if (!isValidUsername(userModel.getName())) {
            throw new UserInputsException("Username must be 3-60 characters long and can contain letters, numbers, dots, underscores, and hyphens.");
        }
        UserModel newUser = userService.createUser(userModel);
        if (newUser == null) {
            System.out.println("User already exists with this email.");
            return null;
        }
        System.out.println(newUser.toString());
        return newUser;
    }

    /**
     * Login a user.
     *
     * @param email    The user's email.
     * @param password The user's password.
     * @return The logged-in user model or null if credentials are invalid.
     */
    public UserModel loginUser(String email, String password) {
        if (email == null || password == null) {
            throw new UserInputsException("Email and password cannot be null.");
        }
        if (!isValidEmail(email)) {
            throw new UserInputsException("Invalid email format.");
        }
        UserModel user = userService.loginUser(email, password);
        if (user == null) {
            System.out.println("Invalid email or password.");
            return null;
        }
        System.out.println("Login successful: " + user.toString());
        return user;
    }

    /**
     * Update an existing user.
     *
     * @param id        The ID of the user to update.
     * @param userModel The user model containing updated details.
     * @return The updated user model.
     */
    public UserModel updateUser(String id, UserModel userModel) {
        UserModel updatedUser = userService.updateUser(id, userModel);
        System.out.println("User updated: " + updatedUser.toString());
        return updatedUser;
    }

    /**
     * Delete a user.
     *
     * @param id The ID of the user to delete.
     * @return The deleted user model or null if the user was not found.
     */
    public UserModel deleteUser(String id) {
        UserModel deletedUser = userService.deleteUser(id);
        if (deletedUser == null) {
            System.out.println("User not found.");
            return null;
        }
        System.out.println("User deleted: " + deletedUser.toString());
        return deletedUser;
    }

    /**
     * Get a user by ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The user model or null if the user was not found.
     */
    public UserModel getUser(String id) {
        UserModel user = userService.getUser(id);
        if (user == null) {
            System.out.println("User not found.");
            return null;
        }
        System.out.println("User retrieved: " + user.toString());
        return user;
    }
}
