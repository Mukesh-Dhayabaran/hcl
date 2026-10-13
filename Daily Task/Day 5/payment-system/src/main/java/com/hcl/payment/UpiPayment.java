package com.hcl.payment;

public class UpiPayment extends Payment implements Refundable{

    public UpiPayment(double amount) {
        super(amount);
    }

    @Override
    public void pay() {
        System.out.println("Processing UPI payment of " + amount);
    }

    @Override
    public void refund(double amount) {
        System.out.println("Refunding " + amount);
    }
}