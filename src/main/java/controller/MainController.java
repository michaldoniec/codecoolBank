package controller;

import dao.AccountDaoSQLite;
import dao.CustomerDaoSQLite;
import dao.JDBCSQLite;
import model.AbstractAccount;
import model.CreditAccount;
import model.Customer;
import model.SavingAccount;
import model.exception.NoSuchAccountException;
import model.exception.NoSuchCustomerInDatabaseException;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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

	public MainController(JDBCSQLite database) {
			database = database;
			customerDaoSQLite = new CustomerDaoSQLite(database);
			accountDaoSQLite = new AccountDaoSQLite(database);
	}

	public Customer getCustomerByLogin (String customerLogin, String customerPassword) {
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

	public void createCustomer(List<String> customerData) {
		try {
			Customer newCustomer = CustomerController.addCustomer(customerData);
			customerDaoSQLite.addCustomer(newCustomer);
		} catch (SQLException e){
			System.out.println(e);
		}
	}

	public void addAccount(List<String> newAccountData) {
		String number = newAccountData.get(0);
		String typeDescription = newAccountData.get(1);
		String statusName = "Active account";
		String statusDescription = "Active account status";
		LocalDate openDate = LocalDate.parse(newAccountData.get(2));
		long balance = 0L;
		long debitLine = Long.parseLong(newAccountData.get(3));
		Integer interest = new Integer(newAccountData.get(4));
		try {
			if(typeDescription.equals("Saving account description")) {
				AbstractAccount newAccount = new SavingAccount(customer, number, typeDescription, statusName,
				 statusDescription, openDate, balance, debitLine,
				 interest);
				accountDaoSQLite.addAccount(newAccount);
			}
			if(typeDescription.equals("Credit account description")) {
				AbstractAccount newAccount = new CreditAccount(customer, number, typeDescription,
				 statusName, statusDescription, openDate, balance, debitLine, interest);
				accountDaoSQLite.addAccount(newAccount);
			}
		} catch (SQLException e){
			System.out.println(e);
		}
	}
}
