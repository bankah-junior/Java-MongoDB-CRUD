package com.amalitech.javamongodbcrud.dao.impl;

import com.amalitech.javamongodbcrud.dao.UserDAO;
import com.amalitech.javamongodbcrud.models.UserModel;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.amalitech.javamongodbcrud.utils.PasswordUtils.hashPassword;
import static com.amalitech.javamongodbcrud.utils.PasswordUtils.verifyPassword;

public class UserDAOImpl implements UserDAO {
    private final MongoCollection<Document> usersCollection;

    public UserDAOImpl(MongoDatabase database) {
        if (database == null) {
            throw new IllegalArgumentException("database must not be null");
        }
        this.usersCollection = database.getCollection("users", Document.class);
    }

    /**
     * Create a new user in the database.
     *
     * @param userModel The user model containing user details.
     * @return The created user model with assigned ID, or null if creation failed.
     */
    @Override
    public UserModel createUser(UserModel userModel) {
        String hashPassword = hashPassword(userModel.getPassword());
        Document doc = new Document("name", userModel.getName())
                .append("email", userModel.getEmail())
                .append("password", hashPassword).append("createdAt", System.currentTimeMillis());
        usersCollection.insertOne(doc);
        ObjectId id = doc.getObjectId("_id");
        if (id != null) {
            userModel.setId(id.toHexString());
            return userModel;
        }
        return null;
    }

    /**
     * Login a user by verifying email and password.
     *
     * @param email    The user's email.
     * @param password The user's password.
     * @return The user model if credentials are valid, otherwise null.
     */
    @Override
    public UserModel loginUser(String email, String password) {
        Document query = new Document("email", email);
        Document userDoc = usersCollection.find(query).first();
        if (userDoc != null) {
            String hashedPassword = userDoc.getString("password");
            if (verifyPassword(password, hashedPassword)) {
                return new UserModel(
                        userDoc.getObjectId("_id").toHexString(),
                        userDoc.getString("name"),
                        userDoc.getString("email"),
                        userDoc.getString("password"),
                        userDoc.getLong("createdAt"),
                        userDoc.getLong("updatedAt")
                );
            } else return null;
        }
        return null;
    }

    /**
     * Update an existing user's details.
     *
     * @param id        The ID of the user to update.
     * @param userModel The user model containing updated details.
     * @return The updated user model.
     */
    @Override
    public UserModel updateUser(String id, UserModel userModel) {
        Document query = new Document("_id", new ObjectId(id));
        Document update = new Document("$set", new Document("name", userModel.getName())
                .append("email", userModel.getEmail())
                .append("password", userModel.getPassword()).append("updatedAt", System.currentTimeMillis()));
        usersCollection.updateOne(query, update);
        userModel.setId(id);
        return userModel;
    }

    /**
     * Delete a user from the database.
     *
     * @param id The ID of the user to delete.
     * @return The deleted user model, or null if the user was not found.
     */
    @Override
    public UserModel deleteUser(String id) {
        Document query = new Document("_id", new ObjectId(id));
        Document userDoc = usersCollection.find(query).first();
        if (userDoc != null) {
            usersCollection.deleteOne(query);
            return new UserModel(
                    userDoc.getObjectId("_id").toHexString(),
                    userDoc.getString("name"),
                    userDoc.getString("email"),
                    userDoc.getString("password"),
                    userDoc.getLong("createdAt"),
                    userDoc.getLong("updatedAt")
            );
        }
        return null;
    }

    /**
     * Retrieve a user by ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The user model, or null if the user was not found.
     */
    @Override
    public UserModel getUser(String id) {
        Document query = new Document("_id", new ObjectId(id));
        Document userDoc = usersCollection.find(query).first();
        if (userDoc != null) {
            return new UserModel(
                    userDoc.getObjectId("_id").toHexString(),
                    userDoc.getString("name"),
                    userDoc.getString("email"),
                    userDoc.getString("password"),
                    userDoc.getLong("createdAt"),
                    userDoc.getLong("updatedAt")
            );
        }
        return null;
    }
}