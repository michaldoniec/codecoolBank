package dao;

import model.AbstractAccount;
import model.Card;
import model.Transaction;
import model.exception.NoSuchAccountException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoSQLite extends CommonDBOperationsDaoSQLite implements TransactionDao {
	private JDBCSQLite database;
	private Connection connection;
	private ResultSet resultSet;
	private AccountDao accountDao;

	public TransactionDaoSQLite(JDBCSQLite database) {
		this.database = database;
		this.connection = database.getConnection();
	}

	public void addTransaction(Transaction transaction) throws SQLException {
		Integer sourceCardId = null;
		Integer transactionTypeId = findTransactionTypeId(transaction.getTransactionTypeName());
		Integer transactionStatusId = findTransactionStatusId(transaction.getTransactionStatusName());
		Integer sourceAccountId = transaction.getSourceAccount().getAccountId();
		if(transaction.getSourceCard() != null) {
			sourceCardId = transaction.getSourceCard().getId();
		}
		Integer destinationAccountId = transaction.getDestinationAccount().getAccountId();
		String addTransactionQuery = String.format("INSERT INTO Transactions (DateOfTransaction, TransactionTypeID, Value, Description, TransactionStatusID, SourceAccountID,\n" +
		  " SourceCardID, DestinationAccountID) VALUES('%s',%d,'%s','%s',%d, %d, %d, %d)", transaction.getDateOfTransaction(),
		 transactionTypeId, transaction.getValue(), transaction.getDescription(), transactionStatusId, sourceAccountId,
		 sourceCardId, destinationAccountId);
		PreparedStatement addQuery = connection.prepareStatement(addTransactionQuery);
		database.executeDBModifyingQuery(addQuery);
	}

	public List<Transaction> findTransactionsByAccountId(Integer customerAccountId)
	 throws SQLException, NoSuchAccountException {
		String findTransactions = String.format("SELECT * FROM Transactions INNER JOIN TransactionTypes ON " +
		  "Transactions.TransactionTypeID = TransactionTypes.TransactionTypeID INNER JOIN " +
		  "TransactionStatuses ON Transactions.TransactionStatusID = TransactionStatuses.TransactionStatusID " +
		  "WHERE Transactions.SourceAccountID = %d",
		 customerAccountId);
		return convertDataToTransactionModelList(findTransactions, customerAccountId);
	}

	public List<Transaction> findTransactionsByAccountId(Integer customerAccountId, Integer destinationAccountID)
	 throws SQLException, NoSuchAccountException {
		String findTransactions = String.format("SELECT * FROM Transactions INNER JOIN TransactionTypes ON " +
		  "Transactions.TransactionTypeID = TransactionTypes.TransactionTypeID INNER JOIN " +
		  "TransactionStatuses ON Transactions.TransactionStatusID = TransactionStatuses.TransactionStatusID " +
		  "WHERE Transactions.SourceAccountID = %d AND Transactions.DestinationAccountID = %d",
		 customerAccountId, destinationAccountID);
		return convertDataToTransactionModelList(findTransactions, customerAccountId);
	}

	public AbstractAccount findAccountById(Integer accountId) throws NoSuchAccountException {
		try {
			String findAccountQuery = String.format("SELECT * FROM Accounts INNER JOIN Customers ON " +
			  "Accounts.CustomerID = Customers.CustomerID INNER JOIN AccountStatuses ON " +
			  "Accounts.AccountStatusID = AccountStatuses.AccountStatusID INNER JOIN \n" +
			  "AccountTypes ON Accounts.AccountTypeID = AccountTypes.AccountTypeID WHERE Accounts.AccountID == %d",
			 accountId);
			PreparedStatement selectQuery = connection.prepareStatement(findAccountQuery);
			ResultSet resultSet = database.executeSelectQuery(selectQuery);
			return convertDataToAccountModel(resultSet);
		} catch (SQLException e){
			throw new NoSuchAccountException("There is no account with such id");
		}
	}

	private List<Transaction> convertDataToTransactionModelList(String query, Integer sourceAccountId) throws SQLException, NoSuchAccountException {
		List<Transaction> transactions = new ArrayList<>();
		Integer typeNameColumn = 10;
		Integer typeDescriptionColumn = 11;
		Integer statusNameColumn  = 14;
		Integer statusDescriptionColumn = 15;
		Integer descriptionColumn = 4;
		PreparedStatement findTransactionsQuery = this.connection.prepareStatement(query);
		ResultSet resultSet = database.executeSelectQuery(findTransactionsQuery);
		while(resultSet.next()){
			String typeName = resultSet.getString(typeNameColumn);
			String typeDescription = resultSet.getString(typeDescriptionColumn);
			String statusName = resultSet.getString(statusNameColumn);
			String statusDescription = resultSet.getString(statusDescriptionColumn);
			String description = resultSet.getString(descriptionColumn);
			LocalDate dateOfTransaction = LocalDate.parse(resultSet.getString("DateOfTransaction"));
			long value = Long.parseLong(resultSet.getString("Value"));
			AbstractAccount sourceAccount = findAccountById(sourceAccountId);
			AbstractAccount destinationAccount = findAccountById(resultSet.getInt("DestinationAccountID"));
			Card card = new Card();
			Transaction transaction = new Transaction(resultSet.getInt("TransactionID"),
			 dateOfTransaction, typeName, typeDescription,value, description, statusName,
			 statusDescription, sourceAccount, card, destinationAccount );
			transactions.add(transaction);
		}
		return transactions;
	}

	private Integer findTransactionTypeId(String transactionTypeName) throws SQLException {
		String findTransactionTypeId = String.format("SELECT TransactionTypeID FROM TransactionTypes WHERE Name = '%s'",
		 transactionTypeName);
		PreparedStatement findTransactionTypeIdQuery = connection.prepareStatement(findTransactionTypeId);
		ResultSet resultSet = database.executeSelectQuery(findTransactionTypeIdQuery);
		return resultSet.getInt("TransactionTypeID");
	}



	private  Integer findTransactionStatusId(String transactionStatusName) throws  SQLException {
		String findTransactionStatusId = String.format("SELECT TransactionStatusID FROM TransactionStatuses" +
		  " WHERE Name = '%s'", transactionStatusName);
		PreparedStatement findTransactionTypeIdQuery = connection.prepareStatement(findTransactionStatusId);
		ResultSet resultSet = database.executeSelectQuery(findTransactionTypeIdQuery);
		return resultSet.getInt("TransactionStatusID");
	}

}
