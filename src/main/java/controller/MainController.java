package controller;

import dao.AccountDaoSQLite;
import dao.CustomerDaoSQLite;
import dao.JDBCSQLite;
import model.AbstractAccount;
import model.Customer;
import model.exception.NoSuchAccountException;
import model.exception.NoSuchCustomerInDatabaseException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by michal on 09.06.17.
 */
public class MainController {
	String dbPath;
	JDBCSQLite database;
	Connection connection;
	AccountController accountController;
	CustomerController customerController;
	TransactionController transactionController;
	AccountDaoSQLite accountDaoSQLite;
	CustomerDaoSQLite customerDaoSQLite;
	Customer customer;

	public MainController(String dbPath) {
		this.dbPath = dbPath;
		try {
			JDBCSQLite.createDatabase(dbPath);
			database = JDBCSQLite.getDatabase();
			customerDaoSQLite = new CustomerDaoSQLite(database);
			accountDaoSQLite = new AccountDaoSQLite(database);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Connection to database failed");
			System.out.println(e);
		}
	}

	public Customer getCustomerByLogin (String customerLogin, String customerPassword) throws  NoSuchAccountException{
		try {

			customer = customerDaoSQLite.findCustomerByLogin(customerLogin);
			customerController = new CustomerController(customer);

			if(customerController.validateCustomer(customerLogin, customerPassword)) {

				List<AbstractAccount> accounts = accountDaoSQLite.findAccountsByCustomerId(customer.getId());
				customer.setAccounts(accounts);
				return customer;
			} else {
				System.out.println("Incorrect login or password");
			}
		} catch (NoSuchCustomerInDatabaseException | NoSuchAccountException e){
			System.out.println("Incorrect login or password");
			System.out.println(e);
		}

		return customer;
	}

	public void viewAllAccounts(){
		System.out.println("Your accounts:");
		for(AbstractAccount account : customer.getAccounts()) {
			String accountData = String.format("Account number: %s," +
			 "account current balance: %d", account.getNumber(), account.getBalance());
			System.out.println(accountData);
		}
	}

	public void addAccoun
}
