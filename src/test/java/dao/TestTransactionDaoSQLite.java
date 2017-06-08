package dao;

import model.Transaction;
import model.exception.NoSuchAccountException;
import model.exception.NoSuchTransactionInDatabaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by michal on 08.06.17.
 */
public class TestTransactionDaoSQLite {
	private JDBCSQLite database;
	private TransactionDao transactionDao;

	@BeforeEach
	public void setUp() throws SQLException, ClassNotFoundException, FileNotFoundException {
		database = new JDBCSQLite("src/test/resources/testBank.db");
		database.resetDatabase();
		transactionDao = new TransactionDaoSQLite(database);
	}

	@AfterEach
	void tearDown() throws SQLException {
		database.closeDatabase();
	}

	@Test
	public void testIfFindByAccountIdReturnAccountList() throws  SQLException, NoSuchAccountException, NoSuchTransactionInDatabaseException {
		List<Transaction> transactions = new ArrayList<>();
		transactions = transactionDao.findTransactionsByAccountId(1);
		System.out.println(transactions);
		assertEquals(1, transactions.size());
	}
}

