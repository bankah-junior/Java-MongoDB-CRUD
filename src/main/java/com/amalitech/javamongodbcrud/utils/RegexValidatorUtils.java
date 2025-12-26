package com.amalitech.javamongodbcrud.utils;

public class RegexValidatorUtils {
    /**
     * Validates if the given email string matches the standard email format.
     *
     * @param email the email string to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password != null && password.matches(passwordRegex);
    }

    public static boolean isValidUsername(String username) {
        // Username must be 3-60 characters long and can contain letters, numbers, dots, underscores, and hyphens
        String usernameRegex = "^[a-zA-Z0-9._-]{3,60}$";
        return username != null && username.matches(usernameRegex);
    }
}
