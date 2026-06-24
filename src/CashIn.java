public class CashIn {
    private double balance;

    public CashIn(double initialBalance) {
        this.balance = initialBalance;
    }

    public double deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
        return this.balance;
    }

    public double getBalance() {
        return this.balance;
    }
}