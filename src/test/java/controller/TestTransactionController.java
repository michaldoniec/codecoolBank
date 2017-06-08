package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import dao.CustomerDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.AbstractAccount;
import model.Card;
import model.CreditAccount;
import model.Customer;
import model.Transaction;
import model.exception.NoSuchAccountException;
import model.SavingAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class TestTransactionController {
    private TransactionController transactionController;
    private AbstractAccount sourceAccount;
    private AbstractAccount destinationAccount;
    private AbstractAccount testAccount;
    private AccountController sourceAccountController;
    private AccountController destinationAccountController;
    private Card sourceCard;
    private Transaction transaction;
    private Customer customer;
    private LocalDate dateOfTransaction;


    @BeforeEach
    public void createTransactionController() {
        dateOfTransaction = LocalDate.of(2017,6,6);
        sourceCard = mock(Card.class);
        destinationAccount = mock(CreditAccount.class);
        sourceAccount = new SavingAccount(customer, "123345556NBP", "type description",
            "Disactive account", "status description", dateOfTransaction, 34000,
            3000, 5);
        destinationAccount = new SavingAccount(customer, "123345556NBP1", "type description",
            "Disactive account", "status description", dateOfTransaction, 34000,
            3000, 5);
        sourceAccountController = new AccountController(sourceAccount);
        destinationAccountController = new AccountController(destinationAccount);
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


}
