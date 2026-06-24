import java.util.List;
import java.util.Scanner;

public class MainApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final GcashEngine engine = new GcashEngine();

    public static void main(String[] args) {
        while (true) {
            if (!engine.isLoggedIn()) {
                showWelcomeMenu();
            } else {
                showDashboardMenu();
            }
        }
    }

    private static void showWelcomeMenu() {
        System.out.println("\n===========================================");
        System.out.println("     SECURE GCASH CORE TERMINAL ENGINE     ");
        System.out.println("===========================================");
        System.out.println("[1] Login with Mobile Number");
        System.out.println("[2] Register New Wallet Account");
        System.out.println("[3] Terminate Engine Connection");
        System.out.println("===========================================");
        System.out.print("Select Command Option: ");

        int choice = getValidIntegerInput();
        if (choice == 1) {
            System.out.print("Enter Mobile Number: ");
            String mobile = scanner.next();
            System.out.print("Enter Password: ");
            String password = scanner.next();
            
            if (engine.login(mobile, password)) {
                System.out.println("\n✔ Token Validated. Loading Dashboard...");
            } else {
                System.out.println("\n❌ ACCESS DENIED: Bad credentials.");
            }
        } else if (choice == 2) {
            scanner.nextLine(); 
            System.out.print("Enter Legal Full Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Mobile Number (09XXXXXXXXX): ");
            String mobile = scanner.next();
            System.out.print("Enter Email: ");
            String email = scanner.next();
            System.out.print("Enter Password: ");
            String password = scanner.next();

            String status = engine.registerAccount(email, password, mobile, name);
            if (status.equals("SUCCESS")) {
                System.out.println("\n✔ REGISTRATION SUCCESS!");
            } else {
                System.out.println("\n❌ ERROR: " + status);
            }
        } else if (choice == 3) {
            System.out.println("\nShutdown safe.");
            scanner.close();
            System.exit(0);
        }
    }

    private static void showDashboardMenu() {
        UserAccount user = engine.getCurrentUser();
        System.out.println("\n===========================================");
        System.out.println("           SECURE WALLET NETWORK           ");
        System.out.println("===========================================");
        System.out.printf(" Holder: %s | Tier: %s\n", user.getFullName(), user.isVerified() ? "VERIFIED ✔" : "UNVERIFIED ⚠️");
        System.out.println("-------------------------------------------");
        System.out.println("[1] Balance Inquiry");
        System.out.println("[2] Load Funds Cash In");
        System.out.println("[3] Send Money (Express Send)");
        System.out.println("[4] Audit Trail Logs");
        System.out.println("[5] KYC Verification");
        System.out.println("[6] Profile Metadata");
        System.out.println("[7] Logout");
        System.out.println("===========================================");
        System.out.print("Select Service Code: ");

        int choice = getValidIntegerInput();
        switch (choice) {
            case 1:
                handleServiceLoop("Balance Inquiry", () -> System.out.printf("\nBalance: PHP %,.2f\n", user.getBalance()));
                break;
            case 2:
                handleServiceLoop("Cash In", () -> {
                    System.out.print("\nEnter Amount: PHP ");
                    double amt = getValidDoubleInput();
                    String status = engine.processCashIn(amt);
                    if (status.equals("SUCCESS")) printReceipt("CASH-IN", amt, "Merchant Node", engine.generateRefNo());
                    else System.out.println("\n❌ FAILED: " + status);
                });
                break;
            case 3:
                handleServiceLoop("Express Send", () -> {
                    System.out.print("\nEnter Recipient Mobile: ");
                    String target = scanner.next();
                    System.out.print("Enter Amount: PHP ");
                    double transferAmt = getValidDoubleInput();

                    String status = engine.processTransfer(target, transferAmt);
                    if (status.equals("SUCCESS")) {
                        UserAccount rec = engine.findAccountByMobile(target);
                        printReceipt("EXPRESS SEND", transferAmt, rec.getFullName(), engine.generateRefNo());
                    } else System.out.println("\n❌ TRANSACTION REJECTED: " + status);
                });
                break;
            case 4:
                handleServiceLoop("Audit Trail", () -> {
                    List<String> logs = engine.getLogs();
                    System.out.println("\n--- RUNTIME SYSTEM TELEMETRY LOGS ---");
                    for (int i = logs.size() - 1; i >= 0; i--) System.out.println("• " + logs.get(i));
                });
                break;
            case 5:
                handleServiceLoop("KYC Identity Verification", () -> {
                    if (user.isVerified()) System.out.println("\nProfile is already verified.");
                    else {
                        System.out.print("Confirm Mobile to verify: ");
                        if (scanner.next().equals(user.getMobileNumber())) {
                            user.setVerified(true);
                            System.out.println("\n✔ Verification active!");
                        } else System.out.println("\n❌ Error: Mismatch.");
                    }
                });
                break;
            case 6:
                handleServiceLoop("Profile Metadata", () -> {
                    System.out.println("\n=========================================");
                    System.out.printf(" Name: %s\n Route: %s\n Email: %s\n", user.getFullName(), user.getMobileNumber(), user.getEmail());
                    System.out.printf(" Limit: PHP %,.2f\n", user.isVerified() ? GcashEngine.VERIFIED_BAL_CAP : GcashEngine.UNVERIFIED_BAL_CAP);
                    System.out.println("=========================================");
                });
                break;
            case 7:
                engine.logout();
                System.out.println("\nTokens purged.");
                break;
        }
    }

    private static void handleServiceLoop(String serviceName, Runnable serviceLogic) {
        boolean insideService = true;
        while (insideService) {
            serviceLogic.run();
            System.out.printf("\n--- [1] Stay in %s | [2] Return to Menu ---\nChoice: ", serviceName);
            if (getValidIntegerInput() == 2) insideService = false;
        }
    }

    private static void printReceipt(String type, double amount, String party, String refNo) {
        System.out.printf("\n=========================================\n TRANSACTION: %s\n AMOUNT: PHP %,.2f\n PARTY: %s\n REF #: %s\n=========================================\n", type, amount, party, refNo);
    }

    private static int getValidIntegerInput() {
        while (!scanner.hasNextInt()) { System.out.print("Enter an integer: "); scanner.next(); }
        return scanner.nextInt();
    }

    private static double getValidDoubleInput() {
        while (true) {
            if (scanner.hasNextDouble()) {
                double val = scanner.nextDouble();
                if (val > 0) return val;
            } else scanner.next();
            System.out.print("Enter a positive numeric value: PHP ");
        }
    }
}