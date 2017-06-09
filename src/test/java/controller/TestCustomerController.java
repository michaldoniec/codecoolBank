package controller;

import dao.CustomerDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Customer;
import model.exception.NoSuchAccountException;
import model.SavingAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class TestCustomerController {
    private CustomerController customerController;
    private SavingAccount savingAccount;
    private Customer customer;
    private LocalDate createDate;
    private LocalDate lastLogin;

    @BeforeEach
    public void createCustomerController(){
        createDate = LocalDate.of(2017,1,1);
        lastLogin = LocalDate.of(2017, 2,2);
        customer = new Customer("Jan", "Kowalski", "abcd",
            "21232f297a57a5a743894a0e4a801fc3",createDate, true, lastLogin);
        customerController = new CustomerController(customer);
        savingAccount = new SavingAccount(1, customer, "123345556NBP", "type description",
            "Disactive account", "status description", createDate, 34000,
            3000, 5);
        }

    @Test
    public void testIfValidateCustomerWithCorrectPasswordAndLogin() {
        assertEquals(true, customerController.validateCustomer("abcd", "21232f297a57a5a743894a0e4a801fc3"));
    }

    @Test
    public void testIfValidateCustomerWithCorrectLoginIncorrectPassword() {
        assertEquals(false, customerController.validateCustomer("abcd", "21232f297a57a5a743894a0e4a801fc3d"));
    }

    @Test
    public void testIfValidateCustomerWithInCorrectLoginCorrectPassword() {
        assertEquals(false, customerController.validateCustomer("abc", "21232f297a57a5a743894a0e4a801fc3"));
    }

    @Test
    public void testAddAccount() {
        SavingAccount firstAccount = mock(SavingAccount.class);
        SavingAccount secondAccount = mock(SavingAccount.class);
        customerController.addAccount(firstAccount);
        customerController.addAccount(secondAccount);
        Integer numberOfAccounts = 2;
        Integer accountListSize = customer.getAccounts().size();
        assertEquals(numberOfAccounts, accountListSize);
    }

    @Test
    public void testIfFindAccountByCorrectId() {
        customerController.addAccount(savingAccount);
        assertEquals(savingAccount, customerController.getAccountById(1));
    }

    @Test
    public void testIfFindAccountByWrongId() {
        customerController.addAccount(savingAccount);
        assertEquals(null, customerController.getAccountById(5));
    }

    @Test
    public void testDeactivateCustomer() {
        customerController.deactivateCustomer(customer);
        assertEquals(false, customer.getIsActive());
    }

    @Test
    public void testIfAccountIsBlockedForDeactivatedCustomer() {
        customerController.addAccount(savingAccount);
        customerController.deactivateCustomer(customer);
        assertEquals("Disabled account", customerController.getAccountById(1).getStatusName());
    }

    @Test
    public void testIfAddNewCustomer() {
        List<String> data = new ArrayList<>(
            Arrays.asList("Jan", "Kowalski", "abcd", "123", "2017-01-01", "2017-01-01", "2017-01-01"));
        customerController.addCustomer(data);
        assertAll("New Customer",
            () -> assertEquals(null, customer.getId()),
            () -> assertEquals("Jan", customer.getFirstName()),
            () -> assertEquals("Kowalski", customer.getLastName())
        );
    }

    @Test
    public void testIfDisplayAllCustomerAccounts() {
        SavingAccount firstAccount = mock(SavingAccount.class);
        SavingAccount secondAccount = mock(SavingAccount.class);
        SavingAccount thirdAccount = mock(SavingAccount.class);
        customerController.addAccount(firstAccount);
        customerController.addAccount(secondAccount);
        customerController.addAccount(thirdAccount);
        assertEquals(3, customerController.getAccountsByCustomer(customer).size());
    }

}
