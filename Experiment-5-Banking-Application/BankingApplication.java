import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

abstract class BankAccount {
    protected String accountNumber;
    protected String holderName;
    protected double balance;

    public BankAccount(String accountNumber, String holderName, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialDeposit;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }
        balance += amount;
    }

    public abstract void withdraw(double amount);

    public abstract void applyMonthlyFeature();

    public void display() {
        System.out.printf("Account: %s | Holder: %s | Balance: %.2f%n", accountNumber, holderName, balance);
    }
}

class SavingsAccount extends BankAccount {
    private double annualInterestRate;

    public SavingsAccount(String accountNumber, String holderName, double initialDeposit, double annualInterestRate) {
        super(accountNumber, holderName, initialDeposit);
        this.annualInterestRate = annualInterestRate;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds in savings account.");
        }
        balance -= amount;
    }

    @Override
    public void applyMonthlyFeature() {
        double monthlyInterest = balance * (annualInterestRate / 12.0) / 100.0;
        balance += monthlyInterest;
    }

    @Override
    public void display() {
        System.out.print("[Savings] ");
        super.display();
    }
}

class CurrentAccount extends BankAccount {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, String holderName, double initialDeposit, double overdraftLimit) {
        super(accountNumber, holderName, initialDeposit);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }
        if ((balance - amount) < -overdraftLimit) {
            throw new IllegalArgumentException("Overdraft limit exceeded for current account.");
        }
        balance -= amount;
    }

    @Override
    public void applyMonthlyFeature() {
        if (balance < 0) {
            balance -= 100;
        }
    }

    @Override
    public void display() {
        System.out.print("[Current] ");
        super.display();
    }
}

public class BankingApplication {

    private static final ArrayList<BankAccount> accounts = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt(scanner, "Enter your choice (1-7): ");

            switch (choice) {
                case 1:
                    createSavingsAccount(scanner);
                    break;
                case 2:
                    createCurrentAccount(scanner);
                    break;
                case 3:
                    depositMoney(scanner);
                    break;
                case 4:
                    withdrawMoney(scanner);
                    break;
                case 5:
                    applyMonthlyUpdates();
                    break;
                case 6:
                    listAccounts();
                    break;
                case 7:
                    running = false;
                    System.out.println("Exiting Banking Application.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-7.");
            }
            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("=== Banking Application ===");
        System.out.println("1. Create Savings Account");
        System.out.println("2. Create Current Account");
        System.out.println("3. Deposit Money");
        System.out.println("4. Withdraw Money");
        System.out.println("5. Apply Monthly Updates");
        System.out.println("6. List All Accounts");
        System.out.println("7. Exit");
    }

    private static void createSavingsAccount(Scanner scanner) {
        String accNo = readString(scanner, "Account Number: ");
        String name = readString(scanner, "Holder Name: ");
        double deposit = readDouble(scanner, "Initial Deposit: ");
        double interest = readDouble(scanner, "Annual Interest Rate (%): ");

        if (deposit < 0 || interest < 0) {
            System.out.println("Initial deposit and interest rate cannot be negative.");
            return;
        }

        accounts.add(new SavingsAccount(accNo, name, deposit, interest));
        System.out.println("Savings account created successfully.");
    }

    private static void createCurrentAccount(Scanner scanner) {
        String accNo = readString(scanner, "Account Number: ");
        String name = readString(scanner, "Holder Name: ");
        double deposit = readDouble(scanner, "Initial Deposit: ");
        double overdraft = readDouble(scanner, "Overdraft Limit: ");

        if (deposit < 0 || overdraft < 0) {
            System.out.println("Initial deposit and overdraft limit cannot be negative.");
            return;
        }

        accounts.add(new CurrentAccount(accNo, name, deposit, overdraft));
        System.out.println("Current account created successfully.");
    }

    private static void depositMoney(Scanner scanner) {
        BankAccount account = getAccountByNumber(scanner);
        if (account == null) {
            return;
        }

        double amount = readDouble(scanner, "Deposit Amount: ");
        try {
            account.deposit(amount);
            System.out.println("Deposit successful.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void withdrawMoney(Scanner scanner) {
        BankAccount account = getAccountByNumber(scanner);
        if (account == null) {
            return;
        }

        double amount = readDouble(scanner, "Withdrawal Amount: ");
        try {
            account.withdraw(amount);
            System.out.println("Withdrawal successful.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void applyMonthlyUpdates() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
            return;
        }

        for (BankAccount account : accounts) {
            account.applyMonthlyFeature();
        }
        System.out.println("Monthly updates applied to all accounts.");
    }

    private static void listAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
            return;
        }

        System.out.println("\n=== Account List ===");
        for (BankAccount account : accounts) {
            account.display();
        }
    }

    private static BankAccount getAccountByNumber(Scanner scanner) {
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
            return null;
        }

        String accNo = readString(scanner, "Enter Account Number: ");
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equalsIgnoreCase(accNo.trim())) {
                return account;
            }
        }

        System.out.println("Account not found.");
        return null;
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException ex) {
                System.out.println("Invalid integer input. Try again.");
                scanner.nextLine();
            }
        }
    }

    private static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException ex) {
                System.out.println("Invalid number input. Try again.");
                scanner.nextLine();
            }
        }
    }

    private static String readString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine();
            if (!value.trim().isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be empty. Try again.");
        }
    }
}
