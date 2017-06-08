package dao;

import model.*;
import model.exception.NoSuchAccountException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AccountDaoSQLite implements AccountDao {
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

	public AbstractAccount findAccountById(Integer accountId) throws NoSuchAccountException {
		try {
				String findAccountQuery = String.format("SELECT * FROM Accounts INNER JOIN Customers ON " +
				 "Accounts.CustomerID = Customers.CustomerID INNER JOIN AccountStatuses ON " +
				 "Accounts.AccountStatusID = AccountStatuses.AccountStatusID INNER JOIN \n" +
				 "AccountTypes ON Accounts.AccountTypeID = AccountTypes.AccountTypeID WHERE Accounts.AccountID == %d",
				 accountId);
				PreparedStatement selectQuery = connection.prepareStatement(findAccountQuery);
				resultSet = database.executeSelectQuery(selectQuery);
				return convertDataToAccountModel(resultSet);
		} catch (SQLException e){
			throw new NoSuchAccountException("There is no account with such id");
		}
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

	private AbstractAccount convertDataToAccountModel(ResultSet resultSet) throws SQLException, NoSuchAccountException {
		Integer accountId = resultSet.getInt("AccountID");
		Integer statusNameColumn = 19;
		Integer statusDescriptionColumn = 20;
		Integer typeIdColumn = resultSet.getInt(21);
		Integer savingAccount = 1;
		Integer creditAccount = 2;
		Integer typeDescriptionColumn = 23;
		Customer customer = convertDataToCustomerModel(resultSet);
		LocalDate openDate = LocalDate.parse(resultSet.getString("OpenDate"));
		long balance = Long.parseLong(resultSet.getString("Balance"));
		long debitLine = Long.parseLong(resultSet.getString("DebitLine"));
		Integer interest = resultSet.getInt("Interest");
		String number = resultSet.getString("Number");
		String statusName = resultSet.getString(statusNameColumn);
		String statusDescription = resultSet.getString(statusDescriptionColumn);
		String typeDescription = resultSet.getString(typeDescriptionColumn);

		if(typeIdColumn == savingAccount) {
			return new SavingAccount(accountId, customer, number, typeDescription, statusName,
			 statusDescription, openDate, balance, debitLine, interest);
		}

		if(typeIdColumn == creditAccount) {
			return new CreditAccount(accountId, customer, number, typeDescription, statusName,
			 statusDescription, openDate, balance, debitLine, interest);
		}

		throw new NoSuchAccountException("There is no such account");
	}

	private void modifyDatabase(String query) throws SQLException {
		PreparedStatement modifyDatabaseQuery = connection.prepareStatement(query);
		database.executeDBModifyingQuery(modifyDatabaseQuery);
	}

	private Customer convertDataToCustomerModel(ResultSet resultSet) throws SQLException {
		Boolean isActive;
		LocalDate createDate = LocalDate.parse(resultSet.getString("CreateDate"));
		LocalDate lastLogin = LocalDate.parse(resultSet.getString("LastLogin"));

		if(resultSet.getInt("IsActive") == 1) {
			isActive = true;
		} else {
			isActive = false;
		}

		return new Customer(resultSet.getInt("CustomerID"), resultSet.getString("FirstName"),
		 resultSet.getString("LastName"), resultSet.getString("Login"),
		 resultSet.getString("Password"), createDate, isActive,
		 lastLogin);
	}

	private  Integer findAccountStatusId(String accountStatusName) throws  SQLException {
		String findAccountStatusId = String.format("SELECT AccountStatusID FROM AccountStatuses " +
		  "WHERE Name = '%s'", accountStatusName);
		PreparedStatement findAccountTypeIdQuery = connection.prepareStatement(findAccountStatusId);
		ResultSet resultSet = database.executeSelectQuery(findAccountTypeIdQuery);
		return resultSet.getInt("AccountStatusID");
	}


}
