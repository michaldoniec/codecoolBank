package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class TestCustomer {
	private Customer customer;
	private LocalDate createDate;
	private LocalDate lastLogin;

	@BeforeEach
	public void createCustomer(){
		createDate = LocalDate.of(2017,1,1);
		lastLogin = LocalDate.of(2017, 2,2);
		customer = new Customer("Michal", "Abc", "abcd",
		 "21232f297a57a5a743894a0e4a801fc3",createDate, true, lastLogin);
	}

	@Test
	public void testIfConstructorWithoutIdCreateCorrectCustomer() {
		assertAll("Customer Constructor",
			 () -> assertEquals("Michal", customer.getFirstName()),
			 () -> assertEquals("Abc", customer.getLastName()),
			 () -> assertEquals(createDate, customer.getCreateDate()),
			 () -> assertEquals(lastLogin, customer.getLastLogin())
		);
	}

	@Test
	public void testIfConstructorWithIdCreateCorrectCustomer() {
		customer = new Customer(1,"Michal", "Abc", "abcd",
		 "abc", createDate, true, lastLogin);
		Integer correctCustomerId = 1;
		assertAll("Customer Constructor",
		 () -> assertEquals(correctCustomerId, customer.getId()),
		 () -> assertEquals("Michal", customer.getFirstName()),
		 () -> assertEquals("Abc", customer.getLastName()),
		 () -> assertEquals(createDate, customer.getCreateDate()),
		 () -> assertEquals(lastLogin, customer.getLastLogin())
		);
	}

	@Test
	public void testIfSetterChangeCustomerStatus() {
		customer.setIsActive(false);
		assertEquals(false, customer.getIsActive());
	}

	@Test
	public void testIfSetterChangeCustomerLastLoginDate() {
		lastLogin = LocalDate.of(2017, 3,3);
		customer.setLastLogin(lastLogin);
		assertEquals(lastLogin, customer.getLastLogin());
	}

}
