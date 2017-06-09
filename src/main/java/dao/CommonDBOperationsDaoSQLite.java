package dao;

import model.AbstractAccount;
import model.CreditAccount;
import model.Customer;
import model.SavingAccount;
import model.exception.NoSuchAccountException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public abstract class CommonDBOperationsDaoSQLite implements CommonDBOperationsDao {

    public AbstractAccount convertDataToAccountModel(ResultSet resultSet)
        throws SQLException, NoSuchAccountException {
        Integer accountId = resultSet.getInt("AccountID");
        Integer statusNameColumn = 19;
        Integer statusDescriptionColumn = 20;
        Integer typeIdColumn = resultSet.getInt(21);
        Integer savingAccount = 1;
        Integer creditAccount = 2;
        Integer typeDescriptionColumn = 23;
        Customer customer = convertDataToCustomerModel(resultSet);
        LocalDate openDate = LocalDate.parse(resultSet.getString("OpenDate"));
        long balance = Long.parseLong(resultSet.getString("Balance"));
        long debitLine = Long.parseLong(resultSet.getString("DebitLine"));
        Integer interest = resultSet.getInt("Interest");
        String number = resultSet.getString("Number");
        String statusName = resultSet.getString(statusNameColumn);
        String statusDescription = resultSet.getString(statusDescriptionColumn);
        String typeDescription = resultSet.getString(typeDescriptionColumn);

        if (typeIdColumn == savingAccount) {
            return new SavingAccount(accountId, customer, number, typeDescription, statusName,
                statusDescription, openDate, balance, debitLine, interest);
        }

        if (typeIdColumn == creditAccount) {
            return new CreditAccount(accountId, customer, number, typeDescription, statusName,
                statusDescription, openDate, balance, debitLine, interest);
        }

        throw new NoSuchAccountException("There is no such account");
    }

    public Customer convertDataToCustomerModel(ResultSet resultSet) throws SQLException {
        Boolean isActive;
        LocalDate createDate = LocalDate.parse(resultSet.getString("CreateDate"));
        LocalDate lastLogin = LocalDate.parse(resultSet.getString("LastLogin"));

        if (resultSet.getInt("IsActive") == 1) {
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
