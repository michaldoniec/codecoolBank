package dao;

import model.Customer;

import java.sql.SQLException;


public interface CustomerDao {

	void addCustomer(Customer customer) throws SQLException;

	void updateCustomer(Customer customer) throws SQLException;

	Customer find(Integer id) throws SQLException;

}
