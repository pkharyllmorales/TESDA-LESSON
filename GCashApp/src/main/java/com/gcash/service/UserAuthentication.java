package com.gcash.service;

import java.util.*;
import java.util.regex.*;

public class UserAuthentication {
    
    // Database simulation - storing users
    private static Map<Integer, User> usersDatabase = new HashMap<>();
    private static int userIdCounter = 1001;
    private static Integer currentLoggedInUserId = null;
    
    // Inner class to represent a User
    private static class User {
        public int id;
        public String name;
        public String email;
        public String number;
        public String pin;
        public boolean isActive;
        
        User(int id, String name, String email, String number, String pin) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.number = number;
            this.pin = pin;
            this.isActive = true;
        }
    }
    
    // Validation methods
    private static boolean validateName(String name) {
        return name != null && !name.trim().isEmpty() && name.length() >= 3;
    }
    
    private static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }
    
    private static boolean validateNumber(String number) {
        return number != null && number.matches("\\d{10,11}");
    }
    
    private static boolean validatePin(String pin) {
        return pin != null && pin.matches("\\d{4,6}");
    }
    
    private static boolean emailExists(String email) {
        for (User user : usersDatabase.values()) {
            if (user.email.equals(email)) {
                return true;
            }
        }
        return false;
    }
    
    // Registration method
    public static Map<String, Object> registration(String name, String email, String number, String pin) {
        Map<String, Object> response = new HashMap<>();
        
        // Validate all fields
        if (!validateName(name)) {
            response.put("success", false);
            response.put("message", "Error: Name must be at least 3 characters long.");
            return response;
        }
        
        if (!validateEmail(email)) {
            response.put("success", false);
            response.put("message", "Error: Invalid email format.");
            return response;
        }
        
        if (emailExists(email)) {
            response.put("success", false);
            response.put("message", "Error: Email already registered.");
            return response;
        }
        
        if (!validateNumber(number)) {
            response.put("success", false);
            response.put("message", "Error: Phone number must be 10-11 digits.");
            return response;
        }
        
        if (!validatePin(pin)) {
            response.put("success", false);
            response.put("message", "Error: PIN must be 4-6 digits.");
            return response;
        }
        
        // Create new user
        int userId = userIdCounter++;
        User newUser = new User(userId, name, email, number, pin);
        usersDatabase.put(userId, newUser);
        
        response.put("success", true);
        response.put("message", "Registration successful!");
        response.put("userId", userId);
        return response;
    }
    
    // Login method
    public static Map<String, Object> login(String email, String pin) {
        Map<String, Object> response = new HashMap<>();
        
        // Check if user exists
        for (User user : usersDatabase.values()) {
            if (user.email.equals(email)) {
                if (!user.isActive) {
                    response.put("success", false);
                    response.put("message", "Error: This account is inactive. Contact support.");
                    return response;
                }
                
                // Check PIN
                if (user.pin.equals(pin)) {
                    currentLoggedInUserId = user.id;
                    response.put("success", true);
                    response.put("message", "Login successful!");
                    response.put("userId", user.id);
                    response.put("userName", user.name);
                    return response;
                } else {
                    response.put("success", false);
                    response.put("message", "Error: Incorrect PIN. Please try again.");
                    return response;
                }
            }
        }
        
        response.put("success", false);
        response.put("message", "Error: Email not found. Please register first.");
        return response;
    }
    
    // Change PIN method
    public static Map<String, Object> changePin(int userId, String oldPin, String newPin) {
        Map<String, Object> response = new HashMap<>();
        
        if (currentLoggedInUserId == null || !currentLoggedInUserId.equals(userId)) {
            response.put("success", false);
            response.put("message", "Error: You must be logged in to change PIN.");
            return response;
        }
        
        User user = usersDatabase.get(userId);
        if (user == null) {
            response.put("success", false);
            response.put("message", "Error: User not found.");
            return response;
        }
        
        if (!user.pin.equals(oldPin)) {
            response.put("success", false);
            response.put("message", "Error: Old PIN is incorrect.");
            return response;
        }
        
        if (!validatePin(newPin)) {
            response.put("success", false);
            response.put("message", "Error: New PIN must be 4-6 digits.");
            return response;
        }
        
        if (oldPin.equals(newPin)) {
            response.put("success", false);
            response.put("message", "Error: New PIN must be different from old PIN.");
            return response;
        }
        
        user.pin = newPin;
        response.put("success", true);
        response.put("message", "PIN changed successfully!");
        return response;
    }
    
    // Logout method
    public static void logout() {
        currentLoggedInUserId = null;
    }
    
    // Get current logged-in user ID
    public static Integer getCurrentUserId() {
        return currentLoggedInUserId;
    }
    
    // Get user info
    public static Map<String, Object> getUserInfo(int userId) {
        Map<String, Object> response = new HashMap<>();
        User user = usersDatabase.get(userId);
        
        if (user != null) {
            response.put("id", user.id);
            response.put("name", user.name);
            response.put("email", user.email);
            response.put("number", user.number);
            response.put("isActive", user.isActive);
        }
        
        return response;
    }
}
