public class CashTransfer {
    private double senderBalance;
    private double receiverBalance;

    public CashTransfer(double senderBalance, double receiverBalance) {
        this.senderBalance = senderBalance;
        this.receiverBalance = receiverBalance;
    }

    public boolean transfer(double amount) {
        if (amount > 0 && senderBalance >= amount) {
            senderBalance -= amount;
            receiverBalance += amount;
            return true;
        }
        return false; // Transfer failed due to insufficient funds or invalid amount
    }

    public double getSenderBalance() { return senderBalance; }
    public double getReceiverBalance() { return receiverBalance; }
}