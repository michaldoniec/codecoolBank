package dao;

import model.AbstractAccount;
import model.CreditAccount;
import model.SavingAccount;
import model.exception.NoSuchAccountException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AccountDaoSQLite extends CommonDBOperationsDaoSQLite implements AccountDao {

	private JDBCSQLite database;
	private Connection connection;
	private ResultSet resultSet;

	public AccountDaoSQLite(JDBCSQLite database){
		this.database = database;
		this.connection = database.getConnection();
	}

	public void addAccount(AbstractAccount account) throws SQLException {
		Integer customerId = account.getCustomer().getId();
		Integer accountTypeId = getAccountTypeId(account);
		String openDate = account.getOpenDate().toString();
		String balance = Long.toString(account.getBalance());
		String debitLine = Long.toString(account.getDebitLine());
		Integer accountStatusId = findAccountStatusId(account.getStatusName());
		System.out.println(accountStatusId);
		String addAccountQuery = String.format("INSERT INTO Accounts (CustomerID, Number, AccountTypeID, AccountStatusID," +
		  "OpenDate, Balance, DebitLine, Interest) VALUES(%d, '%s', %d, %d, '%s', '%s', '%s', %d)", customerId,
		 account.getNumber(), accountTypeId, accountStatusId, openDate, balance, debitLine, account.getInterest());
		System.out.println(addAccountQuery);
		modifyDatabase(addAccountQuery);
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

	public List<AbstractAccount> findAccountsByCustomerId(Integer customerId) throws NoSuchAccountException {
		try {
			List<AbstractAccount> accounts = new ArrayList<>();
			String findAccountQuery = String.format("SELECT * FROM Accounts INNER JOIN Customers ON " +
			  "Accounts.CustomerID = Customers.CustomerID INNER JOIN AccountStatuses ON " +
			  "Accounts.AccountStatusID = AccountStatuses.AccountStatusID INNER JOIN \n" +
			  "AccountTypes ON Accounts.AccountTypeID = AccountTypes.AccountTypeID WHERE Accounts.CustomerID == %d",
			 customerId);
			PreparedStatement selectQuery = connection.prepareStatement(findAccountQuery);
			ResultSet resultSet = database.executeSelectQuery(selectQuery);
			while(resultSet.next()){
				AbstractAccount account = convertDataToAccountModel(resultSet);
				accounts.add(account);
			}
			return accounts;
		} catch (SQLException e){
			throw new NoSuchAccountException("There is no account with such id");
		}
	}

	public void updateAccount(AbstractAccount account) throws SQLException {
		Integer customerId = account.getCustomer().getId();
		Integer accountTypeId = getAccountTypeId(account);
		String openDate = account.getOpenDate().toString();
		String balance = Long.toString(account.getBalance());
		String debitLine = Long.toString(account.getDebitLine());
		Integer accountStatusId = findAccountStatusId(account.getStatusName());
		String updateAccountQuery = String.format("UPDATE Accounts SET CustomerID = %d,  Number = '%s', " +
		  "AccountTypeID = %d, AccountStatusID =  %d,OpenDate = '%s', Balance = '%s', " +
		  "DebitLine = '%s', Interest = %d WHERE AccountID = %d", customerId,
		 account.getNumber(), accountTypeId, accountStatusId, openDate, balance, debitLine, account.getInterest(),
		 account.getAccountId());
		System.out.println(updateAccountQuery);
		modifyDatabase(updateAccountQuery);
	}

	private Integer getAccountTypeId(AbstractAccount account) {
		if(account.getClass() == SavingAccount.class) {
			return 1;
		}
		if(account.getClass() == CreditAccount.class) {
			return 2;
		}
		return null;
	}

	private void modifyDatabase(String query) throws SQLException {
		PreparedStatement modifyDatabaseQuery = connection.prepareStatement(query);
		database.executeDBModifyingQuery(modifyDatabaseQuery);
	}


	private  Integer findAccountStatusId(String accountStatusName) throws  SQLException {
		String findAccountStatusId = String.format("SELECT AccountStatusID FROM AccountStatuses " +
		  "WHERE Name = '%s'", accountStatusName);
		PreparedStatement findAccountTypeIdQuery = connection.prepareStatement(findAccountStatusId);
		ResultSet resultSet = database.executeSelectQuery(findAccountTypeIdQuery);
		return resultSet.getInt("AccountStatusID");
	}
}
