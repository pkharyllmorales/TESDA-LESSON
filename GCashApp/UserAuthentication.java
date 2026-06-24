package GCashApp;

import java.util.*;
import java.util.regex.*;

public class UserAuthentication {
    
    // Database simulation - storing users
    private static Map<Integer, User> usersDatabase = new HashMap<>();
    private static int userIdCounter = 1001;
    private static Integer currentLoggedInUserId = null;
    
    // Inner class to represent a User
    private static class User {
        int id;
        String name;
        String email;
        String number;
        String pin;
        boolean isActive;
        
        User(int id, String name, String email, String number, String pin) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.number = number;
            this.pin = pin;
            this.isActive = true;
        }
    }
    

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
    public static int registration(String name, String email, String number, String pin) {
        // Validate all fields
        if (!validateName(name)) {
            System.out.println("Error: Name must be at least 3 characters long.");
            return -1;
        }
        
        if (!validateEmail(email)) {
            System.out.println("Error: Invalid email format.");
            return -1;
        }
        
        if (emailExists(email)) {
            System.out.println("Error: Email already registered.");
            return -1;
        }
        
        if (!validateNumber(number)) {
            System.out.println("Error: Phone number must be 10-11 digits.");
            return -1;
        }
        
        if (!validatePin(pin)) {
            System.out.println("Error: PIN must be 4-6 digits.");
            return -1;
        }
        
        // Create new user
        int userId = userIdCounter++;
        User newUser = new User(userId, name, email, number, pin);
        usersDatabase.put(userId, newUser);
        
        System.out.println("Registration successful! Your ID: " + userId);
        return userId;
    }
    
    // Login method
    public static int login(String email, String pin) {
        // Check if user exists
        for (User user : usersDatabase.values()) {
            if (user.email.equals(email)) {
                if (!user.isActive) {
                    System.out.println("Error: This account is inactive. Contact support.");
                    return -1;
                }
                
                // Check PIN
                if (user.pin.equals(pin)) {
                    currentLoggedInUserId = user.id;
                    System.out.println("Login successful! Welcome, " + user.name);
                    return user.id;
                } else {
                    System.out.println("Error: Incorrect PIN. Please try again.");
                    return -1;
                }
            }
        }
        
        System.out.println("Error: Email not found. Please register first.");
        return -1;
    }
    
    // Change PIN method
    public static boolean changePin(int userId, String oldPin, String newPin) {
        if (currentLoggedInUserId == null || !currentLoggedInUserId.equals(userId)) {
            System.out.println("Error: You must be logged in to change PIN.");
            return false;
        }
        
        User user = usersDatabase.get(userId);
        if (user == null) {
            System.out.println("Error: User not found.");
            return false;
        }
        
        if (!user.pin.equals(oldPin)) {
            System.out.println("Error: Old PIN is incorrect.");
            return false;
        }
        
        if (!validatePin(newPin)) {
            System.out.println("Error: New PIN must be 4-6 digits.");
            return false;
        }
        
        if (oldPin.equals(newPin)) {
            System.out.println("Error: New PIN must be different from old PIN.");
            return false;
        }
        
        user.pin = newPin;
        System.out.println("PIN changed successfully!");
        return true;
    }
    
    // Logout method
    public static void logout() {
        if (currentLoggedInUserId != null) {
            User user = usersDatabase.get(currentLoggedInUserId);
            System.out.println("User " + user.name + " logged out successfully.");
            currentLoggedInUserId = null;
        } else {
            System.out.println("No user is currently logged in.");
        }
    }
    
    // Helper method to get current logged-in user ID
    public static Integer getCurrentUserId() {
        return currentLoggedInUserId;
    }
    
    // Helper method to get user info (for testing)
    public static String getUserInfo(int userId) {
        User user = usersDatabase.get(userId);
        if (user != null) {
            return "ID: " + user.id + ", Name: " + user.name + ", Email: " + user.email;
        }
        return "User not found";
    }
}
