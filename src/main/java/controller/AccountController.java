package controller;
import model.AbstractAccount;

public class AccountController implements AccountOperation {
    private AbstractAccount account;

    public AccountController(AbstractAccount account) {
        this.account = account;
    }

    public AbstractAccount getAccount() {
        return account;
    }

    public void withdraw(long amount) throws IllegalArgumentException {
        long balance = account.getBalance();

        if(validateAccountStatus() && validateAmount(amount) && validateFunds(amount)) {
            long newBalance = balance - amount;
            account.setBalance(newBalance);
        } else {
            throw new IllegalArgumentException("Transaction forbidden");
        }
    }

    public boolean validateAmount(long amount) {
        if (amount < 0) {
            return false;
        }
        return true;
    }

    public boolean validateFunds(long amount) {
        long balance = account.getBalance();
        long debit = account.getDebitLine();
        long transactionLimit = balance + debit;

        if (amount > transactionLimit) {
            return false;
        }
        return true;
    }

    public boolean validateAccountStatus() {
        String status = account.getStatusName();

        if (status.equals("Active account")){
            return true;
        }
        return false;
    }


    public void deposit(long amount) throws IllegalArgumentException {
        long balance = account.getBalance();

        if (validateAccountStatus() && validateAmount(amount)) {
            long newBalance = balance + amount;
            account.setBalance(newBalance);
        } else {
            throw new IllegalArgumentException("Transaction forbidden");
        }
    }

    public void blockAccount() {
        account.setStatusName("Disabled account");
    }

    public void unblockAccount() {
        account.setStatusName("Active account");
    }
}
