package dao;

import model.*;
import model.exception.NoSuchAccountException;
import model.exception.NoSuchTransactionInDatabaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by michal on 08.06.17.
 */
public class TestTransactionDaoSQLite {
	private static JDBCSQLite database;
	private static TransactionDao transactionDao;

	@BeforeEach
	public void setUp() throws SQLException, ClassNotFoundException, FileNotFoundException {
		JDBCSQLite.createDatabase("src/test/resources/testBank.db");
		database = JDBCSQLite.getDatabase();
		transactionDao = new TransactionDaoSQLite(database);
		database.resetDatabase();
	}

	@AfterEach
	void tearDown() throws SQLException {
		database.closeDatabase();
	}

	@Test
	public void testIfFindByCorrectAccountIdReturnAccountList() throws  SQLException, NoSuchAccountException, NoSuchTransactionInDatabaseException {
		Integer correctListSize = 1;
		Integer correctSourceAccountId = 1;
		List<Transaction> transactions = transactionDao.findTransactionsByAccountId(correctSourceAccountId);
		Integer transactionsListSize = transactions.size();
		assertEquals(correctListSize, transactionsListSize);
	}

	@Test
	public void testIfFindByCorrectAccountIdAndDestinationAccountIdReturnAccountList() throws  SQLException, NoSuchAccountException, NoSuchTransactionInDatabaseException {
		Integer correctListSize = 1;
		Integer correctSourceAccountId = 1;
		Integer correctDestinationAccountId = 2;
		List<Transaction> transactions = transactionDao.findTransactionsByAccountId(correctSourceAccountId, correctDestinationAccountId);
		Integer transactionsListSize = transactions.size();
		assertEquals(correctListSize, transactionsListSize);
	}

	@Test
	public void testIfFindByIncorrectSourceAccountIdReturnEmptyTransactionsList() throws  SQLException, NoSuchAccountException, NoSuchTransactionInDatabaseException {
		Integer emptyListSize = 0;
		Integer incorrectSourceAccountId = 800;
		List<Transaction> transactions = transactionDao.findTransactionsByAccountId(incorrectSourceAccountId);
		Integer transactionsListSize = transactions.size();
		assertEquals(emptyListSize, transactionsListSize);
	}

	@Test
	public void testIfFindByIncorrectDestinationAccountIdReturnEmptyTransactionsList() throws  SQLException, NoSuchAccountException, NoSuchTransactionInDatabaseException {
		Integer emptyListSize = 0;
		Integer correctSourceAccountId = 2;
		Integer incorrectDestinationAccountId = 800;
		List<Transaction> transactions = transactionDao.findTransactionsByAccountId(correctSourceAccountId,
		 incorrectDestinationAccountId);
		Integer transactionsListSize = transactions.size();
		assertEquals(emptyListSize, transactionsListSize);
	}

	@Test
	public void testIfFindByIncorrectSDestinationAccountIdReturnEmptyTransactionsList() throws  SQLException, NoSuchAccountException, NoSuchTransactionInDatabaseException {
		List<Transaction> transactions = new ArrayList<>();
		transactions = transactionDao.findTransactionsByAccountId(800, 900);
		assertEquals(0, transactions.size());
	}

	@Test
	public void testAddTransaction() throws SQLException, NoSuchAccountException, NoSuchTransactionInDatabaseException{
		List<Transaction> transactions = new ArrayList<>();
		LocalDate dateOfTransaction = LocalDate.of(2017,6,6);
		LocalDate createDate = LocalDate.of(2017,1,1);
		LocalDate lastLogin = LocalDate.of(2017, 2,2);
		LocalDate openDate = LocalDate.of(2017,3,3);
		Integer correctNumberOfTransactions = 2;

		Customer customer = new Customer(1,"Michal", "Abc", "abcd",
		 "21232f297a57a5a743894a0e4a801fc3",createDate, true, lastLogin);
		AbstractAccount sourceAccount =  new SavingAccount(1, customer, "123345556NBP", "Saving account description",
		 "Active account", "Active account status", openDate, 34000,
		 3000, 5);
		AbstractAccount destinationAccount = sourceAccount;
		Card sourceCard = null;

		Transaction transaction = new Transaction(dateOfTransaction, "Other transaction",
		 "Not tax or social security transaction", 123, "description","Waiting transaction",
		 "Waiting transaction description", sourceAccount, sourceCard, destinationAccount);

		transactionDao.addTransaction(transaction);
		transactions = transactionDao.findTransactionsByAccountId(1);
		Integer transactionsNumber = transactions.size();
		assertEquals(correctNumberOfTransactions, transactionsNumber);
	}
}

