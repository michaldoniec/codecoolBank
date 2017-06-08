package dao;

import model.AbstractAccount;
import model.Customer;
import model.SavingAccount;
import model.exception.NoSuchAccountException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by michal on 08.06.17.
 */
public class TestAccountDaoSQLite {
	private JDBCSQLite database;
	private AccountDao accountDao;

	@BeforeEach
	public void setUp() throws SQLException, ClassNotFoundException, FileNotFoundException {
		database = new JDBCSQLite("src/test/resources/testBank.db");
		database.resetDatabase();
		accountDao = new AccountDaoSQLite(database);
	}

	@AfterEach
	void tearDown() throws SQLException {
		database.closeDatabase();
	}

	@Test
	public void testFindAccountByCorrectId() throws NoSuchAccountException {
		AbstractAccount account = accountDao.findAccountById(1);
		Integer correctAccountId = 1;
		String correctAccountNumber = "12233444ds";
		LocalDate openDate = LocalDate.of(2017,1,1);
		assertAll("Account",
		 () -> assertEquals(correctAccountId, account.getAccountId()),
		 () -> assertEquals(correctAccountNumber, account.getNumber()),
		 () -> assertEquals(openDate, account.getOpenDate())
		);
	}

	@Test
	public void testIfThrowExceptionIfFindAccountByIncorrectId() throws NoSuchAccountException {
		assertThrows(NoSuchAccountException.class, () ->accountDao.findAccountById(10));
	}

	@Test
	public void testIfAddNewAccount() throws SQLException, NoSuchAccountException {
		LocalDate createDate = LocalDate.of(2017,1,1);
		LocalDate lastLogin = LocalDate.of(2017, 2,2);
		Customer customer = new Customer(1,"Michal", "Abc", "abcd",
		 "21232f297a57a5a743894a0e4a801fc3",createDate, true, lastLogin);
		LocalDate openDate = LocalDate.of(2017,3,3);
		SavingAccount savingAccount = new SavingAccount(customer, "123345556NBP", "Saving account description",
		 "Active account", "Active account status", openDate, 34000,
		 3000, 5);
		accountDao.addAccount(savingAccount);
		AbstractAccount account = accountDao.findAccountById(3);
		Integer correctAccountId = 3;
		String correctAccountNumber = savingAccount.getNumber();
		assertAll("Account",
		 () -> assertEquals(correctAccountId, account.getAccountId()),
		 () -> assertEquals(correctAccountNumber, account.getNumber()),
		 () -> assertEquals(openDate, account.getOpenDate())
		);
	}

	@Test
	public void testIfUpdateAccount() throws  SQLException, NoSuchAccountException {
		AbstractAccount account = accountDao.findAccountById(1);
		account.setStatusName("Disabled account");
		accountDao.updateAccount(account);
		AbstractAccount updatedAccount = accountDao.findAccountById(1);
		assertEquals("Disabled account", updatedAccount.getStatusName());
	}



}
