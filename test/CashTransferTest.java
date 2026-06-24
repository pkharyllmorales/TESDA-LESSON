import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CashTransferTest {

    @Test
    public void testTransferUpdatesBalancesForTwoUsers() {
        // Requirement: write a test if the balance is updating for two users
        // User A balance: 2000.00, User B balance: 500.00
        CashTransfer transaction = new CashTransfer(2000.00, 500.00);

        boolean success = transaction.transfer(300.00);

        assertTrue(success, "The transfer operation itself should be successful.");
        assertEquals(1700.00, transaction.getSenderBalance(), "Sender balance should decrease by the amount.");
        assertEquals(800.00, transaction.getReceiverBalance(), "Receiver balance should increase by the amount.");
    }
}