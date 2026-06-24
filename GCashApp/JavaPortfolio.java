package GCashApp;

public class JavaPortfolio {
    
    public static void main(String[] args) {
        System.out.println("GCash Online Banking System");
        
        // Test Case 1: Successful Registration
        System.out.println("--- Test Case 1: Successful Registration ---");
        int userId1 = UserAuthentication.registration("John Doe", "john@gmail.com", "09123456789", "1234");
        int userId2 = UserAuthentication.registration("Jane Smith", "jane@gmail.com", "09987654321", "5678");
        System.out.println();
        
        // Test Case 2: Registration Validation - Invalid Email
        System.out.println("--- Test Case 2: Invalid Email Registration ---");
        UserAuthentication.registration("Bob", "invalidemail", "09111111111", "1111");
        System.out.println();
        
        // Test Case 3: Registration Validation - Duplicate Email
        System.out.println("--- Test Case 3: Duplicate Email Registration ---");
        UserAuthentication.registration("John Smith", "john@gmail.com", "09222222222", "2222");
        System.out.println();
        
        // Test Case 4: Registration Validation - Invalid Phone Number
        System.out.println("--- Test Case 4: Invalid Phone Number ---");
        UserAuthentication.registration("Alex", "alex@gmail.com", "123", "1234");
        System.out.println();
        
        // Test Case 5: Registration Validation - Invalid PIN
        System.out.println("--- Test Case 5: Invalid PIN ---");
        UserAuthentication.registration("Chris", "chris@gmail.com", "09333333333", "12");
        System.out.println();
        
        // Test Case 6: Successful Login
        System.out.println("--- Test Case 6: Successful Login ---");
        int loginResult = UserAuthentication.login("john@gmail.com", "1234");
        System.out.println();
        
        // Test Case 7: Login - Incorrect PIN
        System.out.println("--- Test Case 7: Login with Incorrect PIN ---");
        UserAuthentication.login("jane@gmail.com", "0000");
        System.out.println();
        
        // Test Case 8: Login - Email Not Found
        System.out.println("--- Test Case 8: Login with Non-existent Email ---");
        UserAuthentication.login("notexist@gmail.com", "1234");
        System.out.println();
        
        // Test Case 9: Change PIN - Successful
        System.out.println("--- Test Case 9: Successful PIN Change ---");
        UserAuthentication.changePin(userId1, "1234", "9999");
        System.out.println();
        
        // Test Case 10: Change PIN - Incorrect Old PIN
        System.out.println("--- Test Case 10: Change PIN with Incorrect Old PIN ---");
        UserAuthentication.changePin(userId1, "0000", "8888");
        System.out.println();
        
        // Test Case 11: Get User Info
        System.out.println("--- Test Case 11: Get User Information ---");
        System.out.println("User Info: " + UserAuthentication.getUserInfo(userId1));
        System.out.println("User Info: " + UserAuthentication.getUserInfo(userId2));
        System.out.println();
        
        // Test Case 12: Logout
        System.out.println("--- Test Case 12: Logout ---");
        UserAuthentication.logout();
        System.out.println();
        
        // Test Case 13: Logout when no one is logged in
        System.out.println("--- Test Case 13: Logout with No Active Session ---");
        UserAuthentication.logout();
        System.out.println();
        
        // Test Case 14: Login with new PIN after change
        System.out.println("--- Test Case 14: Login with New PIN ---");
        UserAuthentication.login("john@gmail.com", "9999");
        System.out.println();
        
        // Test Case 15: Final Logout
        System.out.println("--- Test Case 15: Final Logout ---");
        UserAuthentication.logout();
        
        System.out.println("\n========== Test Completed ==========");
    }
}
