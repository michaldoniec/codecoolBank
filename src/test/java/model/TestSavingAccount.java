package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class TestSavingAccount {
	private SavingAccount savingAccount;
	private Customer customer;
	private LocalDate openDate;

	@BeforeEach
	public void createAccount() {
		LocalDate createDate = LocalDate.of(2017,1,1);
		LocalDate lastLogin = LocalDate.of(2017,2,2);
		openDate = LocalDate.of(2017,3,3);
		customer = mock(Customer.class);
		savingAccount = new SavingAccount(customer, "123345556NBP", "type description",
			"status name", "status description", openDate, 34000,
		3000, 5);
	}

	@Test
	public void testIfAccountConstructorWithoutIdCreateCorrectAccount() {
		long debitLimit = 3000;
		long balance = 34000;
		Integer interest = 5;
		assertAll("SavingAccount constructor",
		 () -> assertEquals(customer, savingAccount.getCustomer()),
		 () -> assertEquals(openDate, savingAccount.getOpenDate()),
		 () -> assertEquals("type description", savingAccount.getTypeDescription()),
		 () -> assertEquals("status name", savingAccount.getStatusName()),
		 () -> assertEquals("status description", savingAccount.getStatusDescription()),
		 () -> assertEquals(debitLimit, savingAccount.getDebitLine()),
		 () -> assertEquals(interest, savingAccount.getInterest()),
		 () -> assertEquals("123345556NBP", savingAccount.getNumber()),
		 () -> assertEquals(balance, savingAccount.getBalance())
		);
	}

	@Test
	public void testIfAccountConstructorWithIdCreateCorrectAccount() {
		savingAccount = new SavingAccount(1, customer, "123345556NBP", "type description",
			"status name", "status description", openDate, 34000,
			3000, 5);
		Integer accountId = 1;
		assertAll("AbstractAccount constructor",
		 () -> assertEquals(accountId, savingAccount.getAccountId()),
		 () -> assertEquals(customer, savingAccount.getCustomer()),
		 () -> assertEquals(openDate, savingAccount.getOpenDate()),
		 () -> assertEquals(34000, savingAccount.getBalance())
		);
	}

	@Test
	public void testIfSetterChangeAccountStatus() {
		savingAccount.setStatusName("Disactivated");
		savingAccount.setStatusDescription("Disactivated description");
		assertAll("SavingAccount Status",
		 () -> assertEquals("Disactivated", savingAccount.getStatusName()),
		 () -> assertEquals("Disactivated description", savingAccount.getStatusDescription())
		);
	}

	@Test
	public void testIfDespositIncreaseValue() {
		savingAccount.deposit(1000);
		long correctBalance = 35000;
		assertEquals(correctBalance, savingAccount.getBalance());
	}

	@Test
	public void testIfMinusDepositAmountThrowException() {
		assertThrows(IllegalArgumentException.class, () -> savingAccount.deposit(-1000));
	}


	@Test
	public void testIfWithdrawDecreaseValue() {
		savingAccount.withdraw(4000);
		long correctBalance = 30000;
		assertEquals(correctBalance, savingAccount.getBalance());
	}

	@Test
	public void testIfMinusWithdrawAmountThrowException() {
		assertThrows(IllegalArgumentException.class, () -> savingAccount.withdraw(-1000));
	}
}
