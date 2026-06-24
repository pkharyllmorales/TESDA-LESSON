import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionsTest {

    @Test
    public void testTransactionsAreDisplayedProperly() {
        Transactions txHistory = new Transactions();
        
        // Populate transaction records
        txHistory.addTransaction("Cash In", 1000.00);
        txHistory.addTransaction("Express Send", 250.00);

        // Requirement: write a test if transactions are displayed properly
        assertTrue(txHistory.areTransactionsDisplayed(), "Transaction log should not be empty.");
        assertEquals(2, txHistory.getHistory().size(), "Transaction history count should accurately match entries.");
        assertTrue(txHistory.getHistory().get(1).contains("Express Send"), "The display details must preserve metadata accuracy.");
    }
}