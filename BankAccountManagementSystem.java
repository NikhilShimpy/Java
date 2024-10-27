import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class BankAccount {
    private static int accountCounter = 1000;
    private int accountNumber;
    private String accountHolder;
    protected double balance;

    public BankAccount(String accountHolder, double balance) {
        this.accountNumber = ++accountCounter;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);
}

class SavingsAccount extends BankAccount {
    private static final double INTEREST_RATE = 0.04;
    private static final double MIN_BALANCE = 500.0;
    private List<String> transactions = new ArrayList<>();

    public SavingsAccount(String accountHolder, double balance) {
        super(accountHolder, balance);
        transactions.add("Account opened with initial balance: " + balance);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add("Deposited: " + amount + ", New Balance: " + balance);
            System.out.println("Successfully deposited " + amount);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && (balance - amount) >= MIN_BALANCE) {
            balance -= amount;
            transactions.add("Withdrew: " + amount + ", New Balance: " + balance);
            System.out.println("Successfully withdrew " + amount);
        } else {
            System.out.println("Insufficient balance or minimum balance requirement not met!");
        }
    }

    public void calculateInterest() {
        double interest = balance * INTEREST_RATE;
        balance += interest;
        transactions.add("Interest added: " + interest + ", New Balance: " + balance);
        System.out.println("Interest of " + interest + " added to your balance.");
    }

    public void printTransactions() {
        System.out.println("\nTransaction History for Account No. " + getAccountNumber() + ":");
        for (String transaction : transactions) {
            System.out.println(transaction);
        }
    }
}

class CheckingAccount extends BankAccount {
    private static final double OVERDRAFT_LIMIT = 1000.0;
    private List<String> transactions = new ArrayList<>();

    public CheckingAccount(String accountHolder, double balance) {
        super(accountHolder, balance);
        transactions.add("Account opened with initial balance: " + balance);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add("Deposited: " + amount + ", New Balance: " + balance);
            System.out.println("Successfully deposited " + amount);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && (balance + OVERDRAFT_LIMIT) >= amount) {
            balance -= amount;
            transactions.add("Withdrew: " + amount + ", New Balance: " + balance);
            System.out.println("Successfully withdrew " + amount);
        } else {
            System.out.println("Overdraft limit exceeded!");
        }
    }

    public void printTransactions() {
        System.out.println("\nTransaction History for Account No. " + getAccountNumber() + ":");
        for (String transaction : transactions) {
            System.out.println(transaction);
        }
    }
}

public class BankAccountManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<BankAccount> accounts = new ArrayList<>();

        try {
            while (true) {
                System.out.println("\n1. Open Account\n2. Deposit\n3. Withdraw\n4. Calculate Interest (Savings)\n5. Print Transactions\n6. Exit");
                System.out.print("Choose an option: ");
                int option = Integer.parseInt(scanner.nextLine());

                if (option == 1) {
                    System.out.print("Enter account holder name: ");
                    String name = scanner.nextLine();
                    System.out.print("Initial deposit: ");
                    double deposit = Double.parseDouble(scanner.nextLine());
                    System.out.print("Choose account type (1 for Savings, 2 for Checking): ");
                    int type = Integer.parseInt(scanner.nextLine());

                    BankAccount account;
                    if (type == 1) {
                        account = new SavingsAccount(name, deposit);
                    } else {
                        account = new CheckingAccount(name, deposit);
                    }
                    accounts.add(account);
                    System.out.println("Account created with Account Number: " + account.getAccountNumber());

                } else if (option == 2) {
                    System.out.print("Enter account number: ");
                    int accountNumber = Integer.parseInt(scanner.nextLine());
                    BankAccount account = findAccountByNumber(accounts, accountNumber);

                    if (account != null) {
                        System.out.print("Enter amount to deposit: ");
                        double amount = Double.parseDouble(scanner.nextLine());
                        account.deposit(amount);
                    } else {
                        System.out.println("Account not found.");
                    }

                } else if (option == 3) {
                    System.out.print("Enter account number: ");
                    int accountNumber = Integer.parseInt(scanner.nextLine());
                    BankAccount account = findAccountByNumber(accounts, accountNumber);

                    if (account != null) {
                        System.out.print("Enter amount to withdraw: ");
                        double amount = Double.parseDouble(scanner.nextLine());
                        account.withdraw(amount);
                    } else {
                        System.out.println("Account not found.");
                    }

                } else if (option == 4) {
                    System.out.print("Enter account number (Savings only): ");
                    int accountNumber = Integer.parseInt(scanner.nextLine());
                    BankAccount account = findAccountByNumber(accounts, accountNumber);

                    if (account != null && account instanceof SavingsAccount) {
                        ((SavingsAccount) account).calculateInterest();
                    } else {
                        System.out.println("Account not found or is not a Savings Account.");
                    }

                } else if (option == 5) {
                    System.out.print("Enter account number to view transactions: ");
                    int accountNumber = Integer.parseInt(scanner.nextLine());
                    BankAccount account = findAccountByNumber(accounts, accountNumber);

                    if (account != null) {
                        if (account instanceof SavingsAccount) {
                            ((SavingsAccount) account).printTransactions();
                        } else if (account instanceof CheckingAccount) {
                            ((CheckingAccount) account).printTransactions();
                        }
                    } else {
                        System.out.println("Account not found.");
                    }

                } else if (option == 6) {
                    System.out.println("Exiting the system.");
                    break;
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter correct data.");
        } finally {
            scanner.close();
        }
    }

    private static BankAccount findAccountByNumber(List<BankAccount> accounts, int accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }
}
