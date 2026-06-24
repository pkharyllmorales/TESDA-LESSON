import java.util.HashMap;
import java.util.Map;

public class CheckBalance {
    // Simulated database mapping user IDs to their respective balances
    private final Map<String, Double> databaseBalances = new HashMap<>();

    public CheckBalance() {
        databaseBalances.put("USER123", 5000.00);
        databaseBalances.put("USER456", 150.75);
    }

    public double getBalance(String userId) {
        return databaseBalances.getOrDefault(userId, -1.0); // Returns -1.0 if user doesn't exist
    }
}