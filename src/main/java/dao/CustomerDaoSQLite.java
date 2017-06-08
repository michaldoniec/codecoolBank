package dao;

import model.Customer;
import model.exception.NoSuchCustomerInDatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerDaoSQLite extends CommonDBOperationsDaoSQLite implements CustomerDao {
	private JDBCSQLite database;
	private Connection connection;
	private ResultSet resultSet;

	public CustomerDaoSQLite(JDBCSQLite database){
		this.database = database;
		this.connection = database.getConnection();
	}

	public void addCustomer(Customer customer) throws SQLException {
		Integer isActive = checkIfActive(customer);
		String addCustomerQuery = String.format("INSERT INTO Customers " +
		 "(FirstName, LastName, Login, Password, CreateDate, IsActive, LastLogin) VALUES('%s','%s','%s','%s'," +
		 "'%s', %d, '%s')", customer.getFirstName(), customer.getLastName(), customer.getLogin(),
		 customer.getPassword(), customer.getCreateDate().toString(), isActive,
		 customer.getLastLogin().toString());
		modifyDatabase(addCustomerQuery);
	}

	public void updateCustomer(Customer customer) throws SQLException {
		Integer isActive = checkIfActive(customer);
		String updateCustomerQuery = String.format("UPDATE Customers " +
		  "SET FirstName = '%s', LastName = '%s', Password = '%s', CreateDate = '%s'," +
		  "IsActive = %d, LastLogin = '%s' WHERE CustomerID = %d", customer.getFirstName(), customer.getLastName(),
		 customer.getPassword(), customer.getCreateDate().toString(), isActive,
		 customer.getLastLogin().toString(), customer.getId());
		modifyDatabase(updateCustomerQuery);
	}

	public Customer findCustomerById(Integer customerId) throws NoSuchCustomerInDatabaseException {
		try {
			String findCustomerQuery = String.format("SELECT * FROM Customers WHERE CustomerID = %d", customerId);
			PreparedStatement selectQuery = connection.prepareStatement(findCustomerQuery);
			resultSet = database.executeSelectQuery(selectQuery);
			return convertDataToCustomerModel(resultSet);
		} catch (SQLException e){
			throw new NoSuchCustomerInDatabaseException("There is no customer with such id");
		}
	}

	public Customer findCustomerByLogin(String login) throws NoSuchCustomerInDatabaseException {
		try {
			Boolean isActive;
			String findCustomerQuery = String.format("SELECT * FROM Customers WHERE Login = '%s'", login);
			PreparedStatement selectQuery = connection.prepareStatement(findCustomerQuery);
			resultSet = database.executeSelectQuery(selectQuery);
			return convertDataToCustomerModel(resultSet);
		} catch (SQLException e){
			throw new NoSuchCustomerInDatabaseException("There is no customer with such login");
		}
	}

	private Integer checkIfActive(Customer customer) {
		if(customer.getIsActive()){
			return 1;
		} else {
			return 0;
		}
	}

//	private Customer convertDataToCustomerModel(ResultSet resultSet) throws SQLException {
//		Boolean isActive;
//		LocalDate createDate = LocalDate.parse(resultSet.getString("CreateDate"));
//		LocalDate lastLogin = LocalDate.parse(resultSet.getString("LastLogin"));
//
//		if(resultSet.getInt("IsActive") == 1) {
//			isActive = true;
//		} else {
//			isActive = false;
//		}
//
//		return new Customer(resultSet.getInt("CustomerID"), resultSet.getString("FirstName"),
//		 resultSet.getString("LastName"), resultSet.getString("Login"),
//		 resultSet.getString("Password"), createDate, isActive,
//		 lastLogin);
//	}

	private void modifyDatabase(String query) throws SQLException {
		PreparedStatement modifyDatabaseQuery = connection.prepareStatement(query);
		database.executeDBModifyingQuery(modifyDatabaseQuery);
	}
}
