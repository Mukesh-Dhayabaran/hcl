package com.hcl.payment;

public abstract class Payment {
    protected double amount;

    public Payment(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(
                    "Amount cannot be negative"
            );
        }
        this.amount = amount;
    }

    public abstract void pay();

    public double getAmount() {
        return amount;
    }
}