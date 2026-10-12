
package app;

import model.BankAccount;
import service.BankAccountService;

public class Main {

    public static void main(String[] args) {

        BankAccount account = new BankAccount("Mukesh", 5000.0);
        BankAccountService service = new BankAccountService();

        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Account Holder: " + account.getAccountHolder());

        service.displayBalance(account);

        service.depositMoney(account, 1000.0);
        service.displayBalance(account);

        service.withdrawMoney(account, 2000.0);
        service.displayBalance(account);
    }
}
