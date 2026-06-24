import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GcashEngine {
    private final Map<String, UserAccount> database = new HashMap<>();
    private final List<String> transactionHistory = new ArrayList<>();
    private UserAccount currentUser = null;
    private final Random random = new Random();

    public static final double UNVERIFIED_BAL_CAP = 10000.00;
    public static final double VERIFIED_BAL_CAP = 100000.00;

    public GcashEngine() {
        database.put("09171234567", new UserAccount("merchant@gcash.ph", hashPassword("pass456"), "09171234567", "Juan Dela Cruz", true, 5000.00));
        database.put("09189876543", new UserAccount("unverified@test.com", hashPassword("pass789"), "09189876543", "Baby Girl", false, 100.00));
    }

    public final String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException("Cryptographic failure", e);
        }
    }

    public boolean isValidMobileNumber(String mobileNumber) {
        return mobileNumber != null && mobileNumber.matches("^09\\d{9}$");
    }

    public String registerAccount(String email, String password, String mobileNumber, String name) {
        if (!isValidMobileNumber(mobileNumber)) return "ERR_INVALID_MOBILE";
        if (database.containsKey(mobileNumber)) return "ERR_MOBILE_TAKEN";
        
        String cleanEmail = email.trim().toLowerCase();
        for (UserAccount acc : database.values()) {
            if (acc.getEmail().equalsIgnoreCase(cleanEmail)) return "ERR_EMAIL_TAKEN";
        }

        UserAccount newAcc = new UserAccount(cleanEmail, hashPassword(password), mobileNumber, name, false, 0.0);
        database.put(mobileNumber, newAcc);
        currentUser = newAcc; 
        return "SUCCESS";
    }

    public boolean login(String mobileNumber, String password) {
        if (!database.containsKey(mobileNumber)) return false;
        UserAccount acc = database.get(mobileNumber);
        if (acc.getPasswordHash().equals(hashPassword(password))) {
            currentUser = acc;
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
        transactionHistory.clear();
    }

    public UserAccount getCurrentUser() { return currentUser; }
    public boolean isLoggedIn() { return currentUser != null; }
    public UserAccount findAccountByMobile(String mobileNumber) { return database.get(mobileNumber.trim()); }

    public String processCashIn(double amount) {
        if (amount <= 0) return "ERR_INVALID_AMOUNT";
        double limit = currentUser.isVerified() ? VERIFIED_BAL_CAP : UNVERIFIED_BAL_CAP;
        if (currentUser.getBalance() + amount > limit) return "ERR_TIER_LIMIT_EXCEEDED";

        currentUser.addBalance(amount);
        transactionHistory.add("Cash In (OTC): +PHP " + String.format("%.2f", amount));
        return "SUCCESS";
    }

    public String processTransfer(String targetMobile, double amount) {
        if (amount <= 0) return "ERR_INVALID_AMOUNT";
        if (!currentUser.isVerified()) return "ERR_SENDER_UNVERIFIED";
        if (!isValidMobileNumber(targetMobile)) return "ERR_INVALID_MOBILE";

        UserAccount recipient = findAccountByMobile(targetMobile);
        if (recipient == null) return "ERR_NO_ACCOUNT";
        if (!recipient.isVerified()) return "ERR_RECIPIENT_UNVERIFIED";
        if (currentUser.getBalance() < amount) return "ERR_INSUFFICIENT_FUNDS";
        if (recipient.getBalance() + amount > VERIFIED_BAL_CAP) return "ERR_RECIPIENT_LIMIT_EXCEEDED";

        currentUser.deductBalance(amount);
        recipient.addBalance(amount);
        transactionHistory.add("Express Send to " + recipient.getFullName() + " (" + recipient.getMobileNumber() + "): -PHP " + String.format("%.2f", amount));
        return "SUCCESS";
    }

    public List<String> getLogs() { return transactionHistory; }
    public String generateRefNo() { return "REF-" + (100000 + random.nextInt(900000)); }
}