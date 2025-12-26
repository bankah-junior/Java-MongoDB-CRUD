package com.amalitech.javamongodbcrud.models;

public class UserModel {
    private String id;
    private String name;
    private String email;
    private String password;
    private Long createdAt;
    private Long updatedAt;

    public UserModel(String id, String name, String email, String password, Long createdAt, Long updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        String updatedAtFromMillisToDate = updatedAt != null ? new java.util.Date(updatedAt).toString() : "null";
        String createdAtFromMillisToDate = createdAt != null ? new java.util.Date(createdAt).toString() : "null";
        return "UserModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAtFromMillisToDate + '\'' +
                ", updatedAt=" + updatedAtFromMillisToDate +
                '}';
    }
}
