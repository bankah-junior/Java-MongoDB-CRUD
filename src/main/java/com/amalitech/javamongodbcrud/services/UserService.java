package com.amalitech.javamongodbcrud.services;

import com.amalitech.javamongodbcrud.models.UserModel;

public interface UserService {
    UserModel createUser(UserModel userModel);
    UserModel loginUser(String email, String password);
    UserModel updateUser(String id, UserModel userModel);
    UserModel deleteUser(String id);
    UserModel getUser(String id);
}
