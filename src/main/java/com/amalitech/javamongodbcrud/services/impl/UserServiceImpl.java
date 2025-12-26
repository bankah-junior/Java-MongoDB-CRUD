package com.amalitech.javamongodbcrud.services.impl;

import com.amalitech.javamongodbcrud.dao.impl.UserDAOImpl;
import com.amalitech.javamongodbcrud.models.UserModel;
import com.amalitech.javamongodbcrud.services.UserService;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class UserServiceImpl implements UserService {
    UserDAOImpl userDAO;
    public UserServiceImpl(MongoDatabase database) {
        userDAO = new UserDAOImpl(database);
    }

    /**
     * Create a new user.
     *
     * @param userModel The user model containing user details.
     * @return The created user model or null if the user already exists.
     */
    @Override
    public UserModel createUser(UserModel userModel) {
        UserModel existingUser = userDAO.loginUser(userModel.getEmail(), userModel.getPassword());
        if (existingUser != null) {
            return null; // User already exists
        }
        return userDAO.createUser(userModel);
    }

    /**
     * Login a user.
     *
     * @param email    The user's email.
     * @param password The user's password.
     * @return The logged-in user model or null if credentials are invalid.
     */
    @Override
    public UserModel loginUser(String email, String password) {
        return userDAO.loginUser(email, password);
    }

    /**
     * Update an existing user.
     *
     * @param id        The ID of the user to update.
     * @param userModel The user model containing updated details.
     * @return The updated user model.
     */
    @Override
    public UserModel updateUser(String id, UserModel userModel) {
        return userDAO.updateUser(id, userModel);
    }

    /**
     * Delete a user.
     *
     * @param id The ID of the user to delete.
     * @return The deleted user model.
     */
    @Override
    public UserModel deleteUser(String id) {
        return userDAO.deleteUser(id);
    }

    /**
     * Get a user by ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The user model.
     */
    @Override
    public UserModel getUser(String id) {
        return userDAO.getUser(id);
    }
}
