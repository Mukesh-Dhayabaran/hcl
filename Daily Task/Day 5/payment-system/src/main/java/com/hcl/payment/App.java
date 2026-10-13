package com.hcl.payment;

public class App {

    // Overloaded method 1
    public static void pay(Payment payment) {
        payment.pay();
    }

    // Overloaded method 2
    public static void pay(Payment payment, double amount) {
        System.out.println("Requested payment amount: " + amount);
        payment.pay();
    }

    public static void main(String[] args) {

        Payment card = new CardPayment(1500);
        Payment upi = new UpiPayment(800);
        Payment cash = new CashPayment(500);

        pay(card);
        pay(upi, 800);
        pay(cash);

        Refundable refundable = (Refundable) card;
        refundable.refund(300);
    }
}