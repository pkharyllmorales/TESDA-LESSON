import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserAuthenticationTest {

    @Test
    public void testValidLogin() {
        UserAuthentication auth = new UserAuthentication();
        // Requirement: write a valid login - check database for reference
        boolean result = auth.login("user@example.com", "securePassword123");
        assertTrue(result, "Login should succeed with correct credentials matching database.");
    }

    @Test
    public void testInvalidLogin() {
        UserAuthentication auth = new UserAuthentication();
        // Requirement: write an invalid login - wrong password or email
        boolean wrongPassword = auth.login("user@example.com", "wrongPassword");
        boolean wrongEmail = auth.login("stranger@example.com", "securePassword123");

        assertFalse(wrongPassword, "Login should fail with an incorrect password.");
        assertFalse(wrongEmail, "Login should fail with an unregistered email.");
    }
}