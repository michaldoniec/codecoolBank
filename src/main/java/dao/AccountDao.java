package dao;

import model.AbstractAccount;
import model.exception.NoSuchAccountException;

import java.sql.SQLException;

public interface AccountDao {
	void addAccount(AbstractAccount account) throws SQLException;

	void updateAccount(AbstractAccount account) throws SQLException;

	AbstractAccount findAccountById(Integer accountId) throws NoSuchAccountException;
}
