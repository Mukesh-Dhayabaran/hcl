package model;

import java.util.Objects;

public class BankAccount {

    private static int accountCounter = 1000;

    private final int accountNumber;
    private String accountHolder;
    private double balance;

    // Constructor 1: No arguments
    public BankAccount() {
        this("Unknown", 0.0);
    }

    // Constructor 2: Account holder only
    public BankAccount(String accountHolder) {
        this(accountHolder, 0.0);
    }

    // Constructor 3: Account holder and opening balance
    public BankAccount(String accountHolder, double openingBalance) {
        if (accountHolder == null || accountHolder.isBlank()) {
            throw new IllegalArgumentException(
                    "Account holder name cannot be empty."
            );
        }

        if (!Double.isFinite(openingBalance) || openingBalance < 0) {
            throw new IllegalArgumentException(
                    "Opening balance cannot be negative or invalid."
            );
        }

        this.accountNumber = ++accountCounter;
        this.accountHolder = accountHolder;
        this.balance = openingBalance;
    }


    // Getters
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    // Deposit money
    public void deposit(double amount) {
        if (!Double.isFinite(amount) || amount <= 0) {
            throw new IllegalArgumentException(
                    "Deposit amount must be greater than zero."
            );
        }

        balance += amount;
    }

    // Withdraw money
    public void withdraw(double amount) {
        if (!Double.isFinite(amount) || amount <= 0) {
            throw new IllegalArgumentException(
                    "Withdrawal amount must be greater than zero."
            );
        }

        if (amount > balance) {
            throw new IllegalArgumentException(
                    "Insufficient balance."
            );
        }

        balance -= amount;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof BankAccount other)) {
            return false;
        }

        return accountNumber == other.accountNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

}
