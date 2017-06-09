package dao;

import model.AbstractAccount;
import model.exception.NoSuchAccountException;

import java.sql.SQLException;
import java.util.List;

public interface AccountDao {
	void addAccount(AbstractAccount account) throws SQLException;

	void updateAccount(AbstractAccount account) throws SQLException;

	AbstractAccount findAccountById(Integer accountId) throws NoSuchAccountException;

	List<AbstractAccount> findAccountsByCustomerId(Integer customerID) throws NoSuchAccountException;
}
