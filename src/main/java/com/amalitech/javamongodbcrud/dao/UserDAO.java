package com.amalitech.javamongodbcrud.dao;

import com.amalitech.javamongodbcrud.models.UserModel;

public interface UserDAO {
    UserModel createUser(UserModel userModel);
    UserModel loginUser(String email, String password);
    UserModel updateUser(String id, UserModel userModel);
    UserModel deleteUser(String id);
    UserModel getUser(String id);
}
