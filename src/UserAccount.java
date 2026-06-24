public class UserAccount {
    private final String email;
    private final String passwordHash; 
    private final String mobileNumber;  
    private final String fullName;
    private boolean isVerified;
    private double balance;

    public UserAccount(String email, String passwordHash, String mobileNumber, String fullName, boolean isVerified, double initialBalance) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.mobileNumber = mobileNumber;
        this.fullName = fullName;
        this.isVerified = isVerified;
        this.balance = initialBalance;
    }

    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getMobileNumber() { return mobileNumber; }
    public String getFullName() { return fullName; }
    public boolean isVerified() { return isVerified; }
    public double getBalance() { return balance; }
    
    public void setVerified(boolean verified) { this.isVerified = verified; }
    public void addBalance(double amount) { this.balance += amount; }
    public void deductBalance(double amount) { this.balance -= amount; }
}