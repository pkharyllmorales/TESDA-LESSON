import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CheckBalanceTest {

    @Test
    public void testCheckBalanceWithDatabase() {
        CheckBalance cb = new CheckBalance();
        // Requirement: check a user id and check if the balance matches what the database contains
        double expectedBalance = 5000.00;
        double actualBalance = cb.getBalance("USER123");

        assertEquals(expectedBalance, actualBalance, "The returned balance must exactly match the database state.");
    }
}