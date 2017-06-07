package model;

import java.time.LocalDate;

/**
 * Created by michal on 07.06.17.
 */
public class SavingAccount extends AbstractAccount {
	private Integer id;
	private Customer customer;
	private String number;
	private AccountType accountType;
	private AccountStatus accountStatus;
	private LocalDate openDate;
	private long balance;
	private long debitLine;
	private Integer interest;


	public SavingAccount(Integer id, Customer customer, String number, AccountType accountType,  AccountStatus accountStatus, LocalDate openDate, long balance,
	                       long debitLine, Integer interest) {
		super(id,customer,number,accountType, accountStatus, openDate, balance, debitLine, interest);
	}

	public SavingAccount(Customer customer, String number, AccountType accountType,
	                       AccountStatus accountStatus, LocalDate openDate, long balance,
	                       long debitLine, Integer interest) {
		super(customer,number,accountType, accountStatus, openDate, balance, debitLine, interest);
	}
}
