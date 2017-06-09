package controller;
import model.Transaction;
import model.AbstractAccount;

public class TransactionController {
    private Transaction transaction;
    private AccountController sourceAccountController;
    private AccountController destinationAccountController;
    private AbstractAccount sourceAccount;
    private AbstractAccount destinationAccount;

    public TransactionController(Transaction transaction) {
        this.transaction = transaction;
    }

    public void makeTransactionFromAccountToAccount(long amount) {
        sourceAccount = transaction.getSourceAccount();
        destinationAccount = transaction.getDestinationAccount();
        long sourceBalance = sourceAccount.getBalance();
        long destinationBalance = destinationAccount.getBalance();

        if (validateAccountsStatus() && validateAmount(amount) && validateFunds(amount)) {
            long newSourceBalance = sourceBalance - amount;
            sourceAccount.setBalance(newSourceBalance);
            long newDestinationBalance = destinationBalance + amount;
            destinationAccount.setBalance(newDestinationBalance);
        } else {
            throw new IllegalArgumentException("Transaction forbidden");
        }
    }


    public boolean validateAccountsStatus() {
        String statusSourceAccount = sourceAccount.getStatusName();
        String statusDestinationAccount = destinationAccount.getStatusName();

        if (statusSourceAccount.equals("Active account") && statusDestinationAccount.equals("Active account")){
            return true;
        }
        return false;
    }

    public boolean validateFunds(long amount) {
        long debit = sourceAccount.getDebitLine();
        long balance = sourceAccount.getBalance();
        long transactionLimit = balance + debit;

        if (amount > transactionLimit) {
            return false;
        }
        return true;
    }

    public boolean validateAmount(long amount) {
        if (amount < 0) {
            return false;
        }
        return true;
    }

}

