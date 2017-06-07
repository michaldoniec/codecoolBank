package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal on 07.06.17.
 */
public class Customer {
	private Integer id;
	private String firstName;
	private String lastName;
	private String login;
	private String password;
	private LocalDate createDate;
	private Boolean isActive;
	private LocalDate lastLogin;
	private List<Account> accounts = new ArrayList<>();

	public Customer(Integer id, String firstName, String lastName, String login, String password, LocalDate createDate, Boolean isActive, LocalDate lastLogin) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.password = password;
		this.createDate = createDate;
		this.isActive = isActive;
		this.lastLogin = lastLogin;
	}

	public Customer(String firstName, String lastName, String login, String password, LocalDate createDate, Boolean isActive, LocalDate lastLogin) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.password = password;
		this.createDate = createDate;
		this.isActive = isActive;
		this.lastLogin = lastLogin;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public LocalDate getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDate lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Integer getId() {
		return id;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean active) {
		isActive = active;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public Boolean validateCustomer(String login, String password){
		if(validateLogin(login) && validatePassword(password)){
			return true;
		} else {
			return false;
		}
	}

	public void addAccount(Account account) {
		accounts.add(account);
	}

	public void removeAccount(Account account) throws NoSuchAccountException {
		Integer accountToRemoveId = account.getAccountId();
		Account accountToRemove = getAccountById(accountToRemoveId);
		if(accountToRemove == null){
			throw new NoSuchAccountException("There is no such account");
		}
		accounts.remove(getAccountById(accountToRemoveId));
	}

	public Account getAccountById(Integer accountId) {
		Account foundAccount = null;
		for(Account account : accounts) {
			if(account.getAccountId() == accountId){
				foundAccount = account;
				return foundAccount;
			}
		}
		return foundAccount;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	private Boolean validateLogin(String loginToValidate){
		if(login.equals(loginToValidate)){
			return true;
		} else {
			return false;
		}
	}

	private Boolean validatePassword(String passwordToValidate){
		if(password.equals(passwordToValidate)){
			return true;
		} else {
			return false;
		}
	}
}
