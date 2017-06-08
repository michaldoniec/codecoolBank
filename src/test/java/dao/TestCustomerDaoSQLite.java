package dao;

import model.Customer;
import model.exception.NoSuchCustomerInDatabaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by michal on 07.06.17.
 */
public class TestCustomerDaoSQLite {
	private JDBCSQLite database;
	private CustomerDao customerDao;

	@BeforeEach
	public void setUp() throws SQLException, ClassNotFoundException, FileNotFoundException {
		database = new JDBCSQLite("src/test/resources/testBank.db");
		database.resetDatabase();
		customerDao = new CustomerDaoSQLite(database);
	}

	@AfterEach
	void tearDown() throws SQLException {
		database.closeDatabase();
	}

	@Test
	public void testIfFindReturnCorrectCustomer() throws SQLException, NoSuchCustomerInDatabaseException {
		Customer customer = customerDao.findCustomerById(1);
		Integer correctCustomerId = 1;
		LocalDate createDate = LocalDate.of(2017,1,1);
		LocalDate lastLogin = LocalDate.of(2017,3,3);
		assertAll("Customer",
		 () -> assertEquals(correctCustomerId, customer.getId()),
		 () -> assertEquals("Mike", customer.getFirstName()),
		 () -> assertEquals("Smith", customer.getLastName()),
		 () -> assertEquals(createDate, customer.getCreateDate()),
		 () -> assertEquals(lastLogin, customer.getLastLogin())
		);
	}

	@Test
	public void testIfExceptionIsThrowWhenIncorrectIDisGiven() {
		assertThrows(NoSuchCustomerInDatabaseException.class, () -> customerDao.findCustomerById(10));
	}

	@Test
	public void testIfAddNewCustomer() throws SQLException, NoSuchCustomerInDatabaseException {
		LocalDate createDate = LocalDate.of(2017,7,1);
		LocalDate lastLogin = LocalDate.of(2017, 8,2);
		Customer newCustomer = new Customer("Tom", "Abc", "ab",
		 "21232f297a57a5a743894a0e4a801fc3",createDate, true, lastLogin);
		customerDao.addCustomer(newCustomer);
		Customer customer = customerDao.findCustomerById(3);
		Integer correctCustomerId = 3;
		assertAll("New Customer",
		 () -> assertEquals(correctCustomerId, customer.getId()),
		 () -> assertEquals("Tom", customer.getFirstName()),
		 () -> assertEquals("Abc", customer.getLastName()),
		 () -> assertEquals(createDate, customer.getCreateDate()),
		 () -> assertEquals(lastLogin, customer.getLastLogin())
		);
	}

	@Test
	public void testIfUpdateCustomer() throws SQLException, NoSuchCustomerInDatabaseException {
		Customer customer = customerDao.findCustomerById(2);
		LocalDate newLastLogin = LocalDate.of(2019,1,1);
		customer.setLastLogin(newLastLogin);
		customerDao.updateCustomer(customer);
		Customer updatedCustomer = customerDao.findCustomerById(2);
		assertEquals(newLastLogin, updatedCustomer.getLastLogin());
	};
}
