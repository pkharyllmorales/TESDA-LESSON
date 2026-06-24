import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CashInTest {

    @Test
    public void testBalanceIsUpdatingAfterCashIn() {
        // Start with an initial balance of 1000.00
        CashIn account = new CashIn(1000.00);
        
        // Requirement: write a test if the balance is updating
        account.deposit(500.00);
        
        assertEquals(1500.00, account.getBalance(), "Balance should correctly reflect the cash-in amount added.");
    }
}