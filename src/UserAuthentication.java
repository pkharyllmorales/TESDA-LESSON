public class UserAuthentication {
    // Simulated database reference record
    private final String dbEmail = "user@example.com";
    private final String dbPassword = "securePassword123";

    public boolean login(String email, String password) {
        if (email == null || password == null) {
            return false;
        }
        return email.equals(dbEmail) && password.equals(dbPassword);
    }
}