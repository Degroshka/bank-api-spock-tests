package org.example;

public class BankAccount {

    private final String owner;
    private double balance;
    private boolean frozen;

    public BankAccount(String owner, double initialBalance) {
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("Owner name cannot be blank");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.owner = owner;
        this.balance = initialBalance;
        this.frozen = false;
    }

    public void deposit(double amount) {
        if (frozen) throw new IllegalStateException("Account is frozen");
        if (amount <= 0) throw new IllegalArgumentException("Deposit amount must be positive");
        balance += amount;
    }

    public void withdraw(double amount) {
        if (frozen) throw new IllegalStateException("Account is frozen");
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive");
        if (amount > balance) throw new IllegalStateException("Insufficient funds");
        balance -= amount;
    }

    public void transferTo(BankAccount target, double amount) {
        this.withdraw(amount);
        target.deposit(amount);
    }

    public void freeze() {
        this.frozen = true;
    }

    public void unfreeze() {
        this.frozen = false;
    }

    public double getBalance() {
        return balance;
    }

    public String getOwner() {
        return owner;
    }

    public boolean isFrozen() {
        return frozen;
    }
}
