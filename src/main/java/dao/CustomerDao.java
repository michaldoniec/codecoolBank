package dao;

import model.Customer;
import model.exception.NoSuchCustomerInDatabaseException;

import java.sql.SQLException;

public interface CustomerDao {

	void addCustomer(Customer customer) throws SQLException;

	void updateCustomer(Customer customer) throws SQLException;

	Customer findCustomerById(Integer id) throws NoSuchCustomerInDatabaseException;

	Customer findCustomerByLogin(String login) throws  NoSuchCustomerInDatabaseException;

}
