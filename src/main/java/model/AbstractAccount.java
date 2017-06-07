package model;

import java.time.LocalDate;


public abstract class AbstractAccount implements Account {
	private Integer id;
	private Customer customer;
	private String number;
	private AccountType accountType;
	private AccountStatus accountStatus;
	private LocalDate openDate;
	private long balance;
	private long debitLine;
	private Integer interest;

	AbstractAccount(Integer id, Customer customer, String number, AccountType accountType,
	                       AccountStatus accountStatus, LocalDate openDate, long balance,
	                       long debitLine, Integer interest) {
		this.id = id;
		this.customer = customer;
		this.number = number;
		this.accountType = accountType;
		this.accountStatus = accountStatus;
		this.openDate = openDate;
		this.balance = balance;
		this.debitLine = debitLine;
		this.interest = interest;
	}

	AbstractAccount(Customer customer, String number, AccountType accountType,
	                       AccountStatus accountStatus, LocalDate openDate, long balance,
	                       long debitLine, Integer interest) {
		this.id = null;
		this.customer = customer;
		this.number = number;
		this.accountType = accountType;
		this.accountStatus = accountStatus;
		this.openDate = openDate;
		this.balance = balance;
		this.debitLine = debitLine;
		this.interest = interest;
	}

	public Customer getCustomer() {
		return customer;
	}

	public LocalDate getOpenDate() {
		return openDate;
	}

	public long getBalance() {
		return balance;
	}

	public Integer getAccountId() {
		return id;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public void withdraw(long amount) throws IllegalArgumentException {
		if(amount < 0){
			throw new IllegalArgumentException("Withdraw amount must be above 0");
		}
		balance -= amount;
	}

	public void deposit(long amount) throws IllegalArgumentException {
		if(amount < 0){
			throw new IllegalArgumentException("Deposit must be above 0");
		}
		balance += amount;
	}

	public String getNumber() {
		return number;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public long getDebitLine() {
		return debitLine;
	}

	public Integer getInterest() {
		return interest;
	}
}
