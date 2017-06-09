package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class TestTransaction {
    private Transaction transaction;
    private SavingAccount sourceAccount;
    private CreditAccount destinationAccount;
    private Card sourceCard;
    private LocalDate dateOfTransaction;

    @BeforeEach
    public void createTransaction() {
        dateOfTransaction = LocalDate.of(2017,6,6);
        sourceAccount =  mock(SavingAccount.class);
        destinationAccount = mock(CreditAccount.class);
        sourceCard = mock(Card.class);
        transaction = new Transaction(dateOfTransaction, "TypeName",
            "TypeDescription", 123, "description","StatusName",
            "StatusDescription", sourceAccount, sourceCard, destinationAccount);
    }

    @Test
    public void testIfTransactionConstructorWithoutIdCreateCorrectTransaction() {
        long value = 123;
        assertAll("Transaction construction",
            ()-> assertEquals(dateOfTransaction, transaction.getDateOfTransaction()),
            ()-> assertEquals("TypeName", transaction.getTransactionTypeName()),
            ()-> assertEquals("TypeDescription", transaction.getTransactionTypeDescription()),
            ()-> assertEquals(value, transaction.getValue()),
            ()-> assertEquals("description", transaction.getDescription()),
            ()-> assertEquals("StatusName", transaction.getTransactionStatusName()),
            ()-> assertEquals("StatusDescription", transaction.getTransactionStatusDescription()),
            ()-> assertEquals(sourceAccount, transaction.getSourceAccount()),
            ()-> assertEquals(sourceCard, transaction.getSourceCard()),
            ()-> assertEquals(destinationAccount, transaction.getDestinationAccount())
        );
    }

    @Test
    public void testIfTransactionConstructorWithIdCreateCorrectTransaction() {
        transaction = new Transaction(1,dateOfTransaction, "TypeName",
            "TypeDescription", 123, "description","StatusName",
            "StatusDescription", sourceAccount, sourceCard, destinationAccount);
        long value = 123;
        Integer id = 1;
        assertAll("Transaction construction",
            ()-> assertEquals(id, transaction.getId()),
            ()-> assertEquals(dateOfTransaction, transaction.getDateOfTransaction()),
            ()-> assertEquals("TypeName", transaction.getTransactionTypeName()),
            ()-> assertEquals("TypeDescription", transaction.getTransactionTypeDescription()),
            ()-> assertEquals(value, transaction.getValue()),
            ()-> assertEquals("description", transaction.getDescription()),
            ()-> assertEquals("StatusName", transaction.getTransactionStatusName()),
            ()-> assertEquals("StatusDescription", transaction.getTransactionStatusDescription()),
            ()-> assertEquals(sourceAccount, transaction.getSourceAccount()),
            ()-> assertEquals(sourceCard, transaction.getSourceCard()),
            ()-> assertEquals(destinationAccount, transaction.getDestinationAccount())
        );
    }

}
