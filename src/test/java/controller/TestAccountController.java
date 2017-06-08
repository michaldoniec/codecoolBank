package controller;

import model.Customer;
import model.SavingAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class TestAccountController {
    private AccountController accountController;
    private SavingAccount testAccount;
    private Customer customer;
    private LocalDate openDate;

    @BeforeEach
    public void createAccountController() {
        testAccount = new SavingAccount(customer, "123345556NBP", "type description",
            "Disactive account", "status description", openDate, 34000,
            3000, 5);
        accountController = new AccountController(testAccount);
        accountController.unblockAccount();
        openDate = LocalDate.of(2017,6,6);
        customer =  mock(Customer.class);

    }

    @Test
    public void testIfDepositIncreaseValue() {
        accountController.deposit(1000);
        long correctBalance = 35000;
        assertEquals(correctBalance, accountController.getAccount().getBalance());
    }

    @Test
    public void testIfMinusDepositAmountThrowException() {
        assertThrows(IllegalArgumentException.class, () -> accountController.deposit(-1000));
    }


    @Test
    public void testIfWithdrawDecreaseValue() {
        accountController.withdraw(4000);
        long correctBalance = 30000;
        assertEquals(correctBalance, accountController.getAccount().getBalance());
    }

    @Test
    @DisplayName("Withdraw negative value")
    public void testIfMinusWithdrawAmountThrowException() {
        assertThrows(IllegalArgumentException.class, () -> accountController.withdraw(-1000));
    }

    @Test
    @DisplayName("Withdraw more than you have")
    public void testIfWithdrawMoreThanTransactionLimitThrowException() {
        assertThrows(IllegalArgumentException.class, () -> accountController.withdraw(100000));
    }

    @Test
    @DisplayName("Withdraw from disabled account")
    public void testIfWithdrawFromDisabledAccountThrowException() {
        accountController.blockAccount();
        assertThrows(IllegalArgumentException.class, () -> accountController.withdraw(200));
    }
}
