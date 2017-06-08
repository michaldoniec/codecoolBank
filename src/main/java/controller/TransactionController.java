package controller;
import model.Card;
import model.SavingAccount;
import model.Transaction;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.AbstractAccount;
import model.Customer;


public class TransactionController {
    private Transaction transaction;
    private AccountController sourceAccountController;
    private AccountController destinationAccountController;

    public TransactionController(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void makeTransactionFromAccountToAccount(long amount) {
        AbstractAccount sourceAccount = sourceAccountController.getAccount();
        AbstractAccount destinationAccount = destinationAccountController.getAccount();
        transaction.setSourceAccount(sourceAccount);
        transaction.setDestinationAccount(destinationAccount);

        sourceAccountController.withdraw(amount, sourceAccount);
        destinationAccountController.deposit(amount, destinationAccount);
    }
}

