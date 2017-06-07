package dao;

import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


/**
 * Created by michal on 07.06.17.
 */
public class CustomerDaoSQLite implements CustomerDao {
	private JDBCSQLite database;
	private Connection connection;
	private ResultSet resultSet;

	CustomerDaoSQLite(JDBCSQLite database){
		this.database = database;
		this.connection = database.getConnection();
	}

	public void addCustomer(Customer customer) throws SQLException {
		Integer isActive;
		if(customer.getIsActive()){
			isActive = 1;
		} else {
			isActive = 0;
		}
		String addCustomerQuery = String.format("INSERT INTO Customers " +
		 "(FirstName, LastName, Login, Password, CreateDate, IsActive, LastLogin) VALUES('%s','%s','%s','%s'," +
		 "'%s', %d, '%s')", customer.getFirstName(), customer.getLastName(), customer.getLogin(),
		 customer.getPassword(), customer.getCreateDate().toString(), isActive,
		 customer.getLastLogin().toString());
		PreparedStatement addQuery = connection.prepareStatement(addCustomerQuery);
		database.executeDBModifyingQuery(addQuery);
	}

	public void updateCustomer(Customer customer) throws SQLException {
		Integer isActive;
		if(customer.getIsActive()){
			isActive = 1;
		} else {
			isActive = 0;
		}
		String updateCustomerQuery = String.format("UPDATE Customers " +
		  "SET FirstName = '%s', LastName = '%s', Login = '%s', Password = '%s', CreateDate = '%s'," +
		  "IsActive = %d, LastLogin = '%s'", customer.getFirstName(), customer.getLastName(), customer.getLogin(),
		 customer.getPassword(), customer.getCreateDate().toString(), isActive,
		 customer.getLastLogin().toString());
		PreparedStatement updateQuery = connection.prepareStatement(updateCustomerQuery);
		database.executeDBModifyingQuery(updateQuery);
	}

	public Customer find(Integer customerId) throws SQLException {
		Customer customer = null;
		Boolean isActive;
		String findCustomerQuery = String.format("SELECT * FROM Customers WHERE CustomerID = %d", customerId);
		PreparedStatement selectQuery = connection.prepareStatement(findCustomerQuery);
		resultSet = database.executeSelectQuery(selectQuery);
		LocalDate createDate = LocalDate.parse(resultSet.getString("CreateDate"));
		LocalDate lastLogin = LocalDate.parse(resultSet.getString("LastLogin"));
		if(resultSet.getInt("IsActive") == 1){
			isActive = true;
		} else {
			isActive = false;
		}
		return new Customer(resultSet.getInt("CustomerID"), resultSet.getString("FirstName"),
		 resultSet.getString("LastName"), resultSet.getString("Login"),
		 resultSet.getString("Password"), createDate, isActive,
		 lastLogin);
	}


}
