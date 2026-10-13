package com.hcl.payment;

public class CardPayment extends Payment implements Refundable{

    public CardPayment(double amount) {
        super(amount);
    }

    @Override
    public void pay() {
        System.out.println("Processing card payment of " + amount);
    }

    @Override
    public void refund(double amount) {
        System.out.println("Refunding ₹" + amount);
    }
}