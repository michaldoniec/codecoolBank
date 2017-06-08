package dao;

import model.Transaction;
import model.exception.NoSuchAccountException;
import model.exception.NoSuchTransactionInDatabaseException;

import java.sql.SQLException;
import java.util.List;


public interface TransactionDao {

	void addTransaction(Transaction transaction) throws SQLException;

	List<Transaction> findTransactionsByAccountId(Integer customerAccountId) throws NoSuchTransactionInDatabaseException,
	 NoSuchAccountException, SQLException;

	List<Transaction>findTransactionsByAccountId(Integer customerAccountId, Integer destinationAccountId) throws
	 NoSuchTransactionInDatabaseException, NoSuchAccountException, SQLException;
}
