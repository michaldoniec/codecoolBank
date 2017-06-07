package model;

import java.time.LocalDate;

public class SavingAccount extends AbstractAccount {
	private Integer id;
	private Customer customer;
	private String number;
	private String statusName;
	private String statusDescription;
	private String typeDescription;
	private LocalDate openDate;
	private long balance;
	private long debitLine;
	private Integer interest;


	public SavingAccount(Integer id, Customer customer, String number, String typeDescription, String statusName,
	 String statusDescription, LocalDate openDate, long balance,
	                       long debitLine, Integer interest) {

		super(id,customer, number, typeDescription, statusName, statusDescription, openDate, balance, debitLine,
		 interest);
	}

	public SavingAccount(Customer customer, String number, String typeDescription, String statusName,
	                     String statusDescription, LocalDate openDate, long balance,
	                     long debitLine, Integer interest) {

		super(customer, number, typeDescription, statusName, statusDescription, openDate, balance, debitLine,
		 interest);
	}
}
