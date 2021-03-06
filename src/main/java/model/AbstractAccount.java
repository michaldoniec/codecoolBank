package model;

import java.time.LocalDate;

public abstract class AbstractAccount  {
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

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public Integer getAccountId() {
		return id;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public long getDebitLine() {
		return debitLine;
	}

	public Integer getInterest() {
		return interest;
	}


}
