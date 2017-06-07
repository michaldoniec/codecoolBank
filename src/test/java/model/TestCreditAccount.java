package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

/**
 * Created by michal on 07.06.17.
 */
public class TestCreditAccount {
    private CreditAccount creditAccount;
    private Customer customer;
    private LocalDate openDate;

    @BeforeEach
    public void createAccount() {
        LocalDate createDate = LocalDate.of(2017,1,1);
        LocalDate lastLogin = LocalDate.of(2017,2,2);
        openDate = LocalDate.of(2017,3,3);
        customer = mock(Customer.class);
        creditAccount = new CreditAccount(customer, "123345556NBP", "type description",
            "status name", "status description", openDate, 34000,
            3000, 5);
    }

    @Test
    public void testIfAccountConstructorWithoutIdCreateCorrectAccount() {
        long debitLimit = 3000;
        long balance = 34000;
        Integer interest = 5;
        assertAll("CreditAccount constructor",
            () -> assertEquals(customer, creditAccount.getCustomer()),
            () -> assertEquals(openDate, creditAccount.getOpenDate()),
            () -> assertEquals("type description", creditAccount.getTypeDescription()),
            () -> assertEquals("status name", creditAccount.getStatusName()),
            () -> assertEquals("status description", creditAccount.getStatusDescription()),
            () -> assertEquals(debitLimit, creditAccount.getDebitLine()),
            () -> assertEquals(interest, creditAccount.getInterest()),
            () -> assertEquals("123345556NBP", creditAccount.getNumber()),
            () -> assertEquals(balance, creditAccount.getBalance())
        );
    }

    @Test
    public void testIfAccountConstructorWithIdCreateCorrectAccount() {
        creditAccount = new CreditAccount(1, customer, "123345556NBP", "type description",
            "status name", "status description", openDate, 34000,
            3000, 5);
        Integer accountId = 1;
        assertAll("AbstractAccount constructor",
            () -> assertEquals(accountId, creditAccount.getAccountId()),
            () -> assertEquals(customer, creditAccount.getCustomer()),
            () -> assertEquals(openDate, creditAccount.getOpenDate()),
            () -> assertEquals(34000, creditAccount.getBalance())
        );
    }

    @Test
    public void testIfSetterChangeAccountStatus() {
        creditAccount.setStatusName("Disactivated");
        creditAccount.setStatusDescription("Disactivated description");
        assertAll("CreditAccount Status",
            () -> assertEquals("Disactivated", creditAccount.getStatusName()),
            () -> assertEquals("Disactivated description", creditAccount.getStatusDescription())
        );
    }

    @Test
    public void testIfDespositIncreaseValue() {
        creditAccount.deposit(1000);
        long correctBalance = 35000;
        assertEquals(correctBalance, creditAccount.getBalance());
    }

    @Test
    public void testIfMinusDepositAmountThrowException() {
        assertThrows(IllegalArgumentException.class, () -> creditAccount.deposit(-1000));
    }


    @Test
    public void testIfWithdrawDecreaseValue() {
        creditAccount.withdraw(4000);
        long correctBalance = 30000;
        assertEquals(correctBalance, creditAccount.getBalance());
    }

    @Test
    public void testIfMinusWithdrawAmountThrowException() {
        assertThrows(IllegalArgumentException.class, () -> creditAccount.withdraw(-1000));
    }
}

