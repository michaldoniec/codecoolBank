package controller;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class TestTransactionController {
    private TransactionController transactionController;
    private AbstractAccount sourceAccount;
    private AbstractAccount destinationAccount;
    private AbstractAccount testAccount;
    private Card sourceCard;
    private Transaction transaction;
    private Customer customer;
    private LocalDate dateOfTransaction;


    @BeforeEach
    public void createTransactionController() {
        dateOfTransaction = LocalDate.of(2017,6,6);
        sourceCard = new Card();
        destinationAccount = mock(CreditAccount.class);
        sourceAccount = new SavingAccount(customer, "123345556NBP", "type description",
            "Active account", "status description", dateOfTransaction, 34000,
            3000, 5);
        destinationAccount = new SavingAccount(customer, "123345556NBP1", "type description",
            "Active account", "status description", dateOfTransaction, 34000,
            3000, 5);
        transaction = new Transaction(dateOfTransaction, "TypeName",
            "TypeDescription", 123, "description","StatusName",
            "StatusDescription", sourceAccount, sourceCard, destinationAccount);
        transactionController = new TransactionController(transaction);
    }

    @Test
    public void testMakeTransaction() {
        transactionController.makeTransactionFromAccountToAccount(1000);
        long correctBalance = 33000;
        assertEquals(correctBalance, sourceAccount.getBalance());
    }

    @Test
    @DisplayName("Transfer negative value")
    public void testIfMinusWithdrawAmountThrowException() {
        assertThrows(IllegalArgumentException.class, () -> transactionController.makeTransactionFromAccountToAccount(-1));
    }

    @Test
    @DisplayName("Transfer more than you have")
    public void testIfWithdrawMoreThanTransactionLimitThrowException() {
        assertThrows(IllegalArgumentException.class, () -> transactionController.makeTransactionFromAccountToAccount(99999));
    }

    @Test
    @DisplayName("Transfer from disabled account")
    public void testIfWithdrawFromDisabledAccountThrowException() {
        sourceAccount.setStatusName("Disable account");
        assertThrows(IllegalArgumentException.class, () -> transactionController.makeTransactionFromAccountToAccount(1));
    }

}
