package controller;

import dao.CustomerDao;
import dao.CustomerDaoSQLite;
import dao.JDBCSQLite;
import model.Customer;
import model.exception.NoSuchCustomerInDatabaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMainController {
	MainController mainController;
	JDBCSQLite database;
	CustomerDao customerDao;

		@BeforeEach
	public void setUp() throws SQLException, ClassNotFoundException, FileNotFoundException {
		JDBCSQLite.createDatabase("src/test/resources/testBank.db");
		database = JDBCSQLite.getDatabase();
		mainController = new MainController(database);
		customerDao = new CustomerDaoSQLite(database);
		database.resetDatabase();
	}

	@AfterEach
	public void tearDown() throws SQLException {
		database.closeDatabase();
	}

	@Test
	public void testIfGetCustomerByCorrectLoginAndPasswordGiveCorrectCustomer() {
		String correctLogin = "Abc";
		String correctPassword = "Def";
		Integer correctId = 1;
		Customer customer = mainController.getCustomerByLogin(correctLogin, correctPassword);
		assertAll("Customer",
		 () -> assertEquals(correctLogin, customer.getLogin()),
		 () -> assertEquals(correctPassword, customer.getPassword()),
		 () -> assertEquals(correctId, customer.getId()));
	}

	@Test
	public void testIfAddNewCustomer() throws NoSuchCustomerInDatabaseException{
		List<String> data = new ArrayList<>(
		 Arrays.asList("Jan", "Kowalski", "abcd", "123", "2017-01-01", "2017-01-01", "2017-01-01"));
		mainController.createCustomer(data);
		Customer customer = customerDao.findCustomerByLogin("abcd");
		assertAll("New Customer",
		 () -> assertEquals("Jan", customer.getFirstName()),
		 () -> assertEquals("Kowalski", customer.getLastName())
		);
	}


}
