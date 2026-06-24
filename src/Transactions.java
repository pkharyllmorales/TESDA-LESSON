import java.util.ArrayList;
import java.util.List;

public class Transactions {
    private final List<String> history = new ArrayList<>();

    public void addTransaction(String type, double amount) {
        history.add(type + ": PHP " + amount);
    }

    public List<String> getHistory() {
        return history;
    }

    public boolean areTransactionsDisplayed() {
        return !history.isEmpty();
    }
}