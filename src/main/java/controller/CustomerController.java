package controller;
import java.time.LocalDate;
import java.util.List;
import model.AbstractAccount;
import model.Customer;

public class CustomerController {
    private Customer customer;

    public CustomerController(Customer customer) {
        this.customer = customer;
    }


    public static Customer addCustomer(List<String> newCustomerData) {
        String firstName = newCustomerData.get(0);
        String secondName = newCustomerData.get(1);
        String login = newCustomerData.get(2);
        String password = newCustomerData.get(3);
        LocalDate localDate = LocalDate.parse(newCustomerData.get(4));
        Boolean isActive = true;
        LocalDate lastLogin = LocalDate.parse(newCustomerData.get(5));

        Customer newCustomer = new Customer(firstName, secondName, login, password, localDate, isActive, lastLogin);
        return newCustomer;
    }


    public Boolean validateCustomer(String login, String password){
        if(validateLogin(login) && validatePassword(password)){
            return true;
        } else {
            return false;
        }
    }

    public void addAccount(AbstractAccount account) {
        customer.getAccounts().add(account);
    }


    public AbstractAccount getAccountById(Integer accountId) {
        AbstractAccount foundAccount = null;
        for(AbstractAccount account : customer.getAccounts())
            if (account.getAccountId() == accountId) {
                foundAccount = account;
                return foundAccount;
            }
        return foundAccount;
    }

    public List<AbstractAccount> getAccountsByCustomer(Customer customer) {
        return customer.getAccounts();
    }

    public void deactivateCustomer(Customer customer) {
        customer.setIsActive(false);
        for (AbstractAccount account : customer.getAccounts()) {
            account.setStatusName("Disabled account");
        }
    }

    private Boolean validateLogin(String loginToValidate){
        if(customer.getLogin().equals(loginToValidate)){
            return true;
        } else {
            return false;
        }
    }

    private Boolean validatePassword(String passwordToValidate){
        if(customer.getPassword().equals(passwordToValidate)){
            return true;
        } else {
            return false;
        }
    }
}
