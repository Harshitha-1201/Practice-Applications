import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SimpleATM {

    private static class User {
        private String userId;
        private String pin;
        private Account account;

        public User(String userId, String pin, Account account) {
            this.userId = userId;
            this.pin = pin;
            this.account = account;
        }

        public String getPin() {
            return pin;
        }

        public Account getAccount() {
            return account;
        }
    }

    private static class Account {
        private double balance;
        private TransactionHistory transactionHistory;

        public Account(double initialBalance) {
            this.balance = initialBalance;
            this.transactionHistory = new TransactionHistory();
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            balance += amount;
            transactionHistory.addTransaction("Deposited: $" + amount);
        }

        public boolean withdraw(double amount) {
            if (amount > balance) {
                return false;
            }
            balance -= amount;
            transactionHistory.addTransaction("Withdrew: $" + amount);
            return true;
        }

        public boolean transfer(Account recipient, double amount) {
            if (withdraw(amount)) {
                recipient.deposit(amount);
                transactionHistory.addTransaction("Transferred: $" + amount + " to account " + recipient.hashCode());
                return true;
            }
            return false;
        }

        public TransactionHistory getTransactionHistory() {
            return transactionHistory;
        }
    }

    private static class TransactionHistory {
        private List<String> transactions;

        public TransactionHistory() {
            transactions = new ArrayList<>();
        }

        public void addTransaction(String transaction) {
            transactions.add(transaction);
        }

        public void printHistory() {
            if (transactions.isEmpty()) {
                System.out.println("No transactions found.");
            } else {
                for (String transaction : transactions) {
                    System.out.println(transaction);
                }
            }
        }
    }

    private static class ATM {
        private Map<String, User> users;
        private User currentUser;

        public ATM() {
            users = new HashMap<>();
            Account account1 = new Account(500.00);
            Account account2 = new Account(1000.00);
            users.put("user1", new User("user1", "1234", account1));
            users.put("user2", new User("user2", "5678", account2));
        }

        public void start() {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Welcome to the Simple ATM");

            while (true) {
                System.out.print("Enter user ID: ");
                String userId = scanner.nextLine();
                System.out.print("Enter PIN: ");
                String pin = scanner.nextLine();

                if (authenticate(userId, pin)) {
                    System.out.println("Authentication successful.");
                    performOperations(scanner);
                } else {
                    System.out.println("Invalid user ID or PIN. Try again.");
                }
            }
        }

        private boolean authenticate(String userId, String pin) {
            User user = users.get(userId);
            if (user != null && user.getPin().equals(pin)) {
                currentUser = user;
                return true;
            }
            return false;
        }

        private void performOperations(Scanner scanner) {
            while (true) {
                System.out.println("\n1. View Transaction History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        currentUser.getAccount().getTransactionHistory().printHistory();
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        scanner.nextLine();
                        if (currentUser.getAccount().withdraw(withdrawAmount)) {
                            System.out.println("Withdrawal successful.");
                        } else {
                            System.out.println("Insufficient funds.");
                        }
                        break;
                    case 3:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        scanner.nextLine();
                        currentUser.getAccount().deposit(depositAmount);
                        System.out.println("Deposit successful.");
                        break;
                    case 4:
                        System.out.print("Enter recipient account ID (hash code): ");
                        int recipientHashCode = scanner.nextInt();
                        scanner.nextLine();
                        Account recipientAccount = findAccountByHashCode(recipientHashCode);
                        if (recipientAccount != null) {
                            System.out.print("Enter amount to transfer: ");
                            double transferAmount = scanner.nextDouble();
                            scanner.nextLine();
                            if (currentUser.getAccount().transfer(recipientAccount, transferAmount)) {
                                System.out.println("Transfer successful.");
                            } else {
                                System.out.println("Transfer failed. Insufficient funds.");
                            }
                        } else {
                            System.out.println("Recipient account not found.");
                        }
                        break;
                    case 5:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }
        }

        private Account findAccountByHashCode(int hashCode) {
            for (User user : users.values()) {
                if (user.getAccount().hashCode() == hashCode) {
                    return user.getAccount();
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
