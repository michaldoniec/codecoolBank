package model;

import java.time.LocalDate;


public abstract class AbstractAccount implements Account {
	private Integer id;
	private Customer customer;
	private String number;
	private String typeDescription;
	private String statusName;
	private String statusDescription;
	private LocalDate openDate;
	private long balance;
	private long debitLine;
	private Integer interest;

	AbstractAccount(Integer id, Customer customer, String number, String typeDescription,
	                       String statusName, String statusDescription, LocalDate openDate, long balance,
	                       long debitLine, Integer interest) {
		this.id = id;
		this.customer = customer;
		this.number = number;
		this.typeDescription = typeDescription;
		this.statusName = statusName;
		this.statusDescription = statusDescription;
		this.openDate = openDate;
		this.balance = balance;
		this.debitLine = debitLine;
		this.interest = interest;
	}

	public AbstractAccount(Customer customer, String number, String typeDescription, String statusName,
	                       String statusDescription, LocalDate openDate, long balance, long debitLine,
	                       Integer interest) {
		this.customer = customer;
		this.number = number;
		this.typeDescription = typeDescription;
		this.statusName = statusName;
		this.statusDescription = statusDescription;
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

	public String getAccountStatus() {
		return statusName;
	}

	public void setAccountStatus(String statusName, String statusDescription) {
		this.statusName = statusName;
		this.statusDescription = statusDescription;
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

	public String getTypeDescription() {
		return typeDescription;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public long getDebitLine() {
		return debitLine;
	}

	public Integer getInterest() {
		return interest;
	}
}
